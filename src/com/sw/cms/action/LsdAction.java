package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Lsd;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.service.LsdService;
import com.sw.cms.service.PosTypeService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.MoneyUtil;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

/**
 * ���۵�����
 * @author liyt
 *
 */
public class LsdAction extends BaseAction {
	
	private LsdService lsdService;	
	private UserService userService;
	private StoreService storeService;
	private ProductKcService productKcService;
	private SysInitSetService sysInitSetService;
	private SjzdService sjzdService;
	private PosTypeService posTypeService;
	private ProductKindService productKindService;
 
	
	private List userList = new ArrayList();
	private List storeList = new ArrayList();
	private List posTypeList = new ArrayList();
 
	private Page productPage;
 
	private String[] ysfs;
 
	
	private Page pageLsd;
	private Lsd lsd = new Lsd();
	
	private List lsdProducts = new ArrayList();
	
	private String client_name = "";
	private String creatdate = DateComFunc.getToday();
	private String creatdate2 = DateComFunc.getToday();
	private String state = "";
	private String orderName ="";
	private String orderType ="";
	private String iscs_flag = "";  //ϵͳ�Ƿ��ʼ��ɱ�־
	private String flag = "";
	private String xsry_name = "";
	private String isqzxlh_flag = "";  //ϵͳ�Ƿ�Ҫ��ǿ�����к�
	private int curPage = 1;
	
	private String id = "";
	private String sp_state = "";
	private String reportstyle="";//������ʽ
	private String sd = "0";
	
	private Map tcblMap;
	private String basic_ratio = "0";
	private String out_ratio = "0";
	private String ds_ratio = "0";
	
	private String product_name = "";
	private String product_kind = "";
	private String prop = "";
	private List kindList = new ArrayList();
	
	
	//��ӡ�������
	private String client_tel = "";
	private String dept_name = "";
	private String skfs = "";
	private String skzh_name = "";
	private String title_name = "";
	private String foot_name = "";
	private String address = "";
	private String remark = "";
	private String jexj_dx = "";

	
	/**
	 * ���۵��б�
	 * @return
	 */
	public String list(){
		
		int rowsPerPage = Constant.PAGE_SIZE;
		reportstyle = userService.getReportStyle();
		String con = "";
		if(!creatdate.equals("")){
			con += " and a.creatdate>='" + creatdate + "'";
		}
		if(!creatdate2.equals("")){
			con += " and a.creatdate<='" + (creatdate2 + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			con += " and a.client_name like'%" + client_name + "%'";
		}
		if(!state.equals("")){
			con += " and a.state='" + state + "'";
		}
		if(!xsry_name.equals("")){
			con += " and b.real_name like '%" + xsry_name + "%'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		pageLsd = lsdService.getLsdList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * �ѳɽ����۵��б��˻�ʱѡ��
	 * @return
	 */
	public String listCjLsd(){
		
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = " and a.state='���ύ'";
		
		if(!creatdate.equals("")){
			con += " and a.creatdate>='" + creatdate + "'";
		}
		if(!creatdate2.equals("")){
			con += " and a.creatdate<='" + (creatdate2 + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			con += " and a.client_name like'%" + client_name + "%'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		pageLsd = lsdService.getLsdList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * ��������۵�ҳ��
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		
		//�������۵����
		lsd.setId(lsdService.updateLsdId());
		
		storeList = storeService.getAllStoreList();
		
		iscs_flag = sysInitSetService.getQyFlag();  //�Ƿ���ɳ�ʼ��־
		isqzxlh_flag = userService.getQzxlh();
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		
		posTypeList = posTypeService.getPosTypeList();
		
		return "success";
	}
	
	
	/**
	 * �������۵���Ϣ
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		lsd.setCzr(user_id);
		
		//�Ƿ���ɳ�ʼ��־
		iscs_flag = sysInitSetService.getQyFlag();
		isqzxlh_flag = userService.getQzxlh();
		//ֻ������ɳ�ʼ��������������Ƿ����������ж�
		if(iscs_flag.equals("1")){
			
			//������۵�״̬Ϊ���ύ��Ҫ�жϿ���Ƿ������������
			//�����������������򱣴����۵���ͬʱ��������ҳ�棬��ʾ�û�
			if(lsd.getState().equals("���ύ")){
				if(!(lsdService.checkKc(lsd, lsdProducts)).equals("")){
					this.setMsg(lsdService.checkKc(lsd, lsdProducts));
					lsd.setState("�ѱ���");
					lsdService.saveLsd(lsd, lsdProducts);
					
					//ҳ���ʼ����
					storeList = storeService.getAllStoreList();
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					userList = userService.getAllEmployeeList();
					
					return INPUT;
				}
				
				if(isqzxlh_flag.equals("01")){
			 	   if(!(lsdService.checkXlh(lsd, lsdProducts)).equals("")){
						this.setMsg(lsdService.checkXlh(lsd, lsdProducts));
						lsd.setState("�ѱ���");
						lsdService.saveLsd(lsd, lsdProducts);
						
						//ҳ���ʼ����
						storeList = storeService.getAllStoreList();
						ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
						posTypeList = posTypeService.getPosTypeList();
						userList = userService.getAllEmployeeList();
						
						return INPUT;
				   }
				}
			}
			
		}
		
		//�ж��Ƿ���ڳ��ͼ���Ʒ������¼����棬�ύ�û��ύ��������ر�
		if(lsd.getState().equals("���ύ") && lsdService.isExistLowLsxj(lsd,lsdProducts)){

			lsd.setState("�ѱ���");
			lsd.setSp_state("1");  //��Ҫ����
			lsdService.saveLsd(lsd, lsdProducts);
			
			//ҳ���ʼ����
			storeList = storeService.getAllStoreList();
			ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
			posTypeList = posTypeService.getPosTypeList();
			userList = userService.getAllEmployeeList();
			
			//������ʾ�����ı�־
			flag = "1";
			
			return INPUT;
		}
		
		lsdService.saveLsd(lsd, lsdProducts);
		return SUCCESS;
	}
	
	
	/**
	 * ���޸����۵�ҳ��
	 * @return
	 */
	public String edit(){
		
		lsd = (Lsd)lsdService.getLsd(id);
		lsdProducts = lsdService.getLsdProducts(id);
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		iscs_flag = sysInitSetService.getQyFlag();
		isqzxlh_flag = userService.getQzxlh();
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		return "success";
	}
	
	
	/**
	 * �������۵���Ϣ
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		lsd.setCzr(user_id);
		
		iscs_flag = sysInitSetService.getQyFlag();
		isqzxlh_flag = userService.getQzxlh();
		//ֻ������ɳ�ʼ��������������Ƿ����������ж�
		if(iscs_flag.equals("1")){
			
			//������۵�״̬Ϊ���ύ��Ҫ�жϿ���Ƿ������������
			//�����������������򱣴����۵���ͬʱ��������ҳ�棬��ʾ�û�
			if(lsd.getState().equals("���ύ")){
				if(!(lsdService.checkKc(lsd, lsdProducts)).equals("")){
					this.setMsg(lsdService.checkKc(lsd, lsdProducts));
					lsd.setState("�ѱ���");
					lsdService.updateLsd(lsd, lsdProducts);
					
					userList = userService.getAllEmployeeList();
					storeList = storeService.getAllStoreList();
					iscs_flag = sysInitSetService.getQyFlag();  //�Ƿ���ɳ�ʼ��־
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					
					return INPUT;
				}
				if(isqzxlh_flag.equals("01")){
				  if(!(lsdService.checkXlh(lsd, lsdProducts)).equals("")){
					this.setMsg(lsdService.checkXlh(lsd, lsdProducts));
					lsd.setState("�ѱ���");
					lsdService.updateLsd(lsd, lsdProducts);
					
					//ҳ���ʼ����
					storeList = storeService.getAllStoreList();
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					userList = userService.getAllEmployeeList();
					
					return INPUT;
				  }
				}
			}
		}
		
		
		//�ж��Ƿ���ڳ��ͼ���Ʒ�������������״̬�޸�Ϊ������
		if(lsd.getState().equals("���ύ") && lsdService.isExistLowLsxj(lsd,lsdProducts)){
			if(flag.equals("1")){
				//�ύ����
				lsd.setState("�ѱ���");
				lsd.setSp_state("2");  //������
			}else{
				//��������ύ����������¼�������û���ʾ
				lsd.setState("�ѱ���");
				lsd.setSp_state("1");  //��Ҫ����
				lsdService.updateLsd(lsd, lsdProducts);
				
				//ҳ���ʼ����
				storeList = storeService.getAllStoreList();
				ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
				posTypeList = posTypeService.getPosTypeList();
				userList = userService.getAllEmployeeList();
				flag = "1";
				
				return INPUT;
			}

		}
		
		lsdService.updateLsd(lsd, lsdProducts);
		return "success";
	}
	
	
	/**
	 * �ύ����
	 * @return
	 */
	public String submitInfo(){		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		lsdProducts = lsdService.getLsdProducts(lsd.getId());
		isqzxlh_flag = userService.getQzxlh();
		String msg = lsdService.checkKc(lsd, lsdProducts);
		if(!msg.equals("")){
			this.saveMessage(msg);
			
			userList = userService.getAllEmployeeList();
			storeList = storeService.getAllStoreList();
			ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
			posTypeList = posTypeService.getPosTypeList();
			
			return "input";
		}
		
		if(isqzxlh_flag.equals("01")){
			 msg = lsdService.checkXlh(lsd, lsdProducts);
			 if(!msg.equals(""))
			 {
				   this.saveMessage(msg);
					
					userList = userService.getAllEmployeeList();
					storeList = storeService.getAllStoreList();
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					
					return "input";
			   }
		}
		
		lsd.setCzr(user_id);
		
		lsd.setSp_state("2");  //�޸����۵�Ϊ������״̬
		lsd.setState("�ѱ���");      //�޸����۵�״̬Ϊ�ѱ���
		
		lsdService.updateLsd(lsd, lsdProducts);
		
		return "success";
		
	}
	
	
	/**
	 * ɾ�����۵�
	 * @return
	 */
	public String del(){
		String id = ParameterUtility.getStringParameter(getRequest(),"id", "");
		lsdService.delLsd(id);
		return "success";
	}
	
	
	
	/**
	 * ��ѡ������Ʒ�б�
	 * @return
	 */
	public String selKcProc(){

		int rowsPerPage = 15;
		
		String con = " and a.state='����'";
		if(!product_name.equals("")){
			product_name = ((product_name.replace("��", " ")).replace(",", "")).replace("��", " ");
			String[] arryCon = product_name.split(" ");
			String tempSql = "";
			if(arryCon != null && arryCon.length > 0){
				con +=" and(";
				for(int i=0;i<arryCon.length;i++){
					if(tempSql.equals("")){
						tempSql = "(a.product_id like '%" + arryCon[i] + "%' or a.product_name like '%" + arryCon[i] + "%' or a.product_xh like '%" + arryCon[i] + "%' or a.sp_txm like '%" + arryCon[i] + "%')";
					}else{
						tempSql = tempSql + " and (a.product_id like '%" + arryCon[i] + "%' or a.product_name like '%" + arryCon[i] + "%' or a.product_xh like '%" + arryCon[i] + "%' or a.sp_txm like '%" + arryCon[i] + "%')";
					}
				}
				con += tempSql + ")";
			}
		}
		if(!product_kind.equals("")){
			con += " and a.product_kind like '" + product_kind + "%'";
		}
		if(!prop.equals("")){
			con += " and prop='" + prop + "'";
		}
		
		productPage = productKcService.getProductKcList(con, curPage, rowsPerPage);
		
		storeList = storeService.getAllStoreList();
		kindList = productKindService.getAllProductKindList();
		
		sd = JMath.round(lsdService.getLssd());
		
		//��ɱ���
		Map tcblMap = lsdService.getTcbl();
		if(tcblMap != null){
			basic_ratio = JMath.round(tcblMap.get("basic_ratio")==null?0:((Double)tcblMap.get("basic_ratio")).doubleValue());
			out_ratio = JMath.round(tcblMap.get("out_ratio")==null?0:((Double)tcblMap.get("out_ratio")).doubleValue());
		}
		
		return "success";
	}
	
	
	
	/**
	 * ���������кŴ���
	 * @return
	 */
	public String importSerial(){
		return "success";
	}
	
	
	/**
	 * ���۵���ϸ
	 * @return
	 */
	public String descLsd(){
		lsdProducts = lsdService.getLsdProducts(id);
		return "success";
	}
	
	
	/**
	 * ���۵�����
	 * @return
	 */
	public String doSp(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		lsd = (Lsd)lsdService.getLsd(id); //���۵�
		lsdProducts = lsdService.getLsdProducts(id);
		iscs_flag = sysInitSetService.getQyFlag();
		isqzxlh_flag = userService.getQzxlh();
		//ֻ��������ͨ��������ɳ�ʼ�������жϿ���Ƿ�����
		if(sp_state.equals("3") && iscs_flag.equals("1")){	
			//����ͨ��,��Ҫ�жϿ���Ƿ�����
			if(!(lsdService.checkKc(lsd, lsdProducts)).equals("")){
				this.setMsg(lsdService.checkKc(lsd, lsdProducts));
				return "input";
			}
			if(isqzxlh_flag.equals("01")){
				  if(!(lsdService.checkXlh(lsd, lsdProducts)).equals("")){
					  this.setMsg(lsdService.checkXlh(lsd, lsdProducts));
					  return "input";  
				  }
			}
		}
		
		//�����������
		lsdService.saveSp(id, sp_state, user_id);
		
		return "success";
	}
	
	/**
	 * �༭����˰��
	 * @return
	 */
	public String editLssd(){
		sd = lsdService.getLssd() + "";
		return "success";
	}
	
	/**
	 * ��������˰��
	 * @return
	 */
	public String saveLssd(){
		lsdService.saveLssd(sd);
		return "success";
	}
	
	
	/**
	 * �򿪴�ӡ���۵�ҳ�� pdf
	 * @return
	 */
	public String printLsd(){
		try{
			lsd = (Lsd)lsdService.getLsd(id);
			
			client_name = StringUtils.nullToStr(lsd.getClient_name()) + "  " + StringUtils.nullToStr(lsd.getLxr());
			client_tel = StringUtils.nullToStr(lsd.getLxdh()) + "  " + StringUtils.nullToStr(lsd.getMobile()) + "  " + StringUtils.nullToStr(lsd.getMail());
			dept_name = StaticParamDo.getDeptNameById(((SysUser)userService.getUser(lsd.getXsry())).getDept());
			xsry_name = StaticParamDo.getRealNameById(lsd.getXsry());
			skfs = StringUtils.nullToStr(lsd.getFkfs());
			skzh_name = StaticParamDo.getAccountNameById(lsd.getSkzh());
			address = StringUtils.nullToStr(lsd.getAddress());
			remark = StringUtils.nullToStr(lsd.getMs());
			jexj_dx = MoneyUtil.toChinese(lsd.getLsdje()+"");
			creatdate = StringUtils.nullToStr(lsd.getCreatdate());
			
			Map map = sysInitSetService.getReportSet();
			
			if(map != null){
				title_name = StringUtils.nullToStr(map.get("title_name")) + "���۵�";
				foot_name = StringUtils.nullToStr(map.get("foot_name"));	
			}

			
			lsdProducts = lsdService.getLsdProducts(id);
		}catch(Exception e){
			log.error("��ӡ���۵�����ԭ��" + e.getMessage());
			return ERROR;
		}
		return "success";
	}
	
	
	/**
	 * �༭��ɱ���
	 * @return
	 */
	public String editTcbl(){
		try{
			tcblMap = lsdService.getTcbl();
		}catch(Exception e){
			log.error("�༭��ɱ�����ԭ��" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
		return "success";
	}
	
	
	/**
	 * ������ɱ���
	 * @return
	 */
	public String saveTcbl(){
		try{
			lsdService.saveTcbl(basic_ratio, out_ratio, ds_ratio);
		}catch(Exception e){
			log.error("������ɱ�����ԭ��" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
		return "success";
	}
	
	
	public Lsd getLsd() {
		return lsd;
	}
	public void setLsd(Lsd lsd) {
		this.lsd = lsd;
	}
	public LsdService getLsdService() {
		return lsdService;
	}
	public void setLsdService(LsdService lsdService) {
		this.lsdService = lsdService;
	}
	public Page getPageLsd() {
		return pageLsd;
	}
	public void setPageLsd(Page pageLsd) {
		this.pageLsd = pageLsd;
	}
	public List getLsdProducts() {
		return lsdProducts;
	}
	public void setLsdProducts(List lsdProducts) {
		this.lsdProducts = lsdProducts;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List getUserList() {
		return userList;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserList(List userList) {
		this.userList = userList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public ProductKcService getProductKcService() {
		return productKcService;
	}
	public void setProductKcService(ProductKcService productKcService) {
		this.productKcService = productKcService;
	}
	public Page getProductPage() {
		return productPage;
	}
	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}
	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}
	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}
	public String getIscs_flag() {
		return iscs_flag;
	}
	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
	}
	public SjzdService getSjzdService() {
		return sjzdService;
	}
	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}
	public String[] getYsfs() {
		return ysfs;
	}
	public void setYsfs(String[] ysfs) {
		this.ysfs = ysfs;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCreatdate2() {
		return creatdate2;
	}
	public void setCreatdate2(String creatdate2) {
		this.creatdate2 = creatdate2;
	}
	public String getXsry_name() {
		return xsry_name;
	}
	public void setXsry_name(String xsry_name) {
		this.xsry_name = xsry_name;
	}
	public String getSp_state() {
		return sp_state;
	}
	public void setSp_state(String sp_state) {
		this.sp_state = sp_state;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public List getPosTypeList() {
		return posTypeList;
	}
	public void setPosTypeList(List posTypeList) {
		this.posTypeList = posTypeList;
	}
	public PosTypeService getPosTypeService() {
		return posTypeService;
	}
	public void setPosTypeService(PosTypeService posTypeService) {
		this.posTypeService = posTypeService;
	}
	public String getProduct_kind() {
		return product_kind;
	}
	public void setProduct_kind(String product_kind) {
		this.product_kind = product_kind;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public List getKindList() {
		return kindList;
	}
	public void setKindList(List kindList) {
		this.kindList = kindList;
	}
	public ProductKindService getProductKindService() {
		return productKindService;
	}
	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	public String getClient_tel() {
		return client_tel;
	}
	public void setClient_tel(String client_tel) {
		this.client_tel = client_tel;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getSkfs() {
		return skfs;
	}
	public void setSkfs(String skfs) {
		this.skfs = skfs;
	}
	public String getSkzh_name() {
		return skzh_name;
	}
	public void setSkzh_name(String skzh_name) {
		this.skzh_name = skzh_name;
	}
	public String getFoot_name() {
		return foot_name;
	}
	public void setFoot_name(String foot_name) {
		this.foot_name = foot_name;
	}
	public String getTitle_name() {
		return title_name;
	}
	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getJexj_dx() {
		return jexj_dx;
	}
	public void setJexj_dx(String jexj_dx) {
		this.jexj_dx = jexj_dx;
	}
	public String getBasic_ratio() {
		return basic_ratio;
	}
	public void setBasic_ratio(String basic_ratio) {
		this.basic_ratio = basic_ratio;
	}
	public String getOut_ratio() {
		return out_ratio;
	}
	public void setOut_ratio(String out_ratio) {
		this.out_ratio = out_ratio;
	}
	public Map getTcblMap() {
		return tcblMap;
	}
	public void setTcblMap(Map tcblMap) {
		this.tcblMap = tcblMap;
	}
	public String getDs_ratio() {
		return ds_ratio;
	}
	public void setDs_ratio(String ds_ratio) {
		this.ds_ratio = ds_ratio;
	}
	public String getIsqzxlh_flag() {
		return isqzxlh_flag;
	}


	public void setIsqzxlh_flag(String isqzxlh_flag) {
		this.isqzxlh_flag = isqzxlh_flag;
	}
	
	public String getReportstyle() {
		return reportstyle;
	}

	public void setReportstyle(String report_style) {
		this.reportstyle = report_style;
	}
}
