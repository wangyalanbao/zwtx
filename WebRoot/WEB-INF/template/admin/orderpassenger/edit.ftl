<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.orderPassenger.edit")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>

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

        [@flash_message /]

            $browserButton.browser();

            // 表单验证
            $inputForm.validate({
                rules: {
                    name:"required",
                    identityType:"required",
                    idCardNo: {
                        required: true,
                        remote: "check_idCardNo.jhtml",
                        cache: false
                    }
                }
            });


        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.orderPassenger.edit")}
</div>
<form id="inputForm" action="update.jhtml" method="post">
    <input type="hidden" name="id" value="${orderPassenger.id}" />
    <table class="input">

        <tr>
            <th>
            ${message("customer.realName")}:
            </th>
            <td>
                <input type="text" id="name" name="name" class="text" value="${orderPassenger.name}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("orderPassenger.pinyin")}:
            </th>
            <td>
                <input type="text" id="pinyin" name="pinyin" class="text" value="${orderPassenger.pinyin}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.telephone")}:
            </th>
            <td>
                <input type="text" id="telephone" name="telephone" class="text" value="${orderPassenger.telephone}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.identityType")}:
            </th>
            <td>
                 <span class="fieldSet">
                 [#list identityTypes as identityType]
                     <label>
                         <input type="radio" name="identityType" value="${identityType}" [#if identityType == orderPassenger.identityType] checked="checked"[/#if]/>${message("customer.identityType." + identityType)}
                     </label>
                 [/#list]
                 </span>
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.identityNo")}:
            </th>
            <td>
                <input type="text" id="idCardNo" name="idCardNo" class="text" value="${orderPassenger.idCardNo}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.identityExpiryEnd")}:
            </th>
            <td>
                <input type="text" id="identityExpiryEnd" name="identityExpiryEnd" class="text Wdate" value="[#if orderPassenger.identityExpiryEnd??]${orderPassenger.identityExpiryEnd?string("yyyy-MM-dd")}[/#if]" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.sex")}:
            </th>
            <td>
                <span class="fieldSet">
                [#list sexs as sex]
                    <label>
                        <input type="radio" name="sex" value="${sex}" [#if sex == orderPassenger.sex] checked="checked"[/#if] />${message("customer.sex." + sex)}
                    </label>
                [/#list]
                </span>
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.birthDate")}:
            </th>
            <td>
                <input type="text" id="birth" name="birth" class="text Wdate" value="[#if orderPassenger.birth??]${orderPassenger.birth?string("yyyy-MM-dd")}[/#if]" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.nationality")}:
            </th>
            <td>
                <input type="text" id="nationality" name="nationality" class="text" value="${orderPassenger.nationality}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.business")}:
            </th>
            <td>
                <input type="text" id="business" name="business" class="text" value="${orderPassenger.business}" maxlength="200" />
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