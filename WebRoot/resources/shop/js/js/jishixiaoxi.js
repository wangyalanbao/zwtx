//查看结果-biaodq

var path=getRootPath();
$(function() {
	var application = $('#application', parent.document).val();
	var mainAccount = $('#mainAccount', parent.document).val();
	var mainToken = $('#mainToken', parent.document).val();
	var myvoipid = $('#myvoipid', parent.document).val();
	
	// 获取voip账号
	$.ajax({
		type : "post",
		url : path+"/getSubByAppid.do",
		data : "application=" + application + "&mainAccount=" + mainAccount
				+ "&mainToken=" + mainToken + "&flag=0&voip="+myvoipid,
		dataType : "text",
		success : function(data) {
			$("#contact").html(data);
			$("#loading").hide();
		}
	});
	
	/*
	 * 获取加入的群
	 */
	
	var subAccount = $('#subAccount',parent.document).val();
	var subToken = $('#subToken',parent.document).val();
	$.ajax({
		type : "post",
		url :path+"/onequeryGroup.do",
		data : "subAccount="+subAccount+"&subToken="+subToken,
		dataType : "text",
		success : function(data) {
			if(data!=null && data!=''){
				var groupids=data.split(",");
				var str="";
				for(var i=0;i<groupids.length;i++){
					str+="<li class='clearfix' style='cursor:hand' onclick='sendto(this,\"group\");'>" +
							"<img class='touxiang' src='images/qun_touxiang.jpg' alt=''/>" +
							"<div class='group_info'>" +
							"<p>"+groupids[i]+"</p>" +
							"</div>" +
							"</li>";
				}
				$("#joined").html(str);
			}
		}
	});
	
	/*
	 * 获取公共群组
	 */
	$.ajax({
		type : "post",
		url :path+"/GetPublicGroups.do",
		data : "subAccount="+subAccount+"&subToken="+subToken,
		dataType : "text",
		success : function(data) {
			if(data!=null && data!=''){
				var groupids=data.split(",");
				var str="";
				for(var i=0;i<groupids.length;i++){
					str+="<li class='clearfix' style='cursor:hand' onclick='sendto(this,\"group\");'> " +
							"<img class='touxiang' src='images/qun_touxiang.jpg' alt=''/>" +
							"<div class='group_info'>" +
							"<p>"+groupids[i]+"</p>" +
							"</div>" +
							"</li>";
				}
				$("#publicGroups").html(str);
			}
		}
	});

	/* 会话、联系人、群组切换 */
	$('.tablist').find('li').click(function() {
		var index = $(this).index();
		$(this).addClass('active').siblings().removeClass('active');
		$('.ulbox').find('.child').eq(index).show().siblings().hide();
	});
	/* 群组内部切换 */
	$('.ul3 h2').find('a').click(function() {
		var index = $(this).index();
		$(this).addClass('current').siblings().removeClass('current');
		$('.grouplist').find('.commonlist').eq(index).show().siblings().hide();
	});
	/* 管理员查看群资料，对群成员的操作：禁言、请出 */
	$('.group_mem_list').find('li').hover(function() {
		$(this).find('.ability').show();
	}, function() {
		$(this).find('.ability').hide();
	});
	
});


function getRootPath() {
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8080
	var localhostPaht = curWwwPath.substring(0, pos);
	//获取带"/"的项目名，如：/nnjssd
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	//alert(localhostPaht+projectName);
	return localhostPaht + projectName;
}