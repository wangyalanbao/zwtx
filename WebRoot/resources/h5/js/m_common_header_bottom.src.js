
(function(){

    /**
     *  m 版 通用头尾
     *
     * 调用方式参照 eg1 ,eg2  ,建议使用eg1方式 方便个性化扩展
     * eg:
     *
     * 	//通用头
     *  var headerArg = {hrederId : 'm_common_header', sid : '2121312', isShowShortCut : false, selectedShortCut : '4'};
     var mchb = new MCommonHeaderBottom();
     mchb.header(arg);

     //通用尾
     var footerPlatforms = mCommonBottom.platformEnum(sid).enum4;
     var mchb = new MCommonHeaderBottom();
     mchb.bottom(bottomArg);

     //jd通用Tip
     var tipArg = {tipId : 'm_common_tip', sid : '555444', isShowTip : true, isfloat: true};
     var mchb = new MCommonHeaderBottom();
     mchb.jdTip(tipArg);

     * 2015-3-13
     * lizhenyou
     *
     * @version 1.01
     *
     */

    MCommonHeaderBottom = function(config){

        var version = '1.02';

        var pageURL = location.href.split('&')[0];

        //静态变量
        var option = {

            useDefaultImp : true,

            css : ['http://st.360buyimg.com/common/commonH_B/css/header.css?v=' + version],

            js : [
                'http://st.360buyimg.com/common/commonH_B/js/m_common.js?v=' + version,
                'http://st.360buyimg.com/m/js/2014/module/plugIn/downloadAppPlugIn.js?v=jd2015030522',
            ]

        };

        if(config){
            if(typeof(config.useDefaultImp) == "boolean"){
                option.useDefaultImp = config.useDefaultImp;
            }
            if(config.css)
                option.css = config.css;
            if(config.js)
                option.js = config.js;
        }

        /**
         *
         * 通用头尾公共方法
         *
         * lizhenyou
         * 2015-3-10
         */
        var mBaseFunction = {

            //是否已经引入css
            _isIncludedCss : false,

            debug : false,

            //获取sid param 字符串
            getSid : function(tag,sid){
                if(this._isNotBlank(sid)){
                    return tag + 'sid=' + sid;
                }
                return '';
            },

            /**
             *
             * @param args 参数
             * @returns 返回处理后结果
             * eg:
             *  var args = {
					templete : '<li> <a href="http://ok.jd.com/m/index-{arg1}.htm">{arg2}</a>',

					param : {arg1: 111,arg2: '商品名称'}

				}

             var str = templeteOutput(args);

             */
            templeteOutput : function(args){

                var templete = args.templete;
                var param = args.param;

                var out = templete;

                for(var key in param){
                    out  = out.replace(new RegExp("{"+key+"}", "g"), param[key]);
                }

                return out;
            },

            //是否有该class
            hasClass : function (obj, cls) {
                return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
            },

            //添加class
            addClass : function (obj, cls) {
                if (!this.hasClass(obj, cls)) obj.className += " " + cls;
            },

            //删除class
            removeClass : function (obj, cls) {
                if (this.hasClass(obj, cls)) {
                    var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
                    obj.className = obj.className.replace(reg, ' ');
                }
            },

            //批量删除class
            removeClassBatch : function(arrayIds,cls){
                for(var v in arrayIds){
                    this.removeClass(this.getElementById(arrayIds[v]),cls);
                }
            },

            //引入Css
            includeCss : function(){

                if(!this._isIncludedCss){

                    for(var i=0;i<option.css.length;i++){
                        this._includeCss(option.css[i]);
                    }

                    this._isIncludedCss = true;
                }
            },

            //生成html
            innerHtml : function(arg){
                var sid = arg.sid;
                var htmlStr = arg.htmlStr;
                var prefix = arg.prefix;
                var divId = arg.divId;

                var showHtml = this.templeteOutput({templete:htmlStr, param:{prefix:prefix, sid:sid }});
                this.getElementById(divId).innerHTML = showHtml;
            },

            //获取element元素
            getElementById : function(id){
                return document.getElementById(id);
            },

            addEvent : function(objId,eventName,fun){
                var obj = this.getElementById(objId);

                if(obj){
                    obj.addEventListener(eventName, fun, false);
                }
            },

            removeEvent : function(objId,eventName,fun){
                var obj = this.getElementById(objId);

                if(obj){
                    obj.removeEventListener(eventName, fun, false);
                }
            },

            divShow : function(obj){
                obj.style.display = "";

            },

            divHide : function(obj){
                obj.style.display = "none";
            },

            loadJS : function(url,callback){
                var s=document.createElement("script");
                s.type="text/javascript";
                s.src=url;
                s.onload=s.onreadystatechange=function(){
                    if (this.readyState && this.readyState=="loading"){
                        return;
                    }
                    callback();
                };
                s.onerror=function(){
                    head.removeChild(s);
                    callback();
                };
                var head=document.getElementsByTagName("head")[0];
                head.appendChild(s);
            },

            runFunction : function(fun){
                if(fun){
                    fun();
                }
            },

            loadDownloadAppPlugIn : function(fun){
                if(option.useDefaultImp){
                    this.loadJS(option.js[1],function(){
                        fun();
                    });
                }
            },

            executeDefaultFunCtion : function(fun,parameter){
                if(fun && option.useDefaultImp){
                    if(parameter){
                        fun(parameter);
                    }else{
                        fun();
                    }
                }
            },

            _includeCss : function(filePath){

                var _isInArray = function(array,str){
                    for(var i=0;i<array.length;i++){
                        if(array[i].href && array[i].href.indexOf(str) != -1){
                            return true;
                        }
                    }

                    return false;
                };

                var links = document.getElementsByTagName("link");
                if(_isInArray(links,filePath)){
                    return true;
                }

                var s = document.createElement("link");
                s.setAttribute('rel','stylesheet');
                s.setAttribute('type','text/css');
                s.setAttribute('charset','utf-8');
                s.setAttribute('href',filePath);

                var head=document.getElementsByTagName("head")[0];
                head.appendChild(s);
                //document.write('<link rel="stylesheet" charset="utf-8" href="'+filePath+'" type="text/css" />');
            },

            _isNotBlank : function(v){
                if(v && v != ''){
                    return true;
                }
                return false;
            },

            printDeugInfo : function(msg){
                if(this.debug){
                    console.log(msg);
                }
            }
        };

        /**
         *
         * jd m页通用头
         *
         * lizhenyou
         * 2015-3-10
         */

        var mCommonHeader = {

            //快捷键 id
            //['m_common_header_shortcut_m_index','m_common_header_shortcut_category_search',
            //'m_common_header_shortcut_p_cart','m_common_header_shortcut_h_home'],
            shortcutIds : [],

            //eg : {hrederId : 'm_common_header', sid : '2121312', isShowShortCut : false, selectedShortCut : 2}
            args : {hrederId : 'm_common_header', sid : '', isShowShortCut : false, selectedShortCut : ''},

            //创建 通用头
            create : function(config){
                this.args = config;

                if(!this._validate()){
                    return false;
                }

                //生成html
                var sid = mBaseFunction.getSid('?',this.args.sid);
                var htmlStr = this.getTempleteHtml();
                var prefix = this.args.hrederId;
                var divId = this.args.hrederId;

                this._initShortcutId(prefix);

                mBaseFunction.innerHtml({divId:divId, sid:sid, htmlStr:htmlStr, prefix:prefix});

                //设置当前选择快捷键
                this._setCutrrentSelectedSyle(this.args.selectedShortCut);

                //隐藏或显示快捷键
                this._initShortCutShow(this.args.isShowShortCut,prefix);

                //添加事件
                var that = this;
                mBaseFunction.addEvent(prefix + '_goback','click',function(){
                    //alert('goback');
                    mBaseFunction.runFunction(that.args.onClickGoback);
                    mBaseFunction.executeDefaultFunCtion(pageBack);
                });

                mBaseFunction.addEvent(prefix + '_jdkey','click',function(){
                    //alert('jdkey');
                    mBaseFunction.runFunction(that.args.onClickJdkey);
                    mBaseFunction.executeDefaultFunCtion(that.shortCutShowHide,prefix);
                });

                mBaseFunction.addEvent(prefix + '_shortcut_m_index','click',function(){
                    //todo
                    mBaseFunction.runFunction(that.args.onClickIndex);
                    //alert('shortcut_m_index');
                });

                mBaseFunction.addEvent(prefix + '_shortcut_category_search','click',function(){
                    //todo
                    mBaseFunction.runFunction(that.args.onClickSearch);
                    //alert('_shortcut_category_search');
                });

                mBaseFunction.addEvent(prefix + '_shortcut_p_cart','click',function(){
                    //todo
                    mBaseFunction.runFunction(that.args.onClickCart);
                    //alert('shortcut_p_cart');
                });

                mBaseFunction.addEvent(prefix + '_shortcut_h_home','click',function(){
                    //todo
                    mBaseFunction.runFunction(that.args.onClickHome);
                    //alert('_shortcut_h_home');
                });

            },

            //获取快捷键id
            getShortcutId : function(index){
                return this.shortcutIds[index - 1];
            },

            getTempleteHtml : function(){
                var htmlStr =
                    '<header class=\"jd-header\">'
                    + '    <div class=\"jd-header-bar\">'
                    + '        <div id=\"{prefix}_goback\" class=\"jd-header-icon-back\"><span></span></div>'
                    + '        <div class=\"jd-header-title\">{title}</div>'
                    + '        <div report-eventid="MCommonHead_NavigateButton"  report-eventparam="" page_name="'+ pageURL +'" id="{prefix}_jdkey" class=\"jd-header-icon-shortcut J_ping\"><span></span></div>'
                    + '    </div>'
                    + '    <ul id="{prefix}_shortcut" class=\"jd-header-shortcut\" style=\"display: none\">'
                    + '        <li id=\"{prefix}_shortcut_m_index\">'
                    + '            <a class="J_ping" report-eventid="MCommonHead_home"  report-eventparam="" page_name="'+ pageURL +'" href=\"http://m.jd.com/index.html{sid}\">'
                    + '                <span class=\"shortcut-home\"></span>'
                    + '                <strong>首页</strong>'
                    + '            </a>'
                    + '        </li>'
                    + '        <li class="J_ping" report-eventid="MCommonHead_CategorySearch"  report-eventparam="" page_name="'+ pageURL +'" id=\"{prefix}_shortcut_category_search\">'
                    + '            <a href=\"http://m.jd.com/category/all.html{sid}\">'
                    + '                <span class=\"shortcut-categories\"></span>'
                    + '                <strong>分类搜索</strong>'
                    + '            </a>'
                    + '        </li>'
                    + '        <li class="J_ping" report-eventid="MCommonHead_Cart"  report-eventparam="" page_name="'+ pageURL +'"  id=\"{prefix}_shortcut_p_cart\">'
                    + '            <a href=\"http://p.m.jd.com/cart/cart.action{sid}\" id=\"html5_cart\">'
                    + '                <span class=\"shortcut-cart\"></span>'
                    + '                <strong>购物车</strong>'
                    + '            </a>'
                    + '        </li>'
                    + '        <li id=\"{prefix}_shortcut_h_home\">'
                    + '            <a class="J_ping" report-eventid="MCommonHead_MYJD"  report-eventparam="" page_name="'+ pageURL +'"  href=\"http://home.m.jd.com/myJd/home.action{sid}\">'
                    + '                <span class=\"shortcut-my-account\"></span>'
                    + '                <strong>我的京东</strong>'
                    + '            </a>'
                    + '        </li>'
                    + '    </ul>'
                    + '</header>';

                var title = '';
                if(this.args.title){
                    title = this.args.title;
                }

                htmlStr = mBaseFunction.templeteOutput({templete:htmlStr, param:{title:title}});

                return htmlStr;
            },

            //快捷键 显示-隐藏 交替
            shortCutShowHide : function(prefix){
                var obj = mBaseFunction.getElementById(prefix + '_shortcut');
                var d = obj.style.display;

                if(d == "none") {
                    mBaseFunction.divShow(obj);
                }else{
                    mBaseFunction.divHide(obj);
                }
            },

            //初始化 快捷键 id
            _initShortcutId : function(prefix){
                var arr = [prefix+'_shortcut_m_index', prefix+'_shortcut_category_search',
                    prefix+'_shortcut_p_cart', prefix+'_shortcut_h_home'];
                this.shortcutIds = arr;
            },

            //初始化快速捷键 显示与否
            _initShortCutShow : function(f,prefix){
                var obj = mBaseFunction.getElementById(prefix + '_shortcut');
                if(f){
                    mBaseFunction.divShow(obj);
                }else{
                    mBaseFunction.divHide(obj);
                }
            },

            //设置当前选中样式
            _setCutrrentSelectedSyle : function(index){

                mBaseFunction.removeClassBatch(this.shortcutIds,'current');

                if(mBaseFunction._isNotBlank(this.getShortcutId(index))){
                    mBaseFunction.addClass(mBaseFunction.getElementById(this.getShortcutId(index)),'current');
                }

            },

            //检查完整性
            _validate : function(){
                var f = true;

                if(!this._isHasHeaderId()){
                    f = false;
                }

                return f;
            },

            //检验是否有headerId
            _isHasHeaderId : function(){
                var f = false;

                if(mBaseFunction.getElementById(this.args.hrederId)){
                    f = true;
                }else{
                    mBaseFunction.printDeugInfo('请正确拼写或添加通用头ID.');
                }

                return f;

            }

        };

        /**

         *  jd m页通用尾
         *
         *  lizhenyou
         *  2015-3-10
         */
        var mCommonBottom = {

            //eg : {bottomId : 'm_common_bottom', sid : '', isShowBottom : true};
            args : {bottomId : 'm_common_bottom', sid : '', isShowBottom : true,
                footerPlatforms : ''},

            //创建通用尾
            create : function(config){
                this.args = config;

                if(!this._validate()){
                    return false;
                }

                var sid = mBaseFunction.getSid('?',this.args.sid);
                var htmlStr = this.getTempleteHtml(this.args);
                var prefix = this.args.bottomId;
                var divId = this.args.bottomId;

                //_initBottomShow(this.args.tipId.isShowBottom, prefix);
                mBaseFunction.innerHtml({divId:divId, htmlStr:htmlStr, prefix:prefix});

                //添加事件
                var that = this;

                this._addFooterPlatformEvent();

            },

            //html
            getTempleteHtml : function(arg){
                var htmlStr =
                    '<footer id=\"{prefix}_jd_footer\" class=\"jd-footer\">'
                    + this._getLinksHtml(arg)
                    + this._getPlatformsHtml(arg)
                    + this._getCopyrightHtml(arg)
                    + '</footer>';

                return htmlStr;
            },

            _getLinksHtml : function(arg){

                var tempStr = '';

                if(arg.pin && arg.pin != ''){
                    tempStr =
                        '        <li class=\"\"><a class="J_ping" report-eventid="MCommonHTail_Account"  report-eventparam="" page_name="'+ pageURL +'" rel="nofollow"  href=\"http://home.m.jd.com/myJd/home.action{sid}\">{pin}</a></li>'
                        + '        <li><a class="J_ping" report-eventid="MCommonHTail_Exit"  report-eventparam="" page_name="'+ pageURL +'" rel="nofollow" href=\"https://passport.m.jd.com/user/logout.action{sid}\">退出</a></li>';;
                }else{
                    tempStr =
                        '        <li class=\"\"><a class="J_ping" report-eventid="MCommonHTail_Login"  report-eventparam="" page_name="'+ pageURL +'" rel="nofollow" href=\"https://passport.m.jd.com/user/login.action{sid}\">登录</a></li>'
                        + '        <li><a class="J_ping" report-eventid="MCommonHTail_Register"  report-eventparam="" page_name="'+ pageURL +'" rel="nofollow"  href=\"http://passport.m.jd.com/register/mobileRegister.action{sid}\">注册</a></li>';
                }

                var htmlStr  =
                    '	<div class=\"jd-1px-line-up\"></div>'
                    + '    <ul class=\"jd-footer-links\">'
                    + tempStr
                    + '        <li><a class="J_ping" report-eventid="MCommonHTail_Feedback"  report-eventparam="" page_name="'+ pageURL +'" rel="nofollow" href=\"http://m.jd.com/showvote.html{sid}\">反馈</a></li>'
                    + '        <li><a class="J_ping" report-eventid="MCommonHTail_ToTop"  report-eventparam="" page_name="'+ pageURL +'" rel="nofollow" href=\"#top\">回到顶部</a></li>'
                    + '    </ul>';

                var sid = mBaseFunction.getSid('?', arg.sid);
                var pin = arg.pin;

                htmlStr = mBaseFunction.templeteOutput({templete:htmlStr, param:{sid:sid ,pin:pin}});

                return htmlStr;
            },

            _getPlatformsHtml : function(arg){
                var prefix = this.args.bottomId;

                var fpf = arg.footerPlatforms;

                if(fpf.length <= 0){
                    return '';
                }

                var str = '';
                var tmp = '';

                for(var i=0; i<fpf.length; i++){
                    tmp = fpf[i];
                    //mBaseFunction.printDeugInfo(tmp);

                    var href = '';
                    if(tmp.href){

                        href =  "href='" + tmp.href + "'";
                    }

                    if(tmp.name == 'standard'){
                        str += '<li id="{prefix}_standard"  class=\"jd-footer-icon-standard\"><a ' + href + '>标准版</a></li>';
                    }else if(tmp.name == 'touchscreen'){
                        str += '<li id="{prefix}_touchscreen" class=\"jd-footer-icon-touchscreen current\"><a ' + href + ' class="J_ping" report-eventid="MCommonHTail_TouchEdition"  report-eventparam="" page_name="'+ pageURL +'">触屏版</a></li>';
                    }else if(tmp.name == 'pc'){
                        str += '<li id="{prefix}_pc" class=\"jd-footer-icon-pc\"><a ' + href + ' class="J_ping" report-eventid="MCommonHTail_PCEdition"  report-eventparam="" page_name="'+ pageURL +'">电脑版</a></li>';
                    }else if(tmp.name == 'apps'){
                        str += '<li id="{prefix}_apps"  class=\"jd-footer-icon-apps\"><a class=\"badge\" "' + href + ' class="J_ping" report-eventid="MCommonHTail_ClientApp"  report-eventparam="" page_name="'+ pageURL +'">客户端</a></li>';
                    }

                }

                var htmlStr =
                    '<div class=\"jd-1px-line-up\"></div>'
                    + '    <ul class=\"jd-footer-platforms\">'
                    + str
                    + '    </ul>';

                return htmlStr;
            },

            _getCopyrightHtml : function(arg){
                var htmlStr =
                    '<div class=\"jd-1px-line-up\"></div>'
                    + '<div class=\"jd-footer-copyright\">Copyright © 2012-2015 京东JD.com 版权所有 </div>';

                return htmlStr;
            },

            //初始化 跳转平台  默认  配置
            _initDefaultFooterPlatforms : function(){

                this.args.footerPlatforms = this.platformEnum(this.args.sid).enum4;
            },

            _addFooterPlatformEvent : function(){
                var that = this;
                var fpf = this.args.footerPlatforms;
                var prefix = this.args.bottomId;

                var addPEvent = function(prefix, name){
                    var id = prefix + '_' + name;
                    if(mBaseFunction.getElementById(id)){
                        mBaseFunction.addEvent(id,'click',function(){
                            for(var i=0; i<fpf.length; i++){
                                if(fpf[i].name == name){
                                    mBaseFunction.runFunction(fpf[i].onClickX);
                                    break;
                                }
                            }

                        });
                    }
                };

                addPEvent(prefix,'standard');
                addPEvent(prefix,'pc');
                addPEvent(prefix,'touchscreen');
                addPEvent(prefix,'apps');

                mBaseFunction.loadDownloadAppPlugIn(function(){

                    var parameters = null;
                    var otherApp = '';

                    if(document.getElementById(prefix + '_apps')){

                        if(that.args.downloadAppPlugIn){
                            if(that.args.downloadAppPlugIn.parameters){
                                parameters = that.args.downloadAppPlugIn.parameters;
                            }

                            if(that.args.downloadAppPlugIn.otherApp){
                                otherApp = that.args.downloadAppPlugIn.otherApp;
                            }

                            $('#' + prefix + '_apps').downloadAppPlugIn(parameters,otherApp);
                        }else{
                            $('#' + prefix + '_apps').downloadAppPlugIn();
                        }
                    }
                });
            },

            _initBottomShow : function(f,prefix){
                var obj = mBaseFunction.getElementById(prefix + '_jd_footer');
                if(f){
                    mBaseFunction.divShow(obj);
                }else{
                    mBaseFunction.divHide(obj);
                }
            },

            //检查完整性
            _validate : function(){
                var f = true;

                if(!this._isHasBottomId()){
                    f = false;
                }

                return f;
            },

            //检验是否有headerId
            _isHasBottomId : function(){
                var f = false;

                if(mBaseFunction.getElementById(this.args.bottomId)){
                    f = true;
                }else{
                    mBaseFunction.printDeugInfo('请正确拼写或添加通用尾ID.');
                }

                return f;

            }
        };

        /**
         * js m页 通用 JD下载提示框
         */
        var mCommonJDDownLoadTip = {

            //eg : {tipId : 'm_common_tip', sid : '555444', isShowTip : true, isfloat: true};
            args : {tipId : 'm_common_tip', sid : '', isShowTip : true, isfloat: true, onClickTrynow : function(){}},

            //创建 通用提示
            create : function(config){
                this.args = config;

                if(!this._validate()){
                    return false;
                }

                //不受限于 24小时内不显示
                var hasStorage = window.localStorage ? true : false;
                if(hasStorage && this.args.isAlwayShow ){
                    //localStorage.clear();
                    //console.log(localStorage);
                    //console.log(localStorage.downCloseDate);
                    //http://st.360buyimg.com/m/js/2014/module/plugIn/downloadAppPlugIn.src.js?v=jd2015030522 中的 downCloseDate
                    localStorage.removeItem('downCloseDate');
                }

                var sid = mBaseFunction.getSid('?',this.args.sid);
                var htmlStr = this.getTempleteHtml(config);
                var prefix = this.args.tipId;
                var divId = this.args.tipId;

                mBaseFunction.innerHtml({divId:divId,sid:sid, htmlStr:htmlStr, prefix:prefix});

                this._setFloat(this.args.isfloat,prefix);
                //this._showTip(this.args.isShowTip,prefix);

                //添加事件
                var that = this;

                mBaseFunction.addEvent(prefix + '_jd_download_tip_x','click',function(){
                    //var obj = mBaseFunction.getElementById(prefix + '_jd_download_tip');
                    //mBaseFunction.divHide(obj);

                    mBaseFunction.runFunction(that.args.onClickTipX);

                });


                mBaseFunction.addEvent(prefix + '_trynow','click',function(){
                    mBaseFunction.runFunction(that.args.onClickTrynow);
                });

                if(!this.args.isUseCustomDownloadApp){
                    mBaseFunction.loadDownloadAppPlugIn(function(){

                        var parameters = null;
                        var otherApp = '';

                        if(document.getElementById(prefix + '_trynow')){

                            if(that.args.downloadAppPlugIn){
                                if(that.args.downloadAppPlugIn.parameters){
                                    parameters = that.args.downloadAppPlugIn.parameters;
                                }

                                if(that.args.downloadAppPlugIn.otherApp){
                                    otherApp = that.args.downloadAppPlugIn.otherApp;
                                }

                                $('#' + prefix + '_trynow').downloadAppPlugIn(parameters,otherApp);
                            }else{
                                $('#' + prefix + '_trynow').downloadAppPlugIn();
                            }

                            $('#' + prefix + '_jd_download_tip_x').downloadAppPlugInClose(prefix + '_jd_download_tip');

                        }
                    });
                }
            },

            //html
            getTempleteHtml : function(config){
                var cw = '客户端首单</br>满79元送79元';//'客户端首单返5元<br>更多特价商品等你抢';
                var style = "";
                if(config.copyWrite){
                    cw = config.copyWrite;
                }

                if(config.location){
                    var location = config.location; //bottom:0px; top:0px;
                    style = ' style = "' + localStyle + '"';
                }

                var htmlStr =
                    '<div id=\"{prefix}_jd_download_tip\" ' + style +  ' class=\"tryme onfoot\">'
                    + '	    <div>'
                    + '	        <div id=\"{prefix}_jd_download_tip_x\" class=\"later\"></div>'
                    + '	        <a id=\"{prefix}_trynow\" href=\"#\" class=\"trynow\"></a>'
                    + '	        <span>' + cw + '</span>'
                    + '	    </div>'
                    + '	</div>';

                return htmlStr;
            },

            //设置是否悬浮 true:悬浮 false: 不悬浮
            _setFloat : function(f, prefix){
                var obj = mBaseFunction.getElementById(prefix + '_jd_download_tip');

                if(f){
                    mBaseFunction.addClass(obj,'onfoot');
                }else{
                    mBaseFunction.removeClass(obj,'onfoot');
                }
            },

            //初始化快速捷径 显示与否
            _showTip : function(f,prefix){
                var obj = mBaseFunction.getElementById(prefix + '_jd_download_tip');
                if(f){
                    mBaseFunction.divShow(obj);
                }else{
                    mBaseFunction.divHide(obj);
                }
            },

            //检查完整性
            _validate : function(){
                var f = true;

                if(!this._isHasTipId()){
                    f = false;
                }

                return f;
            },

            //检验是否有headerId
            _isHasTipId : function(){
                var f = false;

                if(mBaseFunction.getElementById(this.args.tipId)){
                    f = true;
                }else{
                    mBaseFunction.printDeugInfo('请正确拼写或添加通用提示ID.');
                }

                return f;

            }

        };

        //引入m_common.js
        mBaseFunction.loadJS(option.js[0],function(){});

        //引入header.css
        mBaseFunction.includeCss();

        /* 不建议等待，因为怕如果css 加载时间过长 可能会导致 页面假死，影响客户体验
         * 如果感觉页面文字闪烁现象，可以手动在html页面上加载 <link>加载css，保证css加载后在执行js生成html、
         * eg：<link rel="stylesheet" type="text/css" href="http://st.360buyimg.com/common/commonH_B/css/header.css" charset="utf-8"/>
         while(!mBaseFunction._isIncludedCss){
         console.log('需要加载js');
         }
         */

        return {

            header : function(h_config){
                mCommonHeader.create(h_config);
            },

            bottom : function(b_config){
                mCommonBottom.create(b_config);
            },

            jdTip : function(t_config){
                mCommonJDDownLoadTip.create(t_config);
            },

            /**
             * 	enum0 ：none 0个

             enum2 : 客户端 1个

             enum3 : 标准版 触屏版 客户端 3 个

             enum4 : 标准版 触屏版 电脑版 客户端 4个

             */
            platformEnum : function(toPcSkipurl,sid){

                var standardSid = '';
                if(sid){
                    standardSid = mBaseFunction.getSid('&', sid);
                }

                return {

                    enum0 : [],

                    enum1 : [{id:4, name:'apps', href:'', onClickX:function(){}}],

                    enum2_1 : [  {id:2, name:'touchscreen', href:'', onClickX:function(){}},
                        {id:4, name:'apps', href:'', onClickX:function(){}}
                    ],

                    enum3 : [ {id:1, name:'standard', href:'http://wap.jd.com/index.html?v=w' + standardSid, onClickX:function(){}},
                        {id:2, name:'touchscreen', href:'', onClickX:function(){}},
                        {id:4, name:'apps', href:'', onClickX:function(){}}
                    ],

                    enum3_1 : [{id:4, name:'apps', href:'', onClickX:function(){}},
                        {id:2, name:'touchscreen', href:'', onClickX:function(){}},
                        {id:3, name:'pc', href:'', onClickX:function(){skip(toPcSkipurl);}}
                    ],

                    enum3_2 : [{id:3, name:'pc', href:'', onClickX:function(){skip(toPcSkipurl);}},
                        {id:2, name:'touchscreen', href:'', onClickX:function(){}},
                        {id:4, name:'apps', href:'', onClickX:function(){}}
                    ],

                    enum4 : [ {id:1, name:'standard', href:'http://wap.jd.com/index.html?v=w' + standardSid, onClickX:function(){}},
                        {id:2, name:'touchscreen', href:'', onClickX:function(){}},
                        {id:3, name:'pc', href:'', onClickX:function(){skip(toPcSkipurl);}},
                        {id:4, name:'apps', href:'', onClickX:function(){}}
                    ]

                };
            },

            version : version
        };
    };
})();