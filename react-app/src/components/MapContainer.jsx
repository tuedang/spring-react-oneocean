import React from 'react';
import {GoogleApiWrapper, HeatMap, InfoWindow, Map, Marker, Polyline} from 'google-maps-react';

const mapStyles = {
    width: '100%',
    height: '100%',
};

const vesselsStyles = [
    '#B000FA',
    '#0A00CD',
    '#0BC0AB',
    '#ABCF0B',
];

export class MapContainer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showingInfoWindow: false,  // Hides or shows the InfoWindow
            activeMarker: {},          // Shows the active marker upon click
            selectedPlace: {},          // Shows the InfoWindow to the selected place upon a marker,
            markers: [
                {
                    title: '',
                    name: '',
                    position: {lat: '', lng: ''}
                }
            ],
            clickedMap: '',
            boundsChanged: '',
            isForm: !this.props.heatmapPositions && !this.props.vesselPositions,
        };
        this.mapClicked = this.mapClicked.bind(this);
        this.handleBoundsChange = this.handleBoundsChange.bind(this);
    }

    onMarkerClick = (props, marker, e) =>
        this.setState({
            selectedPlace: props,
            activeMarker: marker,
            showingInfoWindow: true
        });

    onClose = props => {
        if (this.state.showingInfoWindow) {
            this.setState({
                showingInfoWindow: false,
                activeMarker: null
            });
        }
    };

    mapClicked(mapProps, map, clickEvent) {
        const {latLng} = clickEvent;
        const position = {
            lat: latLng.lat(),
            lng: latLng.lng()
        };

        this.setState(() => {
            return {
                markers: [
                    {
                        title: "",
                        name: "Vessels",
                        position: {lat: position.lat, lng: position.lng}
                    }
                ]
            };
        });

        this.props.onClickedMap(position);
    }

    handleBoundsChange(mapProps, map) {
        const boundsData = {
            swLat: map.getBounds().getSouthWest().lat(),
            swLng: map.getBounds().getSouthWest().lng(),
            neLat: map.getBounds().getNorthEast().lat(),
            neLng: map.getBounds().getNorthEast().lng(),
        }
        this.props.onBoundsChanged(boundsData);
    }

    render() {
        return (
            <Map
                google={this.props.google}
                zoom={13}
                clickableIcons={true}
                onDragend={!this.state.isForm ? this.handleBoundsChange : undefined}
                onZoomChanged={!this.state.isForm ? this.handleBoundsChange : undefined}
                onClick={this.state.isForm ? this.mapClicked : undefined}
                initialCenter={{
                    lat: 37.774929577713245,
                    lng: -122.41942048072815
                }}
                style={mapStyles}>
                {
                    this.props.heatmapPositions !== undefined ? <HeatMap
                        opacity={10}
                        positions={this.props.heatmapPositions}
                        position={this.props.heatmapPositions}
                        radius={20}
                    /> : null
                }
                {
                    Object.entries(this.props.vesselPositions).map(([vessel, positions]) => {
                        return positions.map((marker, index) => {
                            return (
                                <Marker
                                    key={index}
                                    title='{marker.title}'
                                    name={marker.name}
                                    time={marker.time}
                                    posx={marker.originPosition.x}
                                    posy={marker.originPosition.y}
                                    position={marker}
                                    onClick={this.onMarkerClick}
                                />
                            )
                        });
                    })
                }
                {
                    Object.entries(this.props.vesselPositions).map(([vessel, positions]) => {
                        return (
                            <Polyline
                                path={positions}
                                strokeColor={vesselsStyles[vessel]}
                                strokeOpacity={0.9}
                                strokeWeight={3} />
                        )
                    })
                }
                {this.state.markers.map((marker, index) => (
                    <Marker
                        key={index}
                        title={marker.title}
                        name={marker.name}
                        position={marker.position}
                        onClick={this.onMarkerClick}
                    />
                ))}
                <InfoWindow google={this.props.google}
                            marker={this.state.activeMarker}
                            visible={this.state.showingInfoWindow}
                            onClose={this.onClose}>
                    <div>
                        <h9>{this.state.selectedPlace.name}</h9>
                        <h6>{this.state.selectedPlace.time}</h6>
                        <h6>{this.state.selectedPlace.posx},{this.state.selectedPlace.posy}</h6>
                    </div>
                </InfoWindow>
            </Map>
        )
    }
}

export default GoogleApiWrapper({
    apiKey: process.env.REACT_APP_GOOGLE_API_KEY,
    libraries: ['visualization']
})(MapContainer);
