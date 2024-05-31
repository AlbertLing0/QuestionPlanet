import component from 'element-plus/es/components/tree-select/src/tree-select-option.mjs'
import {
    createRouter,
    createWebHashHistory
} from 'vue-router'

import IndexPage from '~/pages/index.vue'
import About from '~/pages/about.vue'
import NotFound from '~/pages/404.vue'
import LoginPage from '~/pages/login.vue'
import RegisterPage from '~/pages/register.vue'
import design from '~/pages/Design.vue'
import FillIn from '~/pages/FillIn.vue'
import ResetPassword from '~/pages/resetPassword.vue'

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
    meta: { requiresParallax: true }
},{
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFound,
    meta: { requiresParallax: false }
},{
    path: "/design/:id",
    component: design
},{
    path: "/FillIn/:id",
    component: FillIn
}]



const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router;
