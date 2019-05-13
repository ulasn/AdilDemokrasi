import Axios from "axios";
import { bus } from "../main";

Axios.defaults.baseURL = "http://localhost:8000";

export default {
  addUser(data) {
    return Axios.post("/signup/add", data)
      .then(function(response) {
        return true;
      })
      .catch(function(error) {
        return false;
      });
  },

  loginUser(data) {
    return Axios.post("/login/user", data)
      .then(function(response) {
        let token = response.data.access_token;
        localStorage.setItem("access_token", token);
        localStorage.logged = true
        bus.$emit("logged", "User logged");
        return true;
      })
      .catch(function(error) {
        localStorage.removeItem("userToken");
        return false;
      });
  },

  getUserProfileData(){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      }
    };

    return Axios.get("user/get/profile", config)
      .then(function(response) {
        return response;
      })
      .catch(function(response) {
        console.log(response)
      });
  },

  shareAnnouncement(data){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      }
    };

    return Axios.post("user/new/announcement", data, config)
      .then(function(response) {
        return true
      })
      .catch(function(response) {
        return false;
      });
  },

  shareEvent(data) {
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      }
    };

    return Axios.post("user/new/event", data, config)
      .then(function(response) {
        return true
      })
      .catch(function(response) {
        return false;
      });
  }
};

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
