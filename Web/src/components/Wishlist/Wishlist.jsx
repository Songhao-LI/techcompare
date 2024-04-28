import React, {useEffect, useState} from 'react'
import {useSelector} from "react-redux";
import {useParams} from 'react-router-dom';
import axios from 'axios';

const Wishlist = () => {
    // const [productIds, setProductIds] = useState([]);
    // const [productDetails, setProductDetails] = useState();
    // const {productId} = useParams();
    // const currentUser = useSelector((state) => state.user.currentUser);

    // add item to wishlist
    // useEffect(() => {
    //     if (currentUser && productId) {
    //         axios.post('/api/wishlist-items', {
    //             username: currentUser.username,
    //             productId: productId,
    //         }).then(response => {
    //             console.log('Item added to wishlist', response.data);
    //         }).catch((error) => {
    //             console.error("Error adding product to wishlist", error);
    //         });
    //     }
    // }, [currentUser, productId]);
    // useEffect(() => {
    //     if(currentUser) {
    //         axios.get('/api/wishlist-items', {
    //             params:{username: currentUser.username}
    //         }).then((response) => {
    //             setProductIds(response.data);
    //         }).catch((error) => {
    //             console.error("Error fetching wishlist items", error);
    //         });
    //     }
    // }, [currentUser]);
    //
    // useEffect(() => {
    //     productIds.forEach(id => {
    //             axios.get(`/api/products/${id}`).then(response => {
    //                 setProductDetails(response.data);
    //             }).catch((error) => {
    //                 console.error(`Error fetching details for product ${id}`, error);
    //             });
    //         }
    //     )
    // }, [productIds]);

    return (
        <>
            <p>123</p>
            {/*<div>*/}
            {/*    {productDetails.map((product, index) => (*/}
            {/*        <div key={index}>*/}
            {/*            <h2>{product.title}</h2>*/}
            {/*            <p>{product.description}</p>*/}
            {/*            <p>${product.price}</p>*/}
            {/*        </div>*/}
            {/*    ))}*/}
            {/*</div>*/}
        </>
    )
}
export default Wishlist