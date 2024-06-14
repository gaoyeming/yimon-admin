import {createStore} from 'vuex'

import user from "./modules/user";
import menu from "./modules/menu";

export default createStore({
  state: {
		token: null,//当前登录用户的token信息
	},
  modules: {
    user,//用户信息存储，包含当前登录用户
    menu,//菜单信息存储
  }
})