<template>
  <div class="page-header clear-filter" filter-color="orange">
    <div class="page-header-image" style="background-image: url('img/signup.jpg')"></div>
    <div class="content">
      <div class="container">
        <div class="col-md-5 ml-auto mr-auto">
          <el-alert
            title="Kayıt Başarılı."
            type="success"
            :closable=false
            description="Kaydınız başarıyla yapılmıştır. Giriş Yap sayfasından giriş yapabilirsiniz."
            v-if="successLogin"
            effect="dark"
            show-icon
          ></el-alert>
          <card type="login" v-show="cardShow" plain>
            <fg-input
              id="username"
              name="username"
              type="text"
              :class="classUsername"
              addon-left-icon="now-ui-icons users_circle-08"
              placeholder="Kullanıcı Adı"
              v-model="user.username"
              v-on:keyup.enter="onSubmit"
              autocomplete="off"
            ></fg-input>

            <fg-input
              id="name"
              name="name"
              type="text"
              :class="className"
              addon-left-icon="now-ui-icons users_circle-08"
              placeholder="İsim"
              v-model="user.name"
              v-on:keyup.enter="onSubmit"
              autocomplete="off"
            ></fg-input>

            <fg-input
              id="surname"
              name="surname"
              type="text"
              :class="classSurname"
              addon-left-icon="now-ui-icons text_caps-small"
              placeholder="Soyisim"
              v-model="user.surname"
              v-on:keyup.enter="onSubmit"
              autocomplete="off"
            ></fg-input>

             
            <fg-input
              id="email"
              name="email"
              type="text"
              :class="classEmail"
              v-validate="'required|email'"
              addon-left-icon="now-ui-icons ui-1_email-85"
              placeholder="E-posta Adresi"
              v-model="user.email"
              v-on:keyup.enter="onSubmit"
              autocomplete="off"
            >
            </fg-input>
            



            <fg-input
              id="password"
              name="password"
              type="password"
              v-validate="'min:8'"
              :class="classPassword"
              addon-left-icon="now-ui-icons ui-1_lock-circle-open"
              placeholder="Şifre"
              v-model="user.password"
              v-on:keyup.enter="onSubmit"
              autocomplete="off"
            ></fg-input>

            <fg-input
              id="passwordAgain"
              name="passwordAgain"
              type="password"              
              :class="classPasswordAgain"
              addon-left-icon="now-ui-icons ui-1_lock-circle-open"
              placeholder="Şifre Tekrar"
              v-model="passwordAgain"
              v-on:keyup.enter="onSubmit"
              autocomplete="off"
            ></fg-input>

            <el-alert
              v-if="failLogin"
              title="Hata"
              type="warning"
              :description="description"
              :closable=false
              effect="dark"
              show-icon
            ></el-alert>

            <template slot="raw-content">
              <div class="card-footer text-center">
                <a
                  @click="onSubmit"
                  class="btn btn-primary btn-round btn-lg btn-block"
                >Hesap Oluştur</a>
              </div>
              <div class="pull-mid">
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
import { Card, Button, FormGroupInput } from "@/components";
import Actions from "../Request/actions.js";

import MainFooter from "@/layout/MainFooter";
import { truncate } from 'fs';
export default {
  name: "signup-page",
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
        email: "",
        password: "",
        name: "",
        surname: "",
        username: ""
      },
      classInput: "no-border input-lg",
      classEmail: "no-border input-lg",
      classPassword: "no-border input-lg",
      classPasswordAgain: "no-border input-lg",
      className: "no-border input-lg",
      classSurname: "no-border input-lg",
      classUsername: "no-border input-lg",
      unCompletedForm: "has-danger",
      passwordAgain: "",
      successLogin: false,
      failLogin: false,
      cardShow: true,
      passwordMatch:false,
      description:''
    };
  },
  methods: {
    checkSubmit: function() {
      let hasEmpty = false;
      if (this.user.name === "") {
        this.className = this.unCompletedForm;
        hasEmpty = true;
      } else {
        this.className = this.classInput;
      }
      if (this.user.surname === "") {
        this.classSurname = this.unCompletedForm;
        hasEmpty = true;
      } else {
        this.classSurname = this.classInput;
      }
      if (this.user.email === "") {
        this.classEmail = this.unCompletedForm;
        hasEmpty = true;
      } else {
        this.classEmail = this.classInput;
      }
      if (this.user.password === "") {
        this.classPassword = this.unCompletedForm;
        hasEmpty = true;
      } else {
        this.classPassword = this.classInput;
      }
      if (this.user.username == "") {
        this.classUsername = this.unCompletedForm;
        hasEmpty = true;
      } else {
        this.classUsername = this.classInput;
      }
      if (this.passwordAgain == "") {
        this.classPasswordAgain = this.unCompletedForm;
        hasEmpty = true;
      } else {
        this.classPasswordAgain = this.classInput;
      }

      this.passwordMatch = false;
      if (this.user.password == this.passwordAgain) {
        this.passwordMatch  = true;
      }
      
      if(!this.passwordMatch){
        this.description = "Lütfen aynı şifreyi giriniz."
        this.failLogin = true
      }
      else{
        this.description = "Kayıt Yapılamadı. Farklı bir kullanıcı adı deneyin."
      }

      let emailCheck = true
      if(this.errors.first('email') != null){
        this.description = "E-posta adresinizi doğru giriniz."
        this.failLogin = true
        emailCheck = false
      }

      let passwordCheck = true
      if(this.errors.first('password') != null){
         this.description = "Şifre en az 8 haneli olmalıdır."
         this.failLogin = true
         passwordCheck = false
      }


      if (this.passwordMatch  && !hasEmpty && emailCheck && passwordCheck) {
        return true;
      } else {
        return false;
      }
    },

    onSubmit: function() {
      this.failLogin = false
      let checkSubmit = this.checkSubmit();

      if (checkSubmit) {
        let response = Actions.addUser(this.user).then(response => {
          if (response.status == 200) {
            this.successLogin = true;
            this.cardShow = false;
          } else if(response.response.data.message == "Username Already Exists"){
            this.failLogin = true;
            this.description = "Kullanıcı adı kullanılmaktadır."
          }
          else if(response.response.data.message == "Email Already Exists"){
            this.failLogin = true;
            this.description = "Bu E-postaya ait başka bir kullanıcı bulunmaktadır."
          }
          else{
            this.failLogin = true;
            this.description = "Bilinmeyen Hata. Lütfen tekrar deneyiniz."
          }
        });
      }
    }
  }
};
</script>
<style scoped>
.login-page .card-login .input-group:last-child {
  margin-bottom: 5px;
} 
.input-group,
.form-group {
  margin-bottom: 1px;
}

.el-input___inner:active{
  background-color:rgba(255,255,3, 1)!important;
}
</style>
