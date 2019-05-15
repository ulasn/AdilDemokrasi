<template>
  <div
    class="section section-signup"
    style="background-image: url('img/bg11.jpg'); background-size: cover; background-position: top center; min-height: 700px;"
  >
    <div class="container" style="margin-top:-100px">
      <div class="row">
        <card class="card-signup" header-classes="text-center" color="orange">
          <template slot="header">
            <h3 class="card-title title-up">Maİl Lİstesİ</h3>
            
          </template>
          <template>
  

            <fg-input
              class="no-border"
              placeholder="Email"
              :disabled=true
              addon-left-icon="now-ui-icons ui-1_email-85"
            >
            </fg-input>
          </template>
          <div class="card-footer text-center">
            <n-button type="neutral" round size="lg" @click="mailListSubmit">Kaydet </n-button>
          </div>
        </card>
      </div>
      <div class="col text-center">
        <router-link
          to="/signup"
          class="btn btn-simple btn-round btn-white btn-lg"
        >
          Hesap Oluştur
        </router-link>
      </div>
    </div>
  </div>
</template>
<script>
import { Card, FormGroupInput, Button } from '@/components';

export default {
  components: {
    Card,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput
  },
  methods:{
    mailListSubmit(){
      this.$alert('Mail Listesini Yakında oluşturacağız. Beklemede Kal.')
    }
  }
};
</script>
<style scoped>
.card{
  min-height:400px; 
}
</style>
