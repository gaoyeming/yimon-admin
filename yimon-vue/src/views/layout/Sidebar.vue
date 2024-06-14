<template>
  <div class="sidebar">
    <!--unique-opened 控制只有一个菜单可以展开；其他菜单会自动折叠-->
    <el-menu class="sidebar-el-menu" :default-active="this.$route.path" :collapse="collapse" mode="vertical"
      :collapse-transition="false" background-color="#324157" text-color="#bfcbd9" active-text-color="#20a0ff"
      unique-opened router>
      <!-- 首页,所有人登录进来都可以看到无关乎权限 -->
      <el-menu-item index="/index" key="Index">
        <el-icon size="1.5em">
          <HomeFilled />
        </el-icon>
        <template #title>
          <span>首页</span>
        </template>
      </el-menu-item>
      <!-- 开始遍历菜单 -->
      <template v-for="item in menuList">
        <!-- 第一层存在子菜单 -->
        <template v-if="item.children && item.menuType == 0">
          <el-sub-menu :index="item.menuCode" :key="item.menuCode">
            <template #title>
              <component :is="item.menuIcon" style="width: 1.2em; 1.2em;" />
              <span>{{ item.menuName }}</span>
            </template>
            <template v-for="subItem in item.children">
              <!-- 第二存在子菜单 -->
              <template v-if="subItem.children && subItem.menuType == 0">
                <el-sub-menu :index="subItem.menuCode" :key="subItem.menuCode">
                  <template #title>
                    <component :is="subItem.menuIcon" style="width: 1.1em; 1.1em;" />
                    <span>{{ subItem.menuName }}</span>
                  </template>
                  <template v-for="threeItem in subItem.children">
                    <!-- 第三层-->
                    <template v-if="threeItem.menuType == 1">
                      <el-menu-item :key="threeItem.menuCode" :index="threeItem.menuUrl">
                        <component :is="threeItem.menuIcon" style="width: 1em; 1em;" />
                        <span>{{ threeItem.menuName }}</span>
                      </el-menu-item>
                    </template>
                  </template>
                </el-sub-menu>
              </template>
              <!-- 第二没有子菜单 -->
              <template v-else>
                <el-menu-item v-if="subItem.menuType == 1" :index="subItem.menuUrl" :key="subItem.menuCode">
                  <component :is="subItem.menuIcon" style="width: 1em; 1em;" />
                  <span>{{ subItem.menuName }}</span>
                </el-menu-item>
              </template>
            </template>
          </el-sub-menu>
        </template>
        <!-- 第一层没有子菜单 -->
        <template v-else>
          <el-menu-item v-if="item.menuType == 1" :index="item.menuUrl" :key="item.menuCode">
            <component :is="item.menuIcon" style="width: 1em; 1em;" />
            <template #title>{{ item.menuName }}</template>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>
    
<script>
export default {
  name: "SidebarView",
  computed: {
    collapse: {
      get() {
        return this.$store.state.menu.collapse;
      }
    },
    menuList: {
      get() {
        return this.$store.state.menu.menuList;
      }
    }
  },
  data() {
    return {
    }
  },
  created() {
  },
  methods: {
  }
}
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}

.sidebar-el-menu {
  min-height: 100%;
}
</style>