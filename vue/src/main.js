import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import router from './router'
import store from "./store"
import './assets/global.css'
import request from "@/utils/request";

Vue.config.productionTip = false

Vue.use(ElementUI,{size: "mini"});

Vue.prototype.request=request
// Vue.prototype.axios=request

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
