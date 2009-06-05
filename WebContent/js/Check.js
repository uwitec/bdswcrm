function validpassword( pass1, pass2 )
{
	var allValid = true;
	
		
	if( pass1=="" )
	{
		alert("请输入密码！");
		return false;
	}

	if( pass2=="" )
	{
		alert("请确认密码！");
		return false;
	}
	
	if( pass1.length < 6 )
	{
		alert("密码长度至少6个字符！");
		return false;
	}

	if( pass1.length != pass2.length )
	{
		alert("两次输入的密码长度不一致！");
		return false;
	}
	for(i=0;i<pass1.length;i++)
	{
		if( pass1.charAt(i) != pass2.charAt(i) )
		{
			alert("两次输入的密码不一致!");
			allValid = false;
			break;
		}
	}
	return allValid;		
}

function InputValid(d_input,d_notnull, d_type,d_limited, d_low, d_up,d_str)
{
  if ( d_input.length >1 )
  {
    var obj=d_input;
    var m;
    m=d_input.length;
	 m=m.toString();
	 for( var i=0; i<m ; i++ )
	 {
		if( !InputValid_A( obj[i],d_notnull, d_type,d_limited, d_low, d_up,d_str ) ) {
		   return (false);
		}
	 }
  }
  else
  {
      if ( !InputValid_A( d_input,d_notnull, d_type,d_limited, d_low, d_up,d_str ) )
   	   return false;
  }
  return true;
}


function InputValid_A( d_input,d_notnull, d_type,d_limited, d_low, d_up,d_str )
{
//不能以空格开头
   if ( d_input.value.charAt(0) == ' ' ) 
    {
       alert(d_str+" 输入框不能以空格开头" );
       //d_input.focus();
       return (false);
    }
// not null
   if ( d_notnull==1 && d_input.value.length ==0 ) 
    {
       alert(" 必须输入" + d_str );
       //d_input.focus();
       return (false);
    }
    
// "int"
	if (d_type=="int")
	{
		if ( !isInt(d_input.value))
	    {
			alert( d_str+ " 只能是数字");
			//d_input.focus();
			return (false);
	    }
		if  ( d_limited==1 && !(d_low<=d_input.value && d_input.value <= d_up))
		{
			alert(d_str+ "的值必须在"+ d_low + " 到 "+ d_up +"之间.");
			//d_input.focus();
			return (false);
		}
		return true;
	} 

// "float"
	if (d_type=="float")
	{
		if ( !isFloat(d_input.value))
	    {
			alert( d_str+" 只能输入数字及小数点" );
			//d_input.focus();
			return (false);
	    }
	    if  ( d_limited==1 && !( d_low <=d_input.value && d_input.value <= d_up))
		{
			alert(d_str+ "的值必须在"+ d_low + " 到 "+ d_up +"之间");
			//d_input.focus();
			return (false);
		}
		return true;
	}

// "string"
	if (d_type=="string")
	{
		if  (d_limited==1 && !(d_low<=d_input.value.length && d_input.value.length <= d_up))
		{
			alert(d_str+ " 的长度必须在 "+ d_low + " 和"+ d_up +" 之间。");
			//d_input.focus();
			return (false);
		}
		return (true);
	}    

// "date"
	if (d_type=="date")
	{
	    if ( (!isDate(d_input.value)) || (d_input.value.length != 10) )
	    {
			alert("请在"+d_str+"处输入如下的日期形式：2000-08-08");
			//d_input.focus();
			return (false);
	    }	
	    return (true);
	}
// "time"
	if (d_type=="time")
	{
	    if ( (!isTime(d_input.value)) || (d_input.value.length != 5) )
	    {
			alert("请在"+d_str+"处输入24小时制时间格式如下:  18:00");
			//d_input.focus();
			return (false);
	    }	
	    return (true);
	}

// "email"
	if (d_type=="email")
	{
	   if (d_notnull==0 && d_input.value.length==0) return (true);
       if ( !isEmail(d_input.value))
	    {
			alert("请在 "+d_str+"处输入正确的Email地址。");
			//d_input.focus();
			return (false);
	    }	
		return (true);
	}

// "fax"
	if (d_type=="fax")
	{
		//is int
	    if ( !isFax(d_input.value))
	    {
			alert(d_str+" 只能输入数字和'- '");
			//d_input.focus();
			return (false);
	    }
		//limit
		if  ( d_limited==1 && !(d_low<=d_input.value.length && d_input.value.length <= d_up))
		{
			alert(d_str+ "的长度只能在 "+ d_low + " 和 "+ d_up +" 之间.");
			//d_input.focus();
			return (false);
		}
		return true;  
	}

     // auto
	if (d_type=="auto")
	{
		//limit
		if  ( d_input.value==0 )
		{
			alert( "请输入 " + d_str );
			return (false);
		}
		return true;  
	} 
	
// "zip"
	if (d_type=="zip")
	{
	    if ( !isInt(d_input.value) )
	    {
			alert(d_str+" 只能是数字");
			//d_input.focus();
			return (false);
	    }
		if  ( d_limited==1 ){
			if ( (d_low == d_up)&& (d_input.value.length != d_low) ) {
				alert( d_str+ "的长度只能是 "+ d_low +" 位." );
         		//d_input.focus();
				return (false);
			}
			else {
				if ( (d_low < d_input.value.length && d_input.value.length < d_up))
				{
					alert(d_str+ "的长度只能在 "+ d_up +" 位以内.");
         			//d_input.focus();
					return (false);
				}
			}
		}
		return true;  
	}

	return (true);
}


function isInt( d_int)
{
		var checkOK = "0123456789-,";
		var checkStr = d_int;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		if (ch != ",")
			allNum += ch;
		}
		return (allValid)
 }

function isFloat( d_float)
{
		var checkOK = "0123456789-,.";
		var checkStr = d_float;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
			if ( (ch == '-') && (i!=0) )			
			{
				allValid = false;
				break;
			}			
			if (ch != ",")
				allNum += ch;				
			if (ch == ".")
				decPoints += 1;				
		}				
		if ( decPoints > 1 )
		{
			allValid = false;
		}
		return (allValid)
}

function isDate( d_date)
{		
		var checkStr = d_date;

		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			if ((i==4) || (i==7)) 
			{
				if ( ch!='-' )
				{
					return (false);
				}
			}
			else
			{
				if (ch<'0' || ch > '9')
				{
					return (false);
				}
				if ( (i==5 && ch>'1')||(i==8 && ch>'3') ) {
   				return (false);
				}
			}									
		}				
		return (true);
}
function isTime( d_time)
{		
		var checkStr = d_time;
		var hour1='0';
		var hour2='0';
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			if (i==2) 
			{
				if ( ch!=':' )
				{
					return (false);
				}
			}
			else
			{
				if (ch<'0' || ch > '9')
				{
					return (false);
				}
				if ( (i==0 && ch>'2')||(i==3 && ch>'5') ) 
				{
   					return (false);
				}
				if(i==0)
				{
					hour1=ch;
				}
				if(i==1)
				{
					hour2=ch;				
				}
				if((hour1=='2')&&(hour2>'3'))
				{
					return (false);
				}
			}		
							
		}				
		return (true);
}
function isEmail( d_email)
{		
		var checkStr = d_email;
		var emailtag = false;
		var emaildot=0
		var emailat=0
		
		if (checkStr.length<7) return (false);
		
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			
			if (ch=='@') emailat++;	
			if (ch=='.') emaildot++;	
		}				
		
		if (( emailat==1 ) && ( emaildot>=1 )) 
		{
		emailtag = true;
		}
		return (emailtag);  	
}

function isFax( d_int)
{
		var checkOK = "0123456789 -()";
		var checkStr = d_int;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";

		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
			if (ch != ",")
			allNum += ch;
		}
		return (allValid)
}
