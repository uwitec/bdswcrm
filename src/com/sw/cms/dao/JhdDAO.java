package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.JhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Clients;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.StoreHouse;
import com.sw.cms.util.DateComFunc;

public class JhdDAO extends JdbcBaseDAO {

	
	/**
	 * ȡ�������б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getJhdList(String con,int curPage, int rowsPerPage){
		String sql = "select * from jhd where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;		
		}
				
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Jhd.class));
	}
	
	
	/**
	 * ���ݽ�����IDȡ��������ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Object getJhd(String id){
		String sql = "select * from jhd where id='" + id + "'";
		
		return this.queryForObject(sql,new BeanRowMapper(Jhd.class));
	}
	
	/**
	 * ���ݽ�����IDȡ��Ӧ�̵���ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Object getClient(String id){
		String sql = "select a.* from clients a inner join jhd b on a.id=b.gysbh  where b.id='" + id + "'";
		
		return this.queryForObject(sql,new BeanRowMapper(Clients.class));
	}
	
	/**
	 * ���ݽ�����IDȡ��Ӧ����ϵ�˵���ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Object getClientsLinkman(String id){
		String sql = "select c.* from  clients_linkman c inner join clients a on a.id=c.clients_id inner join jhd b on a.id=b.gysbh  where b.id='" + id + "' and c.lx='����ϵ��'";
		
		return this.queryForObject(sql,new BeanRowMapper(ClientsLinkman.class));
	}
	
	/**
	 * ���ݽ�����IDȡ�ֿ����ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Object getStoreHouse(String id){
		String sql = "select a.* from  storehouse a inner join jhd b on a.id=b.store_id  where b.id='" + id + "'";
		
		return this.queryForObject(sql,new BeanRowMapper(StoreHouse.class));
	}
	
	/**
	 * ���涨������Ϣ
	 * @param jhd
	 * @return
	 */
	public void saveJhd(Jhd jhd,List jhdProducts){
		String sql = "insert into jhd(id,gysbh,cg_date,state,fzr,ms,shuil,tzje,total,gysmc,czr,cz_date,fkje,fklx,yfrq,fkfs,yfje,store_id,zq,fkzh,sjcjje,yjdhsj"
				+",kh_address,kh_lxr,kh_lxdh,ysws,fpstate) "
		        +"values(?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?"
				+",?,?,?,?,'δ���')";
		
		Object[] param = new Object[25];
		
		double total = jhd.getTotal();
		double fkje = jhd.getFkje();
		
		param[0] = jhd.getId();
		param[1] = jhd.getGysbh();
		param[2] = jhd.getCg_date();
		param[3] = jhd.getState();
		param[4] = jhd.getFzr();
		param[5] = jhd.getMs();
		param[6] = jhd.getShuil();
		param[7] = jhd.getTzje();
		param[8] = total;
		param[9] = jhd.getGysmc();
		param[10] = jhd.getCzr();
		param[11] = fkje;
		param[12] = jhd.getFklx();
		param[13] = jhd.getYfrq();
		param[14] = jhd.getFkfs();
		param[15] = jhd.getYfje();
		param[16] = jhd.getStore_id();
		param[17] = jhd.getZq();
		param[18] = jhd.getFkzh();
		param[19] = jhd.getTotal();
		param[20] = jhd.getYjdhsj();
		
		param[21] = jhd.getKh_address();
		param[22] = jhd.getKh_lxr();
		param[23] = jhd.getKh_lxdh();
		param[24] = jhd.getYsws();
		
		this.getJdbcTemplate().update(sql, param); //��ӽ�������Ϣ		
		this.addJhdProduct(jhdProducts,jhd.getId());           //��ӽ�������������Ʒ

	}
	
	
	
	/**
	 * �޸Ķ�������Ϣ
	 * @param jhd
	 * @return
	 */
	public void updateJhd(Jhd jhd,List jhdProducts){
		String sql = "update jhd set gysbh=?,cg_date=?,state=?,fzr=?,ms=?,shuil=?,tzje=?,total=?,gysmc=?,czr=?," +
				"cz_date=now(),fkje=?,fklx=?,yfrq=?,fkfs=?,yfje=?,store_id=?,zq=?,fkzh=?,sjcjje=?,"
		        +"kh_address=?,kh_lxr=?,kh_lxdh=?,ysws=?,fpstate='δ���' where id=?";
		
		Object[] param = new Object[24];
		
		double total = jhd.getTotal();
		double fkje = jhd.getFkje();
		
		param[0] = jhd.getGysbh();
		param[1] = jhd.getCg_date();
		param[2] = jhd.getState();
		param[3] = jhd.getFzr();
		param[4] = jhd.getMs();
		param[5] = jhd.getShuil();
		param[6] = jhd.getTzje();
		param[7] = total;
		param[8] = jhd.getGysmc();
		param[9] = jhd.getCzr();
		param[10] = fkje; 
		param[11] = jhd.getFklx();		
		param[12] = jhd.getYfrq();
		param[13] = jhd.getFkfs();
		param[14] = jhd.getYfje();
		param[15] = jhd.getStore_id();
		param[16] = jhd.getZq();
		param[17] = jhd.getFkzh();
		param[18] = jhd.getSjcjje();
		
		param[19] = jhd.getKh_address();
		param[20] = jhd.getKh_lxr();
		param[21] = jhd.getKh_lxdh();
		param[22] = jhd.getYsws();
		param[23] = jhd.getId();
		
		
		this.getJdbcTemplate().update(sql, param);	 //�޸Ľ�������Ϣ	
		
		//�޸��˻ر�־
		if(!jhd.getState().equals("�ѱ���")){
			sql = "update jhd set th_flag='0' where id='" + jhd.getId() + "'";
			this.getJdbcTemplate().update(sql);
		}
		
		this.delJhdProduct(jhd.getId());             //ɾ��������������Ʒ
		this.addJhdProduct(jhdProducts,jhd.getId()); //��ӽ������µĹ�����Ʒ
		
	}
	
	
	/**
	 * ���ݽ�������ȡ����������Ʒ
	 * @param jhd_id
	 * @return
	 */
	public List getJhdProducts(String jhd_id){
		String sql = "select a.*,b.qz_serial_num as qz_flag,b.dw from jhd_product a left join product b on b.product_id=a.product_id where jhd_id='" + jhd_id + "'";
		
		return this.getJdbcTemplate().query(sql, new BeanRowMapper(JhdProduct.class));
	}
	
	
	
	/**
	 * ɾ����������Ϣ
	 * @param id
	 * @return
	 */
	public void delJhd(String id){
		String sql = "delete from jhd where id='" + id + "'";
		
		this.getJdbcTemplate().update(sql); //ɾ����������Ϣ
		this.delJhdProduct(id);             //ɾ��������������Ʒ
	}
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getJhdID() {
		String sql = "select jhdid from cms_all_seq";
		
		String day = DateComFunc.getCurDay();

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// �����кż�1
		sql = "update cms_all_seq set jhdid=jhdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "JH" + day + "-" + curId;
	}
	
	
	
	/**
	 * ���ݿͻ�����ȡ�ͻ�Ӧ�����б�
	 * @param client_id
	 * @return
	 */
	public List getYfkListByClientId(String client_id){
		String sql = "select * from jhd where state='���ύ' and fklx<>'�Ѹ�' and gysbh='" + client_id + "' order by cg_date";
		
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	
	/**
	 * ���½������������ͼ�������
	 * @param id
	 * @param fklx
	 * @param fkje
	 */
	public void updateJhdFklxAndFkje(String id,String fklx,double fkje){
		String sql = "update jhd set fklx='" + fklx  +  "',fkje=" + fkje + " where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ���½�����״̬
	 * @param id
	 * @param state
	 */
	public void updateJhdState(String id,String state){
		String sql = "update jhd set state='" + state + "',cz_date=now() where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��ⵥ�˻غ��޸Ľ�����״̬
	 * @param id
	 * @param state
	 * @param th_flag
	 */
	public void updateJhdAfterRkdTh(String id,String state,String th_flag){
		String sql = "update jhd set state='" + state + "',th_flag='" + th_flag + "' where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��ӽ�������Ʒ
	 * @param jhdProducts
	 */
	private void addJhdProduct(List jhdProducts,String jhd_id){
		String sql = "";
		Object[] param = new Object[8];
		if(jhdProducts != null && jhdProducts.size()>0){
			for(int i=0;i<jhdProducts.size();i++){
				
				JhdProduct jhdProduct = (JhdProduct)jhdProducts.get(i);
				if(jhdProduct != null){
					if(jhdProduct.getProduct_name() != null && !jhdProduct.getProduct_name().equals("")){
						sql = "insert into jhd_product(jhd_id,product_id,product_xh,product_name,price,nums,remark,qz_serial_num) values(?,?,?,?,?,?,?,?)";
						
						param[0] = jhd_id;
						param[1] = jhdProduct.getProduct_id();
						param[2] = jhdProduct.getProduct_xh();
						param[3] = jhdProduct.getProduct_name();
						param[4] = new Double(jhdProduct.getPrice());
						param[5] = new Integer(jhdProduct.getNums());
						param[6] = jhdProduct.getRemark();
						param[7] = jhdProduct.getQz_serial_num();
						
						this.getJdbcTemplate().update(sql, param);
					}
				}
			}
		}
	}
	
	
	
	/**
	 * ���²ɹ�����ʵ�ʳɽ����
	 * @param xsd_id
	 * @param sjcjje
	 */
	public void updateJhdSjcjje(String jhd_id,double sjcjje){
		String sql = "update jhd set sjcjje=" + sjcjje + " where id='" + jhd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ���²ɹ�������Ʒʵ�ʳɽ���
	 * @param xsd_id
	 * @param product_id
	 * @param nums
	 * @param sjcj_xj
	 */
	public void updateJhdSjcjNums(String jhd_id,String product_id,int nums){
		String sql = "update jhd_product set sjcj_nums=" + nums + " where jhd_id='" + jhd_id + "' and product_id='" + product_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	
	
	/**
	 * ɾ��������������������Ʒ
	 * @param jhd_id
	 */
	private void delJhdProduct(String jhd_id){
		String sql = "delete from jhd_product where jhd_id='" + jhd_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ���ݽ�������Ų鿴�������Ƿ��Ѿ��ύ
	 * @param xsd_id
	 * @return
	 */
	public boolean isJhdSubmit(String jhd_id){
		boolean is = false;
		String sql = "select count(*) as counts from jhd where id='" + jhd_id + "' and state<>'�ѱ���'";
		
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
}
