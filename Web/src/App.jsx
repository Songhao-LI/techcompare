import React, { useEffect } from "react";
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
import Compare from "./components/Compare/Compare.jsx";
import SearchResult from "./components/Search/SearchResult";
import AOS from "aos";
import axios from "axios";
import {setUser} from "./redux/actions/userActions";
import {setCart} from "./redux/actions/cartActions";

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

  const dispatch = useDispatch();
  React.useEffect(() => {
    const init = async () => {
      try {
        const userResponse = await axios.get('/api/user/me');

        dispatch(setUser({
          username: userResponse.data.principal.userInfo.claims.username,
          email: userResponse.data.principal.email,
          password: userResponse.data.principal.password,
          phoneNumber: userResponse.data.principal.phoneNumber
        }));

        const username = userResponse.data.principal.userInfo.claims.username;
        const cartItemsResponse = await axios.get('/api/cart-items', {
          params: { username: username }
        });

        const productDetailsPromises = cartItemsResponse.data.map(item =>
          axios.get(`/api/products/${item.productId}`)
        );

        const productDetailsResponses = await Promise.all(productDetailsPromises);
        const products = productDetailsResponses.map((response, index) => ({
          id: cartItemsResponse.data[index].productId,
          quantity: cartItemsResponse.data[index].quantity,
          img: response.data.image,
          title: response.data.title,
          price: response.data.price
        }));
        dispatch(setCart(products));
      } catch (error) {
        console.error('Error fetching data: ', error);
      }
    };
    init().then(() => {
    });

    AOS.init({
      duration: 800,
      easing: "ease-in-sine",
      delay: 100,
      offset: 100,
    });

    AOS.refresh();
  }, []);

    return (
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
              <Route path="/Compare" element={<Compare/>}/>
              <Route path="/SearchResult" element={<SearchResult/>}/>
              {/* 其他路由 */}
          </Routes>
      </HashRouter>
    );
};

export default App;
