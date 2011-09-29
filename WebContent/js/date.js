function fPopUpCalendarDlg(ctrlobj)
{
     showx = event.screenX - event.offsetX - 4 - 210 ; // + deltaX;
     showy = event.screenY - event.offsetY + 18; // + deltaY;
     newWINwidth = 210 + 4 + 18;

     retval = window.showModalDialog("js/date_schedule.htm",  "",  "dialogWidth:197px; dialogHeight:210px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;  "  );
     if( retval != null ){
       ctrlobj.value = retval;
     }
     /*else
     {
       alert("��ȷ����ѡ������?");
     }*/
}
function compDate(ksrq,jsrq){//�ȽϿ�ʼ���ںͽ������� ��ʽ��2000-01-01
	var dt1;
	var dt2;
	if(ksrq!=""){
		dt1 = ksrq.replace(/\-/g,"");
	}
	if(jsrq!=""){
		dt2 = jsrq.replace(/\-/g,"");
	}
	if(dt2>dt1)
		return true;
	else
		return false;
}

var getFFVersion=navigator.userAgent.substring(navigator.userAgent.indexOf("Firefox")).split("/")[1]   
  
//extra height in px to add to iframe in FireFox 1.0+ browsers   
  
var FFextraHeight=getFFVersion>=0.1? 16 : 0    
  
  
function dyniframesize(iframename) {
	var pTar = null;
	if (document.getElementById){  
		pTar = document.getElementById(iframename);   
	}else{
		eval('pTar = ' + iframename + ';');   
	}
	if (pTar && !window.opera){   
		//begin resizing iframe   
		pTar.style.display="block";  
		if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){   
			//ns6 syntax   
			pTar.height = pTar.contentDocument.body.offsetHeight+FFextraHeight;    
		}else if (pTar.Document && pTar.Document.body.scrollHeight){   
			//ie5+ syntax   
			pTar.height = pTar.Document.body.scrollHeight;   
		}   
	}else{
		if(pTar.contentWindow.document && pTar.contentWindow.document.body.scrollHeight){ 
			pTar.height = pTar.contentWindow.document.body.scrollHeight;//Opera 
		}
	} 
} 


