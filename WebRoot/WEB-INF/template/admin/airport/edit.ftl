<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.airport.edit")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <style type="text/css">
        .shippingMethods label {
            width: 150px;
            display: block;
            float: left;
            padding-right: 6px;
        }
    </style>
    <script type="text/javascript">
        $().ready(function() {

            var $inputForm = $("#inputForm");
            var $browserButton = $("#browserButton");
            var $inlandCostId = $("#inlandCostId");
            var $foreignCostId = $("#foreignCostId");
            var $transferCost = $("#transferCost");
            var $maintenanceCost = $("#maintenanceCost");
        [@flash_message /]

            $browserButton.browser();

            // 表单验证
            $inputForm.validate({
                rules: {
                    code_3:"required",
                    name:"required",
                    area:"required",
                    city:"required",
                    cityEnglish:"required",
                    areaEnglish:"required",
                    longitude:"required",
                    latitude:"required",
                    magVariation:"required",
                    meanSeaLevel:"required",
                    code_4: "required",
                    locations: "required",
                    maintenanceCost:{
                        number:true
                    },
                    agencyCost:{
                        number:true
                    },
                    overseasEnterCost:{
                        number:true
                    },
                    navigationCost:{
                        number:true
                    },
                    cateringCost:{
                        number:true
                    },
                    trafficCost:{
                        number:true
                    },
                    customsCost:{
                        number:true
                    },
                    siteCost:{
                        number:true
                    },
                    guestGalleryCost:{
                        number:true
                    },
                    ferryCost:{
                        number:true
                    },
                    groundServiceCost:{
                        number:true
                    },
                    guestLobbyCost:{
                        number:true
                    }
                }
            });


            // 修改国外地面代理费
            $foreignCostId.live("change",function(){
                var $this = $(this);

                var $id = $this.val();
                if($id != "" || $id != undefined){
                    $.ajax({
                        url: "foreignCost.jhtml",
                        type: "GET",
                        data: {foreignCostId: $id},
                        dataType: "json",
                        success: function(data) {
                            $maintenanceCost.val(data.transferCost);
                        }
                    });
                }
                $maintenanceCost.val("");
            });


            // 修改地面代理费
            $inlandCostId.live("change",function(){
                var $this = $(this);
                var $id = $this.val();
                if($id != undefined  && $id != "" ){
                    $.ajax({
                        url: "cost.jhtml",
                        type: "GET",
                        data: {inlandCostId: $id},
                        dataType: "json",
                        success: function(data) {

                            $transferCost.val(data.transferCost);
                            $maintenanceCost.val(data.agencyCost);
                        }
                    });
                }else{

                    $transferCost.val("");
                    $maintenanceCost.val("");
                }
            });


        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.airport.edit")}
</div>
<form id="inputForm" action="update.jhtml" method="post">
    <input type="hidden" name="id" value="${airport.id}" />
    <table class="input">

        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.code_4")}:
            </th>
            <td>
                <input type="text" id="code_4" name="code_4" class="text" value="${airport.code_4}" maxlength="10" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.code_3")}:
            </th>
            <td>
                <input type="text" id="code_3" name="code_3" class="text" value="${airport.code_3}" maxlength="10" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.name")}:
            </th>
            <td>
                <input type="text" id="name" name="name" class="text" maxlength="200" value="${airport.name}" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("airport.baseVirtual")}:
            </th>
            <td>
                <label>
                    <input type="radio" name="isVirtual" value="true"  [#if  airplane.isVirtual == "true"] checked="checked"[/#if]/>是
                    <input type="radio" name="isVirtual" value="false" [#if  airplane.isVirtual == "false"] checked="checked"[/#if]/>否
                </label>
            </td>
        </tr>

        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.city")}:
            </th>
            <td>
                <input type="text" id="cityEnglish" name="cityEnglish" class="text" maxlength="200" value="${airport.cityEnglish}"/>
            </td>
        </tr>

        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.cityEnglish")}:
            </th>
            <td>
                <input type="text" id="city" name="city" class="text" maxlength="200" value="${airport.city}"/>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.area")}:
            </th>
            <td>
                <input type="text" id="areaEnglish" name="areaEnglish" class="text" maxlength="200" value="${airport.areaEnglish}"/>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.areaEnglish")}:
            </th>
            <td>
                <input type="text" id="area" name="area" class="text" maxlength="200" value="${airport.area}"/>
            </td>
        </tr>

        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.longitude")}:
            </th>
            <td>
                <input type="text" id="longitude" name="longitude" class="text" maxlength="200" value="${airport.longitude}"/>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.latitude")}:
            </th>
            <td>
                <input type="text" id="latitude" name="latitude" class="text" maxlength="200" value="${airport.latitude}"/>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.magVariation")}:
            </th>
            <td>
                <input type="text" id="magVariation" name="magVariation" class="text" maxlength="200"value="${airport.magVariation}" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.meanSeaLevel")}:
            </th>
            <td>
                <input type="text" id="meanSeaLevel" name="meanSeaLevel" class="text" maxlength="200"value="${airport.meanSeaLevel}" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.location")}:
            </th>
            <td>
            [#list locations as locations]
                <label>
                    <input type="radio" name="locations" value="${locations}" [#if locations == airport.locations] checked="checked"[/#if]/>${message("airport.Location." + locations)}
                </label>
            [/#list]
            </td>
        </tr>
        <tr>
            <th>
                地面管理费:
            </th>
            <td>
                <select name="inlandCostId" id="inlandCostId">
                    <option value="">请选择</option>
                [#list inlandCost as cost]
                    <option value="${cost.id}" [#if cost == airport.inlandCost] selected="selected"[/#if]>
                    ${cost.name}
                    </option>
                [/#list]
                </select>
            </td>
        </tr>

        <tr>
            <th>
            ${message("airport.transferCost")}
            </th>
            <th>
                <input type="text" id="transferCost" name="transferCost" value="${airport.transferCost}" class="text" maxlength="200" readonly="readonly"/>
            </th>
        <tr>
        <tr>
            <th>
            ${message("airport.maintenanceCost")}
            </th>
            <th>
                <input type="text" id="maintenanceCost" name="maintenanceCost"value="${airport.maintenanceCost}" class="text" maxlength="200" readonly="readonly"/>

            </th>
        <tr>
            <th>
            ${message("airport.navigationalMatters")}
            <th>
            ${message("airport.agencyCost")}:
            </th>
            <td>
                <input type="text" id="cost1" name="agencyCost"value="${airport.agencyCost}" class="text" maxlength="200" onkeyup="count();"/>
            </td>

            </th>

        </tr>
        <tr>
            <th>
            <th>
            ${message("airport.overseasEnterCost")}:
            </th>
            <td>
                <input type="text" id="cost2" name="overseasEnterCost" value="${airport.overseasEnterCost}" class="text" maxlength="200" onkeyup="count();"/>
            </td>

            </th>

        </tr>
        <tr>
            <th>
            <th>
            ${message("airport.navigationCost")}:
            </th>
            <td>
                <input type="text" id="cost3" name="navigationCost" value="${airport.navigationCost}"class="text" maxlength="200" onkeyup="count();"/>
            </td>

            </th>
        </tr>

        <tr>
            <th>
            ${message("airport.unitSafeguard")}
            <th>
            ${message("airport.cateringCost")}:
            </th>
            <td>
                <input type="text" id="cost4" name="cateringCost" value="${airport.cateringCost}" class="text" maxlength="200" onkeyup="count();"/>
            </td>

            </th>

        </tr>
        <tr>
            <th>
            <th>
            ${message("airport.trafficCost")}:
            </th>
            <td>
                <input type="text" id="cost5" name="trafficCost" class="text" value="${airport.trafficCost}" maxlength="200" onkeyup="count();"/>
            </td>

            </th>

        </tr>
        <tr>
            <th>
            <th>
            ${message("airport.customsCost")}:
            </th>
            <td>
                <input type="text" id="cost6" name="customsCost" class="text" value="${airport.customsCost}" maxlength="200" onkeyup="count();"/>
            </td>

            </th>
        </tr>
        <tr>
            <th>
            ${message("airport.storefrontSafeguard")}
            <th>
            ${message("airport.siteCost")}:
            </th>
            <td>
                <input type="text" id="cost7" name="siteCost" class="text"  value="${airport.siteCost}" maxlength="200" onkeyup="count();"/>
            </td>

            </th>

        </tr>
        <tr>
            <th>
            <th>
            ${message("airport.guestGalleryCost")}:
            </th>
            <td>
                <input type="text" id="cost8" name="guestGalleryCost" value="${airport.guestGalleryCost}" class="text" maxlength="200" onkeyup="count();"/>
            </td>

            </th>

        </tr>

        <tr>
            <th>
            <th>
            ${message("airport.ferryCost")}:
            </th>
            <td>
                <input type="text" id="cost9" name="ferryCost" value="${airport.ferryCost}" class="text" maxlength="200" onkeyup="count();"/>
            </td>

            </th>
        </tr>
        <tr>
            <th>
            <th>
            ${message("airport.groundServiceCost")}:
            </th>
            <td>
                <input type="text" id="cost10" name="groundServiceCost" value="${airport.groundServiceCost}"  class="text" maxlength="200" onkeyup="count();"/>
            </td>

            </th>
        </tr>
        <tr>
            <th>
            <th>
            ${message("airport.guestLobbyCost")}:
            </th>
            <td>
                <input type="text" id="cost11" name="guestLobbyCost"  value="${airport.guestLobbyCost}" class="text" maxlength="200" onkeyup="count();"/>
            </td>

            </th>
        </tr>

        </tr>


        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="submit" class="button" value="${message("admin.common.submit")}" />
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>