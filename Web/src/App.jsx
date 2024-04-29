import React, { useEffect } from "react";
import store from './redux/store';
import {BrowserRouter, Routes, Route, HashRouter} from "react-router-dom";
import { Provider, useDispatch } from 'react-redux';
import Home from "./components/Home/Home.jsx";
import FilterProducts from "./components/FilterProducts/FilterProducts.jsx";
import Navbar from "./components/Home/Navbar/Navbar.jsx";
import Register from "./components/Home/Popup/Register.jsx";
import Login from "./components/Home/Popup/Login.jsx";
import ProductDetail from "./components/ProductDetail/ProductDetail.jsx";
import ShoppingCart from "./components/ShoppingCart/ShoppingCart";
import Wishlist from "./components/Wishlist/Wishlist.jsx";

const App = () => {
  const [orderPopup, setOrderPopup] = React.useState(false);
  const [confirmPopup, setConfirmPopup] = React.useState(false);
  const [loginPopup, setLoginPopup] = React.useState(false);
  const [registerPopup, setRegisterPopup] = React.useState(false);

  const handleOrderPopup = () => {
    setOrderPopup(!orderPopup);
  };
  const handleLoginPopup = () => {
    setLoginPopup(!loginPopup);
  };
  const handleRegisterPopup = () => {
    setRegisterPopup(!registerPopup);
  };
  const handleConfirmPopup = () => {
    setConfirmPopup(!confirmPopup)
  }

    return (
        <Provider store={store}>
            <HashRouter>
                <Navbar handleOrderPopup={handleOrderPopup} handleLoginPopup={handleLoginPopup}/>
                <Register registerPopup={registerPopup} handleRegisterPopup={handleRegisterPopup}/>
                <Login loginPopup={loginPopup} handleLoginPopup={handleLoginPopup}
                       handleRegisterPopup={handleRegisterPopup}/>
                <Routes>
                    <Route path="/" element={<Home orderPopup={orderPopup} handleOrderPopup={handleOrderPopup} confirmPopup={confirmPopup} handleConfirmPopup={handleConfirmPopup}/>}/>
                    <Route path="/FilterProducts" element={<FilterProducts/>}/>
                    <Route path="/ShoppingCart" element={<ShoppingCart handleOrderPopup={handleOrderPopup}/>}/>
                    <Route path="/ProductDetail/:productId" element={<ProductDetail/>}/>
                    <Route path="/Wishlist" element={<Wishlist/>}/>
                    {/* 其他路由 */}
                </Routes>
            </HashRouter>
        </Provider>
    );
};

export default App;
