import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { styled } from '@mui/material/styles';
import axios from "axios";
import React, { useEffect, useState } from "react";
import { url } from "../../ServiceApi/ServiceApi";
import orderEmpty from '../../images/order_empty.jpg';
import Navbar from "../Navbar/Navbar";
import MyOrderCard from "./MyOrderCard";
const Img = styled('img')({
  margin: 'auto',
  display: 'block',
  maxWidth: '100%',
  maxHeight: '100%',
});
const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));
function MyOrders(cartCount1) {
  const [expanded, setExpanded] = React.useState(true);
  const handleExpandClick = () => {
    setExpanded(!expanded);
  };
  const [isTrue, setIsTrue] = useState();
  const [isTrue1, setIsTrue1] = useState("");
  const [order, setorder] = useState([]);
  const [totalAmount, setTotalAmount] = useState();
  const data = {
    "userId": localStorage.getItem("userId"),
    "orderStatus": "ACTIVE"
  };
  const [cartCount, setCartCount] = React.useState();
 const [item, setitem] = useState([]);
 const[dateTime, setDateTime] = useState();
 const[totalAmount1, setTotalAmount1] = useState(0);
  function orderlist() {
    axios.get(url+'order/'+ localStorage.getItem('userId')
    , {})
    .then(res => {
      if(res.data==0){
        setIsTrue(orderEmpty);
          setIsTrue1("Your order list is currently empty.");
      }
      setorder(res.data);
      setTotalAmount(res.data.totalAmount);
      for(var i = 0; i<order.length; i++){
          setitem(order[0].cart.item)
           setDateTime(order[0].dateTime);
          setTotalAmount1(order[0].totalAmount);
      }
    })
    .catch(e => {
      console.log(e);
    })
  }
  useEffect(() => {
    orderlist();
  }, []);
  axios.post(url+'order/cart/data', data
  , {})
  .then(res => {
    {
      setCartCount(res.data.item.length);
    }
  })
  .catch(e => {
    console.log(e.message);
  });
  const handleVariableChange = (receivedVariable) => {
    setDateTime(receivedVariable); 
    console.log(receivedVariable);
  };
  return (
    <div>
      <Navbar 
      itemCount2 = {cartCount}
      />
      <br />
      <br />
      <br />
      <h2 className="text-charcoal d-none d-sm-block">Your Orders</h2>
      <Card   style={{ marginBottom: '20px', maxWidth: '80%', marginLeft: '8%', backgroundColor:'#f1f2e1' }}>
      <CardHeader
         title="Your recent orders" >
      </CardHeader>
      <CardContent>
          <Typography>
            Thank you for placing an order with <b>Online mall</b>. We appreciate your business! Below, you will find the order details for your reference:

          </Typography>
      </CardContent>
      <CardActions disableSpacing>
        {}
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick} 
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
        { order.map((e, index) => ( 
<>
       <MyOrderCard onVariableChange={handleVariableChange} item={e.cart.item}  dateTime={e.dateTime} totalAmount={e.totalAmount}/>
        </>
))} 
        </CardContent>
      </Collapse>
    </Card>
      {}
      <div style={{display: "flex", alignItems:"center", justifyContent:'center', height:100, marginTop:70 }}>
           <img height={200}     src={isTrue}/>
           </div>
             <p style={{display: "flex", alignItems:"center", justifyContent:'center', paddingTop:59, fontSize:20}}><b>{isTrue1}</b></p>
    {}
    {}
            {}
  {}
    </div>
  )
}
export default MyOrders
