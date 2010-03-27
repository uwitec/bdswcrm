package com.sw.cms.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 下载文件
 * @author liyt
 *
 */
 public class DownLoadFileServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 8709305597536738258L;
	
	protected transient final Log log = LogFactory.getLog(getClass());
	 
	public DownLoadFileServlet() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	
		try {
			String file = request.getParameter("file");
        	
        	if(file == null || file.equals("")){
        		return;
        	}        	 
        	
        	String rootPath = getServletContext().getRealPath("/");
        	String filePath = rootPath + file;
        	
        	String fileName = file.substring(file.lastIndexOf("/")+1,file.length());
    		
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-disposition","attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            
            OutputStream os = response.getOutputStream();
            
			FileInputStream in = new FileInputStream(filePath);
            byte b[] = new byte[1024];
            while(in.read(b) != -1)
                os.write(b);            
            in.close();            
            os.flush();
            os.close();          
        }catch (Exception ex) {
        	log.info("下载文件失败：" + ex);
        }
        return;
        
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}   	  	    
}