<template>
  <div class="home">
    <!--页头-->
    <Header></Header>
    <!--左侧菜单-->
    <Sidebar></Sidebar>
    <div class="content-box" :class="{ 'content-collapse': collapse }">
      <!--标签-->
      <Tab></Tab>
      <!-- 使用插槽路由到具体页面 -->
      <div class="body">
        <router-view v-slot="{ Component }">
          <transition name="fade-transform" mode="out-in">
            <component :is="Component" :key="this.$route.path" />
          </transition>
        </router-view>
      </div>
      <!--页脚-->
      <Footer></Footer>
    </div>
  </div>
</template>

<script>
import Sidebar from '@/views/layout/Sidebar.vue'
import Header from '@/views/layout/Header.vue'
import Tab from '@/views/layout/Tab.vue'
import Footer from '@/views/layout/Footer.vue'

export default {
  name: "HomeView",
  components: {
    Sidebar,
    Header,
    Tab,
    Footer
  },
  computed: {
    collapse: {
      get() {
        return true;
      },
    },
  },
};
</script>
