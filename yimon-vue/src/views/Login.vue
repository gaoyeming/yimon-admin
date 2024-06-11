<template>
    <div class="login">
        <!-- 背景图片 -->
        <div class="login-bg">
            <el-image style="width: 300px; height: 300px" :src="require('@/assets/img/logo.png')"></el-image>
        </div>
        <!-- 竖线分割 -->
        <el-divider direction="vertical"></el-divider>
        <!-- 登录表单 -->
        <div class="login-form">
            <el-form label-position="right" :rules="loginRules" label-width="80px" :model="loginForm" ref="loginRef">
                <el-form-item label="用户名" prop="loginName">
                    <el-input v-model="loginForm.loginName" placeholder="请输入用户名"></el-input>
                    <span class="input-right-icon">
                        <el-icon>
                            <User />
                        </el-icon>
                    </span>
                </el-form-item>
                <el-form-item label="密码" prop="loginPassword">
                    <el-input :type="loginPassword_input_type" v-model="loginForm.loginPassword"
                        placeholder="请输入密码"></el-input>
                    <span class="input-right-icon" @click="showLoginPassword">
                        <component :is="loginPassword_input_icon" style="width: 1em; 1em;" />
                    </span>
                </el-form-item>
                <el-form-item label="验证码" prop="captcha">
                    <el-input v-model="loginForm.captcha" style="width: 150px; float: left" maxlength="6"
                        placeholder="请输入验证码"></el-input>
                    <el-image :src="captchaImg" @click="getCaptchaImg()" style="width: 120px; float: right"></el-image>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="login('loginRef')">登录</el-button>
                    <el-button @click="$resetForm('loginRef')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
  
<script>
export default {
    name: "LoginView",
    data() {
        return {
            loginForm: {
                loginName: "admin",
                loginPassword: "123456",
                captchaType: "",//验证码类型
                captchaKey: "",//验证码key
                captcha: ""//验证码
            },
            loginRules: {
                loginName: [{ required: true, message: "请输入用户名", trigger: "blur" }],
                loginPassword: [{ required: true, message: "请输入密码", trigger: "blur" }],
                // captchaType: [{ required: true, message: "验证码类型不能为空", trigger: "submit" }],
                // captchaKey: [{ required: true, message: "验证码key不能为空", trigger: "submit" }],
                captcha: [
                    { required: true, message: "请输入验证码", trigger: "blur" },
                    { min: 1, max: 5, message: "验证码为1~5个字符", trigger: "blur" },
                ],
            },
            captchaImg: "",
            loginPassword_input_type: "password",
            loginPassword_input_icon: "Hide"
        };
    },
    created() {
        this.getCaptchaImg();
    },
    methods: {
        //获取验证码
        getCaptchaImg() {
            this.loginForm.captchaType = "login";
            this.loginForm.captchaKey = this.$commonUtils.getUuid();

            const params = {
                captchaType:this.loginForm.captchaType,
                captchaKey:this.loginForm.captchaKey
            }
            this.$axios.post('/verify/captcha/create', params).then(response => {
                if (response["captchaImg"] != null) {
                    this.captchaImg = "data:image/png;base64," + response["captchaImg"];
                }
            })
        },
        //显示或隐藏密码
        showLoginPassword() {
            if (this.loginPassword_input_type === "password") {
                this.loginPassword_input_icon = "View";
                this.loginPassword_input_type = "";
            }
            else {
                this.loginPassword_input_icon = "Hide";
                this.loginPassword_input_type = "password";
            }
        },
        //登录提交
        login(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$axios.post('/user/login', this.loginForm)
                        .then(response => {
                            //登录成功
                            this.$message.success({
                                message: response.loginName + "【登录成功】",
                                duration: 3 * 1000
                            });
                        }).finally(() => {
                            //刷新验证码
                            this.getCaptchaImg();
                        });
                }
            });
        }
    }
};
</script>
<style>
.login {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
}

.login-bg {
    display: inline-block;
    /* 使元素可以被text-align影响 */
    text-align: center;
    /* 使内部文本水平居中 */
}

.el-divider {
    border-top-width: 4px;
    /* 控制竖线的厚度 */
    height: 200px;
}

.input-right-icon {
    position: absolute;
    right: 10px;
    top: 4px;
    font-size: 16px;
    color: #069ff1;
    cursor: pointer;
    user-select: none;
}
</style>