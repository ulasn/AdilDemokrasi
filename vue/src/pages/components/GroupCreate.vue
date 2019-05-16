<template>
<modal :show.sync="value" v-on:close="closeModal" headerClasses="justify-content-center">
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
import {Modal, Button} from '@/components'
import Actions from '../../Request/actions';

export default {
    name:"group-create",
    props:{
        modalOpen:Boolean,
        value:Boolean
    },
    components:{
        [Button.name]: Button,
        Modal
    },
    watch:{
        modalOpen:function(){
            this.modal.classic = this.modalOpen
        }
    },
    data(){
        return{
            group:{
                name:'',
                description:''
            },
            rules:{},
            modal:{
                classic: false
            }

        }
    },
    methods:{
        createGroup(){
            let request = {
                name: this.group.name,
                description: this.group.description,
                admin: localStorage.getItem('username')
            }
            Actions.addGroup(request)
                .then(response => {
                    debugger
                    if(response.response.status == 200){
                        this.$alert('Grubunuz Başarıyla Oluşturuldu.')
                        this.$router.push('/group')
                    }
                    if(response.response.status == 409){
                        this.$alert('Bu Grup adı bulunmaktadır. Başka bir ad ile tekrar deneyin.')
                    }
                    else{
                        this.$alert('Beklenmedik bir hata oldu, lütfen tekrar deneyin.')
                    }
                })
        },
        closeModal(){
            this.$emit('closeit')
        }
    }

}
</script>

<style>

</style>
