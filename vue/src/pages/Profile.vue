<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" v-bind:style="coverLink"></parallax>
      <div class="container">
        <div class="photo-container">
          <img src="img/avatar.png" alt>
        </div>
        <h3 class="title">{{profile.name}} {{profile.surname}}</h3>
        <p class="category">{{profile.job}}</p>
        <div class="content">
          <div class="social-description">
            <h2>{{profile.eventCount}}</h2>
            <p>Etkinlik</p>
          </div>
          <div class="social-description">
            <h2>{{profile.announcementCount}}</h2>
            <p>Duyuru</p>
          </div>
          <div class="social-description">
            <h2>{{profile.commentCount}}</h2>
            <p>Yorumlar</p>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="button-container">
          <n-button
            v-if="ownUser"
            type="primary"
            class="btn btn-primary btn-round btn-lg"
            @click.native="eventModal.classic = true"
          >Yeni Etkinlik</n-button>

          <!-- Modal For New Event Start -->
          <modal :show.sync="eventModal.classic" headerClasses="justify-content-center">
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
                      style="width:140%"
                    ></vue-google-autocomplete>
                  </div>

                  <label for="date">Tarih/Saat</label>
                  <el-date-picker
                    name="date"
                    v-model="event.date"
                    type="datetime"
                    placeholder="Tarih ve Saat Seçin"
                    style="width:125%"
                  ></el-date-picker>
                </div>
              </div>
            </el-form>

            <template slot="footer">
              <n-button type="danger" @click.native="eventModal.classic = false">Kapat</n-button>
              <n-button @click="shareEvent">Paylaş</n-button>
            </template>
          </modal>
          <!-- Modal For New Event End -->

          <!-- Modal For New Announcement Start -->
          <n-button
            v-if="ownUser"
            @click.native="announceModal.classic = true"
            class="btn btn-primary btn-round btn-lg"
          >Yeni Duyuru</n-button>

          <modal :show.sync="announceModal.classic" headerClasses="justify-content-center">
            <h4 slot="header" class="title title-up" style="color:black;">Duyuru</h4>
            <el-form :model="announcement" :rules="rules" ref="ruleForm" class="demo-ruleForm">
              <div slot="default" class="container">
                <div class="block">
                  <label for="announcement">Konu</label>
                  <el-form-item prop="announcement">
                    <el-input
                      name="announcement"
                      type="input"
                      placeholder="Duyuru Başlığını Ekleyin"
                      v-model="announcement.title"
                      style="width:170%"
                    ></el-input>
                  </el-form-item>

                  <label for="content">İçerik</label>
                  <el-form-item prop="content">
                    <el-input
                      name="content"
                      type="textarea"
                      :autosize="{ minRows: 6, maxRows: 6}"
                      placeholder="Duyurunuzun Detaylarını Ekleyin"
                      v-model="announcement.post"
                      style="width:170%"
                    ></el-input>
                  </el-form-item>
                </div>
              </div>
            </el-form>

            <template slot="footer">
              <n-button type="danger" @click.native="announceModal.classic = false">Kapat</n-button>
              <n-button @click="shareAnnouncement">Paylaş</n-button>
            </template>
          </modal>
          <!-- Modal For New Announcement End -->

          <a
            v-if="profile.twitter != null"
            v-bind:href="profile.twitter"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on Twitter"
            target="_blank"
          >
            <i class="fab fa-twitter"></i>
          </a>
          <a
            v-if="profile.instagram != null"
            v-bind:href="profile.instagram"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on Instagram"
            target="_blank"
          >
            <i class="fab fa-instagram"></i>
          </a>
        </div>
        <h3 class="title">Hakkında</h3>
        <h5 class="description">{{profile.aboutMe}}</h5>

        <div class="container">
          <div class="title">
            <h3>
              <b>Profil</b>
            </h3>
          </div>
          <div class="row">
            <div class="col-md-10 ml-auto col-xl-10 mr-auto">
              <!-- Nav tabs -->
              <card name="card">
                <tabs slot="raw-content" tab-content-classes="tab-content-padding text-center">
                  <tab-pane>
                    <template slot="label">
                      <i class="now-ui-icons objects_umbrella-13"></i> Ana Sayfa
                    </template>
                    <p>Ana Sayfa</p>
                  </tab-pane>
                  <tab-pane>
                    <template slot="label">
                      <i class="now-ui-icons objects_spaceship"></i> Etkinlikler
                    </template>
                    <ul v-if="eventExist">
                      <li v-for="event in profile.events" :key="event.title">
                        <card class="customCard" style="width: 20rem;">
                          <div>
                            <h4 class="card-title">{{event.title}}</h4>
                            <p class="card-text">{{event.content}}</p>

                            <n-button type="primary">Detaylar</n-button>
                          </div>
                        </card>
                      </li>
                    </ul>
                    <p v-if="!eventExist">{{this.eventNotExistMessage}}</p>
                  </tab-pane>
                  <tab-pane>
                    <template slot="label">
                      <i class="now-ui-icons shopping_shop"></i> Duyurular
                    </template>
                    <ul v-if="announcementExist">
                      <li v-for="announcement in profile.announcements" :key="announcement.title">
                        <card class="customCard" style="width: 20rem;">
                          <div>
                            <h4 class="card-title">{{announcement.title}}</h4>
                            <p class="card-text">{{announcement.post}}</p>

                            <n-button type="primary">Detaylar</n-button>
                          </div>
                        </card>
                      </li>
                    </ul>
                    <p v-if="!announcementExist">{{this.announcementNotExistMessage}}</p>
                  </tab-pane>

                  <tab-pane v-if="ownUser">
                    <template slot="label">
                      <i class="now-ui-icons ui-2_settings-90"></i> Ayarlar
                    </template>

                    <div class="profileSettings">
                      <label for="name">İsim</label>
                      <el-input name="name" type="input" v-model="settings.name"></el-input>
                      <div class="giveSpace"></div>

                      <label for="surname">Soyadı</label>
                      <el-input name="surname" type="input" v-model="settings.surname"></el-input>
                      <div class="giveSpace"></div>

                      <label for="aboutMe" style="padding-bottom:60px">Hakkında</label>
                      <el-input
                        name="aboutMe"
                        type="textarea"
                        :autosize="{ minRows: 5, maxRows: 5}"
                        placeholder="Kendiniz Hakkında Yazın."
                        v-model="settings.aboutMe"
                      ></el-input>
                      <div class="giveSpace"></div>
                      <div class="giveSpace"></div>

                      <label for="job">Meslek</label>
                      <el-input
                        name="job"
                        type="input"
                        placeholder="Mesleğinizi Belirtin."
                        v-model="settings.job"
                      ></el-input>
                      <div class="giveSpace"></div>

                      <label for="twitter">Twitter</label>
                      <el-input
                        name="twitter"
                        placeholder="Twitter Url'inizi girerseniz profilinize eklenecektir."
                        type="input"
                        v-model="settings.twitter"
                      ></el-input>
                      <div class="giveSpace"></div>

                      <label for="instagram">Instagram</label>
                      <el-input
                        name="instagram"
                        type="input"
                        placeholder="Instagram Url'inizi girerseniz profiline eklecektir."
                        v-model="settings.instagram"
                      ></el-input>
                      <div class="giveSpace"></div>

                      <n-button @click="saveSettings">Kaydet</n-button>
                    </div>
                  </tab-pane>
                </tabs>
              </card>
            </div>

            <!-- End Tabs on plain Card -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Tabs, TabPane, Card, Modal, Button } from "@/components";
import TabsSection from "./components/Tabs";
import Actions from "../Request/actions.js";
import VueGoogleAutocomplete from "vue-google-autocomplete";

export default {
  name: "profile",
  bodyClass: "profile-page",
  components: {
    Tabs,
    TabPane,
    TabsSection,
    Card,
    Modal,
    [Button.name]: Button,
    VueGoogleAutocomplete
  },
  watch: {
    $route(to, from) {
      this.onRefresh();
    }
  },
  beforeMount() {
    this.coverImage();
  },

  mounted() {
    this.$refs.address.focus();
    this.username = this.$route.params.username;
    if (this.username === undefined) {
      this.username = "";
      this.mountData(this.username);
    } else {
      this.mountData(this.username);
    }
  },
  data() {
    return {
      username: "",
      coverLink: "",
      address: "",
      profile: {
        username: "",
        name: "",
        surname: "",
        job: "",
        twitter: "",
        instagram: "",
        aboutMe: "",
        announcements: {},
        events: {},
        eventCount: "",
        announcementCount: "",
        commentCount: ""
      },
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
      settings: {
        name: "",
        surname: "",
        aboutMe: "",
        job: "",
        instagram: "",
        twitter: ""
      },
      announcement: {
        title: "",
        post: ""
      },
      eventModal: {
        classic: false
      },
      announceModal: {
        classic: false
      },
      ownUser: false,

      eventExist: false,
      announcementExist: false,
      eventNotExistMessage:
        "Bu hesabın bağlı olduğu bir etkinlik bulunmamaktadır.",
      announcementNotExistMessage:
        "Bu hesabın paylaştığı bir duyuru bulunmamaktadır.",
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
  methods: {
    onRefresh() {
      this.username = this.$route.params.username;
      if (this.username === undefined) {
        this.username = "";
        this.mountData(this.username);
      } else {
        this.mountData(this.username);
      }
    },

    ownUserCheck() {
      if (this.profile.username == localStorage.getItem("username")) {
        this.ownUser = true;
        this.setSettings();
      } else {
        this.ownUser = false;
      }
    },

    mountData(username) {
      Actions.getUserProfileData(username).then(response => {
        this.fillUserProfile(response.data);
      });
    },

    coverImage() {
      let randomNumber = Math.floor(Math.random() * 5) + 1;
      let link = "background-image:url('img/cover/" + randomNumber + ".jpg')";
      this.coverLink = link;
    },

    events() {
      if (this.profile.eventCount == 0) {
        this.eventExist = false;
      } else {
        this.eventExist = true;
      }
    },

    announce() {
      if (this.profile.announcementCount == 0) {
        this.announcementExist = false;
      } else {
        this.announcementExist = true;
      }
    },

    aboutExist() {
      if (this.profile.aboutMe == null) {
        this.profile.aboutMe =
          "Ayarlar'dan hakkında kısmını doldurabilirsiniz.";
      }
    },

    setSettings() {
      this.settings.name = this.profile.name;
      this.settings.surname = this.profile.surname;
      this.settings.aboutMe = this.profile.aboutMe;
      this.settings.instagram = this.profile.instagram;
      this.settings.job = this.profile.job;
      this.settings.twitter = this.profile.twitter;
    },

    fillUserProfile(data) {
      this.profile.username = data.username;
      this.profile.name = data.name;
      this.profile.surname = data.surname;
      this.profile.job = data.job;
      this.profile.twitter = data.twitter;
      this.profile.instagram = data.instagram;
      this.profile.aboutMe = data.aboutMe;
      this.profile.events = data.events;
      this.profile.announcements = data.announcements;
      this.profile.eventCount = data.eventCount;
      this.profile.announcementCount = data.announcementCount;
      this.profile.commentCount = data.announcementCount;
      this.ownUserCheck();
      this.events();
      this.announce();
      this.aboutExist();
    },

    saveSettings() {
      Actions.saveSettings(this.settings).then(response => {
        if (response) {
          this.$alert("Ayarlarınız başarıyla kaydedilmiştir.");
          this.onRefresh();
        } else {
          this.$alert("Beklenmeyen bir hata oldu lütfen tekrar deneyin.");
          this.onRefresh();
        }
      });
    },

    cleanAnnouncementObject() {
      this.announcement.title = "";
      this.announcement.post = "";
    },

    shareAnnouncement() {
      let announcementRequest = {
        username: localStorage.getItem("username"),
        post: this.announcement.post,
        title: this.announcement.title
      };
      Actions.shareAnnouncement(announcementRequest).then(response => {
        if (response) {
          this.announceModal.classic = false;
          this.$alert("Duyuru başarıyla paylaşılmıştır.");
          this.cleanAnnouncementObject();
          this.$router.push("/profile");
        } else {
          this.announceModal.classic = false;
          this.event = returnCleanEventObject();
          this.$alert("Beklenmedik bir hata oluştu, lütfen tekrar deneyin.");
        }
      });
    },

    getAddressData(addressData, placeResultData, id) {
      debugger;
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
              this.eventModal.classic = false;
              this.$alert("Etkinlik başarıyla kaydedilmiştir.");
              this.event = this.returnCleanEventObject();
              this.$refs.address.clear();
              this.$router.push("/");
            } else {
              this.eventModal.classic = false;
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
ul {
  list-style: none;
}

.customCard {
  text-align: left;
}

.giveSpace {
  padding: 10px;
}

.profileSettings .el-input {
  width: 70%;
  float: right;
}

.profileSettings .label {
  padding-right: 10px;
  display: inline-block;
  float: left;
  clear: left;
  text-align: right;
  width: 150px;
}

.profileSettings .el-textarea {
  width: 70%;
  float: right;
}

.card {
  min-height: 550px;
}

.description,
.card-description,
.footer-big p {
  font-weight: 400;
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

.block .el-input {
  width: 140%;
}

.block .el-textarea {
  width: 140%;
}
</style>
