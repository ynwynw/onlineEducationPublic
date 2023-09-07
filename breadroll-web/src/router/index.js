import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
// Vue解决路由重复点击报错的问题
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
  routes: [
    {
      path: '/', redirect: "/homepage"
    }, {
      path: '/login',
      name: '登录页面',
      component: () => import("../views/login/Login"),
      children: [{
        path:'/loginForm',
        name:'登录表单',
        component: () => import("../components/login/Login_Form")
      },{
        path:'/registerForm',
        name:'注册表单',
        component: () => import("../components/login/Register_Form")
      },{
        path:'/forgotPassword',
        name:'找回密码',
        component: () => import("../components/login/Forgot_Password")
      }]
    },{
      path: '/page',
      name: '主页',
      component: () => import("../views/main/Page"),
      children: [{
        path:'/homePage',
        name:'首页',
        component: () => import("../components/main/HomePage")
      },{
        path:'/coursePage',
        name:'课程',
        component: () => import("../components/main/CoursePage")
      },{
        path:'/courseDetail',
        name:'课程详情',
        component: () => import("../components/main/CourseDetail")
      },{
        path:'/specialDetail',
        name:'特训班详情',
        component: () => import("../components/main/SpecialTrainingDetail")
      },{
        path:'/memberDetails',
        name:'会员详情',
        component: () => import("../components/main/MemberDetails")
      },{
        path:'/coursePreview',
        name:'课程预告',
        component: () => import("../components/main/CoursePreview")
      },{
        path:'/articleList',
        name:'学习锦囊',
        component: () => import("../components/main/ArticleList")
      },{
        path:'/articleDetail',
        name:'文章详情',
        component: () => import("../components/main/ArticleDetail")
      },{
        path:'/learnMaterials',
        name:'学习资料',
        component: () => import("../components/main/LearnMaterials")
      },{
        path:'/special-training',
        name:'特训班',
        component: () => import("../components/main/SpecialTraining")
      },{
        path: '/notfound',
        name: '404',
        component: () => import("../components/error/404"),
      },{
        path: '/notCourse',
        name: '未找到课程',
        component: () => import("../components/error/notCourse"),
      },{
        path:'/userPage',
        name:'个人中心',
        component: () => import("../views/user/UserPage"),
        children: [{
          path:'/accountCenter',
          name:'账户中心',
          component: () => import("../components/user/AccountCenter")
        },{
          path:'/userCenter',
          name:'用户中心',
          component: () => import("../components/user/UserCenter")
        },{
          path:'/orderCenter',
          name:'订单中心',
          component: () => import("../components/user/OrderCenter")
        },{
          path:'/mySpecial',
          name:'我的特训班',
          component: () => import("../components/user/SpecialTrainingCenter")
        },{
          path:'/breadRollGold',
          name:'花卷币',
          component: () => import("../components/user/BreadRollGold")
        },{
          path:'/courseCenter',
          name:'课程中心',
          component: () => import("../components/user/CourseCenter")
        },{
          path:'/recentLearned',
          name:'最近学习',
          component: () => import("../components/user/RecentLearned")
        },{
          path:'/personalMessage',
          name:'我的消息',
          component: () => import("../components/user/PersonalMessage")
        },{
          path:'/systemMessage',
          name:'系统消息',
          component: () => import("../components/user/SystemMessage")
        }]
      }]
    }
  ]
})
