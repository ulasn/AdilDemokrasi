<template>
  <div>
    <div class="page-header clear-filter page-header-small" filter-color="orange">
      <parallax class="page-header-image" style="background-image:url('img/demokrasi-eller.jpg')"></parallax>
      <div class="container">
        <div class="content-center brand" style="top:45%">
          <img style="width:35%;" src="img/logo_transparent.png" alt>
          <h1 class="title">Adil Demokrasi</h1>
          <h3>Yerel Yönetimler ve Sivil Toplum Kuruluşları için interaktif platform</h3>
        </div>
      </div>
    </div>
    <div class="main">
      <div class="section section-about-us">
        <div class="container">
          <div class="row">
            <div class="col-md-8 ml-auto mr-auto text-center">
              <h2 class="title">Amacı Ne?</h2>
              <h5 class="description">
                Yerel Yönetimlerin ve Sivil Toplum kuruluşlarının sivil toplumla olan iletişimini
                arttırmak, beraber hareket etmeye teşvik etmek amacıyla kurulmuştur. 21. yüzyılda
                gerçek demokrasiyi hedefler ve interneti bunu sağlayan bir mecra olarak kullanır.
              </h5>
            </div>
          </div>
          <div class="separator separator-primary"></div>
          <div class="section-story-overview">
            <div class="row">
              <div class="col-md-6">
                <div
                  class="image-container image-left"
                  style="background-image: url('img/handair.jpg')"
                >
                  <!-- First image on the left side -->
                  <p class="blockquote blockquote-primary">
                    "Demokrasi, içindeki insanların izleyici değil, oyuncu olduğu bir sistemdir.
                    "
                    <br>
                    <br>
                    <small>- Noam Chomsky</small>
                  </p>
                </div>
                <!-- Second image on the left side of the article -->
                <div class="image-container" style="background-image: url('img/protest.jpg')"></div>
              </div>
              <div class="col-md-5">
                <!-- First image on the right side, above the article -->
                <div
                  class="image-container image-right"
                  style="background-image: url('img/antik-yunan.jpg')"
                ></div>
                <h3>
                  Yerel yönetimler, merkezi yönetimlerin dışında kalan kamusal örgütlenmelerdir ve en basit şekliyle yerel
                  topluluklarının ortak ihtiyaçlarının yerinden tespiti ve karşılanmasından sorumludurlar.
                </h3>
                <p>
                  Yerel yönetimler kamu idaresinin vatandaşa en yakından hizmet veren birimleridir; görev alanları
                  vatandaşların gündelik hayatta karşılaştıkları en temel sorunları içerir. Başka bir ifadeyle, yerel
                  yönetimler hizmet verdikleri yurttaşlarla iletişim kurmakta en avantajlı durumda olan ve kendilerine
                  verilen görevler gereği bu iletişimi kurmaları zorunlu olan birimlerdir.
                </p>
                <p>
                  Bugün artık yerel yönetimler ile demokrasi, yerinden yönetim ve katılımın birbirlerinin içine geçmiş
                  kavramlar olarak algılandıkları söylenebilir. Yerel düzeyde işleyen bir demokrasi alınan kararların en
                  etkin ölçekte alınmasının sağlanması, yerel halkın karar alma mekanizmalarına aktif katılımı ve alınan
                  kararların yine yerel halkın katılımı ile uygulanması olarak anlaşılmaktadır. Yerel yönetimler hem
                  demokrasi kültürünün yerleşmesi hem de halkın denetim işlevini yerine getirebilmesi için ölçek olarak
                  en uygun kamu idaresi birimleri olarak görülmektedir
                </p>
              </div>
              
            </div>
            
          </div>
          <div class="section-story-overview">
          <div class="row">
            <div class="col-md-5">
              <h3>Sivil Toplum Kuruluşları ve Yerel Yönetimlerdeki Methodoloji</h3>
              <p>
                Kamusal katılım çeşitli biçimlerde, en temelden en gelişkine çeşitli araçların kullanımıyla
                gerçekleştirilebilir. Hangi yöntemin ve hangi aracın kullanılacağı konusundaki tercih, eldeki
                konuya bağlı olacaktır. Kamusal katılımı yapılandırmak için çoğu zaman ‘‘katılım merdiveni’’
                adı verilen yöntem kullanılır
                <br><br>
                Kamusal katılım, vatandaşlarla iletişim anlamına gelir. Bunun muhatabı bireysel vatandaşlar
olabileceği gibi, sendikalar, STK’lar, özel sektör, yaşlılar, kadınlar, gençler ve engelliler gibi belli
hedef gruplar da olabilir. Yönetimin hangi grup veya temsilcilerle iletişim kurmayı seçeceği,
sivil toplumun etkinlik gücüne bağlıdır. Çoğu zaman sade vatandaşlar, kamusal katılımda yer
alma olanağından yoksundur. Bu durumlarda, onları temsil edecek STK’lar bulmak gereklidir. 
              </p>  

             
              <!-- <div class="image-container image-left" style="background-image: url('img/katılım.png')"></div> -->
            </div>
            <div class="col-md-5" style="margin-top:0px; margin-left:100px">
               <!-- <div class="image-container image-right" style="background-image: url('img/katilim.png');"></div> -->
               <img src="img/katilim.png" height="335">
            </div>
          </div>
          </div>
        </div>
      </div>
    </div>

    <SignupForm></SignupForm>
    
  </div>

  
</template>
<script>
import { Parallax } from "@/components";
import BasicElements from "./components/BasicElementsSection";
import Navigation from "./components/Navigation";
import TabsSection from "./components/Tabs";
import ProgressPagination from "./components/ProgressPagination";
import Notifications from "./components/Notifications";
import Typography from "./components/Typography";
import JavascriptComponents from "./components/JavascriptComponents";
import CarouselSection from "./components/CarouselSection";
import NucleoIconsSection from "./components/NucleoIconsSection";
import SignupForm from "./components/SignupForm";
import ExamplesSection from "./components/ExamplesSection";
import DownloadSection from "./components/DownloadSection";

export default {
  name: "index",
  bodyClass: "index-page",
  components: {
    Parallax,
    BasicElements,
    Navigation,
    TabsSection,
    ProgressPagination,
    Notifications,
    Typography,
    JavascriptComponents,
    CarouselSection,
    NucleoIconsSection,
    SignupForm,
    ExamplesSection,
    DownloadSection
  }
};
</script>
<style scoped>

.page-header.page-header-small{
  min-height: 80vh;
}

p{
  font-weight:400;
}
</style>
