<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.orderPassenger.add")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>

    <script type="text/javascript">
        $().ready(function() {

            var $inputForm = $("#inputForm");

        [@flash_message /]

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
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.orderPassenger.add")}
</div>
<form id="inputForm" action="save.jhtml" method="post">
    <table class="input">

        <tr>
            <th>
            ${message("customer.realName")}:
            </th>
            <td>
                <input type="text" id="name" name="name" class="text"  maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("orderPassenger.pinyin")}:
            </th>
            <td>
                <input type="text" id="pinyin" name="pinyin" class="text"  maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.telephone")}:
            </th>
            <td>
                <input type="text" id="telephone" name="telephone" class="text"  maxlength="200" />
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
                    <input type="radio" name="identityType" value="${identityType}"/>${message("customer.identityType." + identityType)}
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
                <input type="text" id="idCardNo" name="idCardNo" class="text"  maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.identityExpiryEnd")}:
            </th>
            <td>
                <input type="text" id="identityExpiryEnd" name="identityExpiryEnd" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.sex")}:
            </th>
            <td>
            <span class="fieldSet">
            [#list sexs as sex ]
                <label>
                    <input type="radio" name="sex" value="${sex}" [#if sex == "male"] checked="checked"[/#if] />${message("customer.sex." + sex)}
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
                <input type="text" id="birth" name="birth" class="text Wdate"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.nationality")}:
            </th>
            <td>
                <input type="text" id="nationality" name="nationality" class="text"  maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("customer.business")}:
            </th>
            <td>
                <input type="text" id="business" name="business" class="text" maxlength="200" />
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