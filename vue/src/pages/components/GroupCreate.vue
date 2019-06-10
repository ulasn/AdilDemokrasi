<template>
  <modal :show.sync="modal.classic" v-on:close="closeEvent" headerClasses="justify-content-center">
    <h4 slot="header" class="title title-up" style="color:black;">Grup Oluştur</h4>
    <el-form :model="group" :rules="rules" ref="ruleForm" class="demo-ruleForm">
      <div slot="default" class="container">
        <div class="block">
          <label for="name">İsim</label>
          <el-form-item prop="name">
            <el-input
              name="name"
              type="input"
              placeholder="Grubunuzu tanımlayan bir ad koyun."
              v-model="group.name"
            ></el-input>
          </el-form-item>

          <label for="description">Tanım</label>
          <el-form-item prop="description">
            <el-input
              name="description"
              type="textarea"
              :autosize="{ minRows: 6, maxRows: 6}"
              placeholder="Grubunuzun genel hatlarıyla misyonunu ve vizyonunu tanımlayın."
              v-model="group.description"
            ></el-input>
          </el-form-item>

          <label for="tag">Tag</label>
          <el-form-item prop="tag">
          <el-select name ="tag" v-model="group.tag" placeholder="Select">
            <el-option label="Eğitim" value="eğitim"></el-option>
            <el-option label="Çevre" value="çevre"></el-option>
            <el-option label="Kültür" value="kültür"></el-option>
          </el-select>
          </el-form-item>
          

          <label for="twitter">Twitter</label>
          <el-form-item prop="twitter">
            <el-input
              name="twitter"
              type="input"
              :autosize="{ minRows: 6, maxRows: 6}"
              placeholder="Twitter url'ini girin. Grup profilinize eklenecektir."
              v-model="group.twitter"
            ></el-input>
          </el-form-item>

          <label for="instagram">Instagram</label>
          <el-form-item prop="instagram">
            <el-input
              name="instagram"
              type="input"
              :autosize="{ minRows: 6, maxRows: 6}"
              placeholder="Instagram Url'ini girin. Grup profilinize eklenecektir."
              v-model="group.instagram"
            ></el-input>
          </el-form-item>
        </div>
      </div>
    </el-form>

    <template slot="footer">
      <n-button type="danger" @click.native="modal.classic = false">Kapat</n-button>
      <n-button @click="createGroup">Oluştur</n-button>
    </template>
  </modal>
</template>

<script>
import { Modal, Button } from "@/components";
import Actions from "../../Request/actions";

export default {
  name: "group-create",
  props: {
    value: Boolean
  },
  components: {
    [Button.name]: Button,
    Modal
  },
  watch: {
    value: function() {
      this.modal.classic = this.value;
    }
  },
  data() {
    return {
      group: {
        name: "",
        description: "",
        tag: "",
        instagram: "",
        twitter: ""
      },
      rules: {},
      modal: {
        classic: false
      }
    };
  },
  methods: {
    clearGroupCreateModel(){
        this.group.name = ''
        this.group.description = ''
        this.group.tag = ''
        this.group.instagram = ''
        this.group.twitter = ''
    },

    createGroup() {
      let request = {
        name: this.group.name,
        description: this.group.description,
        admin: localStorage.getItem("username"),
        tag: this.group.tag,
        instagram: this.group.instagram,
        twitter: this.group.twitter
      };
      Actions.addGroup(request).then((response) => {
        if (response.status == 200) {
          this.$alert("Grubunuz Başarıyla Oluşturuldu.");
          debugger
          let groupName = this.group.name
          this.closeModal();
          this.$router.push({
            name: "groupParam",
            params: { groupname: groupName }
          });
          
        }
        else if (response.response.status == 409) {
          this.$alert(
            "Bu Grup adı bulunmaktadır. Başka bir ad ile tekrar deneyin."
          );
          this.closeModal();
        } else {
          this.$alert("Beklenmedik bir hata oldu, lütfen tekrar deneyin.");
          this.closeModal();
        }
      });
    },
    closeModal() {
      this.modal.classic = false
      this.$emit("closeit");
      this.clearGroupCreateModel()
    },
    closeEvent(){
      this.modal.classic = false
      this.$emit("closeit");
    }
  }
};
</script>

<style scoped>
.modal-content {
  width: 140%;
}

.el-select{
  display:unset;
}

.el-select >>> .el-select-dropdown__item{
text-align: center;
  text-decoration: none;
  display: block;
}

</style>
