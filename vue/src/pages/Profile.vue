<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image:url('img/bg5.jpg')"></parallax>
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
            @click.native="modals.classic = true"
            class="btn btn-primary btn-round btn-lg"
          >Yeni Duyuru</n-button>

          <modal :show.sync="modals.classic" headerClasses="justify-content-center">
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
                    ></el-input>
                  </el-form-item>

                  <label for="content">İçerik</label>
                  <el-form-item prop="content">
                    <el-input
                      name="content"
                      type="textarea"
                      :autosize="{ minRows: 4, maxRows: 4}"
                      placeholder="Duyurunuzun Detaylarını Ekleyin"
                      v-model="announcement.post"
                    ></el-input>
                  </el-form-item>
                </div>
              </div>
            </el-form>

            <template slot="footer">
              <n-button type="danger" @click.native="modals.classic = false">Kapat</n-button>
              <n-button @click="shareAnnouncement">Paylaş</n-button>
            </template>
          </modal>

          <n-button
            v-bind:href="profile.twitter"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on Twitter"
          >
            <i class="fab fa-twitter"></i>
          </n-button>
          <n-button
            v-bind:href="profile.instagram"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on Instagram"
          >
            <i class="fab fa-instagram"></i>
          </n-button>
        </div>
        <h3 class="title">Hakkında</h3>
        <h5 class="description">{{profile.about}}</h5>

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
                  <tab-pane>
                    <template slot="label">
                      <i class="now-ui-icons ui-2_settings-90"></i> Ayarlar
                    </template>                    
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

export default {
  name: "profile",
  bodyClass: "profile-page",
  components: {
    Tabs,
    TabPane,
    TabsSection,
    Card,
    Modal,
    [Button.name]: Button
  },
  mounted() {
    this.mountData();
  },
  data() {
    return {
      profile: {
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
      settings:{
        aboutMe:'',
        job:'',
      },
      announcement: {
        title: "",
        post: ""
      },
      modals: {
        classic: false
      },
      rules: {},
      eventExist: false,
      announcementExist: false,
      eventNotExistMessage:
        "Bu hesabın bağlı olduğu bir etkinlik bulunmamaktır.",
      announcementNotExistMessage:
        "Bu hesabın paylaştığı bir duyuru bulunmamaktadır."
    };
  },
  methods: {
    mountData() {
      let responseData;
      Actions.getUserProfileData().then(response => {
        this.fillUserProfile(response.data);
      });
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

    fillUserProfile(data) {
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
      this.events();
      this.announce();
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
          this.modals.classic = false;
          this.$alert("Duyuru başarıyla paylaşılmıştır.");
          this.cleanAnnouncementObject();
          this.$router.push("/profile");
        } else {
          this.modals.classic = false;
          this.event = returnCleanEventObject();
          this.$alert("Beklenmedik bir hata oluştu, lütfen tekrar deneyin.");
        }
      });
    }
  }
};
</script>
<style>
ul {
  list-style: none;
}

.customCard {
  text-align: left;
}
</style>
