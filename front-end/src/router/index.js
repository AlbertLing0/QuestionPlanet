import component from 'element-plus/es/components/tree-select/src/tree-select-option.mjs'
import {
    createRouter,
    createWebHashHistory
} from 'vue-router'

import IndexPage from '~/pages/index.vue'
import About from '~/pages/personal/about.vue'
import NotFound from '~/pages/404.vue'
import LoginPage from '~/pages/login.vue'
import RegisterPage from '~/pages/register.vue'
import design from '~/pages/Design.vue'
import FillIn from '~/pages/FillIn.vue'
import ResetPassword from '~/pages/resetPassword.vue'
import Test from '~/pages/test3.vue'
import { Username } from '~/main.js';
import questionnaires from "~/pages/personal/questionnaires.vue";
import settings from "~/pages/personal/settings.vue";
import display from "~/pages/display.vue";
import analysis from "~/pages/analysis.vue";
import analysis2 from "~/components/analysis_bar2.vue";
import errorPrompt from '~/pages/errorPrompt.vue'
import chart from '~/pages/chart.vue'
import pieChart from '~/pages/pieChart.vue';

const routes = [{
    path:"/",
    component: IndexPage,
    meta: { requiresParallax: true }
},{
    path: "/login",
    component:LoginPage,
    meta: { requiresParallax: true }
},{
    path: "/register",
    component:RegisterPage,
    meta: { requiresParallax: true }
},{
    path: "/resetPassword",
    component:ResetPassword,
    meta: { requiresParallax: true }
},{
    path: "/about",
    component:About,
    meta: { requiresParallax: false ,requiresGradient:true}
},{
    path: "/questionnaires",
    component:questionnaires,
    meta: { requiresParallax: false ,requiresGradient:true}
},{
    path: "/settings",
    component:settings,
    meta: { requiresParallax: false ,requiresGradient:true}
},{
    path: "/design/:id",
    component: design,
},{
    path: "/FillIn/:id",
    component: FillIn,
},,{
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFound,
    meta: { requiresParallax: false }
},{
    path: "/test",
    component: Test,
    meta:{requiresParallax: false}
},{
    path: "/display/:id",
    component: display,
},{
    path: "/analysis",
    component: analysis,
},{
    path: "/analysis2",
    component: analysis2,
},{
    path: "/errorPrompt",
    component: errorPrompt,
},{
    path: "/chart",
    component: chart,
},{
    path: "/pieChart",
    component: pieChart,
}]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})
// 全局前置守卫
router.beforeEach((to, from, next) => {
    console.log("Username.value is",Username.value);
    console.log("local Username is",localStorage.getItem("username"));
    if (localStorage.getItem("username") == null &&
        ((to.path !== '/')
            &&(to.path!=='/login')
            &&(to.path!=='/register')
            &&(to.path!=='/resetPassword')
            &&(to.path!=='/test')
        )){
        // 如果 username 为 null 且目标路径不是主页，则重定向到主页
        next('/login');
    } else {
        // 否则允许导航
        next();
    }
});


export default router;
