<template>
    <div class="header">
        <div class="header-left">
            <!-- logo标记 -->
            <div class="logo">
                <img src="@/assets/img/logo.png" alt="" width="30px" />
                <span style="font-size: 24px;">后台管理系统</span>
            </div>

            <!-- 折叠按钮 -->
            <div class="collapse-btn" @click="collapseChage">
                <el-icon v-if="!collapse">
                    <Fold />
                </el-icon>
                <el-icon v-else>
                    <Expand />
                </el-icon>
            </div>
        </div>
        <div class="header-right">
            <!-- 消息中心 -->
            <div class="btn-bell" @click="router.push('/tips')">
                <el-tooltip effect="dark" :content="message ? `有${message}条未读消息` : `消息中心`" placement="bottom">
                    <el-icon color="white">
                        <Bell />
                    </el-icon>
                </el-tooltip>
                <span class="btn-bell-badge" v-if="message"></span>
            </div>
            <!-- 全屏 -->
            <div class="btn-fullscreen" @click="setFullScreen">
                <el-tooltip effect="dark" content="全屏" placement="bottom">
                    <el-icon color="white">
                        <FullScreen />
                    </el-icon>
                </el-tooltip>
            </div>
            <!-- 用户头像照片 -->
            <div class="user-avator">
                <el-avatar :size="30"
                    :src="currentUser.headPortraits ? currentUser.headPortraits : require('@/assets/img/user/header_default.jpeg')" />
            </div>
            <!-- 下拉菜单 -->
            <el-dropdown class="user-name" trigger="click" @command="(command) => handleCommand(command)">
                <span class="el-dropdown-link">
                    {{ currentUser.realName }}
                    <el-icon>
                        <CaretBottom />
                    </el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item command="change">个人中心</el-dropdown-item>
                        <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>
    
<script>
export default {
    name: "HeaderView",
    data() {
        return {
            message: 2,
            collapseChage: false,
            currentUser: {
                loginName: 'admin',
                realName: '管理员',
                headPortraits: ''
            }
        }
    },
    created() {
    },
    methods: {
        setFullScreen() {
            if (document.fullscreenElement) {
                document.exitFullscreen();
            } else {
                document.body.requestFullscreen.call(document.body);
            }
        },
    }
}
</script>

<style>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    /* font-size: 22px; */
    color: #fff;
    background: #242f42;
}

.header-left {
    display: flex;
    justify-content: space-between;
    align-items: center;
    text-align: center;
    float: left;
    height: 100%;
    width: 12%;
    /* padding-left: 25px; */
}

.logo {
    display: flex;
    align-items: center;
    float: left;
    /* width: 35px; */
}

.collapse-btn {
    display: flex;
    justify-content: center;
    align-items: center;
    float: right;
    height: 100%;
    padding: 0 10px;
    cursor: pointer;
    opacity: 0.8;
    font-size: 25px;
}

.header-right {
    display: flex;
    justify-content: space-between;
    align-items: center;
    float: right;
    padding-right: 25px;
    height: 100%;
}

.user-name .el-dropdown-link {
    color: #fff;
    cursor: pointer;
    display: flex;
    align-items: center;
}

.user-name .el-dropdown-menu__item {
    text-align: center;
}

.user-avator {
    margin: 0 10px 0 20px;
}

.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    cursor: pointer;
    display: flex;
    align-items: center;
    margin: 0 5px;
    /* font-size: 20px; */
}

.btn-bell {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    cursor: pointer;
    display: flex;
    align-items: center;
    margin: 0 5px;
}

.btn-bell-badge {
    position: absolute;
    right: 4px;
    top: 0px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
}
</style>
