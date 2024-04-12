import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./components/Home/Home.jsx";
import ProductsDetail from "./components/ProductsDetail/ProductsDetail.jsx";
import Navbar from "./components/Home/Navbar/Navbar.jsx";

const App = () => {
    const [orderPopup, setOrderPopup] = React.useState(false);
    const [loginPopup, setLoginPopup] = React.useState(false);

    const handleOrderPopup = () => {
        setOrderPopup(!orderPopup);
    };
    const handleLoginPopup = () => {
        setLoginPopup(!loginPopup);
    };

    return (
        <BrowserRouter>
            <Navbar handleOrderPopup={handleOrderPopup} handleLoginPopup={handleLoginPopup}/>
            <Routes>
                <Route path="/" element={<Home orderPopup={orderPopup} handleOrderPopup={handleOrderPopup} loginPopup={loginPopup} handleLoginPopup={handleLoginPopup} />} />
                <Route path="/ProductsDetail" element={<ProductsDetail/>} />
                {/* 其他路由 */}
            </Routes>
        </BrowserRouter>
    );
};

export default App;
