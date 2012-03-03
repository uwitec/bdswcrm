package com.sw.cms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.opensymphony.webwork.ServletActionContext;
import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.SerialNumPd;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.SerialNumService;
import com.sw.cms.service.StoreService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.FileUtils;
import com.sw.cms.util.StaticParamDo;

/**
 * ���кŹ���
 * @author liyt
 *
 */
public class SerialNumAction extends BaseAction  {
	
	private SerialNumService serialNumService;
	private ProductService productService;
	private StoreService storeService;
	
	private String serial_num = "";
	private String q_serial_num = "";
	private String state = "";
	private String product_name = "";
	private String product_xh = "";
	private int curPage = 1;
	
	private Map serialStateMap = null;
	private List serialFlowList = new ArrayList();
	private List storeList = new ArrayList();
	private Page pageSerialNum;
	private Page pageProduct;
	private Page pageSerialNumPd;
	private SerialNumMng serialNumMng = new SerialNumMng();
	private String msg = "";
	private String store_id = "";
	
	private File txtFile ;
	private String txtFileFileName;
	private String tab_flag = "";
	
	private String[] arryNums;
	private List serialNumList = new ArrayList();
	
	private SerialNumPd serialNumPd = new SerialNumPd();
	
	private String start_date = "";
	private String end_date = "";
	private String jsr = "";
	
	
	
	/**
	 * ���к��б�
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		String con = "";
		if(!q_serial_num.equals("")){
			con += " and serial_num='" + q_serial_num + "'";
		}
		if(!state.equals("")){
			con += " and state='" + state + "'";
		}
		if(!product_name.equals("")){
			con += " and product_name like'%" + product_name + "%'";
		}
		if(!store_id.equals("")){
			con += " and store_id='" + store_id + "'";
		}
		pageSerialNum = serialNumService.getSerialNumPage(con, curPage, rowsPerPage);
		storeList = storeService.getAllStoreList();
		return "success";
	}
	
	/**
	 * �༭���к���Ϣ
	 * @return
	 */
	public String edit(){
		if(!serial_num.equals("")){
			serialNumMng = serialNumService.editSerialNumMng(serial_num);
		}
		storeList = storeService.getAllStoreList();
		return "success";
	}
	
	
	/**
	 * <p>�������к���Ϣ</p>
	 * <p>������д�������£���������������</p>
	 * @return
	 */
	public String update(){
		serialNumService.updateSerialNumState(serialNumMng);
		return "success";
	}
	
	
	/**
	 * ɾ�����к�
	 * @return
	 */
	public String del(){
		serialNumService.delSerialNum(serial_num);
		return "success";
	}
	
	
	/**
	 * ȡ��ѯ���
	 * @return
	 */
	public String getSerialFlow(){
		if(!serial_num.equals("")){
			serialStateMap = serialNumService.getSerialState(serial_num);
			serialFlowList = serialNumService.getSerialFlow(serial_num);
		}
		return "success";
	}
	
	
	/**
	 * ȡ��Ʒ�б�
	 * @return
	 */
	public String getProducts(){
		String con = "";
		int rowsPerPage = Constant.PAGE_SIZE;
		
		if(!product_name.equals("")){
			con += " and product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			con += " and product_xh like '%" + product_xh + "%'";
		}
		
		pageProduct = productService.getProducts(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * ���к��̵�����
	 * @return
	 */
	public String pdSerialNumCon(){
		try{
			storeList = storeService.getAllStoreList();
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * �б���ʾ���к��̵���
	 * @return
	 */
	public String listSerialNumPd(){
		try{
			int rowsPerPage = Constant.PAGE_SIZE2;
			String con = "";
			if(!start_date.equals("")){
				con += " and a.cdate>='" + start_date + "'";
			}
			if(!end_date.equals("")){
				con += " and a.a.cdate<='" + end_date + " 23:59:59'";
			}
			if(!jsr.equals("")){
				con += " and a.jsr like'%" + jsr + "%'";
			}
			con += " order by cz_date desc";
			pageSerialNumPd = serialNumService.getSerialNumPdPage(con, curPage, rowsPerPage);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * ���к��̵���
	 * @return
	 */
	public String pdSerialNumResult(){
		try{ 
			String realFilePath = "";
			if(txtFileFileName != null && !txtFileFileName.equals("")){
				//��ȡ��ǰ�ľ���·��
				String filePath = ServletActionContext.getServletContext().getRealPath("/");
				realFilePath = filePath + "upload/" + UUID.randomUUID().toString();
				
				//�ϴ���ƷͼƬ�ļ�
				FileOutputStream outputStream = new FileOutputStream(realFilePath);
				FileInputStream fileIn = new FileInputStream(txtFile);
				byte[] buffer = new byte[1024];
		
				int len;
				while ((len = fileIn.read(buffer)) > 0) {
					outputStream.write(buffer, 0, len);
				}
				outputStream.flush();
				outputStream.close();
				fileIn.close();		
			}
			
			String strNums = "";
			if(tab_flag.equals("�س�")){
				strNums = FileUtils.readFileByLines(realFilePath);
			}else{
				strNums = FileUtils.readFileByChars(realFilePath);
			}
			
			//ɾ����ʱ�ļ�
			File tempFile = new File(realFilePath);
			tempFile.delete();
						
			if(!strNums.equals("")){
				//��ֹ����ȫ�Ƕ���
				strNums = strNums.replaceAll("��", ",");
				
				//ʵ�����к����
				arryNums = strNums.split(",");
			}
			
			//�������к��б�
			serialNumList = serialNumService.getSerialNumMngListByStoreId(store_id);
			
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * �������к��̵��¼<BR>
	 * �̵��¼�洢�ڹ�ϵ���ݿ��У��̵�����excel��ʽ�洢�����ݿ���
	 * @return
	 */
	public String insertSerialNumPd(){
		try{
			if(serialNumService.isSerialNumPdExist(serialNumPd.getCdate(), serialNumPd.getStore_id())){
				return SUCCESS;
			}
			String filePath = ServletActionContext.getServletContext().getRealPath("/");
			String xlsFileName = UUID.randomUUID().toString() + ".xls";
			String xlsFile = filePath + "upload/" + xlsFileName;
			
			//���ļ� 
	        WritableWorkbook book = Workbook.createWorkbook(new File(xlsFile));
	        //������Ϊ��ͳ�ƽ�����Ĺ���������0��ʾ���ǵ�һҳ 
	        WritableSheet sheet  =  book.createSheet( "�̵���" ,  0 );			
	        
	        //�����п�
	        sheet.setColumnView(0, 40);
	        sheet.setColumnView(1, 100);
	        
	        //���嵥Ԫ���ʽ
	        WritableCellFormat ft_item_center_bold = new WritableCellFormat();
			ft_item_center_bold.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.BOLD));
			ft_item_center_bold.setAlignment(Alignment.CENTRE);
			ft_item_center_bold.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_center_bold.setBorder(Border.ALL,BorderLineStyle.THIN);
			
	        WritableCellFormat ft_item_left = new WritableCellFormat();
			ft_item_left.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.NO_BOLD));
			ft_item_left.setAlignment(Alignment.LEFT);
			ft_item_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_left.setBorder(Border.ALL,BorderLineStyle.THIN);
	        
			//д����
			sheet.mergeCells(0, 0, 1, 0);
			Label label = new Label(0,0,"���к��̵���",ft_item_center_bold);
			sheet.addCell(label);
			
			//дͳ������
			sheet.mergeCells(0, 1, 1, 1);
			String tempCon = "�̵�ʱ�䣺" + serialNumPd.getCdate() + "�̵�ⷿ��"  + StaticParamDo.getStoreNameById(serialNumPd.getStore_id());
			label = new Label(0,1,tempCon,ft_item_center_bold);
			sheet.addCell(label);
			
			//д��ͷ
			label = new Label(0,2,"ʵ��",ft_item_center_bold);
			sheet.addCell(label);
			label = new Label(1,2,"����",ft_item_center_bold);
			sheet.addCell(label);
	        
			String[] arryPdNums = (String[])this.getSession().getAttribute("arryPdNums");
			this.getSession().removeAttribute("arryPdNums");
			List serialPdNumList = (List)this.getSession().getAttribute("serialPdNumList");
			this.getSession().removeAttribute("serialPdNumList");
			
			int i =0;
			if(arryPdNums != null && arryPdNums.length > 0){
				for(;i<arryPdNums.length;i++){
					
					String zmdy = "��";  //�����Ӧ���
					
					if(serialPdNumList != null && serialPdNumList.size() > 0){
						for(int k=0;k<serialPdNumList.size();k++){
							SerialNumMng serialNumMng = (SerialNumMng)serialPdNumList.get(k);
							
							//���������ڸ����к�
							if((arryPdNums[i].trim()).equals(serialNumMng.getSerial_num())){
								
								//�����Ӧ���ȡֵ
								zmdy =serialNumMng.getSerial_num() + "/" + serialNumMng.getProduct_id() + "/" + serialNumMng.getProduct_name() + "/" + serialNumMng.getProduct_xh();
								
								//���������ֻ��Ӧ����У�������ʣ���Ϊû�ж�Ӧ�ϵ�
								serialPdNumList.remove(k);
								break;
							}
						}
					}
					label = new Label(0,i+3,arryPdNums[i],ft_item_left);
					sheet.addCell(label);
					label = new Label(1,i+3,zmdy,ft_item_left);
					sheet.addCell(label);
				}
			}
			if(serialPdNumList != null && serialPdNumList.size() > 0){
				for(int k=0;k<serialPdNumList.size();k++){
					SerialNumMng serialNumMng = (SerialNumMng)serialPdNumList.get(k);
					String zmdy = serialNumMng.getSerial_num() + "/" + serialNumMng.getProduct_id() + "/" + serialNumMng.getProduct_name() + "/" + serialNumMng.getProduct_xh();
					String sjxlh = "��";		
					label = new Label(0,i+3+k,sjxlh,ft_item_left);
					sheet.addCell(label);
					label = new Label(1,i+3+k,zmdy,ft_item_left);
					sheet.addCell(label);
				}
			}
			
	        book.write();
            book.close();
            
            //��ǰ��¼�û�
    		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
    		String user_id = info.getUser_id();
    		
            serialNumPd.setJsr(user_id);
            serialNumPd.setPd_result(xlsFileName);
            
            //�����̵���
            serialNumService.insertSerialNumPd(serialNumPd);
			
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	

	public SerialNumService getSerialNumService() {
		return serialNumService;
	}

	public void setSerialNumService(SerialNumService serialNumService) {
		this.serialNumService = serialNumService;
	}

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public List getSerialFlowList() {
		return serialFlowList;
	}

	public void setSerialFlowList(List serialFlowList) {
		this.serialFlowList = serialFlowList;
	}

	public Map getSerialStateMap() {
		return serialStateMap;
	}

	public void setSerialStateMap(Map serialStateMap) {
		this.serialStateMap = serialStateMap;
	}


	public Page getPageSerialNum() {
		return pageSerialNum;
	}


	public void setPageSerialNum(Page pageSerialNum) {
		this.pageSerialNum = pageSerialNum;
	}


	public SerialNumMng getSerialNumMng() {
		return serialNumMng;
	}


	public void setSerialNumMng(SerialNumMng serialNumMng) {
		this.serialNumMng = serialNumMng;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public int getCurPage() {
		return curPage;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public Page getPageProduct() {
		return pageProduct;
	}

	public void setPageProduct(Page pageProduct) {
		this.pageProduct = pageProduct;
	}

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public List getStoreList() {
		return storeList;
	}

	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getQ_serial_num() {
		return q_serial_num;
	}

	public void setQ_serial_num(String qSerialNum) {
		q_serial_num = qSerialNum;
	}

	public File getTxtFile() {
		return txtFile;
	}

	public void setTxtFile(File txtFile) {
		this.txtFile = txtFile;
	}

	public String getTxtFileFileName() {
		return txtFileFileName;
	}

	public void setTxtFileFileName(String txtFileFileName) {
		this.txtFileFileName = txtFileFileName;
	}

	public String getTab_flag() {
		return tab_flag;
	}

	public void setTab_flag(String tab_flag) {
		this.tab_flag = tab_flag;
	}

	public String[] getArryNums() {
		return arryNums;
	}

	public void setArryNums(String[] arryNums) {
		this.arryNums = arryNums;
	}

	public List getSerialNumList() {
		return serialNumList;
	}

	public void setSerialNumList(List serialNumList) {
		this.serialNumList = serialNumList;
	}

	public SerialNumPd getSerialNumPd() {
		return serialNumPd;
	}

	public void setSerialNumPd(SerialNumPd serialNumPd) {
		this.serialNumPd = serialNumPd;
	}

	public Page getPageSerialNumPd() {
		return pageSerialNumPd;
	}

	public void setPageSerialNumPd(Page pageSerialNumPd) {
		this.pageSerialNumPd = pageSerialNumPd;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

}
