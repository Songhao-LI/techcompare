import { createStore, combineReducers } from 'redux';
import userReducer from './reducers/userReducer';
import cartReducer from './reducers/cartReducer';

const rootReducer = combineReducers({
    user: userReducer,
    cart: cartReducer
});

const store = createStore(rootReducer);

export default store;
