import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Link, useLocation} from "react-router-dom";

const Compare = () => {
    const location = useLocation();
    const {selectedProductIds} = location.state || {};
    const [products, setProducts] = useState([]);

    useEffect(() => {
        console.log(selectedProductIds);
        const fetchProducts = async () => {
            const fetchedProducts = [];
            for (const id of selectedProductIds) {
                try {
                    const response = await axios.get(`/api/products/${id}`);

                    fetchedProducts.push(response.data);
                } catch (error) {
                    console.error(`Failed to fetch product with ID ${id}`, error);
                }
            }
            setProducts(fetchedProducts);
            console.log(fetchedProducts);
        };

        fetchProducts();
    }, [selectedProductIds]);


    return (
        <>
            <p className="text-center text-primary font-semibold text-xl sm:text-2xl mb-4">
                Products Comparison
            </p>
            <Link to={`/Wishlist`}>
                <button
                    className="bg-primary hover:bg-primary-dark text-white font-bold py-2 px-4 rounded transition duration-300 ease-in-out"
                >
                    Return
                </button>
            </Link>
            <div className="container px-5 py-24 mx-auto flex flex-wrap">
                <div className="lg:w-1/4 mt-48 hidden lg:block">
                    <div
                        className="mt-px border-t border-gray-300 border-b border-l rounded-tl-lg rounded-bl-lg overflow-hidden">
                        <p className="bg-red-600 text-white h-12 text-center px-4 flex items-center justify-start -mt-px">Title</p>
                        <p className="text-gray-900 h-12 text-center px-4 flex items-center justify-start">Category</p>
                        <p className="bg-red-600 text-white h-12 text-center px-4 flex items-center justify-start">Price</p>
                        <p className="text-gray-900 h-12 text-center px-4 flex items-center justify-start">Rating</p>
                        <p className="bg-red-600 text-white h-12 text-center px-4 flex items-center justify-start">Details</p>
                    </div>
                </div>
                <div className="flex lg:w-3/4 w-full flex-wrap lg:border border-gray-300 rounded-lg">
                    {products.map((product, index) => (
                        <div key={index} className="lg:w-1/3 w-full mb-10 lg:mb-0 border-2 border-gray-300 rounded-lg">
                            <div
                                className="px-2 text-center h-48 flex flex-col items-center justify-center img-container">
                                <img src={product.image} alt={product.title} className="w-full h-48 object-cover"/>
                            </div>
                            <p className="bg-red-600 text-white h-12 text-center px-2 flex items-center justify-center border-t border-gray-300">{product.title}</p>
                            <p className="text-gray-600 text-center h-12 flex items-center justify-center">{product.category}</p>
                            <p className="bg-red-600 text-white text-center h-12 flex items-center justify-center">${product.price}</p>
                            <p className="text-gray-600 text-center h-12 flex items-center justify-center">{product.rating.rate}</p>
                            <Link to={`/ProductDetail/${product.id}`}>
                                <a href="#"
                                   className="bg-red-600 text-white text-center h-12 flex items-center justify-center">See
                                    More Details</a>
                            </Link>
                            {/*<p class="bg-gray-100 text-gray-600 text-center h-12 flex items-center justify-center">{product.description}</p>*/}
                        </div>
                    ))}
                </div>
            </div>
        </>

    );
};

export default Compare;
