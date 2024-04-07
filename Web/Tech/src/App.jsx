import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./components/Home.jsx";
import ProductsDetail from "./components/ProductsDetail/ProductsDetail.jsx";

const App = () => {
    const [orderPopup, setOrderPopup] = React.useState(false);

    const handleOrderPopup = () => {
        setOrderPopup(!orderPopup);
    };

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home orderPopup={orderPopup} handleOrderPopup={handleOrderPopup} />} />
                <Route path="/blog" element={<ProductsDetail/>} />
                {/* 其他路由 */}
            </Routes>
        </BrowserRouter>
    );
};

export default App;
