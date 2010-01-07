var validrule                  = new Object();
validrule.LetterStr            = /^([a-zA-Z]+)?$/;     
validrule.NumAndStr            = /^([0-9a-zA-Z]+)?$/;
validrule.NumStr               = /^(\d*)?$/;  
validrule.string               = /^([^'<>]+)?$/;
validrule.int                  = /^(\d{1,9})?$/; 
validrule.minusint             = /^(\-([1-9])(\d*))?$/;
validrule.float                = /^((\.([0-9]\d*))|(([0-9]\d*)\.\d+$)|([0-9]\d*))?$/;                    
validrule.date                 = /^((([1-9]\d{3})|([1-9]\d{1}))-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]))?$/;  
validrule.time                 = /^((0[1-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9]))?$/; 
validrule.datetime             = /^((([1-9]\d{3})|([1-9]\d{1}))-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]) (0[1-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9]))?$/;   
validrule.year                 = /^(\d{4})?$/; 
validrule.month                = /^([1-9]|0[1-9]|1[0-2])?$/;
validrule.day                  = /^([1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1])?$/;
validrule.postcode             = /^(\d{6})?$/;           
validrule.email                = /^(.+\@.+\..+)?$/;   
validrule.phone                = /^(\(\d{3}\))?(\(?(\d{3}|\d{4}|\d{5})\)?(-?)(\d+))?((-?)(\d+))?$/; 
validrule.mobiletel            = /^(01(\d{10})|1(\d{10}))?$/; 
validrule.ip                   = /^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5]))?$/;  
validrule.idcard               = /^(\d{15}|\d{18}|\d{17}X|\d{17}x)?$/; 
validrule.tabledefine          = /^(([A-Za-z])([A-Za-z0-9|_]){1,18})?$/; 
validrule.integer              = /^(\d{1,9})?$/; 
validrule.number               = /^(\d*)?$/;
validrule.url                  = /^(http:\/\/)/;
validrule.chinese              = /^[\u4E00-\u9FA5]*$/;
validrule.notsstring           = /^([^'<>%\s]+)?$/;
validrule.htm           	   = /^\w+.+htm?$|\w+.+html?$/;                                     
validrule.file           	   = /^([^'<>]+\:+)|(\\+)([^'<>]+)?$/;
validrule.DataStr              = /^[a-zA-Z]+\w*$/;     

//去左空格;
function ltrim(s)
{
	return s.replace(/(^\s*)/g, "");
}
//去右空格;
function rtrim(s)
{
	return s.replace(/(\s*$)/g, "");
}
//去左右空格;
function trim(s){
	return rtrim(ltrim(s)); 
}

function validateCheckBoxOrRadio(el){
	for(var j=0;j< el.length;j++)
	{
		if(el[j].checked){
			return true;
			break;
		}else{
			continue;
		}
	}
	return false;
}
function doValidate( vform ) 
{
	var elems = vform.elements;
	var frmLen = elems.length;
	var thePat = "";
	var strFormatInfo = "";
	//对于每一个FROM元素
	for(var i=0;i<frmLen;i++) 
	{
		var _elem = elems[i];
		
		//为空检查                
		if((_elem.notNull != null && _elem.notNull == "true") || (_elem.notnull != null && _elem.notnull == "true")) 
		{
			if(_elem.type == "checkbox" || _elem.type == "radio"){
				var el = document.getElementsByName(_elem.name);
				if(!validateCheckBoxOrRadio(el)){
					alert(_elem.vdisp+"不能为空!");
					return false;
				}
			}else{
				if(trim(_elem.value).length == 0) 
				{
					alert(_elem.vdisp+"不能为空!");
					_elem.focus();
					return false;
				}
			}
		}
		
		if(_elem.type == "textarea"){
			if(trim(_elem.value).length > _elem.maxlength){
				alert(_elem.vdisp+"超过最大长度，最大长度限为"+_elem.maxlength+"字！");
				_elem.value=_elem.value.substring(0,_elem.maxlength);
				_elem.focus();
				return false;
			}
		}
                //类型检查                  
		if(_elem.vtype == null) 
		{
			continue;
		}
	      	if(_elem.vtype=="none")
	      	{         
	      	   thePat = "";
	      	   strFormatInfo = "";
	      	}
	      	if(_elem.vtype=="LetterStr")
	      	{       
	      	   thePat = validrule.LetterStr;
	      	   strFormatInfo = "纯字母字符串";
	      	}
	      	if(_elem.vtype=="NumAndStr")
	      	{       
	      	   thePat = validrule.NumAndStr;
	      	   strFormatInfo = "数字和字母字符串";
	      	}
	      	if(_elem.vtype=="NumStr")
	      	{       
	      	   thePat = validrule.NumStr;
	      	   strFormatInfo = "纯数字组成的字符串";
	      	}
	      	if(_elem.vtype=="string")
	      	{       
	      	   thePat = validrule.string;
	      	   strFormatInfo = "不含特殊符号的字符串";
	      	}
	      	if(_elem.vtype=="int")         
	      	{       
	      	   thePat = validrule.int;
	      	   strFormatInfo = "整数";
	      	}
	      	if(_elem.vtype=="minusint")         
	      	{       
	      	   thePat = validrule.minusint;
	      	   strFormatInfo = "负整数，比如-123";
	      	}
	      	if(_elem.vtype=="float")         
	      	{       
	      	   thePat = validrule.float;
	      	   strFormatInfo = "实数，比如356.32";
	      	}
	      	if(_elem.vtype=="date")         
                {       
	      	   thePat = validrule.date;
	      	   strFormatInfo = "日期型，比如 2004-08-12";
	      	}
	      	if(_elem.vtype=="time")         
	      	{       
	      	   thePat = validrule.time;
	      	   strFormatInfo = "时间型，比如08:37:29";
	      	}	      	 
	      	if(_elem.vtype=="datetime")         
	      	{       
	      	   thePat = validrule.datetime;
	      	   strFormatInfo = "日期时间型，比如2004-08-12 08:37:29";
	      	}
                if(_elem.vtype=="year")         
	      	{       
	      	   thePat = validrule.year;
	      	   strFormatInfo = "年代格式，比如 2005";
	      	}
	      	if(_elem.vtype=="month")         
	      	{       
	      	   thePat = validrule.month;
	      	   strFormatInfo = "月份格式，比如 08";
	      	}
                if(_elem.vtype=="day")         
	      	{       
	      	   thePat = validrule.day;
	      	   strFormatInfo = "日子格式，比如 14";
	      	} 
	      	if(_elem.vtype=="postcode")         
	      	{       
	      	   thePat = validrule.postcode;
	      	   strFormatInfo = "邮编，比如 100001";
	      	}	      	
	      	if(_elem.vtype=="email")         
	      	{       
	      	   thePat = validrule.email;
	      	   strFormatInfo = "电子邮件格式，比如 msm@hotmail.com";
	      	}
	      	if(_elem.vtype=="phone")         
	      	{       
	      	   thePat = validrule.phone;
	      	   strFormatInfo = "电话号码格式，比如010-00000000";
	      	}
	      	if(_elem.vtype=="mobiletel")         
	      	{       
	      	   thePat = validrule.mobiletel;
	      	   strFormatInfo = "手机号码格式，比如138000000";
	      	}	      	
	      	if(_elem.vtype=="ip")       
	      	{       
	      	   thePat = validrule.ip;
	      	   strFormatInfo = "机器ip地址格式，比如 172.22.169.11";
	      	}
	      	if(_elem.vtype=="url")       
	      	{       
	      	   thePat = validrule.url;
	      	   strFormatInfo = "url地址格式，比如 http://www.baidu.cn";
	      	}
	      	if(_elem.vtype=="idcard")       
	      	{       
	      	   thePat = validrule.idcard;
	      	   strFormatInfo = "身份证号码，比如15位或者18位数字";
	      	}
	      	if(_elem.vtype=="tabledefine")   
	      	{       
	      	   thePat = validrule.tabledefine;
	      	   strFormatInfo = "不能以数字开头，如p_tablename";
	      	}
	      	if(_elem.vtype=="integer")         
	      	{       
	      	   thePat = validrule.integer;
	      	   strFormatInfo = "整数";
	      	}
	      	if(_elem.vtype=="number")         
	      	{       
	      	   thePat = validrule.number;
	      	   strFormatInfo = "数字字符";
	      	}
	      	if(_elem.vtype=="chinese")
	      	{
	      	   thePat = validrule.chinese;
	      	   strFormatInfo = "汉字";
	      	}
	      	if(_elem.vtype == "notsstring")
	      	{
	      	   thePat = validrule.notsstring;
	      	   strFormatInfo = "不包含空格和特殊字符的字符串";
	      	}
	      	if(_elem.vtype == "file")
	      	{
	      	   thePat = validrule.file;
	      	   strFormatInfo = "文件格式";
	      	}
	      	if(_elem.vtype == "htm")
	      	{
	      	   thePat = validrule.htm;
	      	   strFormatInfo = "html格式或htm格式";
	      	}	 
	      	if(_elem.vtype == "DataStr")
	      	{
	      	   thePat = validrule.DataStr;
	      	   strFormatInfo = "第一个字符必须是英文字母";
	      	}	      		      	
	      	var gotIt = null; 
	      	if(thePat!="")
	      	{
	      	      gotIt = thePat.exec(_elem.value);
	      	}	      	 
	      	if(gotIt == null) 
	      	{
	      		alert(_elem.vdisp+"输入不合法,格式应为："+strFormatInfo);
	      		_elem.focus();
	      		return false;
	      	}
	}  
	return true;
}
  
function submitForm(vform){

	if (doValidate(vform)){
		if (confirm('您确定提交吗？')){
	   		vform.submit();
		}
	}
	 
}
//查询用
function submitDataForm(vform){

	if (doValidate(vform)){
	   		vform.submit();
	}
	 
}
	  