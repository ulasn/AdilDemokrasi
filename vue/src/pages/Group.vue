<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" v-bind:style="coverLink"></parallax>
      <div class="container">
        <div class="photo-container">
          <img src="img/institution.jpg" alt>
        </div>
        <h3 class="title">{{this.group.name}}</h3>
        <p class="category">{{this.group.type}}</p>
        <div class="content">
          <div class="social-description">
            <h2>{{this.eventCount}}</h2>
            <p>Etkinlik</p>
          </div>
          <div class="social-description">
            <h2>{{this.announcementCount}}</h2>
            <p>Duyuru</p>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="button-container">
          <n-button
            v-if="admin"
            type="primary"
            class="btn btn-primary btn-round btn-lg"
            @click.native="eventModal.classic = true"
          >Yeni Etkinlik</n-button>

          <!-- Modal For New Event Start -->
          <modal :show.sync="eventModal.classic" headerClasses="justify-content-center">
            <h4 slot="header" class="title title-up" style="color:black;">Etkİnlİk</h4>
            <el-form :model="event" :rules="rules" ref="ruleFormEvent" class="demo-ruleForm">
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
            v-if="admin"
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
            v-if="group.twitter != null"
            v-bind:href="group.twitter"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on Twitter"
            target="_blank"
          >
            <i class="fab fa-twitter"></i>
          </a>
          <a
            v-if="group.instagram != null"
            v-bind:href="group.instagram"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on Instagram"
            target="_blank"
          >
            <i class="fab fa-instagram"></i>
          </a>
        </div>
        <h3 class="title">Hakkında</h3>
        <h5 class="description">{{group.description}}</h5>

        <div class="container">
          <div class="title">
            <h3>
              <b>Profil</b>
            </h3>
          </div>
          <div class="row">
            <div class="col-md-10 ml-auto col-xl-10 mr-auto">
              <!-- Nav tabs -->
              <card name="card" class="maincard">
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
                      <li v-for="event in group.events" :key="event.title">
                        <card class="customCard" style="width: 20rem;">
                          <div>
                            <h4 class="card-title">
                              <b>Etkinlik:</b>
                              {{event.title}}
                            </h4>
                            <br>
                            <p class="card-text">
                              <b>İçeriği:</b>
                              {{event.content}}
                              <br>
                              <br>
                              <b>Nerede:</b>
                              {{event.address.route}}
                              <br>
                              <br>
                              <b>Ne Zaman:</b>
                              {{event.date}}
                            </p>

                            <n-button type="primary">Katıl</n-button>
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
                      <li v-for="announcement in group.announcements" :key="announcement.title">
                        <card class="customCard" style="width: 20rem;">
                          <div>
                            <h4 class="card-title">
                              <b>Duyuru:</b>
                              {{announcement.title}}
                            </h4>
                            <br>
                            <p class="card-text">
                              <b>İçeriği:</b>
                              {{announcement.post}}
                            </p>
                            <br>

                            <!-- <n-button type="primary">Detaylar</n-button> -->
                          </div>
                        </card>
                      </li>
                    </ul>
                    <p v-if="!announcementExist">{{this.announcementNotExistMessage}}</p>
                  </tab-pane>

                  <tab-pane v-if="admin">
                    <template slot="label">
                      <i class="now-ui-icons ui-2_settings-90"></i> Ayarlar
                    </template>

                    <div class="profileSettings">
                      <label for="name">İsim</label>
                      <el-input name="name" type="input" v-model="settings.name"></el-input>
                      <div class="giveSpace"></div>

                      <label for="description" style="padding-bottom:60px">Hakkında</label>
                      <el-input
                        name="description"
                        type="textarea"
                        :autosize="{ minRows: 5, maxRows: 5}"
                        placeholder="Kendiniz Hakkında Yazın."
                        v-model="settings.description"
                      ></el-input>
                      <div class="giveSpace"></div>
                      <div class="giveSpace"></div>

                      <label for="twitter">Twitter</label>
                      <el-input
                        name="twitter"
                        placeholder="Twitter Url'inizi girerseniz grup profilinize eklenecektir."
                        type="input"
                        v-model="settings.twitter"
                      ></el-input>
                      <div class="giveSpace"></div>

                      <label for="instagram">Instagram</label>
                      <el-input
                        name="instagram"
                        type="input"
                        placeholder="Instagram Url'inizi girerseniz grup profilinize eklecektir."
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
  name: "group",
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
    this.groupname = this.$route.params.groupname;
    if (this.groupname === undefined) {
      this.groupname = "";
      this.$router.push("/");
    } else {
      this.mountData(this.groupname);
    }
  },
  data() {
    return {
      groupname: "",
      coverLink: "",
      address: "",
      group: {
        name: "",
        tag: "",
        twitter: "",
        instagram: "",
        description: "",
        announcements: {},
        events: {},
        isAdmin: false
      },
      announcementCount: null,
      eventCount: null,
      event: {
        title: "",
        content: "",
        date: "",
        address: {
          route: "",
          latitude: "",
          longitude: ""
        }
      },
      settings: {
        name: "",
        description: "",
        tag: "",
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
      admin: false,
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
      this.groupname = this.$route.params.groupname;
      if (this.groupname === undefined) {
        this.groupname = "";
        this.$router.push("/");
      } else {
        this.mountData(this.groupname);
      }
    },

    adminCheck() {
      if (this.group.isAdmin) {
        this.setSettings();
        this.admin = true;
      } else {
        this.admin = false;
      }
    },

    mountData(groupname) {
      Actions.getGroupProfileData(groupname).then(response => {
        this.fillGroupProfile(response.data);
      });
    },

    coverImage() {
      let randomNumber = Math.floor(Math.random() * 5) + 1;
      let link = "background-image:url('img/cover/" + randomNumber + ".jpg')";
      this.coverLink = link;
    },

    elementsExist() {
      if (this.group == null) {
        this.group.description =
          "Ayarlar'dan hakkında kısmını doldurabilirsiniz.";
      }
      if (this.eventCount != 0) {
        this.eventExist = true;
      }
      if (this.announcementCount != 0) {
        this.announcementExist = true;
      }
    },

    setSettings() {
      this.settings.name = this.group.name;
      this.settings.description = this.group.description;
      this.settings.instagram = this.group.instagram;
      this.settings.tag = this.group.tag;
      this.settings.twitter = this.group.twitter;
    },

    fillGroupProfile(data) {
      this.group.name = data.name;
      this.group.description = data.description;
      this.group.twitter = data.twitter;
      this.group.instagram = data.instagram;
      this.group.events = data.events;
      this.group.announcements = data.announcements;
      this.group.isAdmin = data.admin;
      this.eventCount = this.group.events.length;
      this.announcementCount = this.group.announcements.length;
      this.adminCheck();
      this.elementsExist();
    },

    saveSettings() {
      Actions.saveGroupSettings(this.settings).then(response => {
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
        title: this.announcement.title,
        group: this.group.name
      };
      Actions.shareGroupAnnouncement(announcementRequest).then(response => {
        if (response) {
          this.announceModal.classic = false;
          this.$alert("Duyuru başarıyla paylaşılmıştır.");
          this.cleanAnnouncementObject();
          this.onRefresh();
        } else {
          this.announceModal.classic = false;
          this.event = returnCleanEventObject();
          this.$alert("Beklenmedik bir hata oluştu, lütfen tekrar deneyin.");
        }
      });
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
      debugger;
      this.$refs["ruleFormEvent"].validate(valid => {
        if (valid) {
          let eventRequest = {
            title: this.event.title,
            content: this.event.content,
            username: localStorage.getItem("username"),
            group: this.group.name,
            date: this.event.date,
            address: this.event.address
          };
          Actions.shareGroupEvent(eventRequest).then(response => {
            if (response) {
              this.eventModal.classic = false;
              this.$alert("Etkinlik başarıyla kaydedilmiştir.");
              this.event = this.returnCleanEventObject();
              this.$refs.address.clear();
              this.onRefresh();
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
  min-width: 80%;
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

.maincard {
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
