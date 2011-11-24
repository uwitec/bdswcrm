//打开新的窗口（ext的window）
var win = null;  //窗口句柄
var doc = null;  //到开窗口来源页面document对象
function showPopWin(url,wWidth,wHeight,wTitle,targetDoc) {
	doc = targetDoc; 
    win = new Ext.Window({
            title:wTitle,            
            width:wWidth,            
            height:wHeight,            
            maximizable:false,    
            loadMask:"",
            modal:true,
            closeAction:'close'
        }); 
        
    win.html='<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>';     
    win.show(); 
}

//关闭窗口
function closeWin(){
	if( win != null){
		win.close();
		win = null;
	}
}

//关闭窗口刷新父窗口
function closeWinAndReload(){
	if( win != null){
		win.close();
		doc.myform.submit();
		doc = null;
		win = null;
	}
}