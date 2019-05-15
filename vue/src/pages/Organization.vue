<template>
  <div>
    <div class="page-header page-header-small clear-filter" filter-color="orange">
      <parallax
        class="page-header-image"
        style="background-image: url('img/flow.jpg')"
      >
      </parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title">Kurumlar</h1>
         
        </div>
      </div>
    </div>
    <div class="section section-about-us">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto text-center">
            <h2 class="title">Sivil Toplum Kuruluşları</h2>
            <h5 class="description">
              Bu sayfadan sisteme kayıtlı olan kurumlara ulaşabilirsiniz.
            </h5>
            <ul>
              <li>
                LÖSEV-Lösemili Çocuklar Vakfı
              </li>

              <li>
                Mor Çatı Kadın Sığınağı Vakfı

              </li>

              <li>
                TOG- Toplum Gönüllüleri Vakfı

              </li>

              <li>
                TEGV- Türkiye Eğitim Gönüllüleri Vakfı

              </li>
            </ul>
          </div>
        </div>
        <div class="separator separator-primary"></div>
        
      </div>
    </div>
   
    
  </div>
</template>
<script>
import { Button, FormGroupInput } from '@/components';
export default {
  name: 'organization',
  bodyClass: 'organization-page',
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput
  },
  data() {
    return {
      form: {
        firstName: '',
        email: '',
        message: ''
      }
    };
  }
};
</script>
<style scoped>
.description, .card-description, .footer-big p{
  font-weight:400;
}
</style>
