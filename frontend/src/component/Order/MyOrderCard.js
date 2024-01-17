import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { IconButton } from '@mui/material';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import Typography from '@mui/material/Typography';
import { styled } from '@mui/material/styles';
import PropTypes from 'prop-types';
import React from 'react';
import { Col, Row } from 'react-bootstrap';
import '../Order/CSS/order.css';
import ProductReviews from '../Product/ProductReviews';




const MyOrderCard = ({ onVariableChange,  item, dateTime, totalAmount }) => {
  const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
  });


  
  const BootstrapDialog = styled(Dialog)(({ theme }) => ({
    '& .MuiDialog-paper': {
      padding: theme.spacing(2),
      minWidth: '700px !important',
      height: '450px'
    },
    '& .MuiDialogActions-root': {
      padding: theme.spacing(1),
    },
  }));
  const BootstrapDialogTitle = (props) => {
    const { children, onClose, ...other } = props;
    return (
      <DialogTitle sx={{ m: 0, p: 2 }} {...other}>
        {children}
        {onClose ? (
          <IconButton
            aria-label="close"
            onClick={onClose}

          >
          </IconButton>
        ) : null}
      </DialogTitle>
    );
  };


  BootstrapDialogTitle.propTypes = {
    children: PropTypes.node,
    onClose: PropTypes.func.isRequired,
  };



  console.log(item);
  console.log(dateTime);
  console.log(totalAmount);
  const dateString = dateTime;
  const date = new Date(dateString);
  const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric',
    hour12: true,
  };
  const formattedDate = date.toLocaleString('en-US', options); 
          onVariableChange(formattedDate);
  const [expanded, setExpanded] = React.useState(false);
  const [selectedProduct, setSelectedProduct] = React.useState(false);
  const [isAddCategoryOpen, setIsAddCategoryOpen] = React.useState(false);

  function addReviews(prodId){
    setSelectedProduct(prodId);
    toggleAddCategoryModal() ;
  }


  function toggleAddCategoryModal() {
    setIsAddCategoryOpen(!isAddCategoryOpen);
    if (isAddCategoryOpen === true) {
      

    }
  }

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };
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
            const [expanded1, setExpanded1] = React.useState(false);
            const handleExpandClick = () => {
              setExpanded(!expanded);
            };
  return (
    <div>
      <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')} style={{ marginBottom: '20px', maxWidth: '80%', marginLeft: '8%' }}>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1bh-content"
          id="panel1bh-header"
          style={{ position: 'relative', backgroundColor: '', color:'#0a0a0a' }}
        >
          <Col md>
            <Typography sx={{ width: '33%', flexShrink: 0 }} >
              <h6><b>Date</b></h6>
            </Typography>
            <p className="text-pebble mb-0 w-100 mb-2 mb-md-0"><b>{formattedDate}</b></p>
          </Col>
          <Col md={2}>
            <Typography style={{paddingLeft:10}} sx={{ width: '33%', flexShrink: 0 }}>
              <h6><b>Total</b></h6>
            </Typography>
            <p style={{paddingLeft:10}} className="text-pebble mb-0 w-100 mb-2 mb-md-0">$&nbsp;<b>{totalAmount}</b></p>
          </Col>
          <Col md={4}>
            <Typography style={{paddingLeft:10}} sx={{ width: '33%', flexShrink: 0 }}>
              <h6><b>Total Items</b></h6>
            </Typography>
            <p style={{paddingLeft:10}} className="text-pebble mb-0 w-100 mb-2 mb-md-0">    <b>{item.length}</b></p>
          </Col>
          <Col md>
            <Typography sx={{ width: '33%', flexShrink: 0 }}>
              <h6><b>Shipped To</b></h6>
            </Typography>
            <p className="text-pebble mb-0 w-100 mb-2 mb-md-0"><b>{localStorage.getItem('username')}</b></p>
          </Col>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            {item.map(item => (
              <div className="product">
             <Row  className="no-gutters mt-3">
                <Col xs={3} md={3} >
                  {
                    item.product ? 
                    <img style={{ maxHeight: 100, maxWidth: 100 }} alt="complex" src={'data:image/png;base64,' + item.product.imageData} />
                    : ""

                  }
                   </Col>
                <Col xs={9} md={9} className="pr-0 pr-md-3">
                  <h6 className="text-charcoal mb-2 mb-md-1">
                  {
                    item.product ?
                    <p style={{ color: '#507694' }} className="text-charcoal"><b>{item.product.productName}</b></p>
                    :""
                  }
                    </h6>
                  <ul className="list-unstyled text-pebble mb-2 small">
                    <li>
                      <b>Quantity: </b> {item.quantity}
                    </li>
                  </ul>
                  <h6 className="text-charcoal text-left mb-0 mb-md-2"><b>Price:</b>&nbsp;$<b style={{color: 'text.secondary'}}> {item.totalPrice}</b></h6>
                </Col>
                {}
              </Row>
               </div>
            ))}
          </Typography>
        </AccordionDetails>
      </Accordion>



      <BootstrapDialog
        onClose={toggleAddCategoryModal}
        aria-labelledby="customized-dialog-title"
        open={isAddCategoryOpen}
      >
        <BootstrapDialogTitle id="customized-dialog-title" className="toolHeader" style={{ textAlign: 'center', backgroundColor: '#FFEEE3', color: 'black' }}>
          Reviews:
        </BootstrapDialogTitle>

        <ProductReviews addCategoryModal={toggleAddCategoryModal} productId={selectedProduct}/>

      </BootstrapDialog>
    </div>
  )
}
export default MyOrderCard
