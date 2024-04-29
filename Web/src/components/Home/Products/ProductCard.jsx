import React from "react";
import Button from "../Shared/Button.jsx";
import {useDispatch, useSelector} from "react-redux";
import {addToCart, updateQuantity} from "../../../redux/actions/cartActions";
import axios from "axios";

const ProductCard = ({ confirmPopup, handleConfirmPopup, data }) => {
  const dispatch = useDispatch();
  const current_user = useSelector(state => state.user.currentUser);
  const shoppingCart = useSelector(state => state.cart.shoppingCart);

  const addToCartHandler = async (item) => {
    // Check if the item is already in the cart
    const existingItem = shoppingCart.find(cartItem => cartItem.id === item.id);
    console.log(existingItem)

    if (existingItem) {
      // Item exists, so update its quantity
      const newQuantity = existingItem.quantity + 1;
      try {
        const response = await axios.put(`/api/cart-items/${current_user.username}/${item.id}`, {
          quantity: newQuantity
        });
        console.log('Updated cart item successfully:', response.data);
        dispatch(updateQuantity({...existingItem, quantity: newQuantity}));
      } catch (error) {
        console.error('Error updating cart item:', error);
      }
    } else {
      // Item does not exist, so add new item
      const child = {
        id: item.id,
        quantity: 1,
        img: item.img,
        title: item.title,
        price: item.price
      };
      try {
        const response = await axios.post('/api/cart-items', {
          username: current_user.username,
          productId: child.id,
          quantity: 1
        });
        console.log('Added to cart successfully:', response.data);
        dispatch(addToCart(child));
      } catch (error) {
        console.error('Error adding to cart:', error);
      }
    }
    handleConfirmPopup();
  }

  return (
    <div className="mb-10">
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-5 place-items-center">
        {/* card section */}
        {data.map((item) => (
          <div
            data-aos="fade-up"
            data-aos-delay={item.aosDelay}
            className="group"
            key={item.id}
          >
            <div className="relative">
              <img
                src={item.img}
                alt={item.title}
                className="h-[180px] w-[260px] object-cover rounded-md"
              />
              {/* hover button */}
              <div className="hidden group-hover:z-50 group-hover:flex absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2 h-full w-full text-center group-hover:backdrop-blur-sm justify-center items-center duration-200 rounded-md">
                <Button className="z-50" handler={() => addToCartHandler(item)}
                  text={"Add to cart"}
                  bgColor={"bg-primary"}
                  textColor={"text-white"}
                />
              </div>
            </div>
            <div className="leading-7">
              <h2 className="font-semibold">{item.title}</h2>
              <h2 className="font-bold">${item.price}</h2>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductCard;
