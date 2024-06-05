import {createApp, ref} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
// import $ from 'jquery'
import vuetyped from 'vue3typed'
import {useSideBarState} from "~/utils/state.js";
import '@fortawesome/fontawesome-free/css/all.min.css';



const app = createApp(App)

// 定义全局变量username并赋初值为null
const Username = ref(null);
app.provide('Username', Username);
const sideBarState = useSideBarState();
app.provide('sideBarState', sideBarState);
app.use(router)
app.use(vuetyped)
// app.config.globalProperties.$ = $;
app.use(ElementPlus)
app.mount('#app')
export {Username}
