import React from 'react';
import {GoogleApiWrapper, HeatMap, InfoWindow, Map, Marker} from 'google-maps-react';

const mapStyles = {
    width: '100%',
    height: '100%',
};

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
            isForm: !this.props.heatmapPositions,
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
                        name: "Residence",
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
                zoom={17}
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
                        position={this.props.heatmapPositions} // fixme: bug with HeatMap component
                        radius={20}
                    /> : null
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
                        <h4>{this.state.selectedPlace.name}</h4>
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
