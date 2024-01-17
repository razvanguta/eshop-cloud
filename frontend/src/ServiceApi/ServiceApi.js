import axios from "axios";

export const PAYPAL_CLIENT_ID ="AeDKUs80SQ7WBsUtwi1uxhTlg4J4TqJVIaODkQCl2IAhYvVzHrVY1oGacdHB6U3kTBKPmIZN9-SFQUTU";  

export const url =`http://localhost:8888/`;

  export const loginUser = (username, password) => {
    return axios.post(url+'user/signin', {
        email: username,
        password: password
      },{
        headers:{
          'Accept' : '*/*',
         'Content-Type': 'application/json',
         "Access-Control-Allow-Origin":"*",
         "Access-Control-Allow-Headers":"*"
      }
    })
  }

  
  

    const config = {};

 
export const pendingAccount = () => {
    return axios.get(url+'user/allusers', config);
       
  };

  export const StatusApprove = (data) => {
    return axios.put(url+'user/status/'+localStorage.getItem('userId'),data ,config);
  };

 
  
  export const getAllProductApi = (searchValue) => {
    const api = url+'product/search?productName='+searchValue+'&userId='+localStorage.getItem('userId')
    
    return axios.get(api
      , {});
      

    
  }