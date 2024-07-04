<template>
    <TableCustom :query-url="queryUrl" :query-form-data="queryFormData" :options="queryOptions" :columns="columns"
        :rowKey="rowKey" :tableData="page.data" :tableTotal="page.total" :pageNum="page.num" :pageSize="page.size"
        :pageCurrentChange="pageCurrentChange" :pageSizeChange="pageSizeChange" :viewFunc="viewFunc" :editFunc="editFunc"
        :delFunc="delFunc">
        <template #toolbarBtn>
            <el-button type="warning" @click="editVisible = true"><el-icon>
                    <CirclePlusFilled />
                </el-icon>新增</el-button>
        </template>

        <template #headPortraits="{ rows }">
            <el-image style="display: block;margin: auto;width: 40px;height: 40px;" :src="rows.headPortraits">
            </el-image>
        </template>
    </TableCustom>

    <el-dialog title="查看详情" v-model="viewVisible" width="700px" destroy-on-close>
        <TableDetail :title="viewTitle" :list="viewList" :row="viewRow"></TableDetail>
    </el-dialog>

    <el-dialog :title="isEdit ? '编辑' : '新增'" v-model="editVisible" width="700px" destroy-on-close
        :close-on-click-modal="false" @close="closeDialog">
        <TableEdit :edit-form-data="editFormData" :options="options" :edit="isEdit" :submitEditForm="submitEditForm" />
    </el-dialog>
</template>
    
<script>
import { ref } from 'vue';
import TableCustom from '@/components/table/table-custom.vue';
import TableDetail from '@/components/table/table-detail.vue';
import TableEdit from '@/components/table/table-edit.vue';

export default {
    name: "UserView",
    components: {
        TableCustom,
        TableDetail,
        TableEdit,
    },
    data() {
        return {
            //TableQuery相关
            queryUrl:"/crud/sys_user/gets",
            queryFormData: {
                loginName: null,
                realName: null,
            },
            queryOptions: [
                { prop: 'loginName', label: '登录名', type: 'input' },
                { prop: 'realName', label: '真实姓名', type: 'input' }
            ],
            //TableCustom相关
            columns: ref([
                { type: 'selection' },
                { type: 'index', label: '序号', align: 'center', width: '80' },
                { prop: 'loginName', label: '登录名' },
                { prop: 'headPortraits', label: '头像' },
                { prop: 'realName', label: '真实姓名' },
                { prop: 'contactEmail', label: '联系邮箱' },
                {
                    prop: 'contactMobile', label: '联系电话', formatter: (value) => {
                        const masked = value.slice(-8, -4).replace(/\d/g, '*');
                        return value.slice(0, -8) + masked + value.slice(-4);
                    }
                },
                { prop: 'contactAddress', label: '联系地址' },
                { prop: 'operator', label: '操作' },
            ]),
            rowKey: 'id',
            page: {
                data: [],
                total: 0,
                num: 1,
                size: 10,
            },
            //TableDetail
            viewVisible: false,
            viewTitle: null,
            viewList: null,
            viewRow: null,
            //TableEdit
            editFormData: {},
            options: {
                labelWidth: '100px',
                span: 24,
                list: [
                    { type: 'input', label: '登录名', prop: 'loginName', required: true },
                    { type: 'input', label: '真实姓名', prop: 'realName', required: true },
                    { type: 'input', label: '联系邮箱', prop: 'contactEmail', required: true },
                    { type: 'input', label: '联系电话', prop: 'contactMobile', required: true },
                    { type: 'input', label: '联系地址', prop: 'contactAddress', required: true },
                ]
            },
            isEdit: false,
            editVisible: false,

        }
    },
    created() {
        this.queryFunc();
    },
    methods: {
        //TableQuery
        queryFunc() {
            console.log(2222);
            // var params = {};
            // for (let key in this.query) {
            //     if (this.query[key]) {
            //         params[key] = this.query[key];
            //     }
            // }
            // if (this.page && this.page.num && this.page.num > 0 && this.page.size && this.page.size > 0) {
            //     params['@PAGE_NO@'] = this.page.num;
            //     params['@PAGE_SIZE@'] = this.page.size;
            // }
            // this.$axios.post("/crud/sys_user/gets", params).then(response => {
            //     this.page.total = response['@TOTAL@'];
            //     this.page.data = response['@RESULT@'];
            // }).catch((error) => {
            //     console.error("request error:", error);
            // });
        },
        //TableCustom
        viewFunc(row) {
            this.viewTitle = '用户信息';
            this.viewList = [
                { prop: 'loginName', label: '登录名' },
                { prop: 'realName', label: '真实姓名' },
                { prop: 'contactEmail', label: '联系邮箱' },
                { prop: 'contactMobile', label: '联系电话' },
                { prop: 'contactAddress', label: '联系地址' },
            ];
            this.viewRow = row;
            this.viewVisible = true;
        },
        editFunc(row) {
            this.editFormData = row;
            this.isEdit = true;
            this.editVisible = true;
        },
        delFunc(row) {
            var where = {
                'id': row.id
            }
            this.$axios.post("/crud/sys_user/delete", where).then(response => {
                if (response['@ROWS@'] != null && response['@ROWS@'] === 1) {
                    this.$message.success({
                        message: '删除成功',
                        duration: 3 * 1000
                    })
                } else {
                    this.$message.warning({
                        message: '删除失败，请刷新后重试',
                        duration: 3 * 1000
                    })
                }
            }).finally(() => {
                //最终执行
                setTimeout(() => {
                    this.queryFunc();
                }, 1000); // 1秒后执行回调函数
            });
        },
        pageCurrentChange(num) {
            this.page.num = num;
            this.handleSearch();
        },
        pageSizeChange(size) {
            this.page.size = size;
            this.handleSearch();
        },
        submitEditForm(formData) {
            this.$axios.post(this.isEdit ? "/crud/sys_user/post" : "/crud/sys_user/put", formData).then(response => {
                if (response['@ROWS@'] != null && response['@ROWS@'] === 1) {
                    this.$message.success({
                        message: '提交成功',
                        duration: 3 * 1000
                    });
                } else {
                    this.$message.warning({
                        message: '提交失败，请确认！',
                        duration: 3 * 1000
                    });
                }
            }).catch((error) => {
                console.error("request error:", error);
            }).finally(() => {
                //最终执行
                setTimeout(() => {
                    this.queryFunc();
                }, 1000); // 1秒后执行回调函数
            });
        },
        closeDialog() {
            this.editFormData = {};
            this.isEdit = false;
            this.editVisible = false;
        }
    }
}
</script>