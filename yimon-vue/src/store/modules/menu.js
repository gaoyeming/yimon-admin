export default {
	state: {
		// 折叠功能，控制菜单是否折叠
		collapse: false,
		menuList: [
			{
				"id": 1,
				"parentId": 0,
				"menuCode": "Sys-Manage",
				"menuName": "系统管理",
				"menuIcon": "Operation",
				"menuUrl": "",
				"menuPath": "",
				"menuType": 0,
				"children": [
					{
						"id": 2,
						"parentId": 1,
						"menuCode": "Sys-Menu",
						"menuName": "菜单管理",
						"menuIcon": "Menu",
						"menuUrl": "/menu",
						"menuPath": "/sys/Menu",
						"menuType": 1,
						"children": []
					},
					{
						"id": 3,
						"parentId": 1,
						"menuCode": "Sys-Role",
						"menuName": "角色管理",
						"menuIcon": "Rank",
						"menuUrl": "/role",
						"menuPath": "/sys/Role",
						"menuType": 1,
						"children": []
					},
					{
						"id": 4,
						"parentId": 1,
						"menuCode": "Sys-User",
						"menuName": "用户管理",
						"menuIcon": "Avatar",
						"menuUrl": "/user",
						"menuPath": "/sys/User",
						"menuType": 1,
						"children": []
					},
				]
			}
		],
	},
	mutations: {
	},
	actions: {
	},

}