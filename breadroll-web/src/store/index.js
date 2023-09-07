import Vue from 'vue'
import Vuex from 'vuex'
import persistedState from 'vuex-persistedstate'
Vue.use(Vuex)

// 创建vuex的store
const store = new Vuex.Store({
  state: {
    userInfo: null,       //用户信息
    checkCodeKey:null,    //图片验证码Key
    axiosCancelArr:[],    //axios 封装请求拦截多次弹窗
    serialNumber:null,    //开通VIP 序列号
    vipInfo:null,         //我的vip信息
    userCoin:0,           //用户花卷币数量
    messageState:false,   //个人消息状态
  },
  // 更改store的状态
  mutations: {
    saveUserInfo(state,userInfo){
      state.userInfo = userInfo;
    },
    checkCodeKey(state,checkCodeKey){
      state.checkCodeKey = checkCodeKey;
    },
    serialNumber(state,serialNumber){
      state.serialNumber = serialNumber;
    },
    saveVipInfo(state,vipInfo){
      state.vipInfo = vipInfo;
    },
    saveUserCoin(state,userCoin){
      state.userCoin = userCoin;
    },
    saveMessageState(state,messageState){
      state.messageState = messageState;
    },

    //axios取消请求
    pushCancel(state, cancel){
      state.axiosCancelArr.push(cancel.cancelToken);
    },
    clearCancel(state) {
      state.axiosCancelArr.forEach(e => {e && e()});
      state.axiosCancelArr = []
    },
  },
  actions:{
    pushCancel({commit}, cancel){
      commit('pushCancel', cancel)
    },
    clearCancel({commit}){
      commit('clearCancel');
    }
  },

  //默认使用localStorage来固化数据
  plugins: [persistedState()]
})

export default store
