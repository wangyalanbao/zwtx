<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.airline.add")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $inputForm = $("#inputForm");
            var $planeBrandId = $("#planeBrandId");
            var $fieldSet = $(".fieldSet");
            var $typeIds = $("#typeIds");
            var $search = $(".search");

        [@flash_message /]

            // 表单验证
            $inputForm.validate({
                rules: {
                    month:{
                        required: true,
                        range:[1,12],
                        digits:true
                    },
                    timeCost:{
                        required: true,
                        number:true
                    },
                    departureAdd:"required",
                    destinationAdd:"required"
                }
            });


            // 修改飞机品牌
            $fieldSet.live("change",function(){
                var $this = $(this);
                var $typeValue = $typeIds.val();
                var $planeBrandValue = $this.val();
                if($planeBrandValue != '' || $planeBrandValue != undefined){
                    $.ajax({
                        url: "getPlaneType.jhtml",
                        type: "GET",
                        data: {id: $planeBrandValue},
                        dataType: "json",
                        success: function(data) {
                            $typeIds.empty();
                            var $commonOption = $("<option>").val("").text("${message("admin.common.choose")}");
                            $typeIds.append($commonOption);
                            $.each(data, function(i, planType){
                                var $option = $("<option>").val(planType.id).text(planType.typeName);
                                $typeIds.append($option);
                            });
                            $typeIds.val($typeValue);
                        }
                    });
                }
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

            });

        });
    </script>
    <style type="text/css">
        .roles label {
            width: 150px;
            display: block;
            float: left;
            padding-right: 6px;
        }
    </style>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.airline.add")}
</div>
<form id="inputForm" action="save.jhtml" method="post">
    <table class="input">
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airline.departureId")}:
            </th>
            <td>
                <input class="text addInput" type="hidden" id="departureIdAdd"  name="departureIdAdd" />
                <input class="text search addInput" type="text" id="departureAdd"  name="departureAdd" val='addInput' />

            </td>

        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airline.destinationId")}:
            </th>
            <td>
                <input class="text addInput" type="hidden" id="destinationIdAdd"  name="destinationIdAdd"  />
                <input class="text search addInput" type="text" id="destinationAdd"  name="destinationAdd" val='addInput'  />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airline.timeCost")}:
            </th>
            <td>
                <input type="text" id="timeCost" name="timeCost" class="text" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>

            ${message("airline.brandId")}:

            </th>
            <td>
                <select name="brandIds" id="planeBrandId" class="fieldSet">
                    <option value="0">请选择飞机品牌</option>
                [#list planeBrand as brand]
                    <option value="${brand.id}">
                    ${brand.name}
                    </option>
                [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <th>
            ${message("airline.typeId")}:
            </th>
            <td>
                <select id="typeIds" name="typeIds">
                </select>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airline.month")}:
            </th>
            <td>
                <select name="month" id="month">
                    <option value="">请选择月份</option>
                    <option value="1">一月</option>
                    <option value="2">二月</option>
                    <option value="3">三月</option>
                    <option value="4">四月</option>
                    <option value="5">五月</option>
                    <option value="6">六月</option>
                    <option value="7">七月</option>
                    <option value="8">八月</option>
                    <option value="9">九月</option>
                    <option value="10">十月</option>
                    <option value="11">十一月</option>
                    <option value="12">十二月</option>
                </select>
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