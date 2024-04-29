import React from "react";
import {setUser} from "../../redux/actions/userActions";
import {useDispatch} from "react-redux";
import Hero from "./Hero/Hero.jsx";
import Category from "./Category/Category.jsx";
import Category2 from "./Category/Category2.jsx";
import Services from "./Services/Services.jsx";
import Banner from "./Banner/Banner.jsx";
import Partners from "./Partners/Partners.jsx";
import Products from "./Products/Products.jsx";
import Blogs from "./Blogs/Blogs.jsx";
import Footer from "./Footer/Footer.jsx";
import Popup from "./Popup/Popup.jsx";
import Toast from "./Shared/Toast";
import Confirm from "./Popup/Confirm";
import headphone from "../../assets/hero/headphone.png";
import smartwatch2 from "../../assets/category/smartwatch2-removebg-preview.png";

import AOS from "aos";
import "aos/dist/aos.css";
import axios from "axios";
import {setCart} from "../../redux/actions/cartActions";

const BannerData = {
    discount: "30% OFF",
    title: "Fine Smile",
    date: "10 Jan to 28 Jan",
    image: headphone,
    title2: "Air Solo Bass",
    title3: "Winter Sale",
    title4: "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eaque reiciendis",
    bgColor: "#f42c37",
};

const BannerData2 = {
    discount: "30% OFF",
    title: "Happy Hours",
    date: "14 Jan to 28 Jan",
    image: smartwatch2,
    title2: "Smart Solo",
    title3: "Winter Sale",
    title4: "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eaque reiciendis",
    bgColor: "#2dcc6f",
};

// eslint-disable-next-line react/prop-types
const Home = ({ handleOrderPopup, orderPopup, handleConfirmPopup, confirmPopup}) => {
    const dispatch = useDispatch();
    React.useEffect(() => {
      const fetchUserAndCartItems = async () => {
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

          console.log(userResponse.data);
          console.log(cartItemsResponse.data);
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
          console.log(products);
          dispatch(setCart(products));
        } catch (error) {
          console.error('Error fetching data: ', error);
        }
      };

      fetchUserAndCartItems().then(() => {
      });

      AOS.init({
        duration: 800,
        easing: "ease-in-sine",
        delay: 100,
        offset: 100,
      });

      AOS.refresh();
    }, [dispatch]);

    return (
        <div className="bg-white dark:bg-gray-900 dark:text-white duration-200 overflow-hidden">
          <Toast duration={2000}></Toast>
          <Hero handleOrderPopup={handleOrderPopup} />
          <Category />
          <Category2 />
          <Services />
          <Banner data={BannerData} />
          <Products confirmPopup={confirmPopup} handleConfirmPopup={handleConfirmPopup}/>
          <Banner data={BannerData2} />
          <Blogs />
          <Partners />
          <Footer />
          <Popup orderPopup={orderPopup} handleOrderPopup={handleOrderPopup} />
          <Confirm confirmPopup={confirmPopup} handleConfirmPopup={handleConfirmPopup} />
        </div>
    );
};

export default Home;
