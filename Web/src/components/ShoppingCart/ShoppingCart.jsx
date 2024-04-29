import React from "react";
import AOS from "aos";
import {useDispatch, useSelector} from "react-redux";
import {AiOutlineFileExcel} from "react-icons/ai"
import {Link} from "react-router-dom";
import axios from "axios";
import {setUser} from "../../redux/actions/userActions";

const ShoppingCart = (handleOrderPopup) => {
  const current_user = useSelector(state => state.user.currentUser);
  const shoppingCart = useSelector(state => state.cart.shoppingCart);
  const shippingCost = shoppingCart.length > 0 ? 4.99 : 0.00;
  const dispatch = useDispatch();
  console.log(current_user)

  React.useEffect(() => {
    AOS.init({
      duration: 800,
      easing: "ease-in-sine",
      delay: 100,
      offset: 100,
    });
    AOS.refresh();
  }, []);

  const handleCheck = async () => {
    const checkoutUrl = 'https://buy.stripe.com/test_4gw6p8cEA8555nW7st';
    try {
        // const response = await axios.post('/api/checkout', {
        //   username: current_user.username,
        //   userEmail: current_user.email
        // });
        const response = await axios.get('/api/test_checkout');
        console.log('checkout successful:', response);
        window.location.href = response.data;
    } catch (error) {
        if (error.response) {
            console.error('checkout failed:', error.response.data);
        } else if (error.request) {
            console.error('No response:', error.request);
        } else {
            console.error('Error:', error.message);
        }
    }
  }
  const calculateSubtotal = () => {
    return shoppingCart.reduce((total, item) => {
      return total + (item.price * item.quantity);
    }, 0).toFixed(2);
  };
  const calculateTotal = () => {
    const subtotal = parseFloat(calculateSubtotal());
    return (subtotal + shippingCost).toFixed(2);
  };
  const handleQuantityChange = (item, delta) => {
    const newQuantity = item.quantity + delta;
    if (newQuantity > 0) {
      dispatch({
        type: 'UPDATE_QUANTITY',
        payload: { id: item.id, quantity: newQuantity }
      });
    } else {
      removeFromCart(item);
    }
  };
  const removeFromCart = (item) => {
    dispatch({
      type: 'REMOVE_FROM_CART',
      payload: item.id
    });
    const response = axios.delete(`/api/cart-items/${current_user.username}/${item.id}`);
    console.log(response)
  }

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-gray-900 pt-20">
      <h1 className="mb-10 text-center text-2xl font-bold dark:text-slate-500">Cart Items</h1>
      <div className="mx-auto max-w-5xl justify-center px-6 md:flex md:space-x-6 xl:px-0">
        <div className="rounded-lg md:w-2/3">
          {(shoppingCart.length === 0) && (
            <div data-aos="flip-left" data-aos-delay={100} className="dark:shadow-cyan-500/50 shadow-xl justify-center mb-6 rounded-lg dark:bg-gray-400 bg-white p-6 shadow-md sm:flex sm:justify-center">
                <div className="flex items-center text-center border rounded-lg h-96 dark:border-gray-700">
                  <div className="flex flex-col w-full px-16 mx-auto">
                    <div className="p-3 mx-auto dark:text-slate-600 rounded-full">
                      <AiOutlineFileExcel className="w-20 h-20"/>
                    </div>
                    <h1 className="mt-3 text-lg text-gray-800 dark:text-slate-600">No items found</h1>
                    <p className="mt-2 text-gray-500 dark:text-gray-700">You can browse “Shop” to add some items to your shopping cart.</p>
                    <div className="flex justify-center items-center mt-4 sm:mx-auto gap-x-3">
                      <Link to="/">
                        <button
                          className="flex items-center justify-center w-1/2 px-5 py-2 text-sm tracking-wide text-white transition-colors duration-200 bg-blue-500 rounded-lg shrink-0 sm:w-auto gap-x-2 hover:bg-blue-600 dark:hover:bg-blue-500 dark:bg-blue-600">
                          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                               stroke="currentColor" className="w-5 h-5">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                  d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"/>
                          </svg>
                          <span>Shopping now</span>
                        </button>
                      </Link>
                    </div>
                  </div>
                </div>
              </div>
          )}
          {(shoppingCart.length > 0) && shoppingCart.map((item, index) => (
            <div id="cart-detail" data-aos="flip-left" data-aos-delay={100} className="dark:shadow-cyan-500/50 shadow-xl justify-between mb-6 rounded-lg dark:bg-gray-400 bg-white p-6 shadow-md sm:flex sm:justify-start">
            <img
              src={item.img}
              alt="product-image" className="w-full rounded-lg sm:w-40"/>
            <div className="sm:ml-4 sm:flex sm:w-full sm:justify-between">
              <div className="mt-5 sm:mt-0">
                <h2 className="text-lg font-bold text-gray-900">{ item.title }</h2>
                <p className="mt-1 text-xs text-gray-700">Stock: 23</p>
              </div>
              <div className="mt-4 flex justify-between sm:space-y-16 sm:mt-0 sm:block sm:space-x-0">
                <div className="flex items-center border-gray-100">
                  <span onClick={() => handleQuantityChange(item, -1)}
                    className="cursor-pointer rounded-l bg-gray-100 py-1 px-3.5 duration-100 hover:bg-blue-500 hover:text-blue-50"> - </span>
                  <input className="h-8 w-8 border bg-white text-center text-xs outline-none" type="number" value={item.quantity}
                         min="1"/>
                  <span onClick={() => handleQuantityChange(item, 1)}
                    className="cursor-pointer rounded-r bg-gray-100 py-1 px-3 duration-100 hover:bg-blue-500 hover:text-blue-50"> + </span>
                </div>
                <div className="flex items-center space-x-4">
                  <p className="text-sm">unit price: ${item.price}</p>
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" onClick={() => removeFromCart(item)}
                       stroke="currentColor" className="h-5 w-5 cursor-pointer duration-150 hover:text-red-500">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"/>
                  </svg>
                </div>
              </div>
            </div>
          </div>
          ))}
        </div>
        <div data-aos="flip-left" data-aos-delay={100} className="dark:shadow-cyan-500/50 shadow-xl mt-6 h-full rounded-lg border dark:bg-gray-400 bg-white p-6 shadow-md md:mt-0 md:w-1/3">
          <div className="mb-2 flex justify-between">
            <p className="text-gray-700">Subtotal</p>
            <p className="text-gray-700">${calculateSubtotal()}</p>
          </div>
          <div className="flex justify-between">
            <p className="text-gray-700">Shipping</p>
            <p className="text-gray-700">${shippingCost}</p>
          </div>
          <hr className="my-4"/>
          <div className="flex justify-between">
            <p className="text-lg font-bold">Total</p>
            <div className="">
              <p className="mb-1 text-lg font-bold">${calculateTotal()} USD</p>
              <p className="text-sm text-gray-700">including tax</p>
            </div>
          </div>
          <button className="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600 disabled:cursor-not-allowed"
                  onClick={handleCheck} disabled={shoppingCart.length === 0}>
            Check out
          </button>
        </div>
      </div>
    </div>
  );
};

export default ShoppingCart;
