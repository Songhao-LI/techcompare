import React, {useState, useEffect} from 'react';

const ProductReviews = ({productId}) => {
    const [reviews, setReviews] = useState([]);
    useEffect(() => {
            fetch(`/api/products/${productId}/reviews`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    setReviews(data)
                })
        }, [productId]
    );
    return (
        <>
            <br/>
            <p className="text-center text-primary font-semibold text-xl sm:text-2xl mb-4">
                Reviews
            </p>

            <div className="flex justify-center">
                <div
                    className="relative grid grid-cols-1 gap-4 p-4 mb-8 border rounded-lg bg-white shadow-lg review-container"
                    style={{width: '80%'}}> {/* 可以在这里调整宽度 */}
                    {reviews.length > 0 ? (
                        reviews.map((review, index) => (
                            <div key={index} className="relative flex gap-4">
                                <div className="flex flex-col w-full">
                                    <div className="flex flex-row justify-between">
                                        <p className="relative text-l whitespace-nowrap text-gray-500">{review.username}</p>
                                    </div>
                                    <div className="flex flex-col justify-between">
                                        <p className="text-black text-l review-rating">Rating: {review.rate}</p>
                                    </div>
                                    <br/>
                                    <div className="flex flex-col justify-between">
                                        <p className="-mt-4 text-xl text-black review-comment">
                                            "{review.comment}"</p>
                                    </div>

                                </div>
                            </div>
                        ))
                    ) : (
                        <p className="uppercase">No reviews yet</p>
                    )}
                </div>
            </div>

        </>
    )
};

export default ProductReviews