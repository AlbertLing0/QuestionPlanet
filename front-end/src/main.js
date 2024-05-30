import {createApp, ref} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
// import 'element-plus/dist/index.css'
import router from './router'
// import $ from 'jquery'


const app = createApp(App)

// 定义全局变量username并赋初值为null
app.provide('Username', ref(null));

app.use(router)
// app.config.globalProperties.$ = $;
app.use(ElementPlus)
app.mount('#app')
