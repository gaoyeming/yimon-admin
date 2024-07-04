<template>
  <el-container>
    <!--左侧菜单-->
    <el-aside :class="{ 'collapsed': collapse }">
      <Sidebar></Sidebar>
    </el-aside>
    <el-container>
      <!--页头-->
      <el-header>
        <Header></Header>
      </el-header>
      <!--主要内容信息-->
      <el-main>
        <!--标签-->
        <Tab></Tab>
        <!-- 使用插槽路由到具体页面 -->
        <div class="body">
          <router-view v-slot="{ Component }">
            <transition name="fade-transform" mode="out-in">
              <div>
                <component :is="Component" :key="this.$route.path" />
              </div>
            </transition>
          </router-view>
        </div>
      </el-main>
      <!--页脚-->
      <el-footer>
        <Footer></Footer>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
import Sidebar from '@/components/layout/sidebar.vue'
import Header from '@/components/layout/header.vue'
import Tab from '@/components/layout/tab.vue'
import Footer from '@/components/layout/footer.vue'

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
        return this.$store.state.menu.collapse;
      },
    },
  },
};
</script>

<style>
#app>.el-container {
  width: 100%;
  height: 100%;
}

.el-aside {
  /* background-color: #2775cf; */
  color: var(--el-text-color-primary);
  text-align: center;
  width: 200px;
  height: 100%;
  /* border: none; */

  -webkit-transition: left .3s ease-in-out;
  transition: width 0.3s ease-in-out;
}

.el-aside.collapsed {
  width: var(--el-menu--collapse-width);
}

.el-header {
  --el-header-padding: 0;
  --el-header-height: 60px;
  /* background-color: #e89a1d; */
  color: var(--el-text-color-primary);
  text-align: center;
  width: 100%;
  color: #fff;
  background: #242f42;
  /* border: none; */
  overflow: hidden;
}


.el-main {
  --el-main-padding: 0;
  /* background-color: #4cdf4c; */
  color: var(--el-text-color-primary);
  text-align: center;
  /* width: 100%;
  height: 88%; */
  /* border: none; */
  background: #eef0fc;
  overflow-x: hidden;
}

.el-footer {
  --el-footer-padding: 0;
  --el-footer-height: 60px;
  /* background-color: #b3c0d1; */
  color: var(--el-text-color-primary);
  text-align: center;
  width: 100%;
  background: #eef0fc;
  overflow: hidden;
}
</style>
