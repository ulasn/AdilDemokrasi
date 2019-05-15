<template>
  <div>
    <div class="page-header page-header-small clear-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image: url('img/about.png')"></parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title">Hakkında</h1>
        </div>
      </div>
    </div>
    <div class="section section-about-us">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto text-center">
            <h2 class="title">Hedefi ve Rolü</h2>
            <h5 class="description">
              Adil Demokrasi platformunun temel amacı daha güçlü bir sivil toplumun
              oluşumunda yardımcı bir araç olmaktır. Yakın zamana kadar sivil toplum,
               dernekler, vakıflar, sendikalar, kooperatifler ve siyasi partiler gibi 
               örgütlü kuruluşlar anlamına geliyordu. Günümüzde ise, sivil toplum bu
                geleneksel tanımın dışında daha geniş, canlı ve dinamik bir alanı tanımlıyor.

                <br><br>
                Bu yeni tanım, örgütlü toplumun yanında, resmi biçimde örgütlü olmayan,
                 sektörler arası sınırları zorlayan ve yeni deneyimlerle ortaya çıkan,
                  her türlü toplumsal gruplaşmayı aşan, interneti kullanarak veya internet 
                  dışı sivil girişimlerin tümünü kapsıyor. Wall Street'i İşgal Hareketi,
                   Arap Baharı, Gezi olayları bu yeni tanımın en iyi göstergeleridir.
                    <br><br>
                   Adil Demokrasi Platformu Sivil Toplum Kuruluşlarıyla ve Yerel Yönetimlerle 
                   hareket etmeyi amaçlamakla beraber 
                     özgür olarak insanların etkinlik düzenleyebileceği, buluşabileceği, fikirlerini söyleyebileceği
                      bir platform olmak ve zamanla
                   kapsamını genişletmeyi amaçlamaktadır.
            </h5>
          </div>
        </div>
        <div class="separator separator-primary"></div>
      </div>
    </div>
  </div>
</template>
<script>
import { Button, FormGroupInput } from "@/components";
export default {
  name: "about",
  bodyClass: "about-page",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput
  },
  data() {
    return {
      form: {
        firstName: "",
        email: "",
        message: ""
      }
    };
  }
};
</script>
<style scoped>
.description{
    font-weight:400
}
</style>
