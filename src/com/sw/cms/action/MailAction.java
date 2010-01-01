package com.sw.cms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.webwork.ServletActionContext;
import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.MailSet;
import com.sw.cms.model.Page;
import com.sw.cms.sendmail.SendMail;
import com.sw.cms.service.MailService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class MailAction extends BaseAction {
	
	private SendMail sendMail;
	private MailService mailService;
	private SjzdService sjzdService;
	
	private MailSet mailSet;
	
	private String to = "";
	private String content = "";
	private String subject = "";
		
	private File[] suffix;
	private String[] suffixFileName;
	
	private Page pageMail;
	
	
	private String client_type = "";
	private int curPage = 1;
	
	private List wldwlx = new ArrayList();
	private List mailAll=new ArrayList();
	 


	public List getMailAll() {
		return mailAll;
	}


	public void setMailAll(List mailAll) {
		this.mailAll = mailAll;
	}


	/**
	 * ������ʼ�ҳ��
	 * @return
	 */
	public String add(){
		try{
			 
			return SUCCESS;
		}catch(Exception e){
			log.error("���ʼ�����ҳ�����ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * �����ʼ�
	 * @return
	 */
	public String send(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			
			String[] files = null;   //�����飬���ϴ���������
			
			//��ȡ��ǰ�ľ���·��
			String filePath = ServletActionContext.getServletContext().getRealPath("/");			
			if(suffixFileName != null && suffixFileName.length>0){
				files = new String[suffixFileName.length];
				for(int i=0;i<suffixFileName.length;i++){
					if(suffixFileName[i] != null && !suffixFileName[i].equals("")){
						
						String fp = filePath + "upload/" + suffixFileName[i];
						
						//�ϴ�����
						FileOutputStream outputStream = new FileOutputStream(fp);
						FileInputStream fileIn = new FileInputStream(suffix[i]);
						byte[] buffer = new byte[1024];
				
						int len;
						while ((len = fileIn.read(buffer)) > 0) {
							outputStream.write(buffer, 0, len);
						}
						outputStream.flush();
						outputStream.close();
						fileIn.close();	
						
						files[i] = fp;
					}
				}
			}
			
			if(!to.equals("")){
				String[] arryTo = to.split(",");
				for(int i=0;i<arryTo.length;i++){
					sendMail.send(user_id, subject, content, files, arryTo[i]);
				}
			}
			
			if(files != null && files.length > 0){
				for(int i=0;i<files.length;i++){
					File file = new File(files[i]);
					file.delete();
				}
			}
			
			this.setMsg("�ʼ����ͳɹ���");
			return SUCCESS;
		}catch(Exception e){
			this.setMsg("�ʼ�����ʧ�ܣ�");
			log.error("�����ʼ�����ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * ѡ���ʼ���ַ
	 * @return
	 */
	public String selLxr(){
		try{
			int rowsPerPage = Constant.PAGE_SIZE;
			
			wldwlx = sjzdService.getZdxmByZdId("SJZD_WLDWLX");
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("xm_id", "LS_TYPE");
			map.put("xm_name", "���ۿͻ�");
			
			wldwlx.add(map);
			
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("xm_id", "GSYG_TYPE");
			map2.put("xm_name", "��˾Ա��");
			
			wldwlx.add(map2);
			
			pageMail = mailService.getLxrList(client_type, curPage, rowsPerPage);
			mailAll=mailService.getLxrAll(client_type);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("ѡ���ʼ���ַ����ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * �༭�ʼ�����
	 * @return
	 */
	public String editMailSet(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			mailSet = mailService.getMailSet(info.getUser_id());
			return SUCCESS;
		}catch(Exception e){
			log.error("�༭�ʼ����ô���ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * �����ʼ�����
	 */
	public String updateMailSet(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			mailSet.setUser_id(info.getUser_id());
			mailService.updateMailSet(mailSet);
			
			this.setMsg("�����ʼ��������óɹ���");
			
			return SUCCESS;
		}catch(Exception e){
			log.error("�༭�ʼ����ô���ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String[] getSuffixFileName() {
		return suffixFileName;
	}


	public void setSuffixFileName(String[] suffixFileName) {
		this.suffixFileName = suffixFileName;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public SendMail getSendMail() {
		return sendMail;
	}


	public void setSendMail(SendMail sendMail) {
		this.sendMail = sendMail;
	}


	public File[] getSuffix() {
		return suffix;
	}


	public void setSuffix(File[] suffix) {
		this.suffix = suffix;
	}


	public String getClient_type() {
		return client_type;
	}


	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}

	public int getCurPage() {
		return curPage;
	}


	public MailService getMailService() {
		return mailService;
	}


	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}


	public Page getPageMail() {
		return pageMail;
	}


	public void setPageMail(Page pageMail) {
		this.pageMail = pageMail;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public List getWldwlx() {
		return wldwlx;
	}


	public void setWldwlx(List wldwlx) {
		this.wldwlx = wldwlx;
	}


	public MailSet getMailSet() {
		return mailSet;
	}


	public void setMailSet(MailSet mailSet) {
		this.mailSet = mailSet;
	}


}
