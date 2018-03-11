<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>添加乘客信息</title>

    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
    <style type="text/css">
        .specificationSelect {
            height: 100px;
            padding: 5px;
            overflow-y: scroll;
            border: 1px solid #cccccc;
        }

        .specificationSelect li {
            float: left;
            min-width: 150px;
            _width: 200px;
        }
    </style>
    <script type="text/javascript">
        $().ready(function () {

            var $passengerSelect = $("#passengerSelect");
            var $inputForm = $("#inputForm");
            var $deletePassenger = $("a.deletePassenger");
            var $passengerTitle = $("#passengerTitle");
            var $productTable = $("#productTable");
            var $passengerTable = $("#passengerTable");
            var $orderCaterings = $("#orderCaterings");
            var $orderPickups = $("#orderPickups");

        var passengersIds = [#if order.orderPassengers?has_content][[#list order.orderPassengers as passenger]${passenger.id}[#if passenger_has_next], [/#if][/#list]][#else]new Array()[/#if];


        [@flash_message /]

            // 乘客选择
            $passengerSelect.autocomplete("passenger_select.jhtml", {
                dataType: "json",
                max: 20,
                width: 600,
                scrollHeight: 300,
                parse: function (data) {
                    return $.map(data, function (item) {
                        return {
                            data: item,
                            value: item.user
                        }
                    });
                },
                formatItem: function (item) {
                    if ($.inArray(item.id, passengersIds) < 0) {
                        if (item.idCardNo == null) {

                            return '<span title="' + item.user + '">' + item.user.substring(0, 50) + '<\/span>';
                        }
                        return '<span title="' + item.idCardNo + '">' + item.idCardNo + '<\/span>';
                    } else {
                        return false;
                    }
                }
            }).result(function (event, item) {
            [@compress single_line = true]
                var trHtml =
                        '<tr class="productTr">

                        < th >
                        < input type = "hidden" name = "passengersIds" value = "' + item.id + '" \/>
                <\/th >
            < td >
            < span title = "' + item.user + '" > ' + item.user + ' <\/span >
            <\/td >
            < td >
            < span title = "' + item.sex + '" > ' + item.sex + ' <\/span >
            <\/td >
            < td >
            < span title = "' + item.nationality + '" > ' + item.nationality + ' <\/span >
            <\/td >
            < td >
            < span title = "' + item.birth + '" > ' + item.birth + ' <\/span >
            <\/
                td >
                < td >
                < span title = "' + item.identityType + '" > ' + item.identityType + ' <\/span >
            <\/td >
            < td >
            < span title = "' + item.idCardNo + '" > ' + item.idCardNo + ' <\/span >
            <\/td >
            < td >
            < span title = "' + item.identityExpiryEnd + '" > ' + item.identityExpiryEnd + ' <\/
                span >
                <\/
                td >
                < td >
                < a
                href = "javascript:;"
                class
                = "deletePassenger" > [${message("admin.common.delete")}] <\/
                a >
                <\/
                td >
                <\/
                tr > ';
            [/@compress]
                $passengerTitle.show();
                $passengerTable.append(trHtml);
                passengersIds.push(item.id);
            });
            // 删除乘客
            $deletePassenger.live("click", function () {
                var $this = $(this);
                $.dialog({
                    type: "warn",
                    content: "${message("admin.dialog.deleteConfirm")}",
                    onOk: function () {
                        var id = parseInt($this.closest("tr").find("input:hidden").val());
                        passengersIds = $.grep(passengersIds, function (n, i) {
                            return n != id;
                        });
                        $this.closest("tr").remove();
                        if ($passengerTable.find("tr.productTr").size() <= 0) {
                            $passengerTitle.hide();
                        }
                    }
                });
            });


            //添加行李配餐
            $orderCaterings.click(function() {
                var  $orderId = $(this).attr("val");
                    $.dialog({
                    title: "添加行李和配餐",
            [@compress single_line = true]
                    content: '
            <table id="moreTable" class="moreTable">
            <tr>
            <th>
            <span class="requiredField">*<\/span>特殊行李说明:
                <\/th>
            <td>
            <input type="text" name="luggageRequest" id="luggageRequest" class="text" \/>
                <\/td>
            <\/tr>

            <tr>
            <th>
            <span class="requiredField">*<\/span>配餐口味要求:
                <\/th>
            <td>
            <input type="text" name="foodRequest" id="foodRequest" class="text" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            <span class="requiredField">*<\/span>酒水饮料要求:
                <\/th>
            <td>
            <input type="text" name="drinkRequest" id="drinkRequest" class="text" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            <span class="requiredField">*<\/span>其它情况说明:
                <\/th>
            <td>
            <input type="text" name="otherRequest" id="otherRequest" class="text" \/>
                <\/td>
            <\/tr>

            <\/table>',
            [/@compress]
                width: 470,
                        modal: true,
                        ok: "${message("admin.dialog.ok")}",
                        cancel: "${message("admin.dialog.cancel")}",
                        onOk: function() {
                    var $luggageRequest = $("#luggageRequest").val();
                    var $foodRequest = $("#foodRequest").val();
                    var $drinkRequest = $("#drinkRequest").val();
                    var $otherRequest = $("#otherRequest").val();
                    if ($luggageRequest == "" || $luggageRequest == undefined) {
                        alert("行李特殊说明不能为空");
                        return false;
                    } else if ($foodRequest == "" || $foodRequest == undefined) {
                        alert("配餐口味要求不能为空");
                        return false;
                    } else if ($drinkRequest == "" || $drinkRequest == undefined) {
                        alert("酒水饮料要求不能为空");
                        return false;
                    } else if ($otherRequest == "" || $otherRequest == undefined) {
                        alert("其他情况不能为空");
                        return false;
                    } else {
                        $.ajax({
                            url: "addCatering.jhtml",
                            type: "GET",
                            data: {
                                luggageRequest: $luggageRequest,
                                foodRequest: $foodRequest,
                                drinkRequest: $drinkRequest,
                                otherRequest: $otherRequest,
                                orderId: $orderId
                            },
                            dataType: "json",
                            cache: false,
                            success: function (message) {
                                $.message(message);
                                location.reload(true);
                            }
                        });
                    }

                }
            });
        });


        //添加接送人餐
        $orderPickups.click(function() {
            var  $orderId = $(this).attr("val");
                $.dialog({
                title: "添加接送人",
        [@compress single_line = true]
                content: '
        <table id="moreTable" class="moreTable">
        <tr>
        <th>
        <span class="requiredField">*<\/span>联系人:
            <\/th>
        <td>
        <input type="text" name="name" id="name" class="text" \/>
            <\/td>
        <\/tr>

        <tr>
        <th>
        <span class="requiredField">*<\/span>联系人电话:
            <\/th>
        <td>
        <input type="text" name="contact" id="contact" class="text" \/>
            <\/td>
        <\/tr>
        <tr>
        <th>
        <span class="requiredField">*<\/span>地点:
            <\/th>
        <td>
        <select name="site" id="site">
        <option value="">${message("admin.common.choose")}<\/option>
            [#list order.orderAirlines as line]
            <option value="${line.departure}">
            ${line.departure}
            <\/option>
            <option value="${line.destination}">
            ${line.destination}
            <\/option>
            [/#list]
        <\/select>
        <\/td>
        <\/tr>
        <tr>
        <th>
        <span class="requiredField">*<\/span>车牌号码:
            <\/th>
        <td>
        <input type="text" name="carNo" id="carNo" class="text" \/>
            <\/td>
        <\/tr>

        <\/table>',
        [/@compress]
            width: 470,
                    modal: true,
                    ok: "${message("admin.dialog.ok")}",
                    cancel: "${message("admin.dialog.cancel")}",
                    onOk: function() {
                var $name = $("#name").val();
                var $contact = $("#contact").val();
                var $site = $("#site").val();
                var $carNo = $("#carNo").val();
                if ($name == "" || $name == undefined) {
                    alert("姓名不能为空");
                    return false;
                } else if ($contact == "" || $contact == undefined) {
                    alert("电话不能为空");
                    return false;
                } else if ($site == "" || $site == undefined) {
                    alert("接送地不能为空");
                    return false;
                } else if ($carNo == "" || $carNo == undefined) {
                    alert("车牌号不能为空");
                    return false;
                } else {
                    $.ajax({
                        url: "addPickup.jhtml",
                        type: "GET",
                        data: {name: $name, site: $site, contact: $contact, carNo: $carNo, orderId: $orderId},
                        dataType: "json",
                        cache: false,
                        success: function (message) {
                            $.message(message);
                            location.reload(true);
                        }
                    });
                }

            }
        });
        })
        ;


        })
        ;
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 添加乘客信息
</div>
[#--<form id="inputForm" action="" method="post" enctype="multipart/form-data">--]
<input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
<ul id="tab" class="tab">
    <li>
        <input type="button" value="客户信息"/>
    </li>
    <li>
        <input type="button" value="乘客信息"/>
    </li>
    <li>
        <input type="button" value="行李和配餐要求"/>
    </li>
    <li>
        <input type="button" value="接送人信息"/>
    </li>

</ul>
<table class="input tabContent">
    <tr>
        <th>
            客户名称
            Client name
        </th>
        <td>
            <input type="text" name="realName" class="text" maxlength="200" value="${order.customer.realName}"/>
        </td>
    </tr>
    <tr>
        <th>
            联系电话
            Tel
        </th>
        <td>
            <input type="text" name="telephone" class="text" value="${order.customer.telephone}" maxlength="200"/>
        </td>
    </tr>
    <tr>
    [#if order.orderAirlines?has_content]
        [#list order.orderAirlines as line]
            [#if line?has_content]
                <th>
                    飞行日期/时刻/航线<br>
                    Flight
                    Date/Time/Route
                </th>
            [#else]
                <th>
                    飞行日期/时刻/航线<br>
                    Flight
                    Date/Time/Route
                </th>
            [/#if]
            <td>
                [#if line.takeoffTime??]
                    <input type="text" name="takeoffTime" class="text" value="${line.takeoffTime}"/>
                [#else]
                    --
                [/#if]
                [#if line.departure??]
                    <input type="text" name="departure" class="text" value="${line.departure}，至${line.destination}"/>
                [#else]
                    暂无
                [/#if]
            </td>
        [/#list]
    [#else]
        <th>
            飞行日期/时刻/航线<br>
            Flight
            Date/Time/Route
        </th>
        <td>
            --
            <span style="position:absolute; left:400px;">暂无</span>
        </td>
    [/#if]

    </tr>
</table>
<form id="inputPassengerForm" action="savePassenger.jhtml" method="post" enctype="multipart/form-data">
    <input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
    <table id="passengerTable" class="input tabContent">
    [#if order.orderPassengers?has_content]
        [#list order.orderPassengers as passenger]
            <tr>
                <th>
                    添加乘客
                </th>
                <td colspan="2">
                    <input type="text" id="passengerSelect" name="passengerSelect" class="text" maxlength="200"
                           title="${message("admin.orderPassenger.passengerSelect")}"/>
                    <input type="submit" class="button" value="保存"/>
                </td>
            </tr>
            <tr id="passengerTitle" class="title [#if !passenger?has_content] hidden[/#if]">
                <th>
                    <input type="hidden" name="passengersIds" value="${passenger.id}"/>
                    &nbsp;
                </th>
                <td>
                    旅客姓名
                    Name
                </td>

                <td>
                    性别
                    Sex
                </td>

                <td>
                    国籍
                    Nationality
                </td>

                <td>
                    出生日期
                    DOB
                </td>

                <td>
                    证件名称
                    Type of Certificate
                </td>

                <td>
                    证件号码
                    ID NO.
                </td>

                <td>
                    有效期
                    Experation Date
                </td>

                <td>
                    编辑
                </td>
            </tr>
            <tr class="resourcesTr">
                <th>
                    <input type="hidden" name="resourcesIds" value="${passenger.id}" />
                </th>

                <th>
                    <span title="item.name">${passenger.name}</span>

                </th>
                <th>
                    <span title="item.sex">${passenger.sex}</span>
                </th>
                <th>
                    <span title="item.nationality">${passenger.nationality}</span>

                </th>
                <th>
                    <input type="text" name="birth" class="text Wdate" value="${passenger.birth}" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});" readonly/>

                </th>
                <th>
                    <span title="item.identityType">${passenger.identityType}</span>

                </th>
                <th>
                    <span title="item.idCardNo">${passenger.idCardNo}</span>
                </th>
                <th>
                    <input type="text" name="identityExpiryEnd" class="text Wdate" value="${passenger.identityExpiryEnd}" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});" readonly/>
                </th>
                <td>
                    <a href="javascript:;" class="deleteResources">[${message("admin.common.delete")}]</a>
                </td>
            </tr>
        [/#list]
    [#else]

        <tr>
            <th>
                添加乘客
            </th>
            <td colspan="2">
                <input type="text" id="passengerSelect" name="passengerSelect" class="text" maxlength="200"
                       title="${message("admin.orderPassenger.passengerSelect")}"/>
                <input type="submit" class="button" value="保存"/>
            </td>
        </tr>
        <tr id="passengerTitle" class="title hidden">
            <th>
                <input type="hidden" name="passengersIds" value=""/>
                &nbsp;
            </th>
            <td>
                旅客姓名
                Name
            </td>
            <td>
                性别
                Sex
            </td>
            <td>
                国籍
                Nationality
            </td>
            <td>
                出生日期
                DOB
            </td>
            <td>
                证件名称
                Type of Certificate
            </td>
            <td>
                证件号码
                ID NO.
            </td>
            <td>
                有效期
                Experation Date
            </td>
            <td>
                编辑
            </td>
        </tr>
    [/#if]
    </table>
</form>
<form id="inputPassengerForm" action="savePassenger.jhtml" method="post" enctype="multipart/form-data">

    <table class="input tabContent">
    [#if order.orderCaterings?has_content]
        [#list order.orderCaterings as catering]
            <tr>
                <th>
                    特殊行李说明 Luggage :
                </th>
                <td>
                    <input type="text" name="luggageRequest" class="text" value="${catering.luggageRequest}"/>
                </td>
            </tr>
            <tr>
                <th>
                    配餐口味要求 Food :
                </th>
                <td>
                    <input type="text" class="text" name="foodRequest" value="${catering.foodRequest}"/>
                </td>
            </tr>
            <tr>
                <th>
                    酒水饮料要求 Drinks :
                </th>
                <td>
                    <input type="text" name="drinkRequest" class="text" value="${catering.drinkRequest}">
                </td>
            </tr>
            <tr>
                <th>
                    其它情况说明 Other :
                </th>
                <td>
                    <input type="text" name="otherRequest" class="text" value="${catering.otherRequest}">
                </td>
            </tr>
        [/#list]
    [#else]
        <tr>
            <th>
                特殊行李说明 Luggage :
            </th>
            <td>
                <input type="text" name="luggageRequest" class="text"/>
            </td>
        </tr>
        <tr>
            <th>
                配餐口味要求 Food :
            </th>
            <td>
                <input type="text" class="text" name="foodRequest"/>
            </td>
        </tr>
        <tr>
            <th>
                酒水饮料要求 Drinks :
            </th>
            <td>
                <input type="text" name="drinkRequest" class="text">
            </td>
        </tr>
        <tr>
            <th>
                其它情况说明 Other :
            </th>
            <td>
                <input type="text" name="otherRequest" class="text">
            </td>
        </tr>
    [/#if]
        <th>
        </th>
        <td>
            <a href="#" id="orderCaterings" class="orderCaterings" val="${orderId}">+ 添加</a>
        </td>
    </table>
</form>
<form id="inputPickupsForm" action="savePassenger.jhtml" method="post" enctype="multipart/form-data">
    <table id="attributeTable" class="input tabContent">
    [#if order.orderPickups?has_content]
        [#list order.orderPickups as pickup]
            <tr>
                <th>
                    地 点
                    Location
                </th>
                <td>
                    <input type="text" name="site" class="text" value="${pickup.site}"/>

                </td>
            </tr>
            <tr>
                <th>
                    联系人
                    Name
                </th>
                <td>
                    <input type="text" class="text" name="foodRequest" value="${pickup.name}"/>
                </td>
            </tr>
            <tr>
                <th>
                    联系人电话
                    Tel
                </th>
                <td>
                    <input type="text" name="drinkRequest" class="text" value="${pickup.contact}">
                </td>
            </tr>
            <tr>
                <th>
                    车 号
                    Car No.
                </th>
                <td>
                    <input type="text" name="otherRequest" class="text" value="${pickup.carNo}">
                </td>
            </tr>
        [/#list]
    [#else]
        <tr>
            <th>
                地 点
                Location
            </th>
            <td>
                <input type="text" name="site" class="text"/>

            </td>
        </tr>
        <tr>
            <th>
                联系人
                Name
            </th>
            <td>
                <input type="text" class="text" name="foodRequest"/>
            </td>
        </tr>
        <tr>
            <th>
                联系人电话
                Tel
            </th>
            <td>
                <input type="text" name="drinkRequest" class="text">
            </td>
        </tr>
        <tr>
            <th>
                车 号
                Car No.
            </th>
            <td>
                <input type="text" name="otherRequest" class="text">
            </td>
        </tr>
    [/#if]
        <th>
        </th>
        <td>
            <a href="#" id="orderPickups" class="orderPickups" val="${orderId}">+ 添加</a>
        </td>
    </table>
    <table class="input">
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="button" class="button" value="${message("admin.common.back")}"
                       onclick="location.href='list.jhtml'"/>
            </td>
        </tr>
    </table>
</form>
[#--</form>--]
</body>
</html>