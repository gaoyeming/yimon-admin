import { createApp } from 'vue'

import { ElMessage } from 'element-plus'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import App from '@/App.vue'
import router from '@/router/index.js'
import { showLoading, closeLoading } from "@/utils/loading";
import store from '@/store/index'
import axios from "@/utils/axios";
import commonUtils from '@/utils/common'

const app = createApp(App);
//设置公共属性
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$message = ElMessage;
app.config.globalProperties.$showLoading = showLoading;
app.config.globalProperties.$closeLoading = closeLoading;
app.config.globalProperties.$commonUtils = commonUtils

//设置公共方法
//公共方法-复制
app.config.globalProperties.$copyValue = function (sourceData) {
    if(sourceData === undefined || sourceData === null || sourceData.length<=0) {
        this.$message.warning({
            message: '无内容需要复制，请确认',
            duration: 3 * 1000
        })
    }
    this.$copyText(sourceData).then(() => {
        this.$message.success({
            message: '复制成功',
            duration: 3 * 1000
        })
    }).catch(() => {
        this.$message.warning({
            message: '复制失败，手动复制吧',
            duration: 3 * 1000
        })
    });
};
//公共方法-页大小变更
app.config.globalProperties.$handleSizeChange = function (uri, pageSize) {
    this.tableQuery['@PAGE_SIZE@'] = pageSize;
    this.$getTableTree(uri);
};
//公共方法-页号变更
app.config.globalProperties.$handleCurrentChange = function (uri, pageNo) {
    this.tableQuery['@PAGE_NO@'] = pageNo;
    this.$getTableTree(uri);
};
//公共方法-表单重置
app.config.globalProperties.$resetForm = function (formName) {
    this.$refs[formName].resetFields();
};
//公共方法-表单关闭
app.config.globalProperties.$handleClose = function () {
    //只对this.editForm存在的属性进行还原成null
    for (let key in this.editForm) {
        this.editForm[key] = null;
    }
    this.dialogVisible = false;
    this.dialogTitle = "新增";
    this.isAdd = true;
};
//公共方法-分页查询
app.config.globalProperties.$getTableTree = function (uri) {
    this.$axios.post(uri + "/gets", this.tableQuery).then(response => {
        this.tableTotal = response['@TOTAL@'];
        this.tableData = response['@RESULT@'];
    })
};
//公共方法-删除
app.config.globalProperties.$delHandle = function (uri, where) {
    this.$axios.post(uri + "/delete", where).then(response => {
        if (response['@ROWS@'] != null && response['@ROWS@'] === 1) {
            this.$message.success({
                message: '删除成功',
                duration: 3 * 1000
            });
        } else {
            this.$message.warning({
                message: '删除失败，请刷新后重试',
                duration: 3 * 1000
            });
        }
    }).finally(() => {
        //最终执行
        setTimeout(() => {
            this.$getTableTree(uri);
        }, 1000); // 1秒后执行回调函数
    });
};
//公共方法-编辑
app.config.globalProperties.$editHandle = function (uri, params) {
    //对this.editForm存在的属性进行还原成null
    for (let key in this.editForm) {
        this.editForm[key] = null;
    }
    //查询后台交易
    this.$axios.post(uri + '/get', params).then(response => {
        if (response['@RESULT@'] != null) {
            var model = response['@RESULT@'];
            //只对this.editForm存在的属性进行赋值
            for (let key in this.editForm) {
                if (key in model) {
                    this.editForm[key] = model[key];
                }
            }
        }
        //根据参数设置where条件
        const where = [];
        for (let key in params) {
            where.push(key);
        }
        this.editForm['@WHERE@'] = where.join(",");//表单更新条件
        this.dialogTitle = "修改";
        this.dialogVisible = true;
        this.isAdd = false;
    })
};
//公共方法-表单提交(新增，更新)
app.config.globalProperties.$submitForm = function (uri, formName) {
    this.$refs[formName].validate((valid) => {
        if (valid) {
            this.$axios.post(uri + (this.isAdd ? '/put' : '/post'), this.editForm)
                .then(response => {
                    if (response['@ROWS@'] != null && response['@ROWS@'] === 1) {
                        this.$message.success({
                            message: '提交成功',
                            duration: 3 * 1000
                        });
                    } else {
                        this.$message.warning({
                            message: '提交失败，请刷新后重试',
                            duration: 3 * 1000
                        });
                    }
                }).finally(() => {
                    this.$handleClose(formName);
                    setTimeout(() => {
                        this.$getTableTree(uri);
                    }, 1000); // 1秒后执行回调函数
                });
        } else {
            console.log('error submit!!');
            return false;
        }
    });
};


for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}


app.use(router)
app.use(store)
app.use(ElementPlus).mount('#app')
