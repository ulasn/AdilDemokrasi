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
              type="text"
              :class="classUsername"
              addon-left-icon="now-ui-icons users_circle-08"
              placeholder="Kullanıcı Adı"
              v-model="user.username"
            >
            </fg-input>

            <fg-input
              id ="name"
              name ="name"
              type="text"
              :class="className"
              addon-left-icon="now-ui-icons users_circle-08"
              placeholder="İsim"
              v-model="user.name"
            >
            </fg-input>

            <fg-input
              id ="surname"
              name ="surname"
              type="text"
              :class="classSurname"
              addon-left-icon="now-ui-icons text_caps-small"
              placeholder="Soyisim"
              v-model="user.surname"
            >
            </fg-input>

            <fg-input
              id ="email"
              name ="email"
              type="email"
              :class="classEmail"
              addon-left-icon="now-ui-icons ui-1_email-85"
              placeholder="E-posta Adresi"
              v-model="user.email"
            >
            </fg-input>

            <fg-input
              id ="password"
              name ="password"
              type="password"
              :class="classPassword"
              addon-left-icon="now-ui-icons ui-1_lock-circle-open"
              placeholder="Şifre"
              v-model="user.password"
            >
            </fg-input>

            <template slot="raw-content">
              <div class="card-footer text-center">
                <a
                  @click="onSubmit"
                  class="btn btn-primary btn-round btn-lg btn-block"
                  >Hesap Oluştur</a
                >
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
import { Card, Button, FormGroupInput } from '@/components';
import Actions from '../Request/actions.js'

import MainFooter from '@/layout/MainFooter';
export default {
  name: 'signup-page',
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
            email:'',
            password:'',
            name:'',
            surname:'',
            username:''
        },
        classInput:"no-border input-lg",
        classEmail:"no-border input-lg",
        classPassword:"no-border input-lg",
        className:"no-border input-lg",
        classSurname:"no-border input-lg",
        classUsername: "no-border input-lg",
        unCompletedForm:"has-danger"
      }
  },
  methods:{
      onSubmit: function(){
        let hasEmpty = false
        if(this.user.name === '' ){
            this.className = this.unCompletedForm
            hasEmpty = true
        }
        else{
            this.className = this.classInput
        }
        if(this.user.surname === ''){
            this.classSurname = this.unCompletedForm
            hasEmpty = true
        }
        else{
            this.classSurname = this.classInput
        }
        if(this.user.email === ''){
            this.classEmail = this.unCompletedForm
            hasEmpty = true
        }
        else{
            this.classEmail = this.classInput
        }
        if(this.user.password === ''){
            this.classPassword = this.unCompletedForm
            hasEmpty = true
        }
        else{
            this.classPassword = this.classInput
        }
        if(this.user.username == ''){
            this.classUsername = this.unCompletedForm
            hasEmpty = true
        }
        else{
          this.classUsername = this.classInput
        }

        if(!hasEmpty){
          Actions.addUser(this.user)
        }
      }
  }
};
</script>
<style></style>
