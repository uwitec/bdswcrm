//���µĴ��ڣ�ext��window��
var win = null;  //���ھ��
var doc = null;  //����������Դҳ��document����
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

//�رմ���
function closeWin(){
	if( win != null){
		win.close();
		win = null;
	}
}

//�رմ���ˢ�¸�����
function closeWinAndReload(){
	if( win != null){
		win.close();
		doc.myform.submit();
		doc = null;
		win = null;
	}
}