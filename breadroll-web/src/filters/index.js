import tools from "../util/tools";

/**
 * 手机号、身份证号等中间加 *
 * @param val {string} key    15266588662
 * @return {string}   value   152****8662
 */
const hideMiddle = function(val) {
  if(!val) return ''
  let str = val.toString();
  if(str.length>10){
    let s = tools.repeat('*',str.length-7);
    return str.substr(0,3)+s+str.substr(str.length-4,str.split('').length);
  }
  return str.substr(0,3)+'****'+str.substr(7,str.split('').length);
}

const changeHourMin = function (str,second) {
  let con = 0;
  if(parseInt(second)>60){
    con = Math.floor(second / 60);
  }
  if (str !== "0" && str !== "" && str !== null) {
    if(Math.floor(str / 60)===0){
      return ((str % 60).toString().length < 2 ? "0" + (str % 60 + con).toString() : (str % 60 + con).toString())+"分钟";
    }
    return ((Math.floor(str / 60)).toString().length < 2 ? "" + (Math.floor(str / 60)).toString() :
      (Math.floor(str / 60)).toString()) + "小时" + ((str % 60).toString().length < 2 ? "0" + (str % 60 + con).toString() : (str % 60 + con).toString())+"分钟";
  }
  if(str === "0"){
    if(con>0){
      return con+"分" + ((second % 60).toString().length < 2 ? "0" + (second % 60).toString() : (second % 60).toString())+"秒";
    }
    return ((second % 60).toString().length < 2 ? "0" + (second % 60).toString() : (second % 60).toString())+"秒";
  }
  return "";
}

const timeFormate = function (str) {
  if (str !== undefined && str !== null) {
    if (str<10) {
      return "0" + str;
    }
    return str;
  }
  return "";
}

const specialTrainState = function (state) {
  switch (parseInt(state)) {
    case 0:
      return "报名成功"
    case 1:
      return "申请退课"
    case 2:
      return "退课成功"
    case 3:
      return "拒绝退课"
    case 4:
      return "已开课"
  }
}

const vipState = function (state) {
  if(state===null || state===""){
    return ""
  }
  let st = state.split(" ")[0];
  if(st.indexOf("9999")!==-1){
    return ""
  }
  return st+" 到期"
}

export {
  hideMiddle,
  changeHourMin,
  timeFormate,
  specialTrainState,
  vipState,
}
