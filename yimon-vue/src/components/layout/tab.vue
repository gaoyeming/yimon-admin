<template>
    <div class="tab">
        <el-tabs v-model="activePath" type="card" @tab-remove="removeTab" @tab-click="clickTab">
            <el-tab-pane v-for="tab in tabList" :key="tab.name" :label="tab.title" :name="tab.path"
                v-bind:closable="isIndex(tab.path)">
            </el-tab-pane>
        </el-tabs>
        <div class="tags-close-box">
            <el-dropdown @command="handleTags">
                <el-button type="primary">
                    标签选项
                    <el-icon>
                        <ArrowDown />
                    </el-icon>
                </el-button>
                <template #dropdown>
                    <el-dropdown-menu size="small">
                        <el-dropdown-item command="current">关闭当前</el-dropdown-item>
                        <el-dropdown-item command="other">关闭其他</el-dropdown-item>
                        <el-dropdown-item command="all">关闭所有</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>
    
<script>
export default {
    name: "TabView",
    computed: {
        activePath: {
            get() {
                return this.$route.path;
            }
        },
    },
    data() {
        return {
            tabList: [],
        }
    },
    watch: {
        '$route'(to, from) {
            this.addTabList(to);
        },
    },
    created() {
        this.addTabList(this.$route)
    },
    methods: {
        isIndex(targetPath) {
            //只剩首页的情况下是不允许关闭
            if (targetPath === '/index' && this.tabList.length === 1) {
                return false;
            }
            return true;
        },
        addTabList(route) {
            const isExist = this.tabList.some((item) => {
                return item.path === route.path;
            });
            if (!isExist) {
                this.tabList.push({
                    name: route.name,
                    title: route.meta.title,
                    path: route.path,
                });
            }
        },
        removeTab(targetPath) {
            //删除的是当前活动路由
            let nextPath = this.activePath;
            if (nextPath === targetPath) {
                this.tabList.forEach((tab, index) => {
                    if (tab.path === targetPath) {
                        let nextTab = this.tabList[index + 1] || this.tabList[index - 1];
                        if (nextTab) {
                            nextPath = nextTab.path;
                        }
                    }
                });
            }
            //删除的不是当前活动路由，可以直接删除
            this.tabList = this.tabList.filter(tab => tab.path !== targetPath);
            if (this.tabList.length <= 0) {
                this.$router.push({ path: "/" })
            } else {
                this.$router.push({ path: nextPath })
            }
        },
        clickTab(target) {
            this.$router.push({
                path: target.props.name,
            });
        },
        //标签选项操作
        handleTags(command) {
            switch (command) {
                case 'current':
                    this.removeTab(this.$route.path);
                    break;
                case 'other':
                    this.closeOther();
                    break;
                case 'all':
                    this.closeAll();
                    break;
            }
        },
        closeOther() {
            if (this.$route.path === '/index' && this.tabList.length === 1) {
                return;
            }
            this.tabList = this.tabList.filter(tab => tab.path === this.$route.path);
        },
        closeAll() {
            if (this.$route.path === '/index' && this.tabList.length === 1) {
                return;
            }
            this.tabList = [];
            this.$router.push({ path: "/" });
        },
    }
}
</script>

<style>
.tab {
    position: relative;
    overflow: hidden;
    background: #fff;
}

.tab {
    .el-tabs__header {
        margin-bottom: 0;
    }

    .el-tabs__nav {
        height: 28px;
    }

    .el-tabs__nav-next,
    .el-tabs__nav-prev {
        line-height: 32px;
    }

    &.el-tabs {
        --el-tabs-header-height: 28px;
    }
}

.tags-close-box {
    position: absolute;
    right: 0;
    top: 0;
    box-sizing: border-box;
    padding-top: 3px;
    padding-right: 5px;
    text-align: center;
    background: #fff;
    box-shadow: -3px 0 15px 3px rgba(0, 0, 0, 0.1);
    z-index: 10;
}
</style>