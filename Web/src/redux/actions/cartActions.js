// Action Creators
export const setCart = cart => ({
  type: 'SET_CART',
  payload: cart
});

export const addToCart = item => ({
  type: 'ADD_TO_CART',
  payload: item
});

export const removeFromCart = itemId => ({
  type: 'REMOVE_FROM_CART',
  payload: itemId
});

export const updateQuantity = (itemId, quantity) => ({
  type: 'UPDATE_QUANTITY',
  payload: { id: itemId, quantity }
});

export const clearCart = () => ({
  type: 'CLEAR_CART'
});
