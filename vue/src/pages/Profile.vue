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
              <card name="card" class="maincard">
                <tabs slot="raw-content" tab-content-classes="tab-content-padding text-center">
                  <tab-pane>
                    <template slot="label">
                      <i class="now-ui-icons objects_umbrella-13"></i> Ana Sayfa
                    </template>
                    <ul>
                      <li v-for="feed in news" :key="feed.time">
                        <div v-if="feed.type == 'event'" class="feed">
                          <div class="feed-body">
                            <p>
                              {{feed.username }} adlı kullanıcı {{feed.title}} etkinliği düzenledi.
                              <br>
                              <b>Etkinlik Detayı:</b>
                              {{feed.content}}
                            </p>
                          </div>
                          <div class="feed-info">
                            <i
                              style="font-size:0.7rem"
                            >Etkinlik Detayı - {{feed.date}} - {{feed.time}}</i>
                          </div>
                        </div>

                        <div v-if="feed.type == 'announcement'" class="feed">
                          <div class="feed-body">
                            <p>
                              {{feed.username }} adlı kullanıcı {{feed.title}} başlıklı yeni bir duyuru yaptı.
                              <br>
                              <b>Duyuru Detayı:</b>
                              {{feed.content}}
                            </p>
                          </div>
                          <div class="feed-info">
                            <i
                              style="font-size:0.7rem"
                            >Duyuru Detayı - {{feed.date}} - {{feed.time}}</i>
                          </div>
                        </div>

                        <div v-if="feed.type == 'comment'" class="feed">
                          <div class="feed-body">
                            <p>
                              {{feed.username }} adlı kullanıcı bir yorum yaptı.
                              <br>
                              <b>{{feed.title}}</b>
                              etkinliğinde: {{feed.content}}
                            </p>
                          </div>
                          <div class="feed-info">
                            <i style="font-size:0.7rem">Yorum Detayı - {{feed.date}} - {{feed.time}}</i>
                          </div>
                        </div>
                        <div class="giveSpace"></div>
                        <div class="giveSpace"></div>

                      </li>
                    </ul>
                  </tab-pane>
                  <tab-pane>
                    <template slot="label">
                      <i class="now-ui-icons objects_spaceship"></i> Etkinlikler
                    </template>
                    <ul v-if="eventExist">
                      <li v-for="event in profile.events" :key="event.time">
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
                              <b>Tarih:</b>
                              {{event.date}}
                              <br>
                              <br>
                              <b>Zaman:</b>
                              {{event.time}}
                            </p>

                            <n-button type="primary" @click="details(event)">Detaylar</n-button>
                            <modal
                              id="eventDetail"
                              :show.sync="eventDetailsModal"
                              headerClasses="justify-content-center"
                            >
                              <h4 slot="header" class="title title-up" style="color:black;">Etkİnlİk</h4>
                              <div slot="default" class="container">
                                <p class="card-text">
                                  <b>Etkinlik:</b>
                                  {{modalEvent.title}}
                                  <br>
                                  <b>İçeriği:</b>
                                  {{modalEvent.content}}
                                  <br>
                                  <b>Nerede:</b>
                                  {{modalEvent.address.route}}
                                  <br>

                                  <b>Tarih:</b>
                                  {{modalEvent.date}}
                                  <br>
                                  <b>Zaman:</b>
                                  {{modalEvent.time}}
                                </p>
                                <br>

                                <div class="parentParticipant">
                                  <div class="addParticipant">
                                    <h5>
                                      <b>Katılımcı Ekle</b>
                                    </h5>
                                    <el-autocomplete
                                      style="min-width:300px"
                                      v-model="newParticipant"
                                      :fetch-suggestions="querySearchAsync"
                                      placeholder="Arama"
                                      :trigger-on-focus="false"
                                      @select="handleSelect"
                                    >
                                      <template slot-scope="{ item }">
                                        <div class="value">
                                          {{ item.name }}
                                          <span class="tag">{{ item.tag }}</span>
                                        </div>
                                      </template>
                                    </el-autocomplete>
                                    <n-button style="margin-left:20px; min-width:125px; margin-top:5px;" type="danger" @click="addNewEventUser">Ekle</n-button>
                                  </div>
                                  <div class="giveSpace"></div>
                                  
                                  <div class="participants">
                                    <card>
                                      <h5>
                                        <b>Katılımcılar:</b>
                                      </h5>
                                      <ul>
                                        <li v-for="user in eventUsers" :key="user.username">
                                          <div class="event-users">
                                          <div class ="iconWrap"> <img src="img/icon.png" style="width:20px;"></div>
                                          <div class="users-body">
                                            <p style="font-size:1rem">{{user.name}} {{user.surname}}</p>
                                          </div>
                                          </div>
                                        </li>
                                      </ul>
                                    </card>
                                  </div>
                                </div>

                                <div id="comments">
                                  <br>
                                  <center>
                                    <h5>Yorumlar</h5>
                                    <br>
                                  </center>
                                  <p v-if="!commentsExist">
                                    <center>
                                      <i>Henüz yorum bulunmamaktadır. İlk yorumu yap.</i>
                                    </center>
                                  </p>
                                  <ul v-if="commentsExist">
                                    <li v-for="comment in eventComments" :key="comment.time">
                                      <div class="comment">
                                        <div class="comment-info">
                                          <img src="img/icon.png" style="width:50px;">
                                          <p>
                                            {{comment.username}}
                                            <br>
                                            <i
                                              style="font-size:0.7em"
                                            >{{comment.date}} - {{comment.time}}</i>
                                          </p>
                                        </div>

                                        <div
                                          class="comment-body"
                                          style="font-size:0.9em;background-color:(192,192,192,0.5)"
                                        >
                                          <p>{{comment.comment}}</p>
                                        </div>
                                      </div>
                                      <div class="giveSpace"></div>
                                    </li>
                                  </ul>
                                  <br>
                                  <center>
                                    <el-input
                                      name="comment"
                                      type="textarea"
                                      :autosize="{ minRows: 3, maxRows: 3}"
                                      placeholder="Yorum Yapın."
                                      v-model="newComment"
                                      style="width:80%"
                                    ></el-input>
                                  </center>
                                  <center>
                                    <n-button type="default" @click="addComment(newComment)">Gönder</n-button>
                                  </center>
                                </div>
                              </div>

                              <template slot="footer">
                                <n-button
                                  type="danger"
                                  @click.native="eventDetailsModal = false"
                                >Kapat</n-button>
                                <n-button @click="participateEvent" v-if="!ownUser">Katıl</n-button>
                              </template>
                            </modal>
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
                        <card class="customCard" style="width: 20rem;min-height:250px">
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
      searchNewParticipant:'',
      newParticipant:'',
      username: "",
      coverLink: "",
      address: "",
      news: [],
      profile: {
        username: "",
        name: "",
        surname: "",
        job: "",
        twitter: "",
        instagram: "",
        aboutMe: "",
        announcements: [],
        events: [],
        comments: [],
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
      currentEvent: "",
      modalEvent: {
        title: "",
        content: "",
        date: "",
        time: "",
        address: {
          route: ""
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
      eventUsers: [],
      eventComments: [],
      newComment: "",
      commentsExist: false,
      eventModal: {
        classic: false
      },
      announceModal: {
        classic: false
      },
      eventDetailsModal: false,
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

    parseCommentDates() {
      this.eventComments.forEach(function(comment) {
        comment.time = comment.date.substr(11, 8);
        comment.date = comment.date.substr(0, 10);
      });
    }, //2019-05-30T00:34:28.417Z

    getEventComments(event) {
      Actions.getEventComments(event.title).then(response => {
        if (response.data.commentList.length != 0) {
          this.eventComments = response.data.commentList;
          this.parseCommentDates();
          this.commentsExist = true;
        } else {
          this.commentsExist = false;
        }
      });
    },

    getEventUsers(event) {
      var self = this;
      Actions.getEventUsers(event.title).then(response => {
        self.eventUsers = response.data.users;
      });
    },

    details(event) {
      this.eventDetailsModal = true;
      this.modalEvent = event;
      this.currentEvent = event.title;
      this.getEventComments(event);
      this.getEventUsers(event);
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

    getNews() {
      var newItem = {
        title: "",
        content: "",
        date: "",
        time: "",
        type: "",
        username: ""
      };
      var self = this;

      if (self.profile.eventCount != 0) {
        self.profile.events.forEach(event => {
          newItem.title = event.title;
          newItem.content = event.content;
          newItem.date = event.date.substr(0, 10);
          newItem.time = event.date.substr(11, 8);
          newItem.username = event.creator;
          newItem.type = "event";
          let temp = JSON.parse(JSON.stringify(newItem));
          self.news.push(temp);
        });
      }

      if (self.profile.announcementCount != 0) {
        self.profile.announcements.forEach(announcement => {
          newItem.title = announcement.title;
          newItem.content = announcement.post;
          newItem.date = announcement.date.substr(0, 10);
          newItem.time = announcement.date.substr(11, 8);
          newItem.username = announcement.username;
          newItem.type = "announcement";
          let temp = JSON.parse(JSON.stringify(newItem));
          self.news.push(temp);
        });
      }

      if (self.profile.commentCount != 0) {
        self.profile.comments.forEach(comment => {
          newItem.title = "";
          newItem.content = comment.comment;
          newItem.date = comment.date.substr(0, 10);
          newItem.time = comment.date.substr(11, 8);
          newItem.username = comment.username;
          newItem.title = comment.event;
          newItem.type = "comment";
          let temp = JSON.parse(JSON.stringify(newItem));
          self.news.push(temp);
        });
      }

      self.news.sort(function(a, b) {
        return new Date(a.time) - new Date(b.time);
      });
    },

    parseEventTime() {
      this.profile.events.forEach(function(event) {
        event.time = event.date.substr(11, 5);
        event.date = event.date.substr(0, 10);
      });
    }, //2019-05-30T00:34:28.417Z

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
      this.profile.comments = data.comments;
      this.getNews();
      this.parseEventTime();
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
    },

    addComment(item) {
      let comment = {
        comment: item,
        username: localStorage.getItem("username"),
        date: new Date(),
        event: this.currentEvent
      };
      let temp = {
        title: this.currentEvent
      };
      Actions.addComment(comment).then(response => {
        debugger;
        if (response) {
          this.$alert("Yorumunuz başarıyla kaydedildi.");
          this.getEventComments(temp);
        } else {
        }
      });

      this.newComment = "";
    },
    querySearchAsync(queryString, cb) {
      var results;
      if (queryString != "") {
        Actions.searchUser(queryString)
          .then(response => {
            results = response.data.searchElements;

            // var names = [];
            // results.forEach(function(user) {
            //   names.push({ value: user.name });
            // });

            clearTimeout(this.timeout);
            this.timeout = setTimeout(() => {
              cb(results);
            }, 3000 * Math.random());
          })
          .catch(response => {});
      }
    },
    handleSelect(item) {
      this.newParticipant = item.name;
    },
    addNewEventUser(){
      let newUser ={
        username: this.newParticipant,
        event: this.currentEvent
      };
      var self = this;
        Actions.addNewEventUser(newUser)
              .then(response => {
                if(response){
                    this.$alert("Yeni kullanıcı başarıyla eklenmiştir.");
                    self.getEventUsers(self.currentEvent);
                }
                else{

                }
              })
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
  box-shadow: 0px 5px 25px 0px rgba(0, 0, 0, 0.75);
  background-color: rgb(225, 225, 225, 0.5);
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
  min-height: 750px;
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

#eventDetail {
}

#eventDetail >>> .modal-content {
  width: 180%;
  margin-left: -200px;
  background-color: rgb(250, 250, 250);
}

.comment {
  box-shadow: 0px 5px 25px 0px rgba(0, 0, 0, 0.2);
  min-height: 130px;
  height: 95%;
}

.comment-info {
  float: left;
  height: 100px;
  width: 150px;
  padding-left: 15px;
  padding-top: 10px;
}

.comment-body {
  height: 130px;
  padding-top: 10px;
  text-align: left;
  background-color: rgb(192, 192, 192, 0.3);
}

.feed {
  box-shadow: 0px 5px 25px 0px rgba(0, 0, 0, 0.2);
  min-height: 150px;
  height: 100%;
}

.feed-info {
  float: right;
  padding: 15px;
  background-color: rgb(192, 192, 192, 0.2);
  border-radius: 5px;
  margin-top: -50px;
}

.feed-body {
  height: 150px;
  width: 100%;
  padding: 20px;
  float: left;
  text-align: left;
  background-color: rgb(192, 192, 192, 0.15);
  font-size: 0.9em;
  background-color: (192, 192, 192, 0.5);
}

.participants {
  float: left;
  width: 55%;
  min-height: 120px;
}

.addParticipant {
  float: left;
  width: 55%;
  min-height: 100px;
}

.parentParticipant {
  min-height: 250px;
  width: 100%;
  overflow:auto;
}


.users-body {
  float: right;
  text-align: left;
  min-width: 330px;
}

.iconWrap{
  min-height:50px;
  max-width:30px; 
  min-width:20px;
   float:left;
}

.event-users{
  width:100%;
  min-height:50px; 
}
</style>
