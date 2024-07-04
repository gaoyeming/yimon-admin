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
</template>

<script>

export default {
    name: "TableQuery",
    props: {
        queryUrl: {
            type: String,
            required: false
        },
        queryFormData: {
            type: Object,
            required: true
        },
        options: {
            type: Array,
            required: true
        },
        queryFunc: {
            type: Function,
            required: false
            // default: () => {}
        }
    },
    setup(props) {
        // const queryUrl = ref(props.queryUrl);
        // const queryFunc = ref(props.queryFunc);

        // const handleQuery = () => {
        //     if (queryFunc.value) {//父组件实现了该方法则使用父组件的方法，否则使用子组件默认的
        //         props.queryFunc();
        //     } else {
        //         console.log("====222>", queryUrl);
        //     }
        // }

        // return {
        //     handleQuery
        // }

    },
    methods: {
        handleQuery() {
            if (this.queryFunc) {//父组件实现了该方法则使用父组件的方法，否则使用子组件默认的
                this.queryFunc();
            } else {
                console.log("====222>", this.queryUrl);
            }
        },
        resetForm() {
            if (!this.$refs['queryRef']) return
            this.$refs['queryRef'].resetFields();
            this.queryFunc();
        }
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
</style>