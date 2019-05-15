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
        <n-button
          type="primary"
          class="nav-link"
          @click.native="modals.classic = true"
        >Yeni Etkinlik</n-button>
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
        ></el-autocomplete>
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

      <!-- Modal for new Event -->

      <modal :show.sync="modals.classic" headerClasses="justify-content-center">
        <h4 slot="header" class="title title-up" style="color:black;">Etkİnlİk</h4>
        <el-form :model="event" :rules="rules" ref="ruleForm" class="demo-ruleForm">
          <div slot="default" class="container">
            <div class="block">
              <label for="event">Etkinlik Adı</label>
              <el-form-item prop="title">
                <el-input
                  name="event"
                  type="input"
                  placeholder="Kısa, net bir ad ekleyin"
                  v-model="event.title"
                ></el-input>
              </el-form-item>

              <label for="content">Tanım</label>
              <el-form-item prop="content">
                <el-input
                  name="content"
                  native.label="Etkinliğinizle ilgili daha fazla bilgi verin"
                  type="textarea"
                  :autosize="{ minRows: 4, maxRows: 4}"
                  placeholder="Tanım"
                  v-model="event.content"
                ></el-input>
              </el-form-item>

              <label for="location">Lokasyon</label>
              <div class="ui-front">
                <vue-google-autocomplete
                  name="location"
                  id="map"
                  ref="address"
                  class="autocomplete form-control"
                  placeholder="Yazmaya Başlayın"
                  v-on:placechanged="getAddressData"
                  country="tr"
                ></vue-google-autocomplete>
              </div>

              <label for="date">Tarih/Saat</label>
              <el-date-picker
                name="date"
                v-model="event.date"
                type="datetime"
                placeholder="Tarih ve Saat Seçin"
              ></el-date-picker>
            </div>
          </div>
        </el-form>

        <template slot="footer">
          <n-button type="danger" @click.native="modals.classic = false">Kapat</n-button>
          <n-button @click="shareEvent">Paylaş</n-button>
        </template>
      </modal>
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
import VueGoogleAutocomplete from "vue-google-autocomplete";

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
    Modal,
    VueGoogleAutocomplete
  },
  data() {
    return {
      isLogged: this.checkIfIsLogged(),
      modals: {
        classic: false
      },
      search: "",
      links: "",
      timeout: "",
      event: {
        title: "",
        content: "",
        location: "",
        date: "",
        address: {
          route: "",
          latitude: "",
          longitude: ""
        }
      },
      rules: {
        title: [
          {
            required: true,
            message: "Lütfen Etkinlik Adını Giriniz.",
            trigger: "blur"
          },
          {
            min: 3,
            max: 64,
            message:
              "3 Karakterden daha çok, 64 Karakterden daha fazla olamaz.",
            trigger: "blur"
          }
        ],
        content: [
          {
            required: true,
            message: "Lütfen Etkinlik Açıklaması Giriniz.",
            trigger: "blur"
          },
          {
            max: 10000,
            message: "10000 karakterden daha uzun olamaz.",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {
    this.$refs.address.focus();
  },
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
            results = response.data.filteredUserList;

            var usernames = []
            results.forEach(function(user){
              usernames.push({value: user.username})
            })

            clearTimeout(this.timeout);
            this.timeout = setTimeout(() => {
              cb(usernames);
            }, 3000 * Math.random());
          })
          .catch(response => {
          });
      }
    },

    getAddressData(addressData, placeResultData, id) {
      this.event.address.route = addressData.route;
      this.event.address.latitude = addressData.latitude;
      this.event.address.longitude = addressData.longitude;
    },

    returnCleanEventObject() {
      let event = {
        title: "",
        content: "",
        location: "",
        date: "",
        address: {
          route: "",
          latitude: "",
          longitude: ""
        }
      };

      return event;
    },
    handleSelect(item) {
        this.$router.push({name:'profileParam', params:{username: item.value}})
    },

    signOut() {
      localStorage.removeItem("access_token");
      localStorage.logged = false;
      this.isLogged = this.checkIfIsLogged();
      this.$alert("Başarıyla çıkış yapılmıştır.");
      this.$router.push("/");
    },
    shareEvent() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          let eventRequest = {
            title: this.event.title,
            content: this.event.content,
            username: localStorage.getItem("username"),
            location: this.event.location,
            date: this.event.date,
            address: this.event.address
          };
          Actions.shareEvent(eventRequest).then(response => {
            if (response) {
              this.modals.classic = false;
              this.$alert("Etkinlik başarıyla kaydedilmiştir.");
              this.event = this.returnCleanEventObject();
              this.$refs.address.clear();
              this.$router.push("/");
            } else {
              this.modals.classic = false;
              this.event = returnCleanEventObject();
              this.$alert(
                "Beklenmedik bir hata oluştu, lütfen tekrar deneyin."
              );
            }
          });
        } else {
          return false;
        }
      });
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
  width:100%;
}

b {
  font-size: 1.2em;
}
.navbar .navbar-nav .nav-link.btn {
  margin-top: 2px;
  padding:11px 20px;
}
</style>
