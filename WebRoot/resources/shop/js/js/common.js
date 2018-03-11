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
/*将秒转换成时分秒*/
function formatSeconds(value) {  
	var theTime = Number(value);  
	        var theTime1 = 0;  
	        var theTime2 = 0;  
	        if(theTime > 60) {  
	            theTime1 = Number(theTime/60);  
	            theTime = Number(theTime%60);  
	            if(theTime1 > 60) {  
	                theTime2 = Number(theTime1/60);  
	                theTime1 = Number(theTime%60);  
	            }  
	        }  
	        var result = ""+theTime+"秒";  
	        if(theTime1 > 0) {  
	            result = ""+parseInt(theTime1)+"分"+result;  
	        }  
	        if(theTime2 > 0) {  
	            result = ""+parseInt(theTime2)+"时"+result;  
	        }  
	        return result;  
	}  
