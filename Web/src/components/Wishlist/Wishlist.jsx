import React, {useEffect, useState} from 'react'
import {useSelector} from "react-redux";
import axios from 'axios';
import {Link} from 'react-router-dom'

const Wishlist = () => {
    const [productIds, setProductIds] = useState([]);
    const [productDetails, setProductDetails] = useState([]);
    const currentUser = useSelector((state) => state.user.currentUser);

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


    return (
        <>
            <p className="text-center text-primary font-semibold text-xl sm:text-2xl mb-4">
                Wishlist
            </p>

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
        </>
    )
}
export default Wishlist