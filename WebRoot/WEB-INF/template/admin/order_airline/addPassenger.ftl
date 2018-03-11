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
            var $eorderCaterings = $("#eorderCaterings");
            var $orderPickups = $("#orderPickups");
            var $eorderPickups = $("#eorderPickups");
            var $export = $("#export");
            var $listForm = $("#listForm");

        var passengersIds = [#if airline.orderPassengers?has_content][[#list airline.orderPassengers as passenger]${passenger.id}[#if passenger_has_next], [/#if][/#list]][#else]new Array()[/#if];


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
                            value: item.name
                        }
                    });
                },
                formatItem: function (item) {
                    if ($.inArray(item.name, name) < 0) {
                        if (item.idCardNo == null) {

                            return '<span title="' + item.name + '">' + item.name.substring(0, 50) + '<\/span>';
                        }
                        return '<span title="' + item.idCardNo + '">' + item.idCardNo + '<\/span>';
                    } else {
                        return false;
                    }
                }
            }).result(function (event, item) {
            [@compress single_line = true]
                var trHtml =
                        '<tr class="passengersTr">
                        <th>
                        <input type="hidden" name="passengersIds" value="' + item.id + '" \/>
                &nbsp;
                <\/th>
            <td>
            <span title="' + item.name + '">' + item.name.substring(0, 50) + '<\/span>
            <\/td>

            <td>
            <span title="' + item.idCardNo + '">' + item.idCardNo + '<\/span>
            <\/td>
            <td>
            <a href="javascript:;" class="deletePassenger" id="deletePassenger">[${message("admin.common.delete")}]<\/a>
            <\/td>
            <\/tr>';
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
                        if ($passengerTable.find("tr.passengersTr").size() <= 0) {
                            $passengerTitle.hide();
                        }
                    }
                });
            });


            //添加行李配餐
            $orderCaterings.click(function() {
                var  $airlineId = $(this).attr("val");
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
                                airlineId: $airlineId
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

        //编辑行李配餐
        $eorderCaterings.click(function() {
            var $eorderCateringId = $(this).attr("val");
            $.ajax({
                url: "editCatering.jhtml",
                type: "GET",
                data:  {orderCateringId:$(this).attr("val")},
                dataType: "json",
                cache: false,
                success: function(data) {
                    $("#eluggageRequest").attr("value",data.luggageRequest);
                    $("#efoodRequest").attr("value",data.drinkRequest);
                    $("#edrinkRequest").attr("value",data.foodRequest);
                    $("#eotherRequest").attr("value",data.otherRequest);
                    $("#eorderCateringId").attr("value",data.orderCateringId);
                }
            });

                $.dialog({
                title: "添加行李和配餐",
        [@compress single_line = true]
                content: '
        <table id="moreTable" class="moreTable">
        <input type="hidden" name="orderCateringId" id="eorderCateringId" value=""  \/>
            <tr>
        <th>
        <span class="requiredField">*<\/span>特殊行李说明:
            <\/th>
        <td>
        <input type="text" name="luggageRequest" id="eluggageRequest" class="text" \/>
            <\/td>
        <\/tr>

        <tr>
        <th>
        <span class="requiredField">*<\/span>配餐口味要求:
            <\/th>
        <td>
        <input type="text" name="foodRequest" id="efoodRequest" class="text" \/>
            <\/td>
        <\/tr>
        <tr>
        <th>
        <span class="requiredField">*<\/span>酒水饮料要求:
            <\/th>
        <td>
        <input type="text" name="drinkRequest" id="edrinkRequest" class="text" \/>
            <\/td>
        <\/tr>
        <tr>
        <th>
        <span class="requiredField">*<\/span>其它情况说明:
            <\/th>
        <td>
        <input type="text" name="otherRequest" id="eotherRequest" class="text" \/>
            <\/td>
        <\/tr>

        <\/table>',
        [/@compress]
            width: 470,
                    modal: true,
                    ok: "${message("admin.dialog.ok")}",
                    cancel: "${message("admin.dialog.cancel")}",
                    onOk: function() {
                var $luggageRequest = $("#eluggageRequest").val();
                var $foodRequest = $("#efoodRequest").val();
                var $drinkRequest = $("#edrinkRequest").val();
                var $otherRequest = $("#eotherRequest").val();
                var $orderCateringId = $("#eorderCateringId").val();
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
                        url: "updateCatering.jhtml",
                        type: "GET",
                        data: {
                            luggageRequest: $luggageRequest,
                            foodRequest: $foodRequest,
                            drinkRequest: $drinkRequest,
                            otherRequest: $otherRequest,
                            orderPickupId: $orderCateringId
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
            var  $airlineId = $(this).attr("val");
                $.dialog({
                title: "添加接送人",
        [@compress single_line = true]
                content: '
        <table id="moreTable" class="moreTable">
        <tr>
        <th>
        <span class="requiredField">*<\/span>地点:
            <\/th>
        <td>
        <select name="site" id="site">
        <option value="">${message("admin.common.choose")}<\/option>
            [#list airline as line]
            <option value="${airline.departure}">
            ${line.departure}
            <\/option>
            <option value="${airline.destination}">
            ${airline.destination}
            <\/option>
            [/#list]
        <\/select>
        <\/td>
        <\/tr>
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
                        data: {name: $name, site: $site, contact: $contact, carNo: $carNo, airlineId: $airlineId},
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

        //编辑接送人餐
        $eorderPickups.click(function() {
            var  $airlineId = $(this).attr("val");
            var  $eorderCateringId = $(this).attr("val");
            $.ajax({
                url: "editPickup.jhtml",
                type: "GET",
                data:  {orderPickupId:$(this).attr("val")},
                dataType: "json",
                cache: false,
                success: function(data) {
                    $("#esite").attr("value",data.site);
                    $("#ename").attr("value",data.name);
                    $("#econtact").attr("value",data.contact);
                    $("#ecarNo").attr("value",data.carNo);
                    $("#eorderPickupId").attr("value",data.orderPickupId);
                }
            });
                $.dialog({
                title: "添加接送人",
        [@compress single_line = true]
                content: '
        <table id="moreTable" class="moreTable">
        <input type="hidden" name="orderPickupId" id="eorderPickupId" value=""  \/>
            <tr>
        <th>
        <span class="requiredField">*<\/span>地点:
            <\/th>
        <td>
        <select name="site" id="esite">
        <option value="">${message("admin.common.choose")}<\/option>
            [#list airline as line]
            <option value="${airline.departure}">
            ${line.departure}
            <\/option>
            <option value="${airline.destination}">
            ${airline.destination}
            <\/option>
            [/#list]
        <\/select>
        <\/td>
        <\/tr>
        <tr>
        <th>
        <span class="requiredField">*<\/span>联系人:
            <\/th>
        <td>
        <input type="text" name="name" id="ename" class="text" \/>
            <\/td>
        <\/tr>

        <tr>
        <th>
        <span class="requiredField">*<\/span>联系人电话:
            <\/th>
        <td>
        <input type="text" name="contact" id="econtact" class="text" \/>
            <\/td>
        <\/tr>

        <tr>
        <th>
        <span class="requiredField">*<\/span>车牌号码:
            <\/th>
        <td>
        <input type="text" name="carNo" id="ecarNo" class="text" \/>
            <\/td>
        <\/tr>

        <\/table>',
        [/@compress]
            width: 470,
                    modal: true,
                    ok: "${message("admin.dialog.ok")}",
                    cancel: "${message("admin.dialog.cancel")}",
                    onOk: function() {
                var $name = $("#ename").val();
                var $contact = $("#econtact").val();
                var $site = $("#esite").val();
                var $carNo = $("#ecarNo").val();
                var $orderPickupId= $("#eorderPickupId").val();
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
                        url: "updatePickup.jhtml",
                        type: "GET",
                        data: {name: $name, site: $site, contact: $contact, carNo: $carNo, orderPickupId: $orderPickupId},
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


        // 导出
        $export.click(function() {
            $.ajax({
                url: "airlineExport.jhtml",
                type: "GET",
                data: {ids: $("#ids").val()},
                dataType: "json",
                cache: false,
                success: function (message) {
                    $.message(message);
                }
            });
        });

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
<input type="hidden" id="airlineId" name="airlineId" value="${airlineId}"/>
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
    <form id="listForm"  method="get">
        <div class="menuWrap">
            <input type="hidden" id="ids" name="ids" value="${airlineId}"/>
            <a href="airlineExport.jhtml?airlineId=${airlineId}"  class="button"> <span class="deleteIcon">导出信息</span></a>
        </div>
    </form>
    <tr>
        <th>
            客户名称
            Client name
        </th>
        <td>
            <input type="text" name="realName" class="text" maxlength="200" value="${airline.tripOrder.customerId.realName}"/>
        </td>
    </tr>
    <tr>
        <th>
            联系电话
            Tel
        </th>
        <td>
            <input type="text" name="telephone" class="text" value="${airline.tripOrder.customerId.telephone}" maxlength="200"/>
        </td>
    </tr>
    <tr>
    [#if airline?has_content]
        [#if airline?has_content]
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
            [#if airline.takeoffTime??]
                <input type="text" name="takeoffTime" class="text" value="${airline.takeoffTime}"/>
            [#else]
                --
            [/#if]
            [#if airline.departure??]
                <input type="text" name="departure" class="text" value="${airline.departure}，至${airline.destination}"/>
            [#else]
                暂无
            [/#if]
        </td>
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
    <input type="hidden" id="airlineId" name="airlineId" value="${airlineId}"/>
    <table id="passengerTable" class="input tabContent">
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
        <tr id="passengerTitle" class="title">
            <th>
                <input type="hidden" name="passengersIds" value=""/>
                &nbsp;
            </th>
            <td>
                旅客姓名
                Name
            </td>
            <td>
                证件号码
                ID NO.
            </td>
            <td>
                编辑
            </td>
        </tr>
    [#if airline.orderPassengers?has_content]
        [#list airline.orderPassengers as passenger]
            <tr class="resourcesTr">
                <th>
                    <input type="hidden" name="passengersIds" value="" />
                </th>

                <th>
                    <span title="item.name">${passenger.name}</span>

                </th>

                <th>
                    <span title="item.idCardNo">${passenger.idCardNo}</span>
                </th>
                <td>
                    <a href="javascript:;" class="deletePassenger">[${message("admin.common.delete")}]</a>
                </td>
            </tr>
        [/#list]
    [#else]

    [/#if]
    </table>
</form>
<form id="inputPassengerForm" action="savePassenger.jhtml" method="post" enctype="multipart/form-data">
    <input type="hidden" id="airlineId" name="airlineId" value="${airlineId}"/>
    <table class="input tabContent">
    [#if airline.orderCatering?has_content]
        <input type="hidden" id="orderCatering" name="orderCatering" value="${airline.orderCatering.id}"/>
        <tr>
            <th>
                特殊行李说明 Luggage :
            </th>
            <td>
                <input type="text" name="luggageRequest" class="text" value="${airline.orderCatering.luggageRequest}"/>
            </td>
        </tr>
        <tr>
            <th>
                配餐口味要求 Food :
            </th>
            <td>
                <input type="text" class="text" name="foodRequest" value="${airline.orderCatering.foodRequest}"/>
            </td>
        </tr>
        <tr>
            <th>
                酒水饮料要求 Drinks :
            </th>
            <td>
                <input type="text" name="drinkRequest" class="text" value="${airline.orderCatering.drinkRequest}">
            </td>
        </tr>
        <tr>
            <th>
                其它情况说明 Other :
            </th>
            <td>
                <input type="text" name="otherRequest" class="text" value="${airline.orderCatering.otherRequest}">
            </td>
        </tr>
        <th>
        </th>
        <td>
            <a href="#" id="eorderCaterings" class="eorderCaterings" val="${airline.orderCatering.id}">[编辑]</a>
        </td>
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
        <th>
        </th>
        <td>
            <a href="#" id="orderCaterings" class="orderCaterings" val="${airlineId}">+ 添加</a>
        </td>
    [/#if]
    </table>
</form>
<form id="inputPickupsForm" action="savePassenger.jhtml" method="post" enctype="multipart/form-data">
    <input type="hidden" id="airlineId" name="airlineId" value="${airlineId}"/>
    <table id="attributeTable" class="input tabContent">
    [#if airline.orderPickup?has_content]
        <input type="hidden" id="orderPickupId" name="orderPickupId" value="${airline.orderPickup.id}"/>
        <tr>
            <th>
                地 点
                Location
            </th>
            <td>
                <input type="text" name="site" class="text" value="${airline.orderPickup.site}"/>

            </td>
        </tr>
        <tr>
            <th>
                联系人
                Name
            </th>
            <td>
                <input type="text" class="text" name="foodRequest" value="${airline.orderPickup.name}"/>
            </td>
        </tr>
        <tr>
            <th>
                联系人电话
                Tel
            </th>
            <td>
                <input type="text" name="drinkRequest" class="text" value="${airline.orderPickup.contact}">
            </td>
        </tr>
        <tr>
            <th>
                车 号
                Car No.
            </th>
            <td>
                <input type="text" name="otherRequest" class="text" value="${airline.orderPickup.carNo}">
            </td>
        </tr>
        <th>
        </th>
        <td>
            <a href="#" id="eorderPickups" class="eorderPickups" val="${airline.orderPickup.id}">[编辑]</a>
        </td>
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
        <th>
        </th>
        <td>
            <a href="#" id="orderPickups" class="orderPickups" val="${airlineId}">+ 添加</a>
        </td>
    [/#if]
    </table>
    <table class="input">
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'"/>
            </td>
        </tr>
    </table>
</form>
[#--</form>--]
</body>
</html>