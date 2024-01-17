import CloseIcon from '@mui/icons-material/Close';
import CreateIcon from '@mui/icons-material/Create';
import DeleteIcon from '@mui/icons-material/Delete';
import { Typography } from "@mui/material";
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import Snackbar from '@mui/material/Snackbar';
import TextField from '@mui/material/TextField';
import axios from "axios";
import React, { useEffect, useState } from "react";
import { url } from "../../ServiceApi/ServiceApi";
import Navbar from "../Navbar/Navbar";
import ProductAddButton from "../Product/ProductAddButton";
import '../Product/ProductCSS/Product.css';
export default function Category() {
  const [snackopen, setsnackOpen] = React.useState(false);
  const[message, setmessage]= useState('');
  const handleClose1 = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setsnackOpen(false);
  };
  const action = (
    <React.Fragment>
      <IconButton
        size="small"
        aria-label="close"
        color="inherit"
        onClick={handleClose1}
      >
        <CloseIcon fontSize="small" />
      </IconButton>
    </React.Fragment>
  );
  const [open, setOpen] = React.useState(false);
  const [editOpen, setEditOpen] = React.useState(false);
  const handleClickOpen = () => {
    setOpen(!open);
    getCategory();
  };
  const handleClose = () => {
    setaddCategory('');
    handleClickOpen();
  };
  const handleEditClose = () => {
    setEditOpen(false);
    setUpdateCategory('');
  };
     const [categories, setcategories] = useState([]);
    console.log(categories.length);
     useEffect(() => {
      getCategory();
  }, []);
  function getCategory(){
    const api = url+'product/category/ordercategory/'+ localStorage.getItem('userId')
    axios.get(api
      , {})
      .then(res => {
        console.log(res.data);
        setcategories(res.data);
      })
      .catch(e => {
        console.log(e)
      })
    }
    const [updateCategory, setUpdateCategory] = useState();
  const [addCategory, setaddCategory] = useState();
  const categoryData = {"categoryName": updateCategory,
                         "userId":localStorage.getItem('userId')                        
                      }
  const handleSubmit = () => {
    setsnackOpen(true);
    if(!addCategory){
      setmessage('Please enter the category name')
      return ;
    }
    axios.post(url+'product/category', {"categoryName": addCategory,
    "userId":localStorage.getItem('userId')                        
 }
, {})
   .then((response) => {
      console.log(response);
       setmessage('category added');
       setaddCategory('');
       getCategory();
       handleClickOpen();
   })
   .catch((error) => {
    setmessage(error.response.data.message);
    setaddCategory(''); 
    console.log(error.response.data.message);    
    handleClickOpen();
   });
}; 
const handleDelete = (id) => {
  const api = url+'product/category/'+ id +'/'+localStorage.getItem('userId')
  axios.delete(api
    , {})
    .then(res => {
      getCategory();
      console.log(res.data);
    })
    .catch(e => {
       console.log(e)
       setsnackOpen(true);
      setmessage("Can't delete this category, Some products already mapped under this");
    })
}
const [updateId, setUpdateId] = useState();
const handleEdit = (id) => {
  setEditOpen(true);
  const api = url+'product/category/'+ id
  axios.get(api
    , {})
    .then(res => {
      console.log(res.data);
      setUpdateCategory(res.data.categoryName);
      setUpdateId(id)
    })
    .catch(e => {
      console.log(e)
    })
}
const handleUpdate = () => {
  const api = url+'product/category/'+ updateId
  axios.put(api, categoryData
     , {})
     .then(res => {
      setsnackOpen(true);
      setmessage('category updated');
      setEditOpen(false);
      getCategory();
    })
    .catch(e => {
      setEditOpen(false);
      console.log(e)
    })
}
const rows = categories.map((item) => (
  <tr key={item.categoryId}>
    <td style={{textAlign:'center'}}>{item.categoryName}</td>
    <td style={{textAlign:'center'}}><Button onClick={() => handleEdit(item.categoryId)}><CreateIcon /></Button> &nbsp;
    <Button style={{color: "#C8626D"}} onClick={() => handleDelete(item.categoryId)}><DeleteIcon /></Button></td>
  </tr>
));
  return (
      <div className="App">
      <Navbar />
      <Box style={{marginTop:100}}>
      <ProductAddButton label='Add Category'  onClick={handleClickOpen} />
      </Box>
      {}
      <div className="table-container">
      <table>
        <thead >
          <tr style={{padding:'20px'}}>
            <th style={{padding:'20px',textAlign:'center'}}>Category Name</th>
            <th style={{textAlign:'center'}} >Action</th>
          </tr>
        </thead>
        <tbody>{rows}</tbody>
      </table>
      </div> 
      {}
      {}
    {}
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle><Typography fontSize={"20px"}>Category</Typography></DialogTitle>
        <DialogContent>
          <DialogContentText>
             You add some category depends on the product type
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Add Category"
            type="text"
            fullWidth
            value={addCategory}
            onChange={(e) => setaddCategory(e.target.value)}
            variant="standard"
          />
        </DialogContent>
        <DialogActions>
        <ProductAddButton  label="Add" onClick={handleSubmit}/>
            <ProductAddButton  label="Cancel" onClick={handleClose}/>
                  </DialogActions>
      </Dialog>
      <Snackbar
        open={snackopen}
        autoHideDuration={6000}
        onClose={handleClose1}
        message={message}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center"
       }}
        action={action}
      />
      <Dialog open={editOpen} onClose={handleEditClose}>
        <DialogTitle><Typography fontSize={"20px"}>Updating a Category</Typography></DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            type="text"
            fullWidth
            value={updateCategory}
            onChange={(e) => setUpdateCategory(e.target.value)}
            variant="standard"
          />
        </DialogContent>
        <DialogActions>
        <ProductAddButton  label="Update" onClick={handleUpdate}/>
            <ProductAddButton  label="Cancel" onClick={handleEditClose}/>
                  </DialogActions>
      </Dialog>
    </div>
  )
}
