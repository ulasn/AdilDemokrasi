<template>
  <div class="page-header clear-filter" filter-color="orange">
    <div
      class="page-header-image"
      style="background-image: url('img/login.jpg')"
    ></div>
    <div class="content">
      <div class="container">
        <div class="col-md-5 ml-auto mr-auto">
          <card type="login" plain>
            <div slot="header" class="logo-container">
              <img v-lazy="'img/now-logo.png'" alt="" />
            </div>

            <fg-input
              id ="username"
              name ="username"
              type="username"
              class="no-border input-lg"
              addon-left-icon="now-ui-icons users_circle-08"
              placeholder="Kullanıcı Adı"
              v-model="user.username"
            >
            </fg-input>

            <fg-input
              id ="password"
              name ="password"
              type="password"
              class="no-border input-lg"
              addon-left-icon="now-ui-icons text_caps-small"
              placeholder="Şifre"
              v-model = "user.password"
            >
            </fg-input>

            <template slot="raw-content">
              <div class="card-footer text-center">
                <a
                  @click="login"
                  class="btn btn-primary btn-round btn-lg btn-block"
                  >Giriş Yap</a
                >
              </div>
              <div class="pull-left">
                <h6>
                  <a class="link footer-link">
                  <router-link to="/signup" style="color:white">  
                  Hesap Oluştur
                  </router-link>
                  </a>
                </h6>
              </div>
              <div class="pull-right">
                <h6>
                  <a href="#pablo" class="link footer-link">Yardım?</a>
                </h6>
              </div>
            </template>
          </card>
        </div>
      </div>
    </div>
    <main-footer></main-footer>
  </div>
</template>
<script>
import { Card, Button, FormGroupInput } from '@/components';
import Actions from '../Request/actions.js'
import MainFooter from '@/layout/MainFooter';
export default {
  name: 'login',
  bodyClass: 'login-page',
  components: {
    Card,
    MainFooter,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput
  },
  data(){
    return{
      user:{
          username:'',
          password:''
      }
      
    }
  },
  methods:{
    login(){
      Actions.loginUser(this.user)
    }
  }
};
</script>
<style></style>
