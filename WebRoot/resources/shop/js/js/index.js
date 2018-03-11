$(function(){
	/*会话、联系人、群组切换*/
	$('.tablist').find('li').click(function(){
		var index = $(this).index();
		$(this).addClass('active').siblings().removeClass('active');
		$('.ulbox').find('.child').eq(index).show().siblings().hide();	
	})
	/*群组内部切换*/
	$('.ul3 h2').find('a').click(function(){
		var index = $(this).index();
		$(this).addClass('current').siblings().removeClass('current');
		$('.grouplist').find('.commonlist').eq(index).show().siblings().hide();
	})
	/*管理员查看群资料，对群成员的操作：禁言、请出*/
	$('.group_mem_list').find('li').hover(function(){
		$(this).find('.ability').show();
	},function(){
		$(this).find('.ability').hide();
	})
	//左侧列表部分
	var now = -1;
	$('.left_list h2').click(function(){
		var index = $(this).parent().index();
		if(now==index){
			//收缩
			$(this).next().hide();
			$(this).parent().removeClass('active');
			$(this).find('i').removeClass().addClass('pointerR');	
			now=-1;
		}else{
			//收起原来的
			if(now!=-1){
				$('.left_list').children().eq(now).find('ul').hide();
				$('.left_list').children().eq(now).removeClass('active');
				$('.left_list').children().eq(now).find('i').removeClass().addClass('pointerR');
			}
			//展开
			$(this).next().show();
			$(this).parent().addClass('active');
			$(this).find('i').removeClass().addClass('pointerB');	
			now=index;
		}
	});
	/*点击显示当前*/
	$('.left_list').find('li.child').click(function(){
		$('.left_list').find('li.child').removeClass('current');
		$(this).addClass('current').siblings().removeClass('current');	
	});
	
	$("#im").click(function(){
		$("#rightFrame").hide();
		$("#jishixiaoxi").show();
	});
	$("a[target='rightFrame']").each(function(){
		$(this).click(function(){
			$("#jishixiaoxi").hide();
			$("#rightFrame").show();
		});
	});
	$("#newMsg").click(function(){
		$("#newMsg").hide();
		$("#rightFrame").hide();
		$("#jishixiaoxi").show();
	});

});