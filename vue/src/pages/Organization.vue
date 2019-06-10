<template>
  <div>
    <div class="page-header page-header-small clear-filter" filter-color="orange" style="top:45%">
      <parallax class="page-header-image" style="background-image: url('img/forum.jpg')"></parallax>
      <div class="content-center" >
        <div class="container">
          <h1 class="title">FORUM</h1>
        </div>
      </div>
    </div>
    <div class="section section-about-us">
      <div class="container">
        
          <div style="width: 1800px; margin-left:-350px;">
            <div class="yasama" style="float: left; width: 550px;">
              <card class ="education"><center><h2 class="card-title">EĞİTİM </h2></center>
              <hr/>

              <ul>
                      <li v-for="edu in educationNews" :key="edu.eventTime">
                        <div v-if="edu.type == 'event'" class="feed">
                          <div class="feed-body">
                            <p>
                              <router-link :to="{ name: 'groupParam', params: { groupname: edu.groupName }}">{{edu.groupName }}</router-link> adlı organizasyon <b>{{edu.title}}</b> adlı yeni bir etkinlik düzenledi.
                              <br>
                              <b>Etkinlik Detayı:</b>
                              {{edu.content}}
                              <br>
                              <b>Tarih/Saat:</b> {{edu.date}} - {{edu.eventTime}}
                              <br>
                              <b>Mekan:</b> {{edu.eventLocation}}
                            </p>
                          </div>
                        </div>

                        <div v-if="edu.type == 'announcement'" class="feed">
                          <div class="feed-body">
                            <p>
                               <router-link :to="{ name: 'groupParam', params: { groupname: edu.groupName }}">{{edu.groupName }}</router-link> 
                               adlı grup {{edu.title}} başlıklı yeni bir duyuru yaptı.
                              <br>
                              <b>Duyuru Detayı:</b>
                              {{edu.content}}
                            </p>
                          </div>
                          <div class="feed-info">
                            <i
                              style="font-size:0.9rem"
                            >Duyuru Tarihi - {{edu.date}} / {{edu.eventTime}}</i>
                          </div>
                        </div>
                        <div class="giveSpace"></div>
                        <div class="giveSpace"></div>

                      </li>
                    </ul>
              </card>
            </div>

            <div class="emptySpace"></div>

            <div class ="yurutme" style="float: left; width: 550px;">
              <card class ="culture"><center><h2 class="card-title">KÜLTÜR</h2></center>
              <hr/>
              <ul>
                      <li v-for="culture in cultureNews" :key="culture.eventTime">
                        <div v-if="culture.type == 'event'" class="feed">
                          <div class="feed-body">
                            <p>
                              <router-link :to="{ name: 'groupParam', params: { groupname: culture.groupName }}">{{culture.groupName }}</router-link>
                               adlı organizasyon <b>{{culture.title}}</b> adlı yeni bir etkinlik düzenledi.
                              <br>
                              <b>Etkinlik Detayı:</b>
                              {{culture.content}}
                              <br>
                              <b>Tarih/Saat:</b> {{culture.date}} - {{culture.eventTime}}
                              <br>
                              <b>Mekan:</b> {{culture.eventLocation}}
                              <br>
                            </p>
                          </div>
                        </div>

                        <div v-if="culture.type == 'announcement'" class="feed">
                          <div class="feed-body">
                            <p>
                              <router-link :to="{ name: 'groupParam', params: { groupname: culture.groupName }}">{{culture.groupName }}</router-link> 
                               adlı grup {{culture.title}} başlıklı yeni bir duyuru yaptı.
                              <br>
                              <b>Duyuru Detayı:</b>
                              {{culture.content}}
                            </p>
                          </div>
                          <div class="feed-info">
                            <i
                              style="font-size:0.9rem"
                            >Duyuru Tarihi - {{culture.date}} / {{culture.eventTime}}</i>
                          </div>
                        </div>
                        <div class="giveSpace"></div>
                        <div class="giveSpace"></div>

                      </li>
                    </ul>
              </card>
              
            </div>

            <div class="emptySpace"></div>

            <div class = "yargı" style="float: left; width: 550px;">
              <card class ="enviroment"><center><h2 class="card-title">ÇEVRE</h2></center>
              <hr/>
              <ul>
                      <li v-for="env in enviromentNews" :key="env.eventTime">
                        <div v-if="env.type == 'event'" class="feed">
                          <div class="feed-body">
                            <p>
                              <router-link :to="{ name: 'groupParam', params: { groupname: env.groupName }}">{{env.groupName }}</router-link>
                               adlı organizasyon {{env.title}} yeni bir etkinlik düzenledi.
                              <br>
                              <b>Etkinlik Detayı:</b>
                              {{env.content}}
                              <br>
                              <b>Tarih/Saat:</b> {{env.date}} - {{env.eventTime}}
                              <br>
                              <b>Mekan:</b> {{env.eventLocation}}
                            </p>
                          </div>
                        </div>

                        <div v-if="env.type == 'announcement'" class="feed">
                          <div class="feed-body">
                            <p>
                              <router-link :to="{ name: 'groupParam', params: { groupname: env.groupName }}">{{env.groupName }}</router-link> 
                               adlı grup {{env.title}} başlıklı yeni bir duyuru yaptı.
                              <br>
                              <b>Duyuru Detayı:</b>
                              {{env.content}}
                            </p>
                          </div>
                          <div class="feed-info">
                            <i
                              style="font-size:0.9rem"
                            >Duyuru Tarihi - {{env.date}} / {{env.eventTime}}</i>
                          </div>
                        </div>
                        <div class="giveSpace"></div>
                        <div class="giveSpace"></div>

                      </li>
                    </ul>
              </card>
            </div>
            <br style="clear: left;">
          </div>
        
        <div class="separator separator-primary"></div>
      </div>
    </div>
  </div>
</template>
<script>
import { Button, Card, FormGroupInput } from "@/components";
import Actions from "../Request/actions.js";

export default {
  name: "organization",
  bodyClass: "organization-page",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    Card
  },
  data() {
    return {
      form: {
        firstName: "",
        email: "",
        message: ""
      },
      cultureNews:[],
      educationNews:[],
      enviromentNews:[],
      news:[]
      // model:{
      //   title:'',
      //   content:'',
      //   date:'',
      //   groupName:'',
      //   eventTime:'',
      //   eventLocation:'',
      //   tag:'',
      //   type:''
      // }
    };
  },

  mounted(){
      this.getNews();
  },

  methods:{ 
    getNews() {
      this.news = [];
      this.cultureNews = [];
      this.educationNews = [];
      this.enviromentNews = [];
      var self = this;
      Actions.getGroupNews()
              .then(response => {
                  self.cultureNews = response.data.cultureNews
                  self.educationNews = response.data.educationNews
                  self.enviromentNews = response.data.enviromentNews
                  this.parseEventTime();
              })
    },

    parseEventTime() {
      this.cultureNews.forEach(function(event) {
        if(event.type == "announcement"){
            event.eventTime = event.eventTime.substr(11, 5);
            event.date = event.date.substr(0, 10);
        }
      });
      this.educationNews.forEach(function(event) {
        if(event.type == "announcement"){
            event.eventTime = event.eventTime.substr(11, 5);
            event.date = event.date.substr(0, 10);
        }
      });
      this.enviromentNews.forEach(function(event) {
        if(event.type == "announcement"){
            event.eventTime = event.eventTime.substr(11, 5);
            event.date = event.date.substr(0, 10);
        }
      });
    }, //2019-05-30T00:34:28.417Z
  }
};
</script>
<style scoped>
.description,
.card-description,
.footer-big p {
  font-weight: 400;
}



.education{
  box-shadow: 0px 5px 25px 0px rgba(0, 0, 0, 0.2);
  min-height:750px;
  max-height:750px;
 overflow-y: scroll;
  background-color: rgb(173,216,230);  
}

.enviroment{
  box-shadow: 0px 5px 25px 0px rgba(0, 0, 0, 0.2);
  min-height:750px;
  max-height:750px;
  overflow-y: scroll;
  background-color: rgb(144,238,144);
}

.culture{
  box-shadow: 0px 5px 25px 0px rgba(0, 0, 0, 0.2);
  min-height:750px;
  max-height:750px;
  overflow-y: scroll;
  background-color: rgb(255, 127, 80, 0.5);  
}

.emptySpace{
  float: left;
   width: 50px;
   min-height:500px;

}

hr {
    display: block;
    height: 1px;
    border: 0;
    border-top: 1px solid black;
    margin: 1em 0;
    padding: 10px;
}

.tab{
  background-color:blanchedalmond;
}

.feed {
  box-shadow: 0px 5px 25px 0px rgba(0, 0, 0, 0.2);
  min-height: 200px;
  margin-left:-20px;
  background-color: antiquewhite;
  
}

.feed-info {
  border-radius: 5px;
}

.feed-body {
  min-height: 150px;
  width: 100%;
  padding: 20px;
  float: left;
  text-align: left;
  background-color: rgb(192, 192, 192, 0.15);
  font-size: 0.9em;
  
  
  
}

ul {
  list-style: none;
}

.giveSpace{
  padding:5px;
}
</style>
