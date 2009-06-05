package com.sw.cms.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 上传文件
 * @author liyt
 *
 */
public class UploadFile extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = -1091281239321309522L;

	private Log log = LogFactory.getLog(getClass());
	
	public UploadFile() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}  	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sSaveFileName = "";
		String sOriginalFileName = "";
		String sFiletype = "";
		String sPathFileName = "";
		
	    // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
	    DiskFileItemFactory dfif = new DiskFileItemFactory();
	    dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘
	    dfif.setRepository(new File(getServletContext().getRealPath("/") + "/xxfb/UploadFile"));// 设置存放临时文件的目录

	    log.info(getServletContext().getRealPath("/") + "/xxfb/UploadFile");
	    
	    // 用以上工厂实例化上传组件
	    ServletFileUpload sfu = new ServletFileUpload(dfif);
	    // 设置最大上传尺寸
	    sfu.setSizeMax(100*1024*1024);


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
			String u_name = getServletContext().getRealPath("/") + "/xxfb/UploadFile/" + prefix + "." + t_ext;
			
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
		log.info(temp1);

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
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
}