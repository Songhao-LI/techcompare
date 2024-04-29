import React from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import * as L from 'leaflet';

L.Icon.Default.imagePath='images/'

const StoreMap = ({ stores }) => {
    const validStores = stores.filter(store => store.lat !== null && store.lon !== null);

    if (validStores.length === 0) {
        return <p>No stores available on the map.</p>; // Or handle this scenario as needed
    }

    return (
        <MapContainer center={[40.7128, -74.0060]} zoom={12} style={{height: '400px', width: '100%'}}>
            <TileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution="&copy; OpenStreetMap contributors"
            />
            {validStores.map(store => (
                <Marker key={store.id} position={[parseFloat(store.lat), parseFloat(store.lon)]}>
                    <Popup>{store.location}</Popup>
                </Marker>
            ))}
        </MapContainer>
    );
};

export default StoreMap;