<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.versions_update.edit")}</title>


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
			sn: "required"
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.versions_update.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${software.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("software_manage.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${software.name}" maxlength="200" />
				</td>
			</tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("software_manage.sn")}:
                </th>
                <td>
                    <input type="text" name="sn" class="text" value="${software.sn}" maxlength="200" readonly/>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("software_manage.channelTypes")}:
                </th>
                <td>
                    <input type="text" name="channelTypes" class="text" value="${software.channelTypes}" maxlength="200" />
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("software_manage.channelTypesSn")}:
                </th>
                <td>
                    <input type="text" name="channelTypesSn" class="text" value="${software.channelTypesSn}" maxlength="200"  readonly/>
                </td>
            </tr>
			<tr>
				<th>
					&nbsp;
				</th>
                <td>
                    <input type="submit" class="button" value="${message("admin.common.submit")}" />
                    <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml?qId=${questionnaire.id}'" />
                </td>
			</tr>
		</table>
	</form>
</body>
</html>