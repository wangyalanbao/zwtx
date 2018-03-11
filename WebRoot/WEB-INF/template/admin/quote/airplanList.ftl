[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>添加报价</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
    <script type="text/javascript">

        $().ready(function() {

            var $listForm = $("#listForm");
            var $methodSelect = $("#methodSelect");
            var $methodOption = $("#methodOption a");
            var $searchButton = $("#search");
            var $search_regNo = $("#search_regNo");
            var $search_type = $("#search_type");
            var $search = $(".search");
            var $moreWay = $("#moreWay");
            var $oneWay = $("#oneWay");
            var $returnWay =$("#returnWay");
            var $addFlight = $("#addFlight");
            var $flightList = $("#flightList");
            var $airlineDelete = $(".airlineDelete");
            var $quoteButton = $("#quoteButton");
            var $quoting = $("#quoting");
            var $addQuoteAirline = $("#addQuoteAirline");
            var $selectAllQuote =$("#selectAllQuote");
            var $select = $("input[name='select']");
            var $selectAllAirplane = $("#selectAllAirplane");
            var $airplaneIds = $("input[name='airplaneIds']");

        [@flash_message /]

            init();
            function init(){
                if($quoting.val() == "true"){
                    $(".dialogOverlay").show();
                    $addQuoteAirline.show();
                }
            }

            function accMul(arg1,arg2)
            {
                var m=0,s1=arg1.toString(),s2=arg2.toString();
                try{m+=s1.split(".")[1].length}catch(e){}
                try{m+=s2.split(".")[1].length}catch(e){}
                return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
            }

            $selectAllQuote.click(function() {
                if($(this).is(':checked') ){
                    $select.attr("checked", "checked");
                }else{
                    $select.attr("checked", false);
                }
            });

            $selectAllAirplane.click(function() {
                if($(this).is(':checked') ){
                    $airplaneIds.attr("checked", "checked");
                }else{
                    $airplaneIds.attr("checked", false);
                }
            });

            // 进行报价
            $quoteButton.click(function() {
                var bool = true;
                $.each($airplaneIds, function(i, $airplaneId){
                    if($airplaneId.checked == true){
                        bool = true;
                        return false;
                    } else {
                        bool = false;
                    }
                });
                if(bool == false){
                    alert("请选择报价飞机！");
                    return;
                }
                var $takeoffTimes = $("input[name$='takeoffTime']")
                $.each($takeoffTimes, function(i, name){
                    if(name.value == null || name.value == "" || name.value ==undefined ){
                        alert("航段的起飞时间不能为空！");
                        bool = false;
                        return false;
                    }
                });
                if(bool == false){
                    return;
                }
                var $departureIds = $("input[name$='departureId']")
                $.each($departureIds, function(i, name){
                    if(name.value == null || name.value == "" || name.value ==undefined ){
                        alert("航段的起始机场不能为空！");
                        bool = false;
                        return false;
                    }
                });
                if(bool == false){
                    return;
                }
                var $destinationIds = $("input[name$='destinationId']")
                $.each($destinationIds, function(i, name){
                    if(name.value == null || name.value == "" || name.value ==undefined ){
                        alert("航段的目的机场不能为空！");
                        bool = false;
                        return false;
                    }
                });
                if(bool == false){
                    return;
                }

                // 航段数
                var $lineNum = $flightList[0].rows.length - 1;
                for(var i = 0, j = i + 1; i < $lineNum , j < $lineNum + 1; i++,j++){
                    if($("#departureId_" + i).val() == $("#destinationId_" + i).val()){
                        alert("起始机场和目的机场不能相同!");
                        return;
                    }
                    if(j < $lineNum){
                        if($("#departureId_" + j).val() == $("#destinationId_" + j).val()){
                            alert("起始机场和目的机场不能相同!");
                            return;
                        }
                        if($("#takeoffTime_" + i).val() >= $("#takeoffTime_" + j).val()){
                            alert("后一航段的起飞时间要大于前一航段的起飞时间!");
                            return;
                        }
                    }
                }
                $listForm.submit();
                return false;

            });

            // 添加航段
            $addFlight.click(function() {
                var $addTr = $("#addTr").clone();
                var $recordNum =$flightList[0].rows.length - 1;
                $addTr.find("input[name='departureIdAdd']").attr("name","quoteAirlineList[" + $recordNum + "].departureId");
                $addTr.find("input[name='departureAdd']").attr("name","departureName");
                $addTr.find("input[name='destinationIdAdd']").attr("name","quoteAirlineList[" + $recordNum + "].destinationId");
                $addTr.find("input[name='destinationAdd']").attr("name","destinationName");
                $addTr.find("input[name='takeoffTimeAdd']").attr("name","quoteAirlineList[" + $recordNum + "].takeoffTime");
                $addTr.find("#departureIdAdd").attr("id","departureId_" + $recordNum);
                $addTr.find("#departureAdd").attr("id","departure_" + $recordNum);
                $addTr.find("#destinationIdAdd").attr("id","destinationId_" + $recordNum);
                $addTr.find("#destinationAdd").attr("id","destination_" + $recordNum);
                $addTr.find("#takeoffTimeAdd").attr("id","takeoffTime_" + $recordNum);

                $addTr.attr("style","");
                $flightList.append($addTr);
                var num = $recordNum - 1;
                $("#departureId_" + $recordNum).val($("#destinationId_" +num).val());
                $("#departure_" + $recordNum).val($("#destination_" +num).val());

                // 机场选择
                $addTr.find("input[val='addInput']").autocomplete("${base}/admin/airport/search_airport.jhtml", {
                    dataType: "json",
                    max: 20,
                    width: 190,
                    scrollHeight: 430,
                    minChars:0,
                    parse: function(data) {
                        return $.map(data, function(item) {
                            return {
                                data: item,
                                value: item.name
                            }
                        });
                    },
                    formatItem: function(item) {
                        if ($.inArray(item.name, name) < 0) {
                            return '<h4 title="' + item.name + '">' + item.name + '<\/h4>'
                                    + '<span style="color: #999999">'  +'四字码：'+ '<\/span>' + item.code_4  + '<\/br>'
                                    + '<span style="color: #999999">'  +'三字码：'+ '<\/span>' + item.code_3  + '<\/br>'
                                    ;
                        } else {
                            return false;
                        }
                    }
                }).result(function(event, item) {
                    $(this).val(item.name);
                    $(this).closest("td").find('input:first').val(item.id);
                });
            });

            // 删除航段
            $airlineDelete.live("click", function(){
                $(this).closest("tr").remove();
            });

            // 航空公司
            $methodSelect.mouseover(function() {
                var $this = $(this);
                var offset = $this.offset();
                var $menuWrap = $this.closest("div.menuWrap");
                var $popupMenu = $menuWrap.children("div.popupMenu");
                $popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
                $menuWrap.mouseleave(function() {
                    $popupMenu.hide();
                });
            });

            // 航空公司选项
            $methodOption.click(function() {
                var $this = $(this);
                var $dest = $("#companyId");
                if ($this.hasClass("checked")) {
                    $dest.val("");
                } else {
                    $dest.val($this.attr("val"));
                }
                $listForm.attr("action","add.jhtml")
                $listForm.submit();
                return false;
            });

            // 机型，注册号搜索
            $searchButton.click(function() {
               $("#regNo").val($search_regNo.val());
                $("#type").val($search_type.val());
                $listForm.attr("action","add.jhtml")
                $listForm.submit();
                return false;
            });

            // 多程
            $moreWay.click(function(){
                $addFlight.show();
                var $recordNum =$flightList[0].rows.length - 1;
                for(var i =$recordNum; i>1;i--){
                    $flightList[0].rows[i].remove();
                }
                var $addTr = $("#addTr").clone();
                $addTr.find("input[name='departureIdAdd']").attr("name","quoteAirlineList[1].departureId");
                $addTr.find("input[name='departureAdd']").attr("name","departureName");
                $addTr.find("input[name='destinationIdAdd']").attr("name","quoteAirlineList[1].destinationId");
                $addTr.find("input[name='destinationAdd']").attr("name","destinationName");
                $addTr.find("input[name='takeoffTimeAdd']").attr("name","quoteAirlineList[1].takeoffTime");
                $addTr.find("#departureIdAdd").attr("id","departureId_1");
                $addTr.find("#departureAdd").attr("id","departure_1");
                $addTr.find("#destinationIdAdd").attr("id","destinationId_1");
                $addTr.find("#destinationAdd").attr("id","destination_1");
                $addTr.find("#takeoffTimeAdd").attr("id","takeoffTime_1");
                $addTr.find(".airlineDelete").remove();
                $addTr.attr("style","");
                $flightList.append($addTr);

                $("#departureId_1").val($("#destinationId_0").val());
                $("#departure_1").val($("#destination_0").val());

                // 机场选择
                $addTr.find("input[val='addInput']").autocomplete("${base}/admin/airport/search_airport.jhtml", {
                    dataType: "json",
                    max: 20,
                    width: 190,
                    scrollHeight: 430,
                    minChars:0,
                    parse: function(data) {
                        return $.map(data, function(item) {
                            return {
                                data: item,
                                value: item.name
                            }
                        });
                    },
                    formatItem: function(item) {
                        if ($.inArray(item.name, name) < 0) {
                            return '<h4 title="' + item.name + '">' + item.name + '<\/h4>'
                                    + '<span style="color: #999999">'  +'四字码：'+ '<\/span>' + item.code_4  + '<\/br>'
                                    + '<span style="color: #999999">'  +'三字码：'+ '<\/span>' + item.code_3  + '<\/br>'
                                    ;
                        } else {
                            return false;
                        }
                    }
                }).result(function(event, item) {
                    $(this).val(item.name);
                    $(this).closest("td").find('input:first').val(item.id);
                });
            });

            // 单程
            $oneWay.click(function(){
                $addFlight.hide();
                $airlineDelete.hide();
                var $recordNum =$flightList[0].rows.length - 1;
                for(var i =$recordNum; i>1;i--){
                    $flightList[0].rows[i].remove();
                }
            });

            // 往返
            $returnWay.click(function(){
                $addFlight.hide();
                var $recordNum =$flightList[0].rows.length - 1;
                for(var i =$recordNum; i>1;i--){
                    $flightList[0].rows[i].remove();
                }

                var $addTr = $("#addTr").clone();
                $addTr.find("input[name='departureIdAdd']").attr("name","quoteAirlineList[1].departureId");
                $addTr.find("input[name='departureAdd']").attr("name","quoteAirlines[1].departure");
                $addTr.find("input[name='destinationIdAdd']").attr("name","quoteAirlineList[1].destinationId");
                $addTr.find("input[name='destinationAdd']").attr("name","quoteAirlines[1].destination");
                $addTr.find("input[name='takeoffTimeAdd']").attr("name","quoteAirlineList[1].takeoffTime");
                $addTr.find("#departureIdAdd").attr("id","departureId_1");
                $addTr.find("#departureAdd").attr("id","departure_1");
                $addTr.find("#destinationIdAdd").attr("id","destinationId_1");
                $addTr.find("#destinationAdd").attr("id","destination_1");
                $addTr.find("#takeoffTimeAdd").attr("id","takeoffTime_1");
                $addTr.find(".airlineDelete").remove();
                $addTr.attr("style","");
                $flightList.append($addTr);

                $("#departureId_1").val($("#destinationId_0").val());
                $("#departure_1").val($("#destination_0").val());
                $("#departure_1").attr("readonly","readonly");
                $("#destinationId_1").val($("#departureId_0").val());
                $("#destination_1").val($("#departure_0").val());
                $("#destination_1").attr("readonly","readonly");

            });

            // 机场选择
            $search.autocomplete("${base}/admin/airport/search_airport.jhtml", {
                dataType: "json",
                max: 20,
                width: 190,
                scrollHeight: 430,
                minChars:0,
                parse: function(data) {
                    return $.map(data, function(item) {
                        return {
                            data: item,
                            value: item.name
                        }
                    });
                },
                formatItem: function(item) {
                    if ($.inArray(item.name, name) < 0) {
                        return '<h4 title="' + item.name + '">' + item.name + '<\/h4>'
                                + '<span style="color: #999999">'  +'四字码：'+ '<\/span>' + item.code_4  + '<\/br>'
                                + '<span style="color: #999999">'  +'三字码：'+ '<\/span>' + item.code_3  + '<\/br>'
                                ;
                    } else {
                        return false;
                    }
                }
            }).result(function(event, item) {
                $(this).val(item.name);
                $(this).closest("td").find('input:first').val(item.id);
                if($returnWay.attr("checked") == "checked"){
                    $("#departureId_1").val($("#destinationId_0").val());
                    $("#departure_1").val($("#destination_0").val());
                    $("#departure_1").attr("readonly","readonly");
                    $("#destinationId_1").val($("#departureId_0").val());
                    $("#destination_1").val($("#departure_0").val());
                    $("#destination_1").attr("readonly","readonly");
                }
                if($moreWay.attr("checked") == "checked"){
                    $("#departureId_1").val($("#destinationId_0").val());
                    $("#departure_1").val($("#destination_0").val());
                }
            });
        });
    </script>
    <style type="text/css">
        table td p{
            line-height: 1.8;
            height: 42px;
        }
        table td p.separator {
            border-top: 1px dotted #b8d3ee;
        }
        .hideDiv td {
            border: none !important;
        }

        #addQuote table.list tr:last-child td .airlineDelete{
            display: block !important;
        }

        .hideDiv{
            width: 100%;
            min-height: 300px;
            max-height: 100%;
            overflow-y: scroll;
            background-color: #ffffff;
        }
        #addQuoteAirline input{
            border:none;
        }
        .total input{
            width: 50px;
        }

        .total{
            border-top: 1px solid #C9C3C3;
        }

        .inputSelect{
            border: none;
            -moz-appearance: none;
            -webkit-appearance:none;
            appearance:none;
        }
        #listTable td{
            border-bottom: 1px solid #C9C3C3;
            padding-left: 0px
        }
        .deleteLine{
            background-color: #C9C3C3;
        }
        .deleteLine:hover {
            background-color: #C9C3C3 !important;
        }
    </style>
</head>

<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 添加报价
</div>
<form id="listForm" action="quote.jhtml" method="get">
    <input type="hidden" id="airplaneId" name="airplaneId" value="${airplaneId}" />
    <input type="hidden" id="companyId" name="companyId" value="${companyId}" />
    <input type="hidden" id="regNo" name="regNo" value="${regNo}" />
    <input type="hidden" id="type" name="type" value="${type}" />
    <div class="bar">
        <div class="buttonWrap">
            <div class="menuWrap">
                <a href="javascript:;" id="methodSelect" class="button">
                ${message("airplane.company")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="methodOption" class="check">
                        <li >
                            <a href="javascript:;" name="companyId" val=""[#if companyId== ""] class="checked"[/#if]>${message("Order.all")}</a>
                        </li>
                    [#list companyList as company]
                        <li class="separator">
                            <a href="javascript:;" name="companyId" val="${company.id}"[#if company.id == companyId] class="checked"[/#if]>${company.name}</a>
                        </li>
                    [/#list]
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <span style="height: 23px;margin-left: 4px">${message("Order.airplane.regNo")}</span>
                <input type="text" id="search_regNo" value="${regNo}" maxlength="200" style="height: 23px"/>
                <span style="height: 23px;margin-left: 4px">${message("Order.airplane.type")}</span>
                <input type="text" id="search_type" value="${type}" maxlength="200" style="height: 23px"/>
                <input type="button" class="button"  id="search" style="height: 29px" value="${message("admin.common.search")}" />
            </div>
            [#if airplaneNum > 0 ]
                <div class="menuWrap" style="margin-left: 300px">
                    <a href="javascript:;" id="quoteButton"  style="height: 30px;line-height: 26px;padding: 5px 26px 5px 26px;outline: none;border: 1px solid rgb(183, 200, 217);" >
                        报价
                    </a>
                    &nbsp;&nbsp;
                    <a href="list.jhtml" class="cancleButton"  style="height: 30px;line-height: 26px;padding: 5px 26px 5px 26px;outline: none;border: 1px solid rgb(183, 200, 217);" >
                        关闭
                    </a>
                </div>
            [/#if]
        </div>
    </div>
[#if airplaneNum > 0 ]
    <div id="addQuote" class="hideDiv">
        <table class="list" >
            <tr>
                <td><input type="radio" name="planType" id="oneWay" value="oneWay" checked/>任务-单程
                    <input type="radio" name="planType" id="returnWay" value="returnWay"/>任务-往返
                    <input type="radio" name="planType" id="moreWay" value="moreWay"/>任务-多程
                    </td>
                <td></td>
                <td></td>
            </tr>
        </table>
        <table  class="list" id="flightList">
            <tr id="addTr" style="display: none" val="" >
                <td>
                    <span>起飞时间</span>
                    <span>
                        <input type="text" name="takeoffTimeAdd" id="takeoffTimeAdd"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"/>
                    </span>
                </td>
                <td>
                    <span>出发地</span>
                    <input class="text addInput" type="hidden" id="departureIdAdd"  name="departureIdAdd" />
                    <input class="text search addInput" type="text" id="departureAdd"  name="departureAdd" val='addInput' />
                </td>
                <td>
                    <span>目的地</span>
                    <input class="text addInput" type="hidden" id="destinationIdAdd"  name="destinationIdAdd"  />
                    <input class="text search addInput" type="text" id="destinationAdd"  name="destinationAdd" val='addInput'  />
                    <span style="color: #003bb3;float: right;display: none" class="airlineDelete" > 删除&nbsp;&nbsp;</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span>起飞时间</span>
                    <span>
                        <input type="text" id="takeoffTime_0"  name="quoteAirlineList[0].takeoffTime"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"/>
                    </span>
                </td>
                <td>
                    <span>出发地</span>
                    <input class="text" type="hidden" id="departureId_0"  name="quoteAirlineList[0].departureId" />
                    <input class="text search" type="text" id="departure_0"  name="departureName" />
                </td>
                <td>
                    <span>目的地</span>
                    <input class="text" type="hidden" id="destinationId_0"  name="quoteAirlineList[0].destinationId"  />
                    <input class="text search" type="text" id="destination_0"  name="destinationName"  />
                </td>
            </tr>
        </table>
        <div style="text-align: right;width: 100%;"><input type="button" class="button" id="addFlight" value="添加航段" style="display: none"/></div>
        <br/>
        <h2 style="text-align: center;background-color: #63f666">机队</h2>
        <h4>注册号/机型  <a href="javascript:;"  style="height: 30px;line-height: 26px;padding: 5px 26px 5px 26px;outline: none;" >
            <input type="checkbox" id="selectAllAirplane"  value=""/> 全选/反选
        </a></h4>
    [#--<form id="quoteForm" action="quote.jhtml" method="get">--]
        <table class="list" >
        <tr>
        [#list airplaneList as airplane]

            <td>
                <input type="checkbox" name="airplaneIds" value="${airplane.id}" />
            ${airplane.regNo}/${airplane.type}
            </td>
            [#if (airplane_index+1)%8 == 0]
            </tr><tr>
            [/#if]
        [/#list]
        </tr>
        </table>


    [#--</form>--]
    </div>
[#else ]
<div style="text-align: center">
    <br/>
    <br/>
    <br/>
    <br/>
    没有查询到飞机！
</div>
[/#if]
</form>
</body>

</html>