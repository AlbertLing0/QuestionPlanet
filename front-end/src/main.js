import { createApp } from 'vue'
import App from './App.vue'
// import ElementPlus from 'element-plus'
// import 'element-plus/dist/index.css'
import router from './router'
// import $ from 'jquery'


const app = createApp(App)

app.use(router)
// app.config.globalProperties.$ = $;
// app.use(ElementPlus)
app.mount('#app')
