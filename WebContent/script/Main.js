var remote=null;
var MouseX;
var MouseY;
var oldvalue;	//进入焦点时记录下该控件的值
var RequirementValidControls=new Array();	//要验证的控件数组



function rs(n,u,w,h,x) {
	var left = Math.ceil((screen.width - w) / 2);   //实现居中
    var top = Math.ceil((screen.height - h) / 2);  //实现居中
    

  args="width="+w+",height="+h+",left=" + left + ",top=" + top + ",statusbar=yes,resizable=yes,scrollbars=yes,status=0,"+x;  
  remote=window.open(u,n,args);
 
  if (remote != null) {
    if (remote.opener == null)
      remote.opener = self;
      remote.focus();
  }
  if (x == 1) { return remote; }
}


function ShowWindow(u) {

	var w=screen.width-100;
	var h=screen.height-200;
	var n='';

	var left = Math.ceil((screen.width - w) / 2);   //实现居中
    var top = Math.ceil((screen.height - h) / 2)-20;  //实现居中
    

	args="width="+w+",height="+h+",left=" + left + ",top=" + top + "statusbar=yes,resizable=yes,scrollbars=yes,status=0";  
	remote=window.open(u,n,args);
 

  if (remote != null) {
	
    if (remote.opener == null)
      remote.opener = self;
      remote.focus();
  }
 // if (x == 1) { return remote; }
}


function ShowFullWindow(u) {

	var w=screen.width;
	var h=screen.height-10;
	var n='';

	var left = Math.ceil((screen.width - w) / 2);   //实现居中
    var top = Math.ceil((screen.height - h) / 2);  //实现居中
    

	args="statusbar=yes,resizable=yes,scrollbars=yes,status=0;";  
	remote=window.open(u,n,args);
 

  if (remote != null) {
	
	var offset = (navigator.userAgent.indexOf("Mac") != -1 || navigator.userAgent.indexOf("Gecko") != -1 ||
            navigator.appName.indexOf("Netscape") != -1) ? 0 : 4;
       remote.moveTo(-offset, -offset);
        remote.resizeTo(screen.availWidth + (2 * offset), screen.availHeight + (2 * offset));

    if (remote.opener == null)
      remote.opener = self;
      remote.focus();
  }
 // if (x == 1) { return remote; }
}

function ShowMenuWindow(u) {

	var w=screen.width-10;
	var h=screen.height-10;
	var n='';

	var left = Math.ceil((screen.width - w) / 2);   //实现居中
    var top = Math.ceil((screen.height - h) / 2);  //实现居中
    
	args="statusbar=yes,resizable=yes,scrollbars=yes,status=0,width="+w+",height="+h+";";  
	remote=window.open(u,n,args);
 

  if (remote != null) {
	
	var offset = (navigator.userAgent.indexOf("Mac") != -1 || navigator.userAgent.indexOf("Gecko") != -1 ||
            navigator.appName.indexOf("Netscape") != -1) ? 0 : 4;
        remote.moveTo(left-offset, top);
       // remote.resizeTo(screen.availWidth + (2 * offset), screen.availHeight + (2 * offset));

    if (remote.opener == null)
      remote.opener = self;
      remote.focus();
  }
 // if (x == 1) { return remote; }
}


function ShowModuleWindow(n,u,w,h,x) {

  args="resizable:no;scroll:yes;status:no;dialogWidth="+w+"px;dialogHeight="+h+"px;center=yes;help=no";  
  remote=window.showModalDialog(u,n,args);
  if (remote != null) {
    if (remote.opener == null)
      remote.opener = self;
      remote.focus();
  }
  if (x == 1) { return remote; }
}


function ShowInventoryWindow(u) {

	args="dialogWidth=300px;dialogHeight=150px;titlebar:no,resizable:no,status:no;center:yes;scroll:yes;help=no";  
	remote=window.showModalDialog(u,'',args);
 
  if (remote != null) {
    if (remote.opener == null)
      remote.opener = self;
      remote.focus();
  }
  
}


function ShowArroveWindow(u) {

	args="location=no,menubar=no,titlebar=no,resizable=no,status=0,left=0,top=0";  
	remote=window.open(u,'',args);
 
  if (remote != null) {
    if (remote.opener == null)
      remote.opener = self;
      remote.focus();
  }
  
}

function StatusReport() {
	MouseX = event.clientX;  
	MouseY = event.clientY;
}

function OpenDocWnd(u,n) 
{
	rs(n,u,750,550,",left=10,top=10");
}
	
function ViewDoc(sid)
{

	OpenDocWnd("DocumentFiles/ManageDocument.aspx?action=View&fileId="+sid, "View"+sid);
}

function ViewDocInFrame(sid,fmContent)
{
	OpenDocWnd("DocumentFiles/ManageDocument.aspx?action=Download&InFrame=1&Id="+sid+"&Inline=1", fmContent);
}


function ViewDataDocuments(appid,mid,did,materielid)
{
	OpenDocWnd("DocumentFiles/ManageDocument.aspx?action=View&appid="+appid+"&ModuleID="+mid+"&DataID="+did+"&MaterielID="+materielid, "View");
}

function OpenEditDocWnd(u,n) 
{
	rs(n,u,750,500,",left=10,top=10");	
}

/*************************************************************************************************************/

function CheckRequirementValid()
{
		var nofind=true;
	
	   for(var i=0;i<RequirementValidControls.length;i++)
	   {			
			var control= document.getElementById(RequirementValidControls[i]);
			if(control!=null)
			{
			//获取隐藏关联对象
				var chkNull=false;
				var isValid=true;
				
				var hidden= document.getElementById(control.id+"_hidden");
				if(hidden!=null)
				{					
					if(hidden.value.substr(0,1)=="1")		//要检查是否为空
						chkNull=true;
					if(hidden.value.substr(1,1)=="1")		//不合法
						isValid=false;
				}
				if(chkNull)	//要检查是否为空
				{
					if(control.value=='')
						isValid=false;
				}
				
				if(control.tagName=="INPUT" || control.tagName=="TEXTAREA")
				{
					if(!isValid)
					{						
						control.style.borderColor="red";	
						nofind=false;		
					}
					else
						control.style.borderColor="Gainsboro";	
				}				
				else if(control.tagName=="SELECT")
				{			
					if(!isValid && control.parentElement!=null && control.parentElement.tagName=="DIV")
					{									
						control.parentElement.style.borderColor="red";
						nofind=false;	
					}					
					else
					{
						control.parentElement.style.borderColor="Gainsboro";				
					}
				}
			}
				
	   }
	   if(!nofind)
		 alert('保存失败,原因是数据没有输入完整,必须输入的栏目已经用红色边框标识出来,请输入后再保存!');
	   else	//开始保存
		{
			WaitingInformation();						
		}
	   return nofind;
}


function WaitingInformation()
{
			var divBackGround=document.getElementById('divUpdateStatus');
			if(divBackGround!=null)
				divBackGround.className="UpdateTransparent";
			var divSave=document.getElementById('divSave');
			if(divSave!=null)
				divSave.className="UpdateTransparentdiv";
}

/*检查当前输入是否合法*/
function TextProcessKey(ctl) {
	if(event.keyCode==13)
	{
	 	event.keyCode=9;
	}
	OnKeyPressDown(event.keyCode,ctl);
}

/*检查当前输入是否合法*/
function CalendarTextLostFocus(ctl) {

   if(ctl.value!='')
   {
	if(!IsValidDate(ctl.value))
	{
	  alert("请输入合法的日期格式,例如:2008-01-01");	 
	}
   }
   focusout(ctl);
}


//限制输入内容

function TextKeyRestrict(e, validchars) {
 var key='', keychar='';
 key = getKeyCode(e);
 if (key == null) return true;
 keychar = String.fromCharCode(key);
 keychar = keychar.toLowerCase();
 validchars = validchars.toLowerCase();
 if (validchars.indexOf(keychar) != -1)
  return true;
 if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
  return true;
 return false;
}

function getKeyCode(e)
{
 if (window.event)
    return window.event.keyCode;
 else if (e)
    return e.which;
 else
    return null;
}



//控件得到焦点的时候设置

function setfocus(ctl) {
	ctl.select();
	ctl.style.fontWeight='bold';
	ctl.style.borderWidth='2px';
	
	var helpcontrol= document.getElementById(ctl.id+"_span_help");	
	if(helpcontrol!=null)
	    helpcontrol.style.display="inline";
	    
	var validcontrol= document.getElementById(ctl.id+"_span_error");	
	if(validcontrol!=null)
	    validcontrol.style.display="none";
	    

//	if(ctl.parentElement!=null)
//		ctl.parentElement.style.backgroundColor="red";	    
	//CadetBlue
}
//控件失去焦点的

function focusout(ctl) 
{
	
	if(ctl.style.borderColor=='red' && ctl.value!='')
		ctl.style.borderColor="Gainsboro";		
	ctl.style.borderWidth='1px';
	ctl.style.fontWeight='normal';	
	
	var helpcontrol= document.getElementById(ctl.id+"_span_help");	
	if(helpcontrol!=null)
	    helpcontrol.style.display="none";
	    
	var validcontrol= document.getElementById(ctl.id+"_span_error");	
	if(validcontrol!=null)
	    validcontrol.style.display="inline";
}

function DropDownFocusOut(ctl)
{
	if(ctl.parentElement.style.borderColor=='red' && ctl.value!='')
		ctl.parentElement.style.backgroundColor="Gainsboro";		
}

//显示数据选择窗体
function ShowHelp(HelpID )
{
	var URL='HelpWindow.aspx?helpId='+HelpID;
	
	window.showModalDialog(URL,'newwin','dialogHeight:400px; dialogWidth:600px; edge: Raised; center: Yes; help: Yes; resizable: no; status: no;'); 
}

//显示数据选择窗体
function ShowHelperWindow(URL,KeyField,KeyClientId,HiddenId,CodeId,TextID,dlgWidth,dlgHeight)
{
	var strKey='';
	if(KeyField!='' && KeyClicentId!='')
	{
		var control= document.getElementById(KeyClicentId);
		
		strKey='kfid='+KeyField+'&kval='+control.value;	
		
		URL=URL+'&'+strKey;
	}
	
	var ret=window.showModalDialog(URL,'newwin','dialogHeight:'+dlgHeight+'; dialogWidth:'+dlgWidth+'; edge: Raised; center: Yes; help: Yes; resizable: no; status: no;'); 
	if(ret!=null && ret.length>1)
	{
		HiddenId.value=ret[0];
		CodeId.value=ret[1];
		TextID.value=ret[2];
	}
}

//显示关键信息输入窗体
function ShowExtraDataWindow(URL,HiddenId,ExtraHiddenId,NoteHiddenId,dlgWidth,dlgHeight)
{
	if(HiddenId!=null)
	{
		URL=URL+'ExtraId='+HiddenId.value+'&KeyId='+ExtraHiddenId.value+'&NoteId='+NoteHiddenId.value+'&MenuID=0';

		var ret=window.showModalDialog(URL,'newwin','dialogHeight:'+dlgHeight+'; dialogWidth:'+dlgWidth+'; edge: Raised; center: Yes; help: Yes; resizable: no; status: no;'); 
		if(ret!=null && ret.length>1)
		{
			ExtraHiddenId.value=ret[0];
			NoteHiddenId.value=ret[1];	
		}
	}
}

//检查输入是否的合法日期
function IsValidDate(arg){
	var r = arg.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
   	if(r==null)
		return false; 
	var d = new Date(r[1], r[3]-1, r[4]); 
   	return(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}


//*************************************************************************************
//*
//* 	表格内移动移动

//*
//*************************************************************************************

function OnKeyPressDown(keycode,ctl) {
    
      var tr;	
      var td=FindParentTd(ctl);
      var ctlTag=ctl.tagName;
      var type=ctl.type;

      if(td!=null)	//如果有TD的话就处理
      {
	
      		var x=td.cellIndex ;      
      	if(keycode==38)
			tr=td.parentElement.previousSibling;	
		if(keycode==40)
			tr=td.parentElement.nextSibling;
		//if(keycode==37)	//左,39 右
	
      	if(tr!=null && tr.cells!=null && tr.cells[x]!=null && tr.cells[x].all[0]!=null)
		{	    
			var activeCtl=FinActiveControl(tr.cells[x].all[0],ctlTag,type);		     
			if(activeCtl!=null)
				activeCtl.focus();      	
		}
	}

}

function FinActiveControl(ctrl,ctlTag,ctlType)
{
	var ctl;
			
	if(ctrl.tagName==ctlTag && ctrl.type==ctlType)
	{
	  
	   return ctrl;
	}
	else
	{
	   for(var i=0;i<ctrl.all.length;i++)
	   {
		ctl=FinActiveControl(ctrl.all[i],ctlTag,ctlType);
		if (ctl!=null)
		  return ctl;
	   }
	}
	return ctl;
}

	
//寻找上一级的TD
function FindParentTd(ctl)
{
        var td;
	if(ctl.parentElement!=null && ctl.parentElement.tagName!='TD')
	{	   
	    td=FindParentTd(ctl.parentElement);		
	}
	else
	    td=ctl.parentElement;	
	return td;
}


///检查控件内容是否修改
function OnBeforeUnload()
{
		  if(event.clientX>document.body.clientWidth && event.clientY<0||event.altKey)
		  {		
			if(DataIsDirty())
				 event.returnValue = "页面值已经修改，请确认是否放弃修改？";			
		}
}

function DataIsDirty()
{
	//检查INPUT
	var inputs = document.getElementsByTagName('INPUT');
	for(var i=0;i<inputs.length;i++)
	{		
		var control= inputs[i];
		var hidden= document.getElementById(control.id+"_hidden");
		if(hidden!=null)	//有关联控件
		{
			if(hidden.value.substr(8)!=control.value)		//要检查是否为空
			{
				return true;				
			}				
		}
	}
	//检查TEXTAREA
	var texts = document.getElementsByTagName('TEXTAREA');
	for(var i=0;i<texts.length;i++)
	{
		var control= texts[i];
		var hidden= document.getElementById(control.id+"_hidden");
		if(hidden!=null)	//有关联控件
		{
			if(hidden.value.substr(8)!=control.value)		//要检查是否为空
			{
				return true;				
			}				
		}
	}	
	//检查SELECT
	var ddls = document.getElementsByTagName('SELECT');
	for(var i=0;i<ddls.length;i++)
	{
		var control= ddls[i];
		
		var hidden= document.getElementById(control.id+"_hidden");
		if(hidden!=null)	//有关联控件
		{
			if(hidden.value.substr(8)!=control.value)		//要检查是否为空
			{
				return true;				
			}				
		}
	}		

	return false;
}

//***************************2008.07.22 蔡德伟**********************
function ShowTabNav(title,u) {
  parent.addtab(title,u,"");
}


/***************************2008.07.29 潘杰**************************/
// 输入数量金额折扣率后自动计算价格等

	function ForDight(Dight,How) 
	{ 
		Dight = Math.round (Dight*Math.pow(10,How))/Math.pow(10,How); 
		return Dight; 
	} 
	function ChangeValue(obj,strListPrice,strDiscount,strPrice,strQuantity,strAmount)
	{
		var txtListPrice=document.getElementById(strListPrice);
		var txtDiscount=document.getElementById(strDiscount);
		var txtPrice=document.getElementById(strPrice);
		var txtAmount=document.getElementById(strAmount);
		var txtQuantity=document.getElementById(strQuantity);
	
		if(obj.id==strListPrice)
		{
			txtPrice.value=ForDight(txtListPrice.value*txtDiscount.value/100,2);
			txtAmount.value=ForDight(txtQuantity.value*txtPrice.value,2);
		}
		else if(obj.id==strDiscount)
		{
			txtPrice.value=ForDight(txtListPrice.value*txtDiscount.value/100,2);
			txtAmount.value=ForDight(txtQuantity.value*txtPrice.value,2);
		}
		else if(obj.id==strPrice)
		{
			
			if( Number(txtListPrice.value)!=0)
				txtDiscount.value=ForDight(txtPrice.value/txtListPrice.value*100,1);
				
			txtAmount.value=ForDight(txtQuantity.value*txtPrice.value,2);
		}
		
		else if(obj.id==strQuantity)
		{
				
			txtAmount.value=ForDight(txtQuantity.value*txtPrice.value,2);			
		}			

	}

