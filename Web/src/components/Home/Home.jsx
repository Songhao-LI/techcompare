import React, {useEffect} from "react";
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
import headphone from "../../assets/hero/headphone.png";
import smartwatch2 from "../../assets/category/smartwatch2-removebg-preview.png";

import AOS from "aos";
import "aos/dist/aos.css";
import axios from "axios";
import {setUser} from "../../redux/actions/userActions";
import {useDispatch} from "react-redux";
import Toast from "./Shared/Toast";

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
const Home = ({ handleOrderPopup, orderPopup}) => {
    const dispatch = useDispatch();
    React.useEffect(() => {
      axios.get('/api/user/me')
        .then(response => {
          console.log(response.data);
          dispatch(setUser({
            username: response.data.principal.fullName,
            email: response.data.principal.email,
            phoneNumber: response.data.principal.phoneNumber
          }));
        })
        .catch(error => {
          console.error('Error fetching data: ', error);
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
          <Products />
          <Banner data={BannerData2} />
          <Blogs />
          <Partners />
          <Footer />
          <Popup orderPopup={orderPopup} handleOrderPopup={handleOrderPopup} />
        </div>
    );
};

export default Home;
