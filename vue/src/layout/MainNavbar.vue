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
        <router-link class="nav-link" to="/about">
          Hakkında
        </router-link>
      </li>

      <li class="nav-item" v-if="isLogged === false"> 
        <router-link class="nav-link" to="/login">
          Giriş Yap
        </router-link>
      </li>

      <li class="nav-item" v-if ="isLogged === false"> 
        <router-link class="nav-link" to="/signup">
          Kayıt Ol
        </router-link>
      </li>

      <li class="nav-item" v-if ="isLogged === true" > 
        
        
         <n-button type="primary"  class="nav-link" @click="signOut">  Çıkış Yap  </n-button>
        
      </li>

      <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Follow us on Twitter"
          data-placement="bottom"
          href="https://twitter.com/Acil-Demokrasi"
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
          href="https://www.facebook.com/Acil-Demokrasi"
          target="_blank"
        >
          <i class="fab fa-facebook-square"></i>
          <p class="d-lg-none d-xl-none">Facebook</p>
        </a>
      </li>
      <li class="nav-item">
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
      </li>
    </template>
  </navbar>
</template>

<script>
import { DropDown, NavbarToggleButton, Navbar, NavLink,Button } from '@/components';
import { Popover } from 'element-ui';
import {bus} from "../main"

export default {
  name: 'main-navbar',
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
    [Button.name]: Button
  },
  data(){
    return{
      isLogged: this.checkIfIsLogged()
    }
  },
  created(){
      bus.$on('logged', () =>{
        this.isLogged = this.checkIfIsLogged()
      })
  },
  methods:{
    checkIfIsLogged(){
      let token = localStorage.getItem('access_token')
      if(token){
        return true
      }
      else{
        return false
      }
    },
    signOut(){
      localStorage.removeItem('access_token')
      this.isLogged = this.checkIfIsLogged()
      this.$alert('Başarıyla çıkış yapılmıştır.')
      this.$router.push('/')
    }

  }
};
</script>

<style scoped>
.navbar .navbar-nav .nav-link:not(.btn){
  cursor:pointer;
}
</style>
