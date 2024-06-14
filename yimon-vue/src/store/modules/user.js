export default {
	state: {
		token: null,//当前登录用户的token信息
		//记录当前登录用户信息
		loginUser: {
			loginName: null,//登录名
			realName: null,//真实姓名
			contactEmail: null,//联系邮箱
			contactMobile: null,//联系电话
			contactAddress: null,//联系地址
			headPortraits: null,//用户头像
			lastLoginDate: null,//上次登录时间
			lastLoginDevice: null,//上次登录设备
		},
	},
	mutations: {
		//同步方法
		login(state, user) {
			for (let key in state.loginUser) {
				if (key in user) {
					state.loginUser[key] = user[key];
				}
			}
		},
		logout: (state) => {
			//只对state.loginUser存在的属性进行还原成null
			for (let key in state.loginUser) {
				state.loginUser[key] = null;
			}
		}
	},
	actions: {
		//异步方法
	},
}