package com.sw.cms.xls.down;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.sw.cms.util.StaticParamDo;
import com.sw.cms.xls.template.ExportXlsTemplate;

/**
 * 下载导出的excel文件
 * @author liyt
 *
 */
 public class DownLoadXlsServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 8709305597536738258L;
	
	protected transient final Log log = LogFactory.getLog(getClass());
	 
	public DownLoadXlsServlet() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	
		try {
			String action = request.getParameter("action");
        	
        	if(action == null || action.equals("")){
        		return;
        	}        	 
    		ServletContext servletContext = getServletContext();
    		ApplicationContext ctx = StaticParamDo.getCtx();
    		
    		ExportXlsTemplate xlsTemplate = (ExportXlsTemplate)ctx.getBean(action);    		
    		
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-disposition","attachment;filename=" + java.net.URLEncoder.encode("统计结果", "UTF-8") + ".xls");
            
            OutputStream os = response.getOutputStream();
            
            //生成后的Excel文件名称包括路径
            String fileName = xlsTemplate.getXlsFile(request,servletContext);
            
			FileInputStream in = new FileInputStream(fileName);
            byte b[] = new byte[1024];
            while(in.read(b) != -1)
                os.write(b);            
            in.close();            
            os.flush();
            os.close();          
            //删除文件
             
            	File file = new File(fileName);
                file.delete(); 
            
                       
        }catch (Exception ex) {
        	log.info("下载导出文件失败：" + ex);
        }
        return;
        
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}   	  	    
}