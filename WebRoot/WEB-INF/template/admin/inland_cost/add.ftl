<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>添加国内地面代理费</title>


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
            var $areasInland = $("#areasInland");
            var $areasForeigns = $("#areasForeigns");
            var $airfield = $("#airfield");
            var $airfield2 = $("#airfield2");

        [@flash_message /]

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
                    areas: "required",
                    transferCost: "required"
                }
            });


            //用区域类型改变地区名
            $airfield.live("change",function(){
                $("[name='areas']").attr("checked",false);
                $areasInland.show();
                $areasForeigns.hide();
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
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 添加国内地面代理费
</div>
<form id="inputForm" action="save.jhtml" method="post">
    <table class="input">
        <tr>
            <th>
            ${message("Role.name")}:
            </th>
            <td>
                <input type="text" name="name" class="text" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("inlandCost.agencyCost")}:
            </th>
            <td>
                <input type="text" name="agencyCost" class="text" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("inlandCost.transferCost")}:
            </th>
            <td>
                <input type="text" name="transferCost" class="text" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("inlandCost.description")}:
            </th>
            <td>
                <input type="text" name="description" class="text" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airport.location")}:
            </th>
            <td>
                <label>
                    <input type="radio" name="airfield" id="airfield" value="interior" checked="checked" />国内
                    <input type="radio" name="airfield" id="airfield2" value="external" />国外
                </label>

            </td>
        </tr>

        <tr class="authorities">
        <tr>
            <th>
                <span class="requiredField">*</span>城市名:
            </th>
            <td  id="areasInland">
            [#list areasInland as area]
                <label>
                    <input type="checkbox" name="areas" value="${area}" />${area}
                </label>
            [/#list]
            </td>

            <td colspan="2" style="display: none;" id="areasForeigns" >
            [#list areasForeigns as area]
                <label>
                    <input type="checkbox" name="areas" value="${area}" />${area}
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