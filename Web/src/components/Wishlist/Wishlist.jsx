import React, {useEffect, useState} from 'react'
import {useSelector} from "react-redux";
import axios from 'axios';

const Wishlist = () => {
    const [productIds, setProductIds] = useState([]);
    const [productDetails, setProductDetails] = useState([]);
    const currentUser = useSelector((state) => state.user.currentUser);

    useEffect(() => {
        if(currentUser) {
            axios.get('/api/wishlist-items', {
                params: {
                    username: currentUser.username
                }
            }).then((response) => {
                setProductIds(response.data);
                console.log(response.data);
            }).catch((error) => {
                console.error("Error fetching wishlist items", error);
            });
        }
    }, [currentUser]);

    // useEffect(() => {
    //     productIds.forEach(product => {
    //             axios.get(`/api/products/${product.productId}`).then(response => {
    //                 setProductDetails(response.data);
    //                 console.log(response.data);
    //             }).catch((error) => {
    //                 console.error(`Error fetching details for product ${product.productId}`, error);
    //             });
    //         }
    //     )
    // }, [productIds]);

    useEffect(() => {
        if (productIds.length > 0) {
            // 创建一个请求数组
            const requests = productIds.map((product) =>
                axios.get(`/api/products/${product.productId}`)
            );

            // 并行发送所有请求并获取结果
            Promise.all(requests)
                .then((responses) => {
                    // 将所有响应的data属性映射到一个新数组中
                    const productsDetails = responses.map((response) => response.data);
                    setProductDetails(productsDetails); // 使用新数组更新状态
                })
                .catch((error) => {
                    console.error("Error fetching product details", error);
                });
        }
    }, [productIds]);


    return (
        <>
            <p>123</p>
            <div>
                {productDetails.map((product, index) => (
                    <div key={index}>
                        <h2>{product.title}</h2>
                        <p>{product.description}</p>
                        <p>{product.price}</p>
                    </div>
                ))}
            </div>
        </>
    )
}
export default Wishlist