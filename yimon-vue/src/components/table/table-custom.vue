<template>
    <div class="query-container">
        <el-form ref="queryRef" :model="queryFormData" :inline="true">
            <el-form-item v-for="item in options" :label="item.label" :prop="item.prop">
                <!-- 文本框、下拉框、日期框 -->
                <el-input v-if="item.type === 'input'" v-model="queryFormData[item.prop]" :disabled="item.disabled"
                    :placeholder="item.placeholder" clearable :style="item.style"></el-input>
                <el-select v-else-if="item.type === 'select'" v-model="queryFormData[item.prop]" :disabled="item.disabled"
                    :placeholder="item.placeholder" clearable :style="item.style">
                    <el-option v-for="opt in item.opts" :label="opt.label" :value="opt.value"></el-option>
                </el-select>
                <el-date-picker v-else-if="item.type === 'date'" type="date" v-model="queryFormData[item.prop]"
                    :value-format="item.format" :style="item.style"></el-date-picker>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="handleQuery"><el-icon>
                        <Search />
                    </el-icon>查询</el-button>
                <el-button @click="resetForm">
                    <el-icon>
                        <Refresh />
                    </el-icon>重置</el-button>
            </el-form-item>
        </el-form>
    </div>

    <div class="table-toolbar" v-if="hasToolbar">
        <div class="table-toolbar-left">
            <slot name="toolbarBtn"></slot>
        </div>
        <div class="table-toolbar-right">
            <template v-if="multipleSelection.length > 0">
                <el-tooltip effect="dark" content="删除选中" placement="top">
                    <el-icon class="columns-setting-icon" @click="delSelection(multipleSelection)">
                        <Delete />
                    </el-icon>
                </el-tooltip>
                <el-divider direction="vertical" />
            </template>
            <el-tooltip effect="dark" content="刷新" placement="top">
                <el-icon class="columns-setting-icon" @click="refresh">
                    <Refresh />
                </el-icon>
            </el-tooltip>
            <el-divider direction="vertical" />
            <el-tooltip effect="dark" content="列设置" placement="top">
                <el-dropdown :hide-on-click="false" size="small" trigger="click">
                    <el-icon class="columns-setting-icon">
                        <Setting />
                    </el-icon>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item v-for="c in columns">
                                <el-checkbox v-model="c.visible" :label="c.label" />
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-tooltip>
        </div>
    </div>
    <el-table :data="tableData" :style="{ width: '100%' }" :row-key="rowKey" stripe border
        @selection-change="handleSelectionChange" table-layout="auto">
        <template v-for="item in columns" :key="item.prop">
            <el-table-column v-if="item.visible" :prop="item.prop" :label="item.label" :width="item.width" :type="item.type"
                :align="item.align || 'center'">

                <template #default="{ row, column, $index }" v-if="item.type === 'index'">
                    {{ getIndex($index) }}
                </template>
                <template #default="{ row, column, $index }" v-if="!item.type">
                    <slot :name="item.prop" :rows="row" :index="$index">
                        <template v-if="item.prop == 'operator'">
                            <el-button type="warning" size="small" @click="viewFunc(row)">
                                <el-icon>
                                    <View />
                                </el-icon>查看
                            </el-button>
                            <el-button type="primary" size="small" @click="editFunc(row)">
                                <el-icon>
                                    <Edit />
                                </el-icon>编辑
                            </el-button>
                            <el-button type="danger" size="small" @click="handleDelete(row)">
                                <el-icon>
                                    <Delete />
                                </el-icon>删除
                            </el-button>
                        </template>
                        <span v-else-if="item.formatter">
                            {{ item.formatter(row[item.prop]) }}
                        </span>
                        <span v-else>
                            {{ row[item.prop] }}
                        </span>
                    </slot>
                </template>
            </el-table-column>
        </template>
    </el-table>
    <el-pagination v-if="hasPagination" size="mini" layout="total, sizes, prev, pager, next, jumper" :total="tableTotal"
        :current-page.sync="pageNum" :page-size.sync="pageSize" :page-sizes="[10, 20, 50, 100]"
        @current-change="pageCurrentChange" @size-change="pageSizeChange" :background="true">
    </el-pagination>
</template>

<script>
import { ref } from 'vue';
import { ElMessageBox } from 'element-plus';

export default {
    name: "TableCustom",
    props: {
        queryUrl: {//查询url
            type: String,
            required: false
        },
        queryFormData: {//查询条件对象
            type: Object,
            required: true
        },
        options: {//查询条件输入
            type: Array,
            required: true
        },
        queryFunc: {//查询方法
            type: Function,
            required: false
            // default: () => {}
        },

        hasToolbar: {//是否存在插槽
            type: Boolean,
            default: true
        },
        //table相关
        columns: {//table展示的列
            type: Array,
            required: true,
            default: []
        },
        tableData: {//table展示的数据
            type: Array,
            default: []
        },
        rowKey: {//table每行数据的key，默认值是id
            type: String,
            default: 'id'
        },
        multipleSelection: {//多选数据
            type: Array,
            default: []
        },
        //分页相关
        hasPagination: {//是否展示分页
            type: Boolean,
            default: true
        },
        tableTotal: {//表单数据总数
            type: Number,
            default: 0
        },
        pageNum: {//页号
            type: Number,
            default: 1
        },
        pageSize: {//每页条数
            type: Number,
            default: 10
        },
        pageCurrentChange: {//页码变更事件
            type: Function,
            default: () => { }
        },
        pageSizeChange: {//每页条数变更事件
            type: Function,
            default: () => { }
        },
        //其他功能
        refresh: {//刷新
            type: Function,
            default: () => { }
        },
        viewFunc: {//查看详情
            type: Function,
            default: () => { }
        },
        editFunc: {//编辑
            type: Function,
            default: () => { }
        },
        delFunc: {//删除
            type: Function,
            default: () => { }
        },
    },
    setup(props) {
        //table选中的数据
        const multipleSelection = ref(props.multipleSelection);
        // 当选择项发生变化时会触发该事件
        const handleSelectionChange = (selection) => {
            multipleSelection.value = selection;
        }

        const columns = ref(props.columns);
        columns.value.forEach((item) => {
            if (item.visible === undefined) {
                item.visible = true
            }
        });

        return {
            columns,
            multipleSelection,
            handleSelectionChange
        }
    },
    methods: {
        handleQuery() {
            if (this.queryFunc) {//父组件实现了该方法则使用父组件的方法，否则使用子组件默认的
                this.queryFunc();
            } else {
                var params = {};
                for (let key in this.queryFormData) {
                    if (this.queryFormData[key]) {
                        params[key] = this.queryFormData[key];
                    }
                }
                if (this.pageNum && this.pageNum > 0 && this.pageSize && this.pageSize > 0) {
                    params['@PAGE_NO@'] = this.pageNum;
                    params['@PAGE_SIZE@'] = this.pageSize;
                }
                this.$axios.post(this.queryUrl, params).then(response => {
                    this.tableTotal = response['@TOTAL@'];
                    this.tableData = response['@RESULT@'];
                }).catch((error) => {
                    console.error("request error:", error);
                });
            }
        },
        resetForm() {
            if (!this.$refs['queryRef']) return
            this.$refs['queryRef'].resetFields();
            this.queryFunc();
        },

        getIndex(index) {
            return index + 1 + (this.pageNum - 1) * this.pageSize;
        },
        handleDelete(row) {//删除单笔
            ElMessageBox.confirm('确定删除？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 确认操作
                this.delFunc(row);
            }).catch(() => {
                // 取消操作
            });
        },
        delSelection(multipleSelection) {//删除选中
            if (!multipleSelection || multipleSelection.length <= 0) {
                return; //未选中数据的情况下不进行操作
            }
            // 确认操作
            ElMessageBox.confirm('确定删除选中数据？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                multipleSelection.forEach((singleSelection) => {
                    this.delFunc(singleSelection);
                });
            }).catch(() => {
                // 取消操作
            });
        },
    }
}
</script>

<style>
.query-container {
    text-align: left;
    padding: 20px 30px 0;
    margin-bottom: 10px;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 5px
}

.table-toolbar {
    text-align: center;
    width: 100%;
    height: 50px;
    background-color: #fff;
}

.table-toolbar-left {
    display: flex;
    justify-content: space-between;
    align-items: center;
    text-align: center;
    float: left;
    height: 100%;
    padding-left: 20px;
}

.table-toolbar-right {
    display: flex;
    justify-content: space-between;
    align-items: center;
    text-align: center;
    float: right;
    height: 100%;
    padding-right: 20px;
}

.columns-setting-icon {
    display: block;
    font-size: 18px;
    cursor: pointer;
    color: #676767;
}

.el-table {
    overflow: auto;
}
</style>