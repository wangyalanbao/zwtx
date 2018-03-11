<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>编辑地面代理费</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <style type="text/css">
        .authorities label {
            min-width: 120px;
            _width: 120px;
            display: block;
            float: left;
            padding-right: 4px;
            _white-space: nowrap;
        }
    </style>
    <script type="text/javascript">
        $().ready(function() {

            var $inputForm = $("#inputForm");
            var $selectAll = $("#inputForm .selectAll");
            var $areasForeigns = $("#areasForeigns");
            var $areasInland = $("#areasInland");
            var $airfield = $("#airfield");
            var $airfield2 = $("#airfield2");
        [@flash_message /]

            if($('input:radio[name="airfield"]:checked').val() == "interior"){
                $areasForeigns.hide();
                $areasInland.show();
            }else if($('input:radio[name="airfield"]:checked').val() == "external"){
                $areasInland.hide();
                $areasForeigns.show();
            }

            $selectAll.click(function() {
                var $this = $(this);
                var $thisCheckbox = $this.closest("tr").find(":checkbox");
                if ($thisCheckbox.filter(":checked").size() > 0) {
                    $thisCheckbox.prop("checked", false);
                } else {
                    $thisCheckbox.prop("checked", true);
                }
                return false;
            });

            // 表单验证
            $inputForm.validate({
                rules: {
                    agencyCost: "required",
                    area: "required",
                    transferCost: "required"
                }
            });


            //用区域类型改变地区名
            $airfield.live("change",function(){
                $("[name='areas']").attr("checked",false);
                $areasForeigns.hide();
                $areasInland.show();
            });
            $airfield2.live("change",function(){
                $("[name='areas']").attr("checked",false);
                $areasInland.hide();
                $areasForeigns.show();
            });


        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑地面代理费
</div>
<form id="inputForm" action="update.jhtml" method="post">
    <input type="hidden" name="id" value="${inlandCost.id}" />
    <table class="input">
        <tr>
            <th>
            ${message("Role.name")}:
            </th>
            <td>
                <input type="text" name="name" class="text" value="${inlandCost.name}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("inlandCost.agencyCost")}:
            </th>
            <td>
                <input type="text" name="agencyCost" class="text" value="${inlandCost.agencyCost}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("inlandCost.transferCost")}:
            </th>
            <td>
                <input type="text" name="transferCost" class="text"  value="${inlandCost.transferCost}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("inlandCost.description")}:
            </th>
            <td>
                <input type="text" name="description" class="text" value="${inlandCost.description}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.location")}:
            </th>
            <td>
                <label>
                    <input type="radio" name="airfield" id="airfield" value="interior" [#if  inlandCost.airfield == "interior"] checked="checked"[/#if] />国内
                    <input type="radio" name="airfield" id="airfield2" value="external" [#if  inlandCost.airfield == "external"] checked="checked"[/#if]/>国外
                </label>

            </td>
        </tr>
        <tr class="authorities">
        <tr>
            <th>
                <span class="requiredField">*</span>城市名:
            </th>
            <td  id="areasInland">
            [#list areas as area]
                <label>
                    <input type="checkbox" name="area" value="${area}"[#if strs?seq_contains(area)] checked="checked"[/#if] />${area}
                </label>
            [/#list]
            </td>

            <td  id="areasForeigns" >
            [#list areasForeign as area]
                <label>
                    <input type="checkbox" name="area" value="${area}"[#if strs?seq_contains(area)] checked="checked"[/#if] />${area}
                </label>
            [/#list]
            </td>
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