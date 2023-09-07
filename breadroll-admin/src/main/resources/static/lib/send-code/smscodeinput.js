/*
author:yiroon
e-mail:yiroon@qq.com
*/
function yiuiSmsCodeBox(opts){
    var yiuismscodebox = document.createElement('div');
    opts = typeof opts == 'undefined' ? {} :opts;
    opts.maxLength = typeof opts.maxLength == 'undefined' ? 4 : opts.maxLength;
    opts.text = opts.text || '验证码已发送到您填写的手机号码上，请注意查收';
    var dn = 0;
    var initdiv = '';
    while(dn < opts.maxLength){
        initdiv+='<div></div>';
        dn++;
    }

    var codeHtml = '\
        <style>\
        .input-smscode{text-align: center;font-size: 2rem;}\
        .input-smscode>*{padding:0.5rem 0.5rem;background-color: #F1F1F1;border-radius:0.5rem;height:3rem;width:3rem;display:inline-block;line-height: 1;vertical-align: middle;}\
        .input-smscode>*:not(:last-child){margin-right:0.5rem;}\
        </style>\
        <div style="background-color: rgba(0,0,0,0.5);padding:1rem;align-items: center;justify-content: center; display:flex;display:-webkit-flex;position: fixed;left:0;top:0;width:100%;height:100%;z-index:99999;">\
        <div class="input-smscode-inner" style="background-color: #FFF;position:relative;width:500px;max-width:100%;padding:2rem;border-radius:0.5rem;">\
            <div class="input-smscode-button-close" style="position:absolute;right:1rem;top:1rem;cursor:pointer;">\
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14.09" viewBox="0 0 14 14.09">\
            <path id="路径_4" data-name="路径 4" d="M283.407,278.277l5.708-5.708a.787.787,0,0,0,0-1.079l-.045-.045a.732.732,0,0,0-1.034,0l-5.708,5.753-5.708-5.708a.732.732,0,0,0-1.034,0l-.045.045a.787.787,0,0,0,0,1.079l5.708,5.663-5.708,5.708a.787.787,0,0,0,0,1.079l.045.045a.732.732,0,0,0,1.034,0l5.708-5.753,5.708,5.708a.732.732,0,0,0,1.034,0l.045-.045a.787.787,0,0,0,0-1.079Z" transform="translate(-275.328 -271.232)" fill="#9b9b9b"/>\
            </svg>\
            </div>\
            <div style="margin:2rem;display: flex;display: -webkit-flex;align-items: center;">\
                <svg xmlns="http://www.w3.org/2000/svg" width="68.727" height="82.491" viewBox="0 0 68.727 82.491">\
                    <g id="组_1" data-name="组 1" transform="translate(-90.449 -6.024)">\
                    <path id="路径_1" data-name="路径 1" d="M389.368,73.782h15.776a14.943,14.943,0,0,1,14.9,14.9v3.557a14.943,14.943,0,0,1-14.9,14.9H389.368a14.943,14.943,0,0,1-14.9-14.9V88.681A14.943,14.943,0,0,1,389.368,73.782Zm-5.853,9.009v4.647h25.251V82.791Zm0,9.031v4.647h13.9V91.822Z" transform="translate(-260.868 -62.235)" fill="#2700f3"/>\
                    <path id="路径_2" data-name="路径 2" d="M515.523,429.8h14.028c1.013,0,2.6,1.175,1.842,1.841l-.746.652c-.762.666-.964,1.337-1.841,1.841l-12.556,7.216c-1.556.894-2.569.294-2.569-1.423v-8.286A1.847,1.847,0,0,1,515.523,429.8Z" transform="translate(-388.732 -389.228)" fill="#2700f3"/>\
                    <path id="路径_3" data-name="路径 3" d="M101.771,6.024h14.968a27.734,27.734,0,0,0-5.506,11.558h-10.9a5.339,5.339,0,0,0-5.323,5.323V63.481a5.339,5.339,0,0,0,5.323,5.323h39.7a5.339,5.339,0,0,0,5.323-5.323V50.423a27.734,27.734,0,0,0,4.56-1.6V77.193A11.356,11.356,0,0,1,138.6,88.515H101.771A11.356,11.356,0,0,1,90.449,77.193V17.346A11.355,11.355,0,0,1,101.771,6.024Zm18.413,66.551a4.786,4.786,0,1,0,4.786,4.786A4.786,4.786,0,0,0,120.184,72.575Z" transform="translate(0 0)" fill="#acc5ff"/>\
                    </g>\
                </svg>\
                <div style="flex:1;margin-left:2rem;">\
                    <p >'+opts.text+'</p>\
                </div>\
            </div>\
            <div style="text-align: center;position: relative;">\
                <div>输入收到的'+opts.maxLength+'位验证码</div>\
                <div class="input-smscode" style="margin-top:1rem;">\
                    '+initdiv+'\
                </div>\
                <input type="text" class="input-smscode-value" maxlength="'+opts.maxLength+'" style="outline: none;width:100%;height:1px;border:0;background-color: transparent;color:transparent;">\
            </div>\
            <div style="text-align:center;margin-top:2rem;">\
                <button class="input-smscode-submit" style="border:0;background:#2700F3;cursor: pointer;color:#FFF;-webkit-appearance: none;padding:1rem 3rem;border-radius:0.5rem;font-size:1.125rem;display: inline-block;outline: none;">确定</button>\
            </div>\
        </div>\
    </div>';

    yiuismscodebox.innerHTML = codeHtml;

    document.querySelector('body').appendChild(yiuismscodebox);

    var smsCodeInput =  document.querySelector('.input-smscode-value');
    var smsCodeSubmitButton = document.querySelector('.input-smscode-submit');
    var inputSmscodeCloseButton = document.querySelector('.input-smscode-button-close');
    function smsCodeInput_onkeyup(e){
        var tmpCodes = smsCodeInput.value.split('');
        var smsCodeBox = document.querySelector('.input-smscode');
        var tmphtml = '';
        smsCodeBox.innerHTML = tmphtml;
        var i = 0;
        [].forEach.call(tmpCodes,function(code){
            tmphtml+= '<div>'+code+'</div>';
            i++;
        });
        while(i < opts.maxLength){
            tmphtml+='<div></div>';
            i++;
        }
        
        smsCodeBox.innerHTML =  tmphtml ? tmphtml : '<div></div><div></div><div></div><div></div>';
    }
    function smsCodeInput_onblur(e){
        smsCodeInput.focus();
    }
    function smsCodeInput_submit(){
        if(typeof opts.submit == 'function'){
            opts.submit(smsCodeInput.value);
            //smsCodeInput_destory();
        }
    }
    function smsCodeInput_setfocus(){
        smsCodeInput.focus();
    }


    function smsCodeInput_destory(){
        document.removeEventListener('keyup',smsCodeInput_onkeyup);
        smsCodeInput.removeEventListener('blur',smsCodeInput_onblur);
        smsCodeSubmitButton.removeEventListener('click',smsCodeInput_submit);
        inputSmscodeCloseButton.removeEventListener('click',smsCodeInput_destory);
        document.querySelector('.input-smscode-inner').removeEventListener('click',smsCodeInput_setfocus);

        document.querySelector('body').removeChild(yiuismscodebox);
    }

    smsCodeInput.focus();

    document.addEventListener('keyup',smsCodeInput_onkeyup,false);
    smsCodeInput.addEventListener('blur',smsCodeInput_onblur,false);
    smsCodeSubmitButton.addEventListener('click',smsCodeInput_submit,false);
    inputSmscodeCloseButton.addEventListener('click',smsCodeInput_destory,false);
    document.querySelector('.input-smscode-inner').addEventListener('click',smsCodeInput_setfocus,false);
    return {destory:smsCodeInput_destory};
}
