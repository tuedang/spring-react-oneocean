import React from 'react'
import MapContainer from './MapContainer';
import axios from "axios";

export default class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            heatmapPositions: [],
        };

        this.handleOnMapBoundsChanged = this.handleOnMapBoundsChanged.bind(this);
        this.fetchResidents = this.fetchResidents.bind(this);
    }

    componentDidMount() {
        const initialData = {
            swLat: 37.77278296552943, swLng: -122.42425918579102,
            neLat: 37.77707612757211, neLng: -122.4146032333374
        };
        this.fetchResidents(initialData.swLat, initialData.swLng, initialData.neLat, initialData.neLng);
    }

    handleOnMapBoundsChanged(event) {
        this.fetchResidents(event.swLat, event.swLng, event.neLat, event.neLng);
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

    render() {
        return (
            <div className="container">
                <h1>Heatmap</h1>
                <div style={{position: 'relative', 'paddingBottom': '56.25%'}}>
                    <MapContainer onBoundsChanged={this.handleOnMapBoundsChanged}
                                  heatmapPositions={this.state.heatmapPositions}/>
                </div>
            </div>
        )
    }
}
