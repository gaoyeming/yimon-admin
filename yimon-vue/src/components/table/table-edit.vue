<template>
	<el-form ref="editRef" :model="editFormData" :rules="rules" :label-width="options.labelWidth">
		<el-row>
			<el-col :span="options.span" v-for="item in options.list">
				<el-form-item :label="item.label" :prop="item.prop">
					<!-- 文本框、数字框、下拉框、日期框、开关、上传 -->
					<el-input v-if="item.type === 'input'" v-model="editFormData[item.prop]" :disabled="item.disabled"
						:placeholder="item.placeholder" clearable></el-input>
					<el-input-number v-else-if="item.type === 'number'" v-model="editFormData[item.prop]"
						:disabled="item.disabled" controls-position="right"></el-input-number>
					<el-select v-else-if="item.type === 'select'" v-model="editFormData[item.prop]"
						:disabled="item.disabled" :placeholder="item.placeholder" clearable>
						<el-option v-for="opt in item.opts" :label="opt.label" :value="opt.value"></el-option>
					</el-select>
					<el-date-picker v-else-if="item.type === 'date'" type="date" v-model="editFormData[item.prop]"
						:value-format="item.format"></el-date-picker>
					<el-switch v-else-if="item.type === 'switch'" v-model="editFormData[item.prop]"
						:active-value="item.activeValue" :inactive-value="item.inactiveValue" :active-text="item.activeText"
						:inactive-text="item.inactiveText"></el-switch>
					<el-upload v-else-if="item.type === 'upload'" class="avatar-uploader" action="#" :show-file-list="false"
						:on-success="handleAvatarSuccess">
						<img v-if="editFormData[item.prop]" :src="editFormData[item.prop]" class="avatar" />
						<el-icon v-else class="avatar-uploader-icon">
							<Plus />
						</el-icon>
					</el-upload>
					<slot :name="item.prop" v-else>

					</slot>
				</el-form-item>
			</el-col>
		</el-row>

		<el-form-item>
			<el-button type="primary" @click="saveEdit">保 存</el-button>
		</el-form-item>
	</el-form>
</template>

<script>
export default {
	name: "TableEdit",
	props: {
		edit: {//是否是编辑操作
			type: Boolean,
			default: false,
			required: true
		},
		editFormData: {//表单数据
			type: Object,
			default: {},
			required: true,
		},
		rules: {//表单规则
			type: Array,
			default: [],
			required: true
		},
		options: {//表单标签说明
			type: Object,
			default: {},
			required: true,
		},
		submitEditForm: {//表单提交，及更新或者新增操作提交
			type: Function,
			default: () => { }
		},
	},
	setup(props) {
		//表单验证规则
	},
	methods: {
		saveEdit() {
			if (!this.$refs['editRef']) return;
			this.$refs['editRef'].validate(valid => {
				if (!valid) return false;
				this.submitEditForm(this.editFormData);
			});
		}
	}
}
</script>

<style>
.avatar-uploader .el-upload {
	border: 1px dashed var(--el-border-color);
	border-radius: 6px;
	cursor: pointer;
	position: relative;
	overflow: hidden;
	transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
	border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
	font-size: 28px;
	color: #8c939d;
	width: 178px;
	height: 178px;
	text-align: center;
}
</style>