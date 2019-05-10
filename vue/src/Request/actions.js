import Axios from "axios";


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
            const token = response.data.access_token
            localStorage.userToken = token
            localStorage.connected = true
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