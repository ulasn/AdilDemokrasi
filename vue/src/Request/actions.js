import Axios from "axios";


Axios.defaults.baseURL = "http://localhost:8000";

export default{
    addUser(data){
        return Axios.post('/signup/add', data)
        .then(function(response){
            console.log(response)
        })
        .catch(function(error){
            console.log(error)
        });
    },

    // retrieveToken(credentials){
    //     const requestBody = {
    //         "grant_type" : "password",
    //         "username" : credentials.username,
    //         "password" : credentials.password
    //     } 

    //     const config = {
    //         headers:{
    //             "Content-Type": 'application/x-www-form-urlencoded'
    //         }
    //     }

    //     Axios.post('oauth/token', requestBody, config)
    //     .then((response) =>
    //     console.log(response) 
    //     )
    //     .catch((error) =>
    //     console.log(error)
    //     )
    // },

    loginUser(data){
        return Axios.post('/login/user', data)
        .then(function(response){
            console.log(response)
        })
        .catch(function(error){
            console.log(error)
        });
    },

}

