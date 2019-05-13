import Vue from 'vue';
import App from './App.vue';
// You can change this import to `import router from './starterRouter'` to quickly start development from a blank layout.
import router from './router';
import NowUiKit from './plugins/now-ui-kit';
import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/en'
import 'element-ui/lib/theme-chalk/index.css';
import VeeValidate from 'vee-validate';


Vue.config.productionTip = false;

Vue.use(NowUiKit);
Vue.use(ElementUI, { locale });
Vue.use(VeeValidate,  {  inject: true,
  fieldsBagName: 'veeFields' });

export const bus = new Vue();

new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
