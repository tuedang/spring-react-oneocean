import React from 'react'
import MapContainer from './MapContainer';
import axios from "axios";

export default class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            heatmapPositions: [],
            vesselPositions: [],
        };

        this.handleOnMapBoundsChanged = this.handleOnMapBoundsChanged.bind(this);
        this.fetchResidents = this.fetchResidents.bind(this);
        this.fetchVessels = this.fetchVessels.bind(this);
    }

    componentDidMount() {
        const initialData = {
            swLat: 37.77278296552943, swLng: -122.42425918579102,
            neLat: 37.77707612757211, neLng: -122.4146032333374
        };
        this.fetchResidents(initialData.swLat, initialData.swLng, initialData.neLat, initialData.neLng);
        this.fetchVessels(initialData.swLat, initialData.swLng, initialData.neLat, initialData.neLng);
    }

    handleOnMapBoundsChanged(event) {
        this.fetchResidents(event.swLat, event.swLng, event.neLat, event.neLng);
        this.fetchVessels(event.swLat, event.swLng, event.neLat, event.neLng);
    }

    fetchResidents(swLat, swLng, neLat, neLng) {
        const params = {swLat, swLng, neLat, neLng};
        axios.get('http://localhost:8080/api/residence/filterBounds', {params}).then(response => {
            const positions = response.data.map(x => {
                return {lat: x.latitude, lng: x.longitude, weight: x.qtResidents}
            })
            this.setState({heatmapPositions: positions});
        })
    }

    fetchVessels(swLat, swLng, neLat, neLng) {
        const params = {swLat, swLng, neLat, neLng};
        axios.get('http://localhost:8080/api/vessels/group', {params}).then(response => {
            const data = response.data;
            const vessels = Object.entries(data).map(([vessel, positions]) => {
                return positions.map(x => {
                    return {lat: x.coordinate.y, lng: x.coordinate.x}
                });
            });
            this.setState({vesselPositions: vessels});
        })
    }

    render() {
        return (
            <div className="container">
                <h1>Vessel Map</h1>
                <div style={{position: 'relative', 'paddingBottom': '56.25%'}}>
                    <MapContainer onBoundsChanged={this.handleOnMapBoundsChanged}
                                  vesselPositions={this.state.vesselPositions}
                                  heatmapPositions={this.state.heatmapPositions}/>
                </div>
            </div>
        )
    }
}
