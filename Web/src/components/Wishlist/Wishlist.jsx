import React, {useEffect, useState} from 'react'
import {useSelector} from "react-redux";
import axios from 'axios';
import {Link} from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const Wishlist = () => {
    const [productIds, setProductIds] = useState([]);
    const [productDetails, setProductDetails] = useState([]);
    const currentUser = useSelector((state) => state.user.currentUser);
    const [selectedProducts, setSelectedProducts] = useState(new Set());
    const navigate = useNavigate();

    useEffect(() => {
        if (currentUser) {
            axios.get('/api/wishlist-items', {
                params: {
                    username: currentUser.username
                }
            }).then((response) => {
                // setProductIds(response.data);
                // console.log(response.data);
                const uniqueProductIds = new Set(response.data.map(item => item.productId));
                setProductIds([...uniqueProductIds]); // 转换Set为Array并更新状态
                console.log([...uniqueProductIds]); // 打印去重后的ID数组
            }).catch((error) => {
                console.error("Error fetching wishlist items", error);
            });
        }
    }, [currentUser]);


    useEffect(() => {
        // 使用 Promise.all 并发请求所有产品的详细信息
        Promise.all(productIds.map(productId => axios.get(`/api/products/${productId}`)))
            .then(responses => {
                // 使用 .map() 获取所有响应的数据部分
                const products = responses.map(response => response.data);
                console.log(products);
                setProductDetails(products);
            })
            .catch(error => {
                console.error("Error fetching product details", error);
            });
    }, [productIds]);

    const handleSubmit = (event) => {
        event.preventDefault();
        const selectedProductIds = Array.from(selectedProducts);
        navigate('/Compare', { state: { selectedProductIds } });

        console.log(Array.from(selectedProducts));
    };

    const handleChange = (productId) => {
        setSelectedProducts((prevSelected) => {
            const newSelection = new Set(prevSelected);
            if (newSelection.has(productId)) {
                newSelection.delete(productId);
            } else if (newSelection.size < 3) {
                newSelection.add(productId);
            }
            return newSelection;
        });
    };

    return (
        <>
            <p className="text-center text-primary font-semibold text-xl sm:text-2xl mb-4">
                Wishlist
            </p>

            <form onSubmit={handleSubmit}>
                <div className="flex justify-end">

                    <button
                        type="submit"
                        className="bg-primary hover:bg-primary-dark text-white font-bold py-2 px-4 rounded transition duration-300 ease-in-out"
                    >
                        Compare
                    </button>
                </div>
                <br/>
                {productDetails.map((product, index) => (
                    <div key={index}
                         className="rounded-3xl border-2 border-gray-200 p-4 lg:p-8 grid grid-cols-12 mb-8 max-lg:max-w-lg max-lg:mx-auto gap-y-4 ">
                        <div className="col-span-12 lg:col-span-2 img box">
                            <img src={product.image} alt="Product Image" className="max-lg:w-full lg:w-[180px] "/>
                        </div>
                        <div className="col-span-12 lg:col-span-10 detail w-full lg:pl-3">
                            <div className="flex items-center justify-between w-full mb-4">
                                <h5 className="font-manrope font-bold text-2xl leading-9 text-gray-900">
                                    {product.title}
                                </h5>
                                <input
                                    type="checkbox"
                                    value={product.id}
                                    onChange={() => handleChange(product.id)}
                                    checked={selectedProducts.has(product.id)}
                                />
                            </div>
                            <p className="font-normal text-base leading-7 text-gray-500 mb-6">
                                {product.description}
                                <br/>
                                <Link to={`/ProductDetail/${product.id}`}>
                                    <a href="#" className="text-indigo-600">More....</a>
                                </Link>
                            </p>
                            <div className="flex justify-between items-center">
                                <h6 className="text-indigo-600 font-manrope font-bold text-2xl leading-9 text-right">
                                    ${product.price}
                                </h6>
                            </div>
                        </div>
                    </div>
                ))}

            </form>
        </>
    )
}
export default Wishlist