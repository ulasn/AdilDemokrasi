<template>
  <navbar
    position="fixed"
    type="primary"
    :transparent="transparent"
    :color-on-scroll="colorOnScroll"
    menu-classes="ml-auto"
  >
    <template slot-scope="{ toggle, isToggled }">
      <router-link class="navbar-brand" to="/">
        <b>ADİL Demokrasİ</b>
      </router-link>
    </template>
    <template slot="navbar-menu">
      <li class="nav-item">
        <router-link class="nav-link" to="/about">Hakkında</router-link>
      </li>
      <li class="nav-item">
        <router-link class="nav-link" to="/organization">Kurumlar</router-link>
      </li>

      <li class="nav-item" v-if="isLogged === true">
        <router-link class="nav-link" to="/profile">Profİl</router-link>
      </li>

      <li class="nav-item" v-if="isLogged === false">
        <router-link class="nav-link" to="/login">Gİrİş Yap</router-link>
      </li>

      <li class="nav-item" v-if="isLogged === false">
        <router-link class="nav-link" to="/signup">Kayıt Ol</router-link>
      </li>

      <div style="padding:5px"></div>

      <li class="nav-item" v-if="isLogged === true">
        <n-button type="primary" class="nav-link" @click="openModal">Grup Oluştur</n-button>
      </li>

      <div style="padding:5px"></div>

      <li class="nav-item" v-if="isLogged === true">
        <n-button type="primary" class="nav-link" @click="signOut">Çıkış Yap</n-button>
      </li>

      <div style="padding:5px"></div>

      <li class="nav-item" v-if="isLogged === true">
        <el-autocomplete
          v-model="search"
          :fetch-suggestions="querySearchAsync"
          placeholder="Arama"
          :trigger-on-focus="false"
          @select="handleSelect"
        >
          <template slot-scope="{ item }">
            <div class="value">{{ item.name }} <span class="tag">{{ item.tag }}</span></div>
            
          </template>
        </el-autocomplete>
      </li>

      <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Follow us on Twitter"
          data-placement="bottom"
          href="https://twitter.com/adil_demokrasi"
          target="_blank"
        >
          <i class="fab fa-twitter"></i>
          <p class="d-lg-none d-xl-none">Twitter</p>
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Like us on Facebook"
          data-placement="bottom"
          href="https://www.facebook.com/Adil-Demokrasi-2304114006473739/"
          target="_blank"
        >
          <i class="fab fa-facebook-square"></i>
          <p class="d-lg-none d-xl-none">Facebook</p>
        </a>
      </li>
      <!-- Navigation End -->

      <!-- <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Follow us on Instagram"
          data-placement="bottom"
          href="https://www.instagram.com/Acil-Demokrasi"
          target="_blank"
        >
          <i class="fab fa-instagram"></i>
          <p class="d-lg-none d-xl-none">Instagram</p>
        </a>
      </li>-->
    </template>
  </navbar>
</template>

<script>
import {
  DropDown,
  NavbarToggleButton,
  Navbar,
  NavLink,
  Button,
  Modal
} from "@/components";
import { Popover } from "element-ui";
import { bus } from "../main";
import Actions from "../Request/actions.js";

export default {
  name: "main-navbar",
  props: {
    transparent: Boolean,
    colorOnScroll: Number
  },
  components: {
    DropDown,
    Navbar,
    NavbarToggleButton,
    NavLink,
    [Popover.name]: Popover,
    [Button.name]: Button,
    Modal
  },
  data() {
    return {
      isLogged: this.checkIfIsLogged(),
      modals: {
        classic: false
      },
      search: "",
      links: "",
      timeout: ""
    };
  },
  mounted() {},
  created() {
    bus.$on("logged", () => {
      this.isLogged = this.checkIfIsLogged();
    });
  },
  methods: {
    checkIfIsLogged() {
      if (localStorage.logged == "true") {
        return true;
      } else {
        return false;
      }
    },

    querySearchAsync(queryString, cb) {
      var results;
      if (queryString != "") {
        Actions.searchUser(queryString)
          .then(response => {
            results = response.data.searchElements;

            // var names = [];
            // results.forEach(function(user) {
            //   names.push({ value: user.name });
            // });

            clearTimeout(this.timeout);
            this.timeout = setTimeout(() => {
              cb(results);
            }, 3000 * Math.random());
          })
          .catch(response => {});
      }
    },

    handleSelect(item) {
      if(item.tag == "Kişi"){
        this.$router.push({
        name: "profileParam",
        params: { username: item.name }
      });
      }
      
      if(item.tag == "Grup"){
        this.$router.push({
        name: "groupParam",
        params: { groupname: item.name }
      })
      }
    },

    openModal() {
      bus.$emit("groupModal", "open");
    },

    signOut() {
      localStorage.removeItem("access_token");
      localStorage.logged = false;
      this.isLogged = this.checkIfIsLogged();
      this.$alert("Başarıyla çıkış yapılmıştır.");
      this.$router.push("/");
    }
  }
};
</script>

<style scoped>
.navbar .navbar-nav .nav-link:not(.btn) {
  cursor: pointer;
  font-size: 0.8em;
  padding: 0.5rem 1.2rem;
}
.block {
  display: grid;
  grid-template-columns: max-content max-content;
  grid-gap: 10px;
}
.block label {
  text-align: right;
  color: black;
  margin-top: 5px;
}

.el-input {
  width: 100%;
}

b {
  font-size: 1.2em;
}
.navbar .navbar-nav .nav-link.btn {
  margin-top: 2px;
  padding: 11px 20px;
}

.modal-content{
  width:140%;
}

.tag{
  font-size:10px;
  display: inline-block;
  float: right;
}
</style>
