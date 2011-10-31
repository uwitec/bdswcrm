package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CgthdDAO;
import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.LsdDAO;
import com.sw.cms.dao.PosTypeDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.ProductSaleFlowDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.dao.XsdDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.ProductSaleFlow;
import com.sw.cms.model.Qtzc;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.SysUser;
import com.sw.cms.model.Xsd;
import com.sw.cms.model.XsdProduct;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

/**
 * ���ⵥ����
 * @author jinyanni
 *
 */
public class CkdService {
	
	private CkdDAO ckdDao;
	private StoreDAO storeDao;
	private CgthdDAO cgthdDao;
	private ProductKcDAO productKcDao;
	private XsdDAO xsdDao;
	private SerialNumDAO serialNumDao;
	private SysInitSetDAO sysInitSetDao;
	private XsskDAO xsskDao;
	private AccountDzdDAO accountDzdDao;
	private AccountsDAO accountsDao;
	private CgfkDAO cgfkDao;
	private YufukDAO yufukDao;
	private ProductDAO productDao;
	private LsdDAO lsdDao;
	private ProductSaleFlowDAO productSaleFlowDao;
	private UserDAO userDao;
	private PosTypeDAO posTypeDao;
	private QtzcDAO qtzcDao;

	
	/**
	 * ȡ�б�������ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCkdList(String con,int curPage, int rowsPerPage){
		return ckdDao.getCkdList(con, curPage, rowsPerPage);
	}
	
	
	
	/**
	 * <p>������ⵥ��Ϣ</p>
	 * 
	 * @param ckd
	 * @param ckdProducts
	 */
	public void saveCkd(Ckd ckd,List ckdProducts){
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct cdkProduct = (CkdProduct)ckdProducts.get(i);
				if(cdkProduct != null){
					if(!cdkProduct.getProduct_id().equals("")){
						XsdProduct xsdProduct = (XsdProduct)xsdDao.getXsdProductInfo(ckd.getXsd_id(), cdkProduct.getProduct_id());
						if(xsdProduct != null){
							cdkProduct.setCbj(xsdProduct.getCbj());
							cdkProduct.setPrice(xsdProduct.getPrice());
							cdkProduct.setJgtz(xsdProduct.getJgtz());
						}else{
							cdkProduct.setCbj(0);
							cdkProduct.setPrice(0);
							cdkProduct.setJgtz(0);
						}
					}
				}
			}
		}
		
		
		ckdDao.saveCkd(ckd, ckdProducts);
		
		if(ckd.getState().equals("�ѳ���")){
			
			this.updateProductKc(ckd, ckdProducts); //�޸Ŀ��
			
			//ֻ����ϵͳ��ʽʹ�ú��ȥ�������к�
			//ϵͳ��ʽ����ǰ������ǿ�����к������ƣ�����������к�ϵͳͬ�����账��
			//if(sysInitSetDao.getQyFlag().equals("1")){				
			this.updateSerialNum(ckd, ckdProducts); //�������к�
			//}
			
			this.updateXsdState(ckd.getXsd_id(), "�ѳ���",ckd.getYsfs(),ckd.getCx_tel(),ckd.getJob_no(),ckd.getSend_time(),ckd.getStore_id(),ckd.getFzr(),ckd.getCk_date());
		}
	}
	
	
	/**
	 * ���³��ⵥ��Ϣ
	 * @param ckd
	 * @param ckdProducts
	 */
	public void updateCkd(Ckd ckd,List ckdProducts){
		
		//���³��ⵥ
		ckdDao.updateCkd(ckd, ckdProducts);
		
		if(ckd.getState().equals("�ѳ���")){
			
			//��ɱ���
			Map tcblMap = lsdDao.getTcbl();
			double basic_ratio = 0;
			double out_ratio = 0;
			double ds_ratio = 0;
			if(tcblMap != null){
				basic_ratio = tcblMap.get("basic_ratio")==null?0:((Double)tcblMap.get("basic_ratio")).doubleValue();
				out_ratio = tcblMap.get("out_ratio")==null?0:((Double)tcblMap.get("out_ratio")).doubleValue();
				ds_ratio = tcblMap.get("ds_ratio")==null?0:((Double)tcblMap.get("ds_ratio")).doubleValue();
			}
			
			//���ⵥ��Ӧ�����۵���Ϣ
			Xsd xsd = (Xsd)xsdDao.getXsd(ckd.getXsd_id());
			
			//˰��
			double sd = 0l;
			if(!xsd.getFplx().equals("���ⵥ")){
				sd = lsdDao.getLssd();
			}
			
			//���ⵥ��Ӧ�����۵���Ʒ��ϸ
			List xsdProducts = new ArrayList();
			if(ckdProducts != null && ckdProducts.size()>0){
				for(int i=0;i<ckdProducts.size();i++){
					CkdProduct cdkProduct = (CkdProduct)ckdProducts.get(i);
					if(cdkProduct != null && !cdkProduct.getProduct_id().equals("")){
						XsdProduct xsdProduct = (XsdProduct)xsdDao.getXsdProductInfo(ckd.getXsd_id(), cdkProduct.getProduct_id());
						Product product = (Product)productDao.getProductById(cdkProduct.getProduct_id());
						
						//��д���۶�������ʱ�ĳɱ��۵���Ϣ
						xsdProduct.setCbj(product.getPrice());     //�ɱ���
						xsdProduct.setKh_cbj(product.getKhcbj());  //���˳ɱ���
						xsdProduct.setYgcbj(product.getYgcbj());   //Ԥ���ɱ���
						xsdProduct.setGf(product.getGf());         //����(������ɱ)
						xsdProduct.setBasic_ratio(basic_ratio);    //�������
						xsdProduct.setOut_ratio(out_ratio);        //�������
						xsdProduct.setSd(sd);
						xsdProduct.setSfcytc(product.getSfcytc());  //�Ƿ�������
						
						//����˰���۵��������޼�ʱ ��ɱ��Ҫ���Ա���
						double ds = product.getDss();
						if((xsdProduct.getPrice()/ (1 + sd/100)) < product.getLsxj()){
							ds = ds * ds_ratio/100;
						}
						xsdProduct.setDs(ds);                     //����ɱ
						
						xsdProducts.add(xsdProduct);
						
						cdkProduct.setCbj(product.getPrice());  //�ɱ���ȡ��ǰ���ĳɱ���
						if(xsdProduct != null){
							cdkProduct.setPrice(xsdProduct.getPrice());  //���ۼ۸�ȡ���۶����൱�۸�
							cdkProduct.setJgtz(xsdProduct.getJgtz());
						}else{
							cdkProduct.setPrice(0);
							cdkProduct.setJgtz(0);
						}
					}
				}
			}
			
			//�޸Ŀ��
			this.updateProductKc(ckd, ckdProducts); 
			
			//ϵͳ��ʽ����ǰ������ǿ�����к������ƣ�����������к�ϵͳͬ�����账��			
			this.updateSerialNum(ckd, ckdProducts);
			
			//�����Ӧ���۵�״̬
			this.updateXsdState(ckd.getXsd_id(), "�ѳ���",ckd.getYsfs(),ckd.getCx_tel(),ckd.getJob_no(),ckd.getSend_time(),ckd.getStore_id(),ckd.getFzr(),ckd.getCk_date());
				
			//�޸����۵���Ӧ�ĳɱ��ۼ����˳ɱ���
			if(xsdProducts != null && xsdProducts.size() > 0){
				xsdDao.updateXsdProducts(xsdProducts);
			}
			
			//�����Ӧ���۵����տ�����Ϊ�ֽ�,�����տ������,�����۽�����
			if(xsd.getSklx().equals("�ֽ�") && xsd.getSkje() != 0){
				
				//��������տ���Ϣ,�����˵���Ϣ
				this.saveXssk(xsd);//��������տ���Ϣ
				
				//�����˻����
				this.addAccountJe(xsd);//�����˻����
				
				//������ʽΪˢ��������POS����Ų�Ϊ�գ��Զ����������Ϣ
				if(xsd.getSkfs().equals("ˢ��") && !xsd.getPos_id().equals("")){
					this.saveQtzc(xsd);
				}
			}
			
			//������Ʒ������ϸ
			this.addProductSaleFlow(xsd, xsdProducts);
		}
	}

	
	
	/**
	 * ���ݳ��ⵥID��ȡ��Ʒ�б�
	 * @param ckd_id
	 * @return
	 */
	public List getCkdProducts(String ckd_id){
		return ckdDao.getCkdProducts(ckd_id);
	}
	
	
	/**
	 * ���ݳ��ⵥID��ȡ���ⵥ��Ϣ
	 * @param ckd_id
	 * @return
	 */
	public Object getCkd(String ckd_id){
		return ckdDao.getCkd(ckd_id);
	}
	
	
	/**
	 * ɾ�����ⵥ��Ϣ
	 * @param ckd_id
	 */
	public void delCkd(String ckd_id){
		ckdDao.delCkd(ckd_id);
	}
	
	
	/**
	 * ���ⵥ�˻ض�������
	 * @param ckd
	 */
	public void doTh(Ckd ckd){
		
		//ɾ������Ӧ���ⵥ
		ckdDao.delCkd(ckd.getCkd_id());
		
		String xsd_id = ckd.getXsd_id();
		
		//��������۵���
		if(xsd_id.indexOf("XS") != -1){
			
			//�޸����۵�״̬���˻ر��
			xsdDao.updateXsdAfterCkdTh(xsd_id, "�ѱ���", "1");
			
			//�鿴���۶����ύʱ���Ƿ���ֱ���տ�
			//��������ύʱ��ֱ���տ��Ҫɾ���տ���Ϣ��Ϣ
			Xssk xssk = xsskDao.getXsskByDeleteKey(xsd_id);	
			
			if(xssk != null){
				
				//ɾ�������տ���Ϣ
				xsskDao.delXssk(xssk.getId());
				
				//ɾ���˻���ˮ��Ϣ
				accountDzdDao.delDzd(xssk.getId());
				
				//���˻����
				accountsDao.updateAccountJe(xssk.getSkzh(), xssk.getSkje());
			}
		}
	}
	
	
	/**
	 * ���ݶ�Ӧ���۵���Ų鿴�Ƿ������Ӧ�ĳ��ⵥ
	 * @param xsd_id
	 * @return
	 */
	public boolean isCkdExist(String xsd_id){
		return ckdDao.isCkdExist(xsd_id);
	}
	
	
	/**
	 * �鿴���ⵥ�Ƿ�ɲ��������⡢�˻أ�
	 * @param ckd_id
	 * @return false:���ܲ�����true:�ܲ���
	 */
	public boolean isCkdSubmit(String ckd_id){
		Ckd ckd = (Ckd)ckdDao.getCkd(ckd_id);
		if(ckd == null){
			//���ⵥ�Ѿ������ڣ����ܲ���
			return false;
		}else{
			if(ckd.getState().equals("�ѳ���")){
				//���ⵥ�ѳ��⣬���ܲ���
				return false;
			}else{
				return true;
			}
		}
	}
	
	/**
	 * ���ⵥ�Ƿ��Ѿ�ɾ��
	 * @param ckd_id
	 * @return
	 */
	public boolean isCkdDel(String ckd_id) {
		return ckdDao.isCkdDel(ckd_id);
	}
	
	/**
	 * ȡ���пⷿ�б�
	 * @return
	 */
	public List getStoreList(){
		return storeDao.getAllStoreList();
	}
	
	
	/**
	 * ȡ���õĳ��ⵥ��
	 * @return
	 */
	public String updateCkdId(){
		return ckdDao.getCkdID();
	}
	
	
	/**
	 * �жϿ�����Ƿ����������Ҫ
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkKc(Ckd ckd,List ckdProducts){
		String msg = "";
		String store_id = ckd.getStore_id();
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getProduct_name().equals("")){
						String product_id = ckdProduct.getProduct_id();
						
						//�ж���Ʒ�Ƿ��ǿ����Ʒ,ֻ�Կ����Ʒ�Ž��п�������ж�
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("�����Ʒ")){	
							int cknums = ckdProduct.getNums();  //Ҫ��������
							int kcnums = productKcDao.getKcNums(product_id, store_id);//�������
							
							if(cknums>kcnums){
								msg += ckdProduct.getProduct_name() + " ��ǰ���Ϊ��" + kcnums + "  �޷����⣬���ȵ���\n";
							}
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	/**
	 * �ж����к��Ƿ����������Ҫ
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkXlh(Ckd ckd,List ckdProducts){
		String msg = "";
		String store_id = ckd.getStore_id();
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getProduct_name().equals("") && ckdProduct.getQz_flag().equals("��")){
						String product_id = ckdProduct.getProduct_id();
						String[] arryNums = (ckdProduct.getQz_serial_num()).split(",");
						
						//�ж���Ʒ�Ƿ��ǿ����Ʒ,ֻ�Կ����Ʒ�Ž���ǿ�����к��ж�
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("�����Ʒ")){	
							for(int k=0;k<arryNums.length;k++){
							  String serialNum = arryNums[k];  //Ҫ��������к�
							  boolean is_store = serialNumDao.getSerialNumState(product_id, store_id,serialNum);//���кŵ�״̬
							
							  if(is_store){								
							  }
							  else
							  {
								  msg += ckdProduct.getProduct_name() + " ���к�Ϊ��" + serialNum + " �����ڣ���ȷ�Ϻ��ٽ��г��⴦��\n";
							  }
							}
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	/**
	 * ���������տ���Ϣ
	 * @param lsd
	 */
	private void saveXssk(Xsd xsd){
		Xssk xssk = new Xssk();
		
		String xssk_id = xsskDao.getXsskID();
		xssk.setId(xssk_id);
		xssk.setSk_date(xsd.getCreatdate());
		xssk.setClient_name(xsd.getClient_name());
		xssk.setJsr(xsd.getFzr());
		xssk.setSkzh(xsd.getSkzh());
		xssk.setSkje(xsd.getSkje());
		xssk.setState("���ύ");
		xssk.setCzr(xsd.getCzr());
		xssk.setRemark("�����տ���۵���� [" + xsd.getId() + "]");
		xssk.setDelete_key(xsd.getId());
		
		List<XsskDesc> xsskDescs = new ArrayList<XsskDesc>();
		
		XsskDesc xsskDesc = new XsskDesc();
		xsskDesc.setXsd_id(xsd.getId());
		xsskDesc.setXssk_id(xssk_id);
		xsskDesc.setBcsk(xsd.getSkje());
		xsskDesc.setRemark("�����տ���۵���� [" + xsd.getId() + "]");
		
		xsskDescs.add(xsskDesc);
		
		xsskDao.saveXssk(xssk, xsskDescs);
		
		//������˵���Ϣ
		this.saveAccountDzd(xssk.getSkzh(), xssk.getSkje(), xssk.getCzr(), xssk.getJsr(), "�����տ���[" + xssk.getId() + "]",xssk.getId());
	}
	
	/**
	 * ���տ������˻���
	 * @param lsd
	 */
	private void addAccountJe(Xsd xsd){
		String account_id = xsd.getSkzh();
		double je = xsd.getSkje();
		
		accountsDao.addAccountJe(account_id, je);
	}
	
	/**
	 * ����ʽ��׼�¼
	 * @param cgfk
	 */
	private void saveAccountDzd(String account_id,double jyje,String czr,String jsr,String remark,String xssk_id){
		AccountDzd accountDzd = new AccountDzd();
		
		double zhye = 0;
		Map map = accountsDao.getAccounts(account_id);
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}
		
		accountDzd.setAccount_id(account_id);
		accountDzd.setJyje(jyje);
		accountDzd.setZhye(zhye + jyje);
		accountDzd.setRemark(remark);
		accountDzd.setCzr(czr);
		accountDzd.setJsr(jsr);
		accountDzd.setAction_url("viewXssk.html?id=" + xssk_id);
		
		accountDzdDao.addDzd(accountDzd);
	}
	/**
	 * �������۵���Ϣ���ˢ��֧������
	 * @param xsd
	 */
	private void saveQtzc(Xsd xsd){
		Qtzc qtzc = new Qtzc();
		
		String id = qtzcDao.getQtzcID();
		
		String dept = "";
		if(!StringUtils.nullToStr(xsd.getFzr()).equals("")){
			dept = ((SysUser)userDao.getUser(xsd.getFzr())).getDept();
		}
		
		qtzc.setId(id);
		qtzc.setZc_date(xsd.getCreatdate());
		qtzc.setType("02");
		qtzc.setZcje(posTypeDao.getBrushCardfy(xsd.getPos_id(), xsd.getSkje()));
		qtzc.setZczh(xsd.getSkzh());
		qtzc.setJsr(xsd.getFzr());
		qtzc.setRemark("ˢ�������ѣ������۵�[" + xsd.getId() + "]�Զ�����");
		qtzc.setCzr(xsd.getCzr());
		qtzc.setState("���ύ");
		qtzc.setYwy(xsd.getFzr());
		qtzc.setSqr(xsd.getFzr());
		qtzc.setYwy_dept(dept);
		qtzc.setZcxm("ˢ��������");
		qtzc.setFklx("ˢ��");
		qtzc.setFysq_id("��");
		
		qtzcDao.saveQtzc(qtzc);  //��������֧����һ����ã�
		
		accountsDao.updateAccountJe(xsd.getSkzh(),posTypeDao.getBrushCardfy(xsd.getPos_id(), xsd.getSkje())); //�޸��˻����
		
		double jyje = 0 - posTypeDao.getBrushCardfy(xsd.getPos_id(), xsd.getSkje());
		AccountDzd accountDzd = new AccountDzd();
		accountDzd.setAccount_id(xsd.getSkzh());
		accountDzd.setJyje(jyje);		
		double zhye = 0;
		Map map = accountsDao.getAccounts(xsd.getSkzh());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}		
		accountDzd.setZhye(zhye + jyje);
		accountDzd.setRemark("һ����ã����[" + id + "]");
		accountDzd.setCzr(xsd.getCzr());
		accountDzd.setJsr(xsd.getFzr());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //����ʽ��׼�¼
		
	}
	
	
	
	/**
	 * ������Ʒ���
	 * @param lsd
	 * @param lsdProducts
	 */
	private void updateProductKc(Ckd ckd,List ckdProducts){
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getProduct_name().equals("")){
						productKcDao.updateProductKc(ckd.getStore_id(),ckdProduct.getProduct_id(), ckdProduct.getNums());
					}
				}
			}
		}
	}
	
	
	/**
	 * ������Ӧ���۵�״̬
	 * @param xsd_id     ���۵����
	 * @param state      ״̬
	 * @param ysfs       ���䷽ʽ
	 * @param cx_tel     ��ѯ�绰
	 * @param job_no     ������
	 * @param store_id   �����ⷿ
	 * @param ck_jsr     ���⾭����
	 * @param send_time  ����ʱ��
	 */
	private void updateXsdState(String xsd_id,String state,String ysfs,String cx_tel,String job_no,String send_time,String store_id,String ck_jsr,String ck_date){
		xsdDao.updateXsdState(xsd_id, state,ysfs,cx_tel,job_no,send_time, store_id, ck_jsr,ck_date);
	}
	
	
	/**
	 * <p>�������к�</p>
	 * <p>�����������</p>
	 * <p>һ�����۳��⣻�����ɹ��˻�����</p>
	 * <p></p>
	 * @param ckd
	 * @param ckdProducts
	 */
	private void updateSerialNum(Ckd ckd,List ckdProducts){
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getQz_serial_num().equals("")){
						String[] arryNums = (ckdProduct.getQz_serial_num()).split(",");
						
						SerialNumMng serialNumMng = new SerialNumMng();
						SerialNumFlow serialNumFlow = new SerialNumFlow();
						
						String state = "";
						String yw_url = "";
						String yw_type = "";
						String xsd_id = ckd.getXsd_id();
						if(xsd_id.indexOf("XS") != -1){
							state = "����";
							yw_url = "viewXsd.html?id=";
							yw_type = "����";
						}else{
							state = "���˻�";
							yw_url = "viewCgthd.html?id=";
							yw_type = "�ɹ��˻�";
						}
						
						for(int k=0;k<arryNums.length;k++){
							serialNumMng.setProduct_id(ckdProduct.getProduct_id());
							serialNumMng.setProduct_name(ckdProduct.getProduct_name());
							serialNumMng.setProduct_xh(ckdProduct.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState(state);
							serialNumMng.setStore_id("");
							serialNumMng.setYj_flag("0");
							
							serialNumDao.updateSerialNumState(serialNumMng); //�������к�״̬
							
							serialNumFlow.setClient_name(StaticParamDo.getClientNameById(ckd.getClient_name()));
							serialNumFlow.setTel(ckd.getTel());
							serialNumFlow.setCzr(ckd.getCzr());							
							serialNumFlow.setYwtype(yw_type);							
							serialNumFlow.setFs_date(ckd.getCk_date());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(ckd.getXsry()));
							serialNumFlow.setKf_dj_id(ckd.getCkd_id());
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewCkd.html?ckd_id=");
							serialNumFlow.setYw_dj_id(ckd.getXsd_id());							
							serialNumFlow.setYw_url(yw_url);
							serialNumFlow.setXsdj(ckdProduct.getPrice() + ckdProduct.getJgtz());
							
							serialNumDao.saveSerialFlow(serialNumFlow);  //�������к���ת����
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * �������۶���������Ӧ����Ʒ������ˮ��Ϣ
	 * @param xsd
	 * @param xsdProducts
	 */
	public void addProductSaleFlow(Xsd xsd, List xsdProducts){
		if(xsdProducts != null && xsdProducts.size()>0){
			for(int i=0;i<xsdProducts.size();i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
				if(xsdProduct != null && !xsdProduct.getProduct_id().equals("") && !xsdProduct.getProduct_name().equals("")){
					ProductSaleFlow info = new ProductSaleFlow();
					
					info.setId(xsd.getId());
					info.setYw_type("���۵�");
					info.setClient_name(xsd.getClient_name());
					info.setXsry(xsd.getFzr());
					info.setCz_date(DateComFunc.getToday());
					info.setProduct_id(xsdProduct.getProduct_id());
					info.setNums(xsdProduct.getNums());
					info.setPrice(xsdProduct.getPrice());
					info.setHjje(xsdProduct.getSjcj_xj());
					info.setDwcb(xsdProduct.getCbj());
					info.setCb(xsdProduct.getCbj()*xsdProduct.getNums());
					info.setDwkhcb(xsdProduct.getKh_cbj());
					info.setKhcb(xsdProduct.getKh_cbj()*xsdProduct.getNums());
					info.setDwygcb(xsdProduct.getYgcbj());
					info.setYgcb(xsdProduct.getYgcbj()*xsdProduct.getNums());
					info.setSd(xsdProduct.getSd());
					info.setBhsje(xsdProduct.getSjcj_xj() / (1 + xsdProduct.getSd()/100));
					info.setGf(xsdProduct.getGf());
					info.setDs(xsdProduct.getDs() * xsdProduct.getNums());
					info.setBasic_ratio(xsdProduct.getBasic_ratio());
					info.setOut_ratio(xsdProduct.getOut_ratio());
					info.setLsxj(xsdProduct.getLsxj() * xsdProduct.getNums());
					info.setSfcytc(xsdProduct.getSfcytc());
					
					productSaleFlowDao.insertProductSaleFlow(info);
				}
			}
		}
	}
	
	
	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
	}
	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}

	public CkdDAO getCkdDao() {
		return ckdDao;
	}
	public StoreDAO getStoreDao() {
		return storeDao;
	}
	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}
	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}
	public XsdDAO getXsdDao() {
		return xsdDao;
	}
	public void setXsdDao(XsdDAO xsdDao) {
		this.xsdDao = xsdDao;
	}
	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}
	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}
	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}
	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}



	public CgthdDAO getCgthdDao() {
		return cgthdDao;
	}



	public void setCgthdDao(CgthdDAO cgthdDao) {
		this.cgthdDao = cgthdDao;
	}



	public XsskDAO getXsskDao() {
		return xsskDao;
	}



	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
	}



	public AccountDzdDAO getAccountDzdDao() {
		return accountDzdDao;
	}



	public void setAccountDzdDao(AccountDzdDAO accountDzdDao) {
		this.accountDzdDao = accountDzdDao;
	}



	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}



	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}



	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}



	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
	}



	public YufukDAO getYufukDao() {
		return yufukDao;
	}



	public void setYufukDao(YufukDAO yufukDao) {
		this.yufukDao = yufukDao;
	}



	public ProductDAO getProductDao() {
		return productDao;
	}



	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}



	public LsdDAO getLsdDao() {
		return lsdDao;
	}



	public void setLsdDao(LsdDAO lsdDao) {
		this.lsdDao = lsdDao;
	}



	public ProductSaleFlowDAO getProductSaleFlowDao() {
		return productSaleFlowDao;
	}



	public void setProductSaleFlowDao(ProductSaleFlowDAO productSaleFlowDao) {
		this.productSaleFlowDao = productSaleFlowDao;
	}



	public UserDAO getUserDao() {
		return userDao;
	}



	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}



	public PosTypeDAO getPosTypeDao() {
		return posTypeDao;
	}



	public void setPosTypeDao(PosTypeDAO posTypeDao) {
		this.posTypeDao = posTypeDao;
	}



	public QtzcDAO getQtzcDao() {
		return qtzcDao;
	}



	public void setQtzcDao(QtzcDAO qtzcDao) {
		this.qtzcDao = qtzcDao;
	}

}
