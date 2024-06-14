import { createRouter, createWebHashHistory } from "vue-router";

import Home from "@/views/Home.vue";

//常量路由
const constantRoutes = [{
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login')
},
{
    path: '/',
    name: 'Home',
    component: Home,
    redirect: '/index',
    children: [
        {
            path: '/index',
            name: 'Index',
            meta: {
                title: "首页"
            },
            component: () => import('@/views/sys/Index')
        },
        {
            path: '/user',
            name: 'User',
            meta: {
                title: "用户管理"
            },
            component: () => import('@/views/sys/User')
        },
        //其他菜单动态从后台数据获取
    ]
},
]
//初始化默认路由
const router = createRouter({
    history: createWebHashHistory(),
    base: process.env.BASE_URL,
    routes: constantRoutes
});

export default router