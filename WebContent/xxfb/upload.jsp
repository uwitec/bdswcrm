<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.regex.*"%>
<%@ page import="net.fiyu.edit.RemotePic"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="org.apache.commons.fileupload.FileUploadException"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>

<jsp:useBean id="date" scope="page" class="net.fiyu.edit.TimeStamp"/>
<%@ page import="net.fiyu.edit.UploadWebHelper,net.fiyu.edit.UploadBean"%>

<%!
// 参数变量
String sType, sStyleName;
//' 设置变量
String sAllowExt, sUploadDir,sBaseUrl,sContentPath;
int  nAllowSize,fileSize;
//' 接口变量
String sFileExt,sSaveFileName,sOriginalFileName,sPathFileName,FileName, nFileNum,sFiletype;
String sAction;
Connection connect = null;
%>
<%!
/*' ============================================
' 去除Html格式，用于从数据库中取出值填入输入框时
' 注意：value="?"这边一定要用双引号
' ============================================*/
public String inHTML(String str)
{
	String sTemp;
	sTemp = str;
	if(sTemp.equals(""))
	{
		System.exit(0);
	}
	sTemp = sTemp.replaceAll("&", "&amp;");
	sTemp = sTemp.replaceAll("<", "&lt;");
	sTemp = sTemp.replaceAll(">", "&gt;");
	sTemp = sTemp.replaceAll("\"", "&quot;");
	return sTemp;
}
//初始化上传限制数据
public void InitUpload(String realpath){
	UploadWebHelper uw = new UploadWebHelper();
	uw.filename = realpath+"/WEB-INF/style.xml";
	uw.getInstance();
	UploadBean bean = uw.InitPara();
	try{

		sUploadDir = bean.getSuploaddir();
		if(sType.equalsIgnoreCase("remote"))
		{
			sAllowExt =  bean.getSremoteext();
                        sAllowExt = sAllowExt + "|" + sAllowExt.toUpperCase();
			nAllowSize =  Integer.parseInt(bean.getSremotesize()) ;

		}
                else if(sType.equalsIgnoreCase("file"))
               {
			sAllowExt = bean.getSfileext();
                        sAllowExt = sAllowExt + "|" + sAllowExt.toUpperCase();
			nAllowSize = Integer.parseInt(bean.getSfilesize());

               }
               else if(sType.equalsIgnoreCase("media"))
		{
			sAllowExt =  bean.getSmediaext();
                        sAllowExt = sAllowExt + "|" + sAllowExt.toUpperCase();
			nAllowSize = Integer.parseInt(bean.getSmediasize());

		}
                else if(sType.equalsIgnoreCase("flash"))
                {
			sAllowExt =  bean.getSflashext();
                        sAllowExt = sAllowExt + "|" + sAllowExt.toUpperCase();
			nAllowSize = Integer.parseInt(bean.getSflashsize());

                }
		else
                {
			sAllowExt =  bean.getSimageext();
                        sAllowExt = sAllowExt + "|" + sAllowExt.toUpperCase();
			nAllowSize = Integer.parseInt(bean.getSimagesize());
               
                }
	}
	catch(Exception e){
        }
}
%>
<%
//设置类型
sType=request.getParameter("type");
if(sType==null)
{
sType="image";
}
else
sType=request.getParameter("type").trim();
//设置样式
sStyleName=request.getParameter("style");
if (sStyleName==null)
{
  sStyleName="standard";
}
else
sStyleName=request.getParameter("style").trim();
//设置动作
sAction=request.getParameter("action");
if(sAction==null)
{
  sAction="sun";
}
else
sAction=request.getParameter("action").trim();
%>
<%
try{

//初始化上传变量
InitUpload(config.getServletContext().getRealPath("/"));
//断开数据库连接
//sAction = UCase(Trim(Request.QueryString("action"))
if(sAction.equalsIgnoreCase("remote"))
{    //远程自动获取
	String sContent;
		String RemoteFileurl=null;
	String Protocol,sUrl;
	int Port;
	String LocalFileurl=null;
	String SaveFileName=null;
	sContent=request.getParameter("eWebEditor_UploadText");
    if(sContent==null)
	{
		sContent="sunshanfeng";
	}
	else
	{	
	
	 sContent=new String(request.getParameter("eWebEditor_UploadText").getBytes("UTF-8"));
 
  if(null==sContent||sContent.equals("")){
		//out.println("<script language=javascript> alert('无编辑内容，不得提交！'); </script>");
		return;
		}
		
  }
	if(sAllowExt!="")
	{
	//sAllowExt=gif|jpg|jpeg|bmp|GIF|JPG|JPEG|BMP
	Pattern pRemoteFileurl = Pattern.compile("((http|https|ftp|rtsp|mms):(//|\\\\){1}(([A-Za-z0-9_-])+[.]){1,}(net|com|cn|org|cc|tv|[0-9]{1,3})(\\S*/)((\\S)+[.]{1}("+sAllowExt+")))");//取得网页上URL的正则表达式
    Matcher mRemoteFileurl = pRemoteFileurl.matcher(sContent);//对传入的字符串进行匹配
	Protocol=request.getProtocol();//取得通讯的协议
	String ProtocolA[]=Protocol.split("/");//取得协议前面的字母，如HTTP/1.1,变为"HTTP","1.1"
	sUrl = ProtocolA[0]+"://"+request.getServerName();//取得本地URL路径,如http://localhost
	//ProtocolA[]=null;
	Port=request.getServerPort();//取得端口值

	if(Port!=80)
	{//查看端口是否为80，如果不是还需要在联接上加上端口
     sUrl=sUrl+":"+Port;
	}

	String context=request.getContextPath();
	sUrl=sUrl+context+"/"+sUploadDir;
	StringBuffer sb=new StringBuffer();
	boolean result=mRemoteFileurl.find();

	int i=0;

       while(result)
			{

             i++;
             RemoteFileurl=mRemoteFileurl.group(0);
			 sOriginalFileName=RemoteFileurl.substring(RemoteFileurl.lastIndexOf("/"));
			 Pattern pFileType=Pattern.compile("[.]{1}("+sAllowExt+")");//二次匹配取得文件的类型
			 Matcher mFileType=pFileType.matcher(RemoteFileurl);
			 while(mFileType.find())
				{
				 String SaveFileType=mFileType.group();
				 LocalFileurl=sUploadDir+(String)date.Time_Stamp()+i+SaveFileType;//文件的路径，以时间戳命名  LocalFileurl=UploadFile/20060308102208718+1+.gif
				}

			   String LoadFile=sUploadDir.substring(0,sUploadDir.length()-1);	// =UploadFile
			   SaveFileName = request.getRealPath("/") + "\\xxfb\\" +LoadFile + "\\" +LocalFileurl.substring(LocalFileurl.lastIndexOf("/") +1);
			   				
                sSaveFileName=LocalFileurl.substring(LocalFileurl.lastIndexOf("/"));  // ="/200603081022087181.gif"
                RemotePic Down=new RemotePic();
				Down.picurl=RemoteFileurl;     //RemoteFileurl=http://news.163.com/img/2320.gif
				Down.savepath=SaveFileName;    //SaveFileName=e:/tomcat5.0/webapps/edit/uploadfile/200..gif

             if (Down.download())//如果上载保存成功，则更换html标记里的文件路径
				{
				 mRemoteFileurl.appendReplacement(sb,LocalFileurl);//替换路径
				}
             result=mRemoteFileurl.find();
			}
			mRemoteFileurl.appendTail(sb);
		sContent=sb.toString();
	}
	sContent=inHTML(sContent);

	out.println("<HTML><HEAD><TITLE>远程上传</TITLE><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head><body>");
	out.print("<input type=hidden id=UploadText value=\"");
	out.print(sContent);
	out.println("\">");
	out.println("</body></html>");
	out.println("<script language=javascript>");
	out.print("parent.setHTML(UploadText.value);try{parent.addUploadFile('");//为什么只取一半的值？且只取复制网页插入位置之前的值？
	out.print(sOriginalFileName);
	out.print("', '");
	out.print(sSaveFileName);
	out.print("', '");
	out.print(SaveFileName);
	out.println("');} catch(e){} parent.remoteUploadOK();");
	out.println("</script>");

  //DoRemote();
}
else if(sAction.equalsIgnoreCase("save"))
{
  //显示上传菜单
 out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<TITLE>文件上传</TITLE>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.println("<style type=\"text/css\">");
        out.println("body, a, table, div, span, td, th, input, select{font:9pt;font-family: \"宋体\", Verdana, Arial, Helvetica, sans-serif;}");
        out.println("body {padding:0px;margin:0px}");
        out.println("</style>");
        out.println("<script language=\"JavaScript\" src=\"dialog/dialog.js\">");
        out.println("</script>");
        out.println("</head>");
        out.println("<body bgcolor=menu>");
        out.print("<form action=\"?action=save&type=");//注意此处为什么不用println()
        out.print(sType);
        out.print("&style=");
        out.print(sStyleName);
        out.println("\" method=post name=myform enctype=\"multipart/form-data\">");
        out.println("<input  type=file name=uploadfile size=1 style=\"width:100%\" onchange=\"originalfile.value=this.value\">");
        out.println("<input type=hidden name=originalfile value=\"\">");
        out.println("</form>");
        out.println("<script language=javascript>");
        out.print("var sAllowExt = \"");
        out.print(sAllowExt);
        out.println("\";");
        out.println("// 检测上传表单");
        out.println("function CheckUploadForm() {");
        out.println("if (!IsExt(document.myform.uploadfile.value,sAllowExt)){");
        out.println("parent.UploadError(\"提示：\\n\\n请选择一个有效的文件，\\n支持的格式有（\"+sAllowExt+\"）！\");");
        out.println("return false;");
        out.println("}");
        out.println("return true");
        out.println("}");
        out.println("// 提交事件加入检测表单");
        out.println("var oForm = document.myform;");
        out.println("oForm.attachEvent(\"onsubmit\", CheckUploadForm) ;");
        out.println("if (! oForm.submitUpload) oForm.submitUpload = new Array() ;");
        out.println("oForm.submitUpload[oForm.submitUpload.length] = CheckUploadForm ;");
        out.println("if (! oForm.originalSubmit) {");
        out.println("oForm.originalSubmit = oForm.submit ;");
        out.println("oForm.submit = function() {");
        out.println("if (this.submitUpload) {");
        out.println("for (var i = 0 ; i < this.submitUpload.length ; i++) {");
        out.println("this.submitUpload[i]() ;");
        out.println("			}");
        out.println("		}");
        out.println("		this.originalSubmit() ;");
        out.println("	}");
        out.println("}");
        out.println("// 上传表单已装入完成");
        out.println("try {");
        out.println("	parent.UploadLoaded();");
        out.println("}");
        out.println("catch(e){");
        out.println("}");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");

    // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
    DiskFileItemFactory dfif = new DiskFileItemFactory();
    dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘
    dfif.setRepository(new File(request.getRealPath("/") + "/xxfb/" + sUploadDir));// 设置存放临时文件的目录

    // 用以上工厂实例化上传组件
    ServletFileUpload sfu = new ServletFileUpload(dfif);
    // 设置最大上传尺寸
    sfu.setSizeMax(nAllowSize*1024*1024);


    // 从request得到 所有 上传域的列表
    List fileList = null;
    try {
		fileList = sfu.parseRequest(request);
    } catch (FileUploadException e) {// 处理文件尺寸过大异常
		e.printStackTrace();
	}
    

    // 得到所有上传的文件
    Iterator fileItr = fileList.iterator();
    // 循环处理所有文件
	while (fileItr.hasNext()) {
		FileItem fileItem = null;
		String path = null;
		long size = 0;
		// 得到当前文件
		fileItem = (FileItem) fileItr.next();
		
		// 忽略简单form字段而不是上传域的文件域(<input type="text" />等)
		if (fileItem == null || fileItem.isFormField()) {
			continue;
		}
		
		// 得到文件的完整路径
		path = fileItem.getName();

		// 得到去除路径的文件名
		String t_name = path.substring(path.lastIndexOf("\\") + 1);
		// 得到文件的扩展名(无扩展名时将得到全名)
		String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);

		long now = System.currentTimeMillis();
		// 根据系统时间生成上传后保存的文件名
		String prefix = String.valueOf(now);
		
		// 保存的最终文件完整路径
		String u_name = request.getRealPath("/") + "/xxfb/" + sUploadDir + prefix + "." + t_ext;
		
		sOriginalFileName= t_name;
		sFiletype = fileItem.getContentType().trim();
		sSaveFileName=prefix+"." + t_ext;
     
		try {
			// 保存文件
			fileItem.write(new File(u_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	String temp1 = sSaveFileName+";"+sOriginalFileName+";"+sFiletype.substring(0,sFiletype.indexOf("/"))+":"+sFiletype.substring(sFiletype.indexOf("/")+1);


	
    out.println("<script language=javascript>");
	out.print("parent.UploadSaved('");
	out.print(temp1);
	out.print("');  var obj=parent.dialogArguments.dialogArguments;if (!obj) obj=parent.dialogArguments;try{obj.addUploadFile('");
	out.print(sOriginalFileName);
	out.print("', '");
	out.print(sSaveFileName);
	out.print("', '");	
	out.print(sPathFileName);
	out.print("');} catch(e){}");
	out.println(";history.back()</script>");
}
else
{
  //显示上传表单
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<TITLE>文件上传</TITLE>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.println("<style type=\"text/css\">");
        out.println("body, a, table, div, span, td, th, input, select{font:9pt;font-family: \"宋体\", Verdana, Arial, Helvetica, sans-serif;}");
        out.println("body {padding:0px;margin:0px}");
        out.println("</style>");
        out.println("<script language=\"JavaScript\" src=\"dialog/dialog.js\">");
        out.println("</script>");
        out.println("</head>");
        out.println("<body bgcolor=menu>");
        out.print("<form action=\"" + request.getContextPath() + "/UploadFile\" method=post name=myform enctype=\"multipart/form-data\">");        
        System.out.print("<form action=\"" + request.getContextPath() + "/UploadFile\" method=post name=myform enctype=\"multipart/form-data\">");        
        out.println("<input type=file name=uploadfile size=1 style=\"width:100%\" onchange=\"originalfile.value=this.value\">");
        out.println("<input type=hidden name=originalfile value=\"\">");
        out.println("</form>");
        out.println("<script language=javascript>");
        out.print("var sAllowExt = \"");
        out.print(sAllowExt);
        out.println("\";");
        out.println("// 检测上传表单");
        out.println("function CheckUploadForm() {");
        out.println("	if (!IsExt(document.myform.uploadfile.value,sAllowExt)){");
        out.println("		parent.UploadError(\"提示：\\n\\n请选择一个有效的文件，\\n支持的格式有（\"+sAllowExt+\"）！\");");
        out.println("		return false;");
        out.println("	}");
        out.println("	return true");
        out.println("}");
        out.println("// 提交事件加入检测表单");
        out.println("var oForm = document.myform ;");
        out.println("oForm.attachEvent(\"onsubmit\", CheckUploadForm) ;");
        out.println("if (! oForm.submitUpload) oForm.submitUpload = new Array() ;");
        out.println("oForm.submitUpload[oForm.submitUpload.length] = CheckUploadForm ;");
        out.println("if (! oForm.originalSubmit) {");
        out.println("	oForm.originalSubmit = oForm.submit ;");
        out.println("	oForm.submit = function() {");
        out.println("		if (this.submitUpload) {");
        out.println("			for (var i = 0 ; i < this.submitUpload.length ; i++) {");
        out.println("				this.submitUpload[i]() ;");
        out.println("			}");
        out.println("		}");
        out.println("		this.originalSubmit() ;");
        out.println("	}");
        out.println("}");
        out.println("// 上传表单已装入完成");
        out.println("try {");
        out.println("	parent.UploadLoaded();");
        out.println("}");
        out.println("catch(e){");
        out.println("}");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
}
}catch(Exception e){
	System.out.println(e);
}
%>


