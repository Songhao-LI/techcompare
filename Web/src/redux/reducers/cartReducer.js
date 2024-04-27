const initialState = {
  shoppingCart: []
};

const cartReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_CART':
      return {
        ...state,
        shoppingCart: action.payload  // 这里假设 payload 是一个完整的购物车数组
      };
    case 'ADD_TO_CART':
      const itemIndex = state.shoppingCart.findIndex(item => item.id === action.payload.id);
      if (itemIndex >= 0) {
        // 商品已存在，更新数量
        let newCart = [...state.shoppingCart];
        newCart[itemIndex].quantity += action.payload.quantity;
        return {
          ...state,
          shoppingCart: newCart
        };
      } else {
        // 商品不存在，添加新商品
        return {
          ...state,
          shoppingCart: [...state.shoppingCart, action.payload]
        };
      };
    case 'REMOVE_FROM_CART':
      return {
        ...state,
        shoppingCart: state.shoppingCart.filter(item => item.id !== action.payload)
      };
    case 'UPDATE_QUANTITY':
      return {
        ...state,
        shoppingCart: state.shoppingCart.map(item =>
          item.id === action.payload.id ? { ...item, quantity: action.payload.quantity } : item
        )
      };
    case 'CLEAR_CART':
      // 重置购物车为初始状态
      return {
        ...state,
        shoppingCart: []
      };
    default:
      return state;
  }
};

export default cartReducer;
