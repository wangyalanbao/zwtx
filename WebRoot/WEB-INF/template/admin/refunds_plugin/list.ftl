<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.refundsPlugin.list")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $listTable = $("#listTable");
	var $install = $("#listTable a.install");
	var $uninstall = $("#listTable a.uninstall");
	
	[@flash_message /]

	// 安装
	$install.click(function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.paymentPlugin.installConfirm")}",
			onOk: function() {
				$.ajax({
					url: $this.attr("href"),
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(message) {
						if (message.type == "success") {
							location.reload(true);
						} else {
							$.message(message);
						}
					}
				});
			}
		});
		return false;
	});
	
	// 卸载
	$uninstall.click(function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.paymentPlugin.uninstallConfirm")}",
			onOk: function() {
				$.ajax({
					url: $this.attr("href"),
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(message) {
						if (message.type == "success") {
							location.reload(true);
						} else {
							$.message(message);
						}
					}
				});
			}
		});
		return false;
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.refundsPlugin.list")} <span>(${message("admin.page.total", refundsPlugins?size)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th>
					<span>${message("PaymentPlugin.name")}</span>
				</th>
				<th>
					<span>${message("PaymentPlugin.version")}</span>
				</th>
				<th>
					<span>${message("PaymentPlugin.author")}</span>
				</th>
				<th>
					<span>${message("PaymentPlugin.isEnabled")}</span>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list refundsPlugins as refundsPlugin]
				<tr>
					<td>
						[#if refundsPlugin.siteUrl??]
							<a href="${refundsPlugin.siteUrl}" target="_blank">${refundsPlugin.name}</a>
						[#else]
							${refundsPlugin.name}
						[/#if]
					</td>
					<td>
						${refundsPlugin.version!'-'}
					</td>
					<td>
						${refundsPlugin.author!'-'}
					</td>
					<td>
						<span class="${refundsPlugin.isEnabled?string("true", "false")}Icon">&nbsp;</span>
					</td>
					<td>
						[#if refundsPlugin.isInstalled]
							[#if refundsPlugin.settingUrl??]
								<a href="${refundsPlugin.settingUrl}">[${message("admin.paymentPlugin.setting")}]</a>
							[/#if]
							[#if refundsPlugin.uninstallUrl??]
								<a href="${refundsPlugin.uninstallUrl}" class="uninstall">[${message("admin.paymentPlugin.uninstall")}]</a>
							[/#if]
						[#else]
							[#if refundsPlugin.installUrl??]
								<a href="${refundsPlugin.installUrl}" class="install">[${message("admin.paymentPlugin.install")}]</a>
							[/#if]
						[/#if]
					</td>
				</tr>
			[/#list]
		</table>
	</form>
</body>
</html>