import { createRouter, createWebHashHistory } from "vue-router";

//常量路由
const constantRoutes = [{
    path: '/login',
    name: 'Login',
    component: () => import('@/components/Login.vue'),
    meta: {
        title: "登录页"
    },
},
{
    path: '/',
    name: 'Home',
    component: () => import('@/components/Home.vue'),
    redirect: '/index',
    children: [
        {
            path: '/index',
            name: 'Index',
            component: () => import('@/components/Index.vue'),
            meta: {
                title: "首页"
            },
        },
        {
            path: '/404',
            name: 'NotFound',
            component: () => import('@/components/error/404.vue'),
            meta: {
                title: "NotFound"
            }
        },
        {
            path: '/:catchAll(.*)',
            //当路由不存在时， 重定向到404页面
            redirect: '/404',
        },
        //其他页面路由
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