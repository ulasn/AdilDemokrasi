<template>
  <div class="page-header clear-filter" filter-color="orange">
    <div class="page-header-image" style="background-image: url('img/login.png')"></div>
    <div class="content">
      <div class="container">
        <div class="col-md-5 ml-auto mr-auto">
          <card type="login" plain>
            <div slot="header">
              <img style="width:65%;" src="img/logo_transparent.png" alt>
            </div>
              
            <fg-input
              id="username"
              name="username"
              type="username"
              class="no-border input-lg"
              addon-left-icon="now-ui-icons users_circle-08"
              placeholder="Kullanıcı Adı"
              v-model="user.username"
              v-on:keyup.enter="login"
            ></fg-input>

            <fg-input
              id="password"
              name="password"
              type="password"
              class="no-border input-lg"
              addon-left-icon="now-ui-icons text_caps-small"
              placeholder="Şifre"
              v-model="user.password"
              v-on:keyup.enter="login"
              style="margin-bottom:-10px"
            ></fg-input>

            <el-alert
              v-if="failLogin"
              title="Hata"
              type="warning"
              :description="description"
              :closable="false"
              effect="dark"
              show-icon
            ></el-alert>

            <template slot="raw-content">
              <div class="card-footer text-center">
                <a @click="login" class="btn btn-primary btn-round btn-lg btn-block">Giriş Yap</a>
              </div>
              <div class="pull-left">
                <h6>
                  <a class="link footer-link">
                    <router-link to="/signup" style="color:white">Kayıt Ol</router-link>
                  </a>
                </h6>
              </div>
              <div class="pull-right">
                <h6>
                  <a href="#" class="link footer-link">Yardım?</a>
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
import { Card, Button, FormGroupInput } from "@/components";
import Actions from "../Request/actions.js";
import MainFooter from "@/layout/MainFooter";
export default {
  name: "login",
  bodyClass: "login-page",
  components: {
    Card,
    MainFooter,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput
  },
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      description: "Beklenmeyen bir hata gerçekleşti",
      failLogin: false
    };
  },
  methods: {
    login() {
      let canLogin = true;
      this.failLogin = false;
      if (this.user.password == "") {
        canLogin = false;
        this.description = "Lütfen Şifrenizi Girin.";
        this.failLogin = true;
      }

      if (this.user.username == "") {
        if (!canLogin) {
          this.description = "Lütfen Kullanıcı Adı ve Şifrenizi Girin";
        } else {
          canLogin = false;
          this.description = "Lütfen Kullanıcı Adını Girin.";
        }
        this.failLogin = true;
      }

      if (canLogin) {
        Actions.loginUser(this.user)
                .then(response => {
                    if(response){
                        localStorage.setItem('username', this.user.username)
                        this.$router.push('/profile')
                    }
                    else{
                       this.description = "Lütfen doğru kullanıcı adı ve şifre giriniz."
                       this.failLogin = true
                    }
                });
      }
    }
  }
};
</script>
<style></style>
