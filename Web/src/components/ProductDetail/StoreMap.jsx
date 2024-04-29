import React from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import * as L from 'leaflet';

L.Icon.Default.imagePath='images/'

const StoreMap = ({ stores }) => (
    <MapContainer center={[40.7128, -74.0060]} zoom={12} style={{ height: '400px', width: '100%' }}>
        <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution="&copy; OpenStreetMap contributors"
        />
        {stores.map(store => (
            <Marker key={store.id} position={[parseFloat(store.lat), parseFloat(store.lon)]}>
                <Popup>{store.location}</Popup>
            </Marker>
        ))}
    </MapContainer>
);

export default StoreMap;