<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.role.edit")}</title>


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
                    name: "required",
                    authorities: "required"
                }
            });

        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.role.edit")}
</div>
<form id="inputForm" action="update.jhtml" method="post">
    <input type="hidden" name="id" value="${role.id}" />
    <table class="input">
        <tr>
            <th>
                <span class="requiredField">*</span>${message("Role.name")}:
            </th>
            <td>
                <input type="text" name="name" class="text" value="${role.name}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("Role.description")}:
            </th>
            <td>
                <input type="text" name="description" class="text" value="${role.description}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;
            </td>
        </tr>
        <tr class="authorities">
            <th>
                <a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.role.systemGroup")}</a>
            </th>
            <td>
					<span class="fieldSet">

						<label>
                            <input type="checkbox" name="authorities" value="admin:admin" [#if role.authorities?seq_contains("admin:admin")] checked="checked"[/#if] />${message("admin.role.admin")}
                        </label>
						<label>
                            <input type="checkbox" name="authorities" value="admin:role" [#if role.authorities?seq_contains("admin:role")] checked="checked"[/#if] />${message("admin.role.role")}
                        </label>
                        <label>
                            <input type="checkbox" name="authorities" value="admin:company" [#if role.authorities?seq_contains("admin:company")] checked="checked"[/#if]/>${message("admin.main.company")}
                        </label>
                        <label>
                            <input type="checkbox" name="authorities" value="admin:software_manage" [#if role.authorities?seq_contains("admin:software_manage")] checked="checked"[/#if] />软件管理
                        </label>
                        <label>
                            <input type="checkbox" name="authorities" value="admin:storagePlugin" [#if role.authorities?seq_contains("admin:storagePlugin")] checked="checked"[/#if] />${message("admin.role.storagePlugin")}
                        </label>
						[#--<label>--]
                            [#--<input type="checkbox" name="authorities" value="admin:versions_update" [#if role.authorities?seq_contains("admin:versions_update")] checked="checked"[/#if] />版本管理--]
                        [#--</label>--]

					</span>
            </td>
        </tr>
        <tr class="authorities">
            <th>
                <a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.main.base")}</a>
            </th>
            <td>
					<span class="fieldSet">
						<label>
                            <input type="checkbox" name="authorities" value="admin:planeBrand" [#if role.authorities?seq_contains("admin:planeBrand")] checked="checked"[/#if]/>${message("admin.main.planeBrand")}
                        </label>
						<label>
                            <input type="checkbox" name="authorities" value="admin:airplane" [#if role.authorities?seq_contains("admin:airplane")] checked="checked"[/#if]/>${message("admin.main.airplane")}
                        </label>
                        <label>
                            <input type="checkbox" name="authorities" value="admin:airport" [#if role.authorities?seq_contains("admin:airport")] checked="checked"[/#if]/>${message("admin.main.airport")}
                        </label>

                        <label>
                            <input type="checkbox" name="authorities" value="admin:airline" [#if role.authorities?seq_contains("admin:airline")] checked="checked"[/#if]/>${message("admin.main.airline")}
                        </label>

					</span>
            </td>
        </tr>
        <tr class="authorities">
            <th>
                <a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.main.customer")}</a>
            </th>
            <td>
					<span class="fieldSet">
						<label>
                            <input type="checkbox" name="authorities" value="admin:customer" [#if role.authorities?seq_contains("admin:customer")] checked="checked"[/#if] />${message("admin.customer.lists")}
                        </label>
						<label>
                            <input type="checkbox" name="authorities" value="admin:customerFeedback" [#if role.authorities?seq_contains("admin:customerFeedback")] checked="checked"[/#if] />${message("admin.main.customer_feedback")}
                        </label>
					</span>
            </td>
        </tr>
    [#if role.isSystem]
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <span class="tips">${message("admin.role.editSystemNotAllowed")}</span>
            </td>
        </tr>
    [/#if]
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="submit" class="button" value="${message("admin.common.submit")}"[#if role.isSystem] disabled="disabled"[/#if] />
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>