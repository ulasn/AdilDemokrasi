import Axios from "axios";
import { bus } from "../main";

Axios.defaults.baseURL = "http://localhost:8000";

Axios.interceptors.response.use(response => {
  return response;
}, error => {
 if (error.response.status === 401) {
    localStorage.removeItem("access_token");
    bus.$emit("logged", "User logged out");
    localStorage.logged = false;
    this.$router.push('/login')
 }
 return error;
});

export default {
  addUser(data) {
    return Axios.post("/signup/add", data)
      .then(function(response) {
        return response;
      })
      .catch(function(error) {
        return error;
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

  getUserProfileData(username){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      },
      params:{
        username: username
      }
    };

    return Axios.get("user/get/profile", config)
      .then(function(response) {
        return response;
      })
      .catch(error => {
        let errorObject=JSON.parse(JSON.stringify(error));
        console.log(errorObject);
      });
  },

  getGroupProfileData(name){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      },
      params:{
        name: name
      }
    };

    return Axios.get("ngo/get/profile", config)
      .then(function(response) {
        return response;
      })
      .catch(error => {
        let errorObject=JSON.parse(JSON.stringify(error));
        console.log(errorObject);
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

  shareGroupAnnouncement(data){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      }
    };

    return Axios.post("ngo/new/announcement", data, config)
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
        debugger
        return true
      })
      .catch(function(response) {
        return false;
      });
  },

  shareGroupEvent(data){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      }
    };

    return Axios.post("ngo/new/event", data, config)
      .then(function(response) {
        return true
      })
      .catch(function(response) {
        return false;
      });
  },

  searchUser(data){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token"),
      },
      params:{
        searchQuery: data
      }
    };

    return Axios.get("user/search/user", config)
      .then(function(response) {
        return response
      })
      .catch(function(response) {
        return response;
      });

  },

  saveSettings(data){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      }
    };

    return Axios.post("user/settings", data, config)
      .then(function(response) {
        return true
      })
      .catch(function(response) {
        return false;
      });
  },

  saveGroupSettings(data){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      }
    };

    return Axios.post("ngo/settings", data, config)
      .then(function(response) {
        return true
      })
      .catch(function(response) {
        return false;
      });
  },

  addGroup(data){
    var config = {
      headers: {
        Authorization: "bearer " + localStorage.getItem("access_token")
      }
    };

    return Axios.post("ngo/add", data, config)
      .then(response => {
          return response
      })
      .catch(response => {
          return response
      })
    
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
