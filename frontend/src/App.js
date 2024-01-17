import { ThemeProvider, createTheme } from "@mui/material/styles";
import axios from "axios";
import React from "react";
import { Route, Routes } from 'react-router-dom';
import { url } from "./ServiceApi/ServiceApi";
import AddToCartIcon from "./component/Cart/AddToCartIcon";
import Category from "./component/Category/Category";
import MyOrders from "./component/Order/MyOrders";
import Product from "./component/Product/Product";
import RegistrationForm from "./component/RegisterForm/RegistrationForm";
import InActive from "./component/login/InActive";
import Login from "./component/login/Login";
 




export default function App() {

  const [cartCount, setCartCount] = React.useState();

  function getCartCount(){
    const data = {
      "userId": localStorage.getItem("userId"),
      "orderStatus": "ACTIVE"
    };
    axios.post(url+'order/cart/data', data
       , {})
        .then(res => {
   
          {
            
           setCartCount( res.data.item.length);
          }
  
  
        })
        .catch(e => {
  
          console.log(e.message);
  
        })
  }
  getCartCount();

const theme = createTheme({
  typography: {
   "fontFamily": `Saira`,
   "fontSize": '1.5 rem',
   "fontWeightLight": 300,
   "fontWeightRegular": 400,
   "fontWeight": 700
  }
});

  return (
    <ThemeProvider theme={theme}>
    <div className="App">
    <Routes>
      <Route  path="/" element={<Login/>} />
      <Route path="/register" element={<RegistrationForm/>} /> 
      <Route path="/home" element={<Product setCartCount={setCartCount} />} />
      <Route path="/category" element={<Category/>} />
      <Route path="/myorders" element={<MyOrders setCartCount={setCartCount}/>}/>
      <Route path="/mycart" element={<AddToCartIcon setCartCount={setCartCount}/>}></Route>
      <Route path="/noaccess" element={<InActive/>}></Route>
    </Routes>
    </div>
    </ThemeProvider>
  );
}

