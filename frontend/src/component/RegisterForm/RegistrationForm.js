import CloseIcon from '@mui/icons-material/Close';
import { InputLabel, MenuItem, Select } from '@mui/material';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import FormControl from '@mui/material/FormControl';
import IconButton from '@mui/material/IconButton';
import Snackbar from '@mui/material/Snackbar';
import TextField from '@mui/material/TextField';
import axios from "axios";
import {
  MDBCard,
  MDBCardBody,
  MDBCol,
  MDBContainer,
  MDBRow
} from 'mdb-react-ui-kit';
import * as React from 'react';
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { url } from "../../ServiceApi/ServiceApi";
import Shopping from '../../images/logo_cart.png';

function RegistrationForm() {
  const navigate = useNavigate();
  const [open, setOpen] = React.useState(false);
 
  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
  };
  const action = (
    <React.Fragment>
      <IconButton
        size="small"
        aria-label="close"
        color="inherit"
        onClick={handleClose}
      >
        <CloseIcon fontSize="small" />
      </IconButton>
    </React.Fragment>
  );
  const [fname, setfName] = useState('')
  const [lName, setlName] = useState('')
  const [role, setRole] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  function clearData() {
    setfName('');
    setlName('');
    setEmail('');
    setPassword('');
  }
  const handleFname = (e) => {
    setfName(e.target.value)
  }
 
  const handlelName = (e) => {
    setlName(e.target.value)
  }
  const handleRole = (e) => {
    setRole(e.target.value)
  }
  const handleEmail = (e) => {
    setEmail(e.target.value)
  }
  
  const handlePassword = (e) => {
    setPassword(e.target.value)
  }
  const emailPattern = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
  const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%?=*&]).{8,}$/;
  const [message, setmessage] = useState('');
  const handleApi = () => {
    setOpen(true);

    

    if (!fname) {
      setmessage('Please enter your first name.')
      return;
    }
    if (!lName) {
      setmessage('Please enter your last name.')
      return;
    }

    
    if (!emailPattern.test(email)) {
      setmessage('Please enter a valid email address');
      return;
    }
    if (!password) {
      setmessage('Please enter your password')
      return;
    }
    if (!role) {
      setmessage('Please choose any role')
      return;
    }

    setOpen(false);
    
    let data = JSON.stringify({
      firstname: fname,
      lastname: lName,
      email: email,
      password: password,
      role: role
    })

    alert(data);
    axios.post(url+'user/signup', data, {
      headers: {
        "Content-Type": "application/json"
      }
    }).then(result => {
      setmessage('Registered successfully');
      console.log(result.data)
      setTimeout(function () {
        navigate('/');
      }, 2000);
    })
      .catch(error => {
        console.log(error)
        setmessage(error.response.data.message);
        clearData();
      })
  }
  const handlesignin = () => {
    navigate('/')
  }
  return (
    <div>
      <MDBContainer  style={{backgroundColor:'aliceblue', minHeight:'calc(100vh - 10px)'}}>
        <MDBRow>
          <MDBCol md='3'></MDBCol>
          <MDBCol md='6'>
          <MDBCard className='my-2 ' style={{ borderRadius: '1rem', textAlign: 'center', top:'10px'}}>
              <MDBCardBody style={{ textAlign: 'center', }}>
                <img style={{ height: 130, width: 130, cursor: 'pointer' }} src={Shopping} />
                <h5 className="fw-bold mb-2 text-center">Create your Account</h5>
                <MDBRow>
                  <MDBCol col='12'>
                    <TextField fullWidth size='small' value={fname} onChange={handleFname} label={'First Name'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={lName} onChange={handlelName} label={'Last Name'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={email} onChange={handleEmail} label={'Email'} id="margin-dense" margin="dense" required />
                    
                    <TextField fullWidth size='small' value={password} onChange={handlePassword} label={'Password'} id="margin-dense" type={'password'} margin="dense" required />
                
                 
                    
                    <FormControl fullWidth size='small'  margin="dense">
                    <InputLabel id="demo-simple-select-label">Are you a </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      value={role}
                      size='small'
                      label="Are you a"
                      onChange={handleRole}
                    >
                      <MenuItem value={"admin"}>ADMIN</MenuItem>
                      <MenuItem value={"user"}>USER</MenuItem>
                    </Select>
                  </FormControl>
                  </MDBCol> 
                  
                </MDBRow>
                
                
                <FormControl required fullWidth sx={{ m: 1, minWidth: 150 }} size="medium">
                </FormControl>
                <Box >
                  <div style={{ display: 'flex', justifyContent: 'center' }}>
                    <Button variant="contained" size='small' style={{width:'70%'}} fullWidth onClick={handleApi} >Sign Up</Button>
                  </div>
                  <p>Already have an account?  <a style={{ color: 'blue', cursor: 'pointer', textDecoration: 'none' }} onClick={handlesignin}>Login</a>
                  </p>
                </Box>
              </MDBCardBody>
            </MDBCard>
          </MDBCol> <MDBCol md='3'></MDBCol>
        </MDBRow>
      </MDBContainer>
      <Snackbar
        open={open}
        autoHideDuration={30}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center"
        }}
        onClose={handleClose}
        message={message}
        action={action}
      />
    </div>
  );
}
export default RegistrationForm;
