import Axios from "axios";
import {bus} from "../main"

Axios.defaults.baseURL = "http://localhost:8000";

export default{
    addUser(data){
        return Axios.post('/signup/add', data)
        .then(function(response){
            console.log("Response: " + response.data)
            return true
        })
        .catch(function(error){
            console.log("Error: " + error)
            return false
        });
    },

    loginUser(data){
        return Axios.post('/login/user', data)
        .then(function(response){
            let token = response.data.access_token
            localStorage.setItem('access_token', token)
            bus.$emit('logged', 'User logged')
            return true
        })
        .catch(function(error){
            localStorage.removeItem('userToken')
            console.log(error)
            return false
        });
    },

}

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