import Vue from 'vue'
import App from './App'
import router from './router'
Vue.config.productionTip = false
//引入vuex-store
import store from './store'

//引入ElementUI 和 css样式 使用 ElementUI 组件
import ElementUI, {MessageBox} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

//引入 ElementUI 的 Notification组件
import { Notification } from 'element-ui'

//使用按钮60s倒计时
import countDown from './util/countDown'
Vue.prototype.$countDown = countDown;

//使用预防按钮多次重复点击指令
import preventReClick from './util/preventRepeatClick'
Vue.use(preventReClick);

//使用cookie
import cookie from "./lib/cookie.js";
Vue.prototype.$cookie = cookie;

//引入nprogress 和 css样式
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

//绑定在vue的原型上
import qs from "qs"
Vue.prototype.$qs = qs;

//引入自定义工具类
import tools from "@/util/tools";
Vue.prototype.$tools = tools;

// 读取json文件
import readJson from "@/util/readJson";
Vue.prototype.$readJson = readJson;

// 读取json文件
import config from "@/config/index";
Vue.prototype.$config = config;

//引入api接口
import {globalApi} from "./api/global";
import {loginApi} from "./api/login";
import {userApi} from "./api/user";
import {courseApi} from "./api/course";
Vue.prototype.$globalApi = globalApi;
Vue.prototype.$loginApi = loginApi;
Vue.prototype.$userApi = userApi;
Vue.prototype.$courseApi = courseApi;

//向剪贴板放置内容
import VueClipboard from 'vue-clipboard2'
Vue.use(VueClipboard)

//播放器插件
import VideoPlayer  from 'vue-video-player'
import 'video.js/dist/video-js.css'
Vue.use(VideoPlayer)

// 引入和注册全局过滤器
import * as filters from './filters'
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

// 代码高亮
import Prism from 'prismjs'
import 'prismjs/themes/prism-tomorrow.css'
Prism.highlightAll()

import NewMessage from './lib/message.js'
Vue.prototype.$message = NewMessage

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

//页面顶部加载进度条
NProgress.configure({
  easing: 'ease',  // 动画方式
  speed: 500,  // 进度条递增速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.1 // 初始化时的最小百分比
})

router.beforeEach((to, from , next) => {
  store.dispatch('clearCancel');
  // 每次切换页面时，调用进度条
  NProgress.start();
  if (to.matched.some(record => record.meta.requiresAuth)) {
    //判断当前是否有登录的权限
    if (cookie.getToken() === null) {
      Notification({title: '警告', message: '请先登录！', type: 'warning', offset: 50});
      next({path: '/loginForm'});
      NProgress.done();
    }

    if (cookie.getToken() !== null && (store.state.userInfo)) {
      MessageBox.alert('当前身份已过期，请重新登录', '提示', {
        confirmButtonText: '确定',
        type: 'warning',
        callback: () => {
          cookie.clearToken();
          next({path: '/loginForm'});
          NProgress.done();
        }
      });
    }
  }
  //404页面
  if (to.matched.length ===0) {  //如果未匹配到路由，跳转404页面
    next({path: '/notfound'});
  }
  next(); // 确保一定要调用 next()
});

// 在即将进入新的页面组件前，关闭掉进度条
router.afterEach(() => {
  NProgress.done()
});
