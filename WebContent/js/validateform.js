/*************************************************
	validateform v1.00 by 开发一部(dev1 one)
*************************************************/
var validrule                  = new Object();

validrule.require 			   = /.+/;
//字母类型
validrule.LetterStr            = /^([a-zA-Z]+)?$/; 
//必填且只能是字母
validrule.english 			   = /^[A-Za-z]+$/;    
validrule.NumAndStr            = /^([0-9a-zA-Z]+)?$/;
validrule.NumAndStr2           = /^([0-9a-zA-Z]+)?$/;
validrule.NumStr               = /^(\d*)?$/;  

validrule.string               = /.*/; // /^([^'<>"&]+)?$/;
validrule.qstring              = /^([^'<>"&%_]+)?$/;//用于查询add by ellis gao
validrule.notsstring           = /^([^'<>\s]+)?$/;
validrule.chinese			   = /^([\u0391-\uFFE5]+)?$/;
validrule.unsafe 			   = /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/;
validrule.issafe 			   = function(str){return !validrule.unsafe.test(str);};
validrule.safestr		 	   = "validrule.issafe(_elem.value)";

validrule.currency 			   = /^\d+(\.\d+)?$/;
validrule.double 			   = /^[-\+]?\d+(\.\d+)?$/;
validrule.int                  = /^(\d{1,9})?$/; 
validrule.minusint             = /^(\-([1-9])(\d*))?$/;
validrule.float                = /^((\.([0-9]\d*))|(([0-9]\d*)\.\d+$)|([0-9]\d*))?$/;
validrule.integer              = /^(\d{1,9})?$/; 
validrule.number               = /^(\d*)?$/;                      

validrule.date                 = /^((([1-9]\d{3})|([1-9]\d{1}))-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]))?$/;  
validrule.time                 = /^((0[1-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9]))?$/; 
validrule.datetime             = /^((([1-9]\d{3})|([1-9]\d{1}))-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]) (0[1-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9]))?$/;   
validrule.year                 = /^(\d{4})?$/; 
validrule.month                = /^([1-9]|0[1-9]|1[0-2])?$/;
validrule.day                  = /^([1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1])?$/;
validrule.datefmt			   = "IsDate(_elem.value, getAttribute('min'), getAttribute('format'))";

validrule.postcode             = /^(\d{6})?$/;
validrule.zip 				   = /^[1-9]\d{5}$/;           

validrule.email                = /^(.+\@.+\..+)?$/;   
validrule.phone                = /^(\(\d{3}\))?(\(?(\d{3}|\d{4}|\d{5})\)?(-?)(\d+))?((-?)(\d+))?$/; 
validrule.mobiletel            = /^(013(\d{9})|13(\d{9})|15(\d{9})|015(\d{9})|18(\d{9})|018(\d{9}))?$/; 

validrule.ip                   = /^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5]))?$/;  
validrule.idcard               = /^(\d{15}|\d{18}|\d{17}X|\d{17}x)?$/; 
validrule.idcard2              = "IsIdCard(_elem.value)";

validrule.file           	   	             = /^([^'<>]+\:+)|(\\+)([^'<>]+)?$/;
validrule.url 								 = /^(http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*)?$/;

validrule.repeat 							 = "_elem.value == document.getElementsByName(getAttribute('to'))[0].value";
validrule.range								 =	"getAttribute('min') <= (_elem.value|0) && (_elem.value|0) <= getAttribute('max')";
validrule.compare 						     = "compare(_elem.value,getAttribute('operator'),getAttribute('to'))";
validrule.group 							 = "MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))";


validrule.filter 							 = "DoFilter(_elem.value, getAttribute('accept'))";
validrule.limit 							 = "limit(_elem.value.length,getAttribute('min'),  getAttribute('max'))";
validrule.limitb 							 = "limit(LenB(_elem.value), getAttribute('min'), getAttribute('max'))";

validrule.custom 							 = "Exec(_elem.value, getAttribute('regexp'))";

function doValidate( vform ) 
{
	var elems = vform.elements;
	var frmLen = elems.length;
	var thePat = "";
	var strFormatInfo = "";
	//对于每一个FROM元素
	for(var i=0;i<frmLen;i++) 
	{
		with(elems[i]){
		var _elem = elems[i];
		 var nvl1 = getAttribute("notNull");
		 var nvl2 = getAttribute("notnull");
		
		var _vdisp = getAttribute("vdisp");
		//为空检查                
		if((nvl1 != null &&nvl1 == "true") || (nvl2 != null && nvl2== "true")) 
		{
			if(trim(_elem.value).length == 0) 
			{
				alert(_vdisp + "不能为空!")
				_elem.focus();
				return false;
			}
		}		 
		if(_elem.type == "textarea"){
			if(trim(_elem.value).length > _elem.maxlength){
				alert(_elem.vdisp+"超过最大长度!最大长度为"+_elem.maxlength+"!");
				_elem.value=_elem.value.substring(0,_elem.maxlength);
				_elem.focus();
				return false;
			}
		}		
		var _vtype = getAttribute("vtype");
                //类型检查                  
		if( _vtype == null) 
		{
			continue;
		}
	      	if( _vtype=="none")
	      	{         
	      	   thePat = "";
	      	   strFormatInfo = "";
	      	}
	      	if( _vtype=="LetterStr")
	      	{       
	      	   thePat = validrule.LetterStr;
	      	   strFormatInfo = "纯字母字符串";
	      	}
	      	if( _vtype=="NumAndStr")
	      	{       
	      	   thePat = validrule.NumAndStr;
	      	   strFormatInfo = "数字和字母字符串";
	      	}
	      	if( _vtype=="NumAndStr2")
	      	{       
	      	   thePat = validrule.NumAndStr;
	      	   strFormatInfo = "数字和字母或_ 组成的字符串字符串";
	      	}
	      	if( _vtype=="NumStr")
	      	{       
	      	   thePat = validrule.NumStr;
	      	   strFormatInfo = "纯数字组成的字符串";
	      	}
	      	if( _vtype=="string")
	      	{       
	      	   thePat = validrule.string;
	      	   strFormatInfo = "不含特殊符号的字符串";
	      	}
	      	if( _vtype=="int")         
	      	{       
	      	   thePat = validrule.int;
	      	   strFormatInfo = "整数";
	      	}
	      	if( _vtype=="minusint")         
	      	{       
	      	   thePat = validrule.minusint;
	      	   strFormatInfo = "负整数，比如-123";
	      	}
	      	if( _vtype=="float")         
	      	{       
	      	   thePat = validrule.float;
	      	   strFormatInfo = "实数，比如356.32";
	      	}
	      	if( _vtype=="date")         
                {       
	      	   thePat = validrule.date;
	      	   strFormatInfo = "日期型，比如 2004-08-12";
	      	}
	      	if( _vtype=="time")         
	      	{       
	      	   thePat = validrule.time;
	      	   strFormatInfo = "时间型，比如08:37:29";
	      	}	      	 
	      	if( _vtype=="datetime")         
	      	{       
	      	   thePat = validrule.datetime;
	      	   strFormatInfo = "日期时间型，比如2004-08-12 08:37:29";
	      	}
                if( _vtype=="year")         
	      	{       
	      	   thePat = validrule.year;
	      	   strFormatInfo = "年代格式，比如 2005";
	      	}
	      	if( _vtype=="month")         
	      	{       
	      	   thePat = validrule.month;
	      	   strFormatInfo = "月份格式，比如 08";
	      	}
                if( _vtype=="day")         
	      	{       
	      	   thePat = validrule.day;
	      	   strFormatInfo = "日子格式，比如 14";
	      	} 
	      	if( _vtype=="postcode")         
	      	{       
	      	   thePat = validrule.postcode;
	      	   strFormatInfo = "邮编，比如 100001";
	      	}	
	      	if( _vtype=="zip")         
	      	{       
	      	   thePat = validrule.zip;
	      	   strFormatInfo = "邮编，比如 100001";
	      	}	         	
	      	if( _vtype=="email")         
	      	{       
	      	   thePat = validrule.email;
	      	   strFormatInfo = "电子邮件格式，比如 msm@hotmail.com";
	      	}
	      	if( _vtype=="phone")         
	      	{       
	      	   thePat = validrule.phone;
	      	   strFormatInfo = "电话号码格式，比如010-67891234";
	      	}
	      	if( _vtype=="mobiletel")         
	      	{       
	      	   thePat = validrule.mobiletel;
	      	   strFormatInfo = "手机号码格式，比如13867891234";
	      	}	      	
	      	if( _vtype=="ip")       
	      	{       
	      	   thePat = validrule.ip;
	      	   strFormatInfo = "机器ip地址格式，比如 172.22.169.11";
	      	}	      	
	      	if( _vtype=="idcard")       
	      	{       
	      	   thePat = validrule.idcard;
	      	   strFormatInfo = "身份证号码，比如15位或者18位数字";
	      	}
	      	if( _vtype == "notsstring")
	      	{
	      	   thePat = validrule.notsstring;
	      	   strFormatInfo = "不包含空格和特殊字符的字符串";
	      	}
	      	
	      	if( _vtype=="integer")         
	      	{       
	      	   thePat = validrule.integer;
	      	   strFormatInfo = "整数";
	      	}
	      	if( _vtype == "file")
	      	{
	      	   thePat = validrule.file;
	      	   strFormatInfo = "文件格式";
	      	}
	      	if( _vtype=="number")         
	      	{       
	      	   thePat = validrule.number;
	      	   strFormatInfo = "数字字符";
	      	}
	      	if( _vtype=="qstring")         
	      	{       
	      	   thePat = validrule.qstring;
	      	   strFormatInfo = "不含特殊符号（如%，&，'）的字符串";
	      	}	 
	      	if( _vtype=="chinese")         
	      	{       
	      	   thePat = validrule.chinese;
	      	   strFormatInfo = "中文";
	      	}	 
	      	if( _vtype=="url")         
	      	{       
	      	   thePat = validrule.url;
	      	   strFormatInfo = "网站格式，如：http://www.xx.com";
	      	}	
	      	if( _vtype=="unsafe")         
	      	{       
	      	   thePat = validrule.unsafe;
	      	   strFormatInfo = "";
	      	}	
	      	
	      	if( _vtype=="require")         
	      	{       
	      	   thePat = validrule.require;
	      	   strFormatInfo = "不能为空";
	      	}	
	      	if( _vtype=="currency")         
	      	{       
	      	   thePat = validrule.currency;
	      	   strFormatInfo = "货币";
	      	}	
	      	if( _vtype=="double")         
	      	{       
	      	   thePat = validrule.double;
	      	   strFormatInfo = "双精度";
	      	}	
	      	if( _vtype=="english")         
	      	{       
	      	   thePat = validrule.english;
	      	   strFormatInfo = "英文字母";
	      	}	
	      	
	      var gotIt = null; 
	   
	      if(_vtype =="repeat"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "两次输入一致";
	     		if(!eval(validrule.repeat)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      
	      if(_vtype =="idcard2"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "两次输入一致";
	     		if(!eval(validrule.idcard2)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      
	      if(_vtype =="range"){
	    	  
    		var rule = validrule.int.exec(_elem.value);
    		if(rule == null){
    	  		alert(_elem.vdisp+"输入不合法,格式应为整数!");
    	  		_elem.focus();
    			return false;
    		}
	    		
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "在"+getAttribute('min')+"和"+(getAttribute('max')+"之间");
	     		if(!eval(validrule.range)){	 		
	     		 		gotIt = null;
	     		}
	      }
	     
	     if(_vtype =="compare"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "";
	     		if(!eval(validrule.compare)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      
	      if(_vtype =="group"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "";
	     		if(!eval(validrule.group)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      if(_vtype =="safestr"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "";
	     		if(!eval(validrule.safestr)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      
	      if(_vtype =="filter"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "";
	     		if(!eval(validrule.filter)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      
	       if(_vtype =="limit"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "";
	     		if(!eval(validrule.limit)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      
	       if(_vtype =="limitb"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "";
	     		if(!eval(validrule.limitb)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      
	       if(_vtype =="datefmt"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "";
	     		if(!eval(validrule.datefmt)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      if(_vtype =="custom"){
	      	thePat = "";   
	      	gotIt = "n";   
	      	strFormatInfo = "";
	     		if(!eval(validrule.custom)){	 		
	     		 		gotIt = null;
	     		}
	      }
	      
	     	 var _vmsg = getAttribute("vmsg"); 
	      	 	
	      
	      		      
	      	if(thePat!="")
	      	{
	      	    gotIt = thePat.exec(_elem.value);
	      	}
	           	 
	      	if(gotIt == null) 
	      	{
	      		if(_vmsg !=null&&_vmsg!=""){
	      			alert(_vmsg);
	      		}else{
	      			alert(_vdisp+"输入不合法，格式应为："+strFormatInfo);
	      		}
	      		_elem.focus();
	      		return false;
	      	}
	     }
	}  
	return true;
} 

limit = function(len,min, max){
		min = min || 0;
		max = max || Number.MAX_VALUE;
		return min <= len && len <= max;
}
LenB = function(str){
		return str.replace(/[^\x00-\xff]/g,"**").length;
}

Exec = function(op, reg){
		return new RegExp(reg,"g").test(op);
}
 
var compare = function(op1,operator,op2){
	  op1 = parseFloat(op1);
	  op2 = parseFloat(op2);
		switch (operator) {
			case "NotEqual":
				return (op1 != op2);
			case "GreaterThan":
				return (op1 > op2);
			case "GreaterThanEqual":
				return (op1 >= op2);
			case "LessThan":
				return (op1 < op2);
			case "LessThanEqual":
				return (op1 <= op2);
			case "Equal":
				return (op1 == op2);
			case "ne":
				return (op1 != op2);
			case "gt":
				return (op1 > op2);
			case "ge":
				return (op1 >= op2);
			case "lt":
				return (op1 < op2);
			case "le":
				return (op1 <= op2);
			case "eq":
				return (op1 == op2);
			default:
				return (op1 == op2);            
		}
		
	}
MustChecked = function(name, min, max){
		var groups = document.getElementsByName(name);
		var hasChecked = 0;
		min = min || 1;
		max = max || groups.length;
		for(var i=groups.length-1;i>=0;i--)
			if(groups[i].checked) hasChecked++;
		return min <= hasChecked && hasChecked <= max;
}
DoFilter = function(input, filter){
	return new RegExp("^.+\.(?=EXT)(EXT)$".replace(/EXT/g, filter.split(/\s*,\s*/).join("|")), "gi").test(input);
}
IsIdCard = function(number){
		var date, Ai;
		var verify = "10x98765432";
		var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
		var area = ['','','','','','','','','','','','北京','天津','河北','山西','内蒙古','','','','','','辽宁','吉林','黑龙江','','','','','','','','上海','江苏','浙江','安微','福建','江西','山东','','','','河南','湖北','湖南','广东','广西','海南','','','','重庆','四川','贵州','云南','西藏','','','','','','','陕西','甘肃','青海','宁夏','新疆','','','','','','台湾','','','','','','','','','','香港','澳门','','','','','','','','','国外'];
		var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/i);
		if(re == null) return false;
		if(re[1] >= area.length || area[re[1]] == "") return false;
		if(re[2].length == 12){
			Ai = number.substr(0, 17);
			date = [re[9], re[10], re[11]].join("-");
		}
		else{
			Ai = number.substr(0, 6) + "19" + number.substr(6);
			date = ["19" + re[4], re[5], re[6]].join("-");
		}
		if(!this.IsDate(date, "ymd")) return false;
		var sum = 0;
		for(var i = 0;i<=16;i++){
			sum += Ai.charAt(i) * Wi[i];
		}
		Ai +=  verify.charAt(sum%11);
		return (number.length ==15 || number.length == 18 && number == Ai);
	}
IsDate = function(op, formatString){
		formatString = formatString || "ymd";
		var m, year, month, day;
		switch(formatString){
			case "ymd" :
				m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
				if(m == null ) return false;
				day = m[6];
				month = m[5]*1;
				year =  (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
				break;
			case "dmy" :
				m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
				if(m == null ) return false;
				day = m[1];
				month = m[3]*1;
				year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
				break;
			default :
				break;
		}
		if(!parseInt(month)) return false;
		month = month==0 ?12:month;
		var date = new Date(year, month-1, day);
        return (typeof(date) == "object" && year == date.getFullYear() && month == (date.getMonth()+1) && day == date.getDate());
		function GetFullYear(y){return ((y<30 ? "20" : "19") + y)|0;}
}

var textCounter=function(obj, maxlimit) { 
	if (obj.value.length > maxlimit){ 
		obj.value = obj.value.substring(0, maxlimit); 
	}
} 
var setAreaLength = function (obj,len) { 
	try{
		if(!-[1,]){ //if("\v"=="v")  如果是IE
			obj.onpropertychange = textCounter(obj,len); 
		}else{ 
			obj.addEventListener("input",textCounter(obj,len),false); 
		} 
	}catch(e){}
		
} 
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