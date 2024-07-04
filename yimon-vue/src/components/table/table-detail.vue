<template>
	<el-descriptions :title="title" :column="column" border>
		<el-descriptions-item v-for="item in list" :span="item.span">
			<template #label> {{ item.label }} </template>
			<slot :name="item.prop" :rows="row">
				{{ item.value || row[item.prop] }}
			</slot>
		</el-descriptions-item>
	</el-descriptions>
</template>

<script>
export default {
	name: "TableDetail",
	props: {
		title: {
			type: String,
			required: true
		},
		column: {
			type: Number,
			default: 2,
		},
		list: {
			type: Array,
			default: [],
			required: true
		},
		row: {
			type: Object,
			default: {},
			required: true,
		}

	}
}
</script>