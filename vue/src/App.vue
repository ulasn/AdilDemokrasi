<template>
  <div id="app">
    <router-view name="header"/>

    <div class="wrapper">
      <router-view/>
      <group-modal v-model="groupModalOpen" v-on:closeit="resetModal"></group-modal>
    </div>

    <router-view name="footer"/>
  </div>
</template>
<script>
import GroupModal from "./pages/components/GroupCreate";
import { bus } from "./main.js";

export default {
  name:"app",

  components: {
    GroupModal
  },
  data() {
    return {
      groupModalOpen: false
    };
  },
  created() {
    bus.$on("groupModal", (param) => {
      if(param == "open"){
        this.groupModalOpen = true
      }
    });
  },
  methods: {
    resetModal(){
      this.groupModalOpen = false
    }
  }
};
</script>
<style>
.pac-container {
  z-index: 1050;
}
</style>

