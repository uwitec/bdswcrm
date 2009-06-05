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
 * �ϴ��ļ�
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
		
	    // ʵ����һ��Ӳ���ļ�����,���������ϴ����ServletFileUpload
	    DiskFileItemFactory dfif = new DiskFileItemFactory();
	    dfif.setSizeThreshold(4096);// �����ϴ��ļ�ʱ������ʱ����ļ����ڴ��С,������4K.���ڵĲ��ֽ���ʱ����Ӳ��
	    dfif.setRepository(new File(getServletContext().getRealPath("/") + "/xxfb/UploadFile"));// ���ô����ʱ�ļ���Ŀ¼

	    log.info(getServletContext().getRealPath("/") + "/xxfb/UploadFile");
	    
	    // �����Ϲ���ʵ�����ϴ����
	    ServletFileUpload sfu = new ServletFileUpload(dfif);
	    // ��������ϴ��ߴ�
	    sfu.setSizeMax(100*1024*1024);


	    // ��request�õ� ���� �ϴ�����б�
	    List fileList = null;
	    try {
			fileList = sfu.parseRequest(request);
	    } catch (FileUploadException e) {// �����ļ��ߴ�����쳣
			e.printStackTrace();
		}
	    

	    // �õ������ϴ����ļ�
	    Iterator fileItr = fileList.iterator();
	    // ѭ�����������ļ�
		while (fileItr.hasNext()) {
			FileItem fileItem = null;
			String path = null;
			
			// �õ���ǰ�ļ�
			fileItem = (FileItem) fileItr.next();
			
			// ���Լ�form�ֶζ������ϴ�����ļ���(<input type="text" />��)
			if (fileItem == null || fileItem.isFormField()) {
				continue;
			}
			
			// �õ��ļ�������·��
			path = fileItem.getName();

			// �õ�ȥ��·�����ļ���
			String t_name = path.substring(path.lastIndexOf("\\") + 1);
			// �õ��ļ�����չ��(����չ��ʱ���õ�ȫ��)
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);

			long now = System.currentTimeMillis();
			// ����ϵͳʱ�������ϴ��󱣴���ļ���
			String prefix = String.valueOf(now);
			
			// ����������ļ�����·��
			String u_name = getServletContext().getRealPath("/") + "/xxfb/UploadFile/" + prefix + "." + t_ext;
			
			sOriginalFileName= t_name;
			sFiletype = fileItem.getContentType().trim();
			sSaveFileName=prefix+"." + t_ext;
	     
			try {
				// �����ļ�
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