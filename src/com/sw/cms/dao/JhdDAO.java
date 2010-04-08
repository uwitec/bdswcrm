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
	 * 取进货单列表（带分页）
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
	 * 根据进货单ID取进货单详细信息
	 * @param id
	 * @return
	 */
	public Object getJhd(String id){
		String sql = "select * from jhd where id='" + id + "'";
		
		return this.queryForObject(sql,new BeanRowMapper(Jhd.class));
	}
	
	/**
	 * 根据进货单ID取供应商的详细信息
	 * @param id
	 * @return
	 */
	public Object getClient(String id){
		String sql = "select a.* from clients a inner join jhd b on a.id=b.gysbh  where b.id='" + id + "'";
		
		return this.queryForObject(sql,new BeanRowMapper(Clients.class));
	}
	
	/**
	 * 根据进货单ID取供应商联系人的详细信息
	 * @param id
	 * @return
	 */
	public Object getClientsLinkman(String id){
		String sql = "select c.* from  clients_linkman c inner join clients a on a.id=c.clients_id inner join jhd b on a.id=b.gysbh  where b.id='" + id + "' and c.lx='主联系人'";
		
		return this.queryForObject(sql,new BeanRowMapper(ClientsLinkman.class));
	}
	
	/**
	 * 根据进货单ID取仓库的详细信息
	 * @param id
	 * @return
	 */
	public Object getStoreHouse(String id){
		String sql = "select a.* from  storehouse a inner join jhd b on a.id=b.store_id  where b.id='" + id + "'";
		
		return this.queryForObject(sql,new BeanRowMapper(StoreHouse.class));
	}
	
	/**
	 * 保存定货单信息
	 * @param jhd
	 * @return
	 */
	public void saveJhd(Jhd jhd,List jhdProducts){
		String sql = "insert into jhd(id,gysbh,cg_date,state,fzr,ms,shuil,tzje,total,gysmc,czr,cz_date,fkje,fklx,yfrq,fkfs,yfje,store_id,zq,fkzh,sjcjje,yjdhsj"
				+",kh_address,kh_lxr,kh_lxdh,ysws,fpstate) "
		        +"values(?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?"
				+",?,?,?,?,'未入库')";
		
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
		
		this.getJdbcTemplate().update(sql, param); //添加进货单信息		
		this.addJhdProduct(jhdProducts,jhd.getId());           //添加进货单关联的商品

	}
	
	
	
	/**
	 * 修改定货单信息
	 * @param jhd
	 * @return
	 */
	public void updateJhd(Jhd jhd,List jhdProducts){
		String sql = "update jhd set gysbh=?,cg_date=?,state=?,fzr=?,ms=?,shuil=?,tzje=?,total=?,gysmc=?,czr=?," +
				"cz_date=now(),fkje=?,fklx=?,yfrq=?,fkfs=?,yfje=?,store_id=?,zq=?,fkzh=?,sjcjje=?,"
		        +"kh_address=?,kh_lxr=?,kh_lxdh=?,ysws=?,fpstate='未入库' where id=?";
		
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
		
		
		this.getJdbcTemplate().update(sql, param);	 //修改进货单信息	
		
		//修改退回标志
		if(!jhd.getState().equals("已保存")){
			sql = "update jhd set th_flag='0' where id='" + jhd.getId() + "'";
			this.getJdbcTemplate().update(sql);
		}
		
		this.delJhdProduct(jhd.getId());             //删除进货单关联商品
		this.addJhdProduct(jhdProducts,jhd.getId()); //添加进货单新的关联商品
		
	}
	
	
	/**
	 * 根据进货单号取进货关联商品
	 * @param jhd_id
	 * @return
	 */
	public List getJhdProducts(String jhd_id){
		String sql = "select a.*,b.qz_serial_num as qz_flag,b.dw from jhd_product a left join product b on b.product_id=a.product_id where jhd_id='" + jhd_id + "'";
		
		return this.getJdbcTemplate().query(sql, new BeanRowMapper(JhdProduct.class));
	}
	
	
	
	/**
	 * 删除进货单信息
	 * @param id
	 * @return
	 */
	public void delJhd(String id){
		String sql = "delete from jhd where id='" + id + "'";
		
		this.getJdbcTemplate().update(sql); //删除进货单信息
		this.delJhdProduct(id);             //删除进货单关联商品
	}
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getJhdID() {
		String sql = "select jhdid from cms_all_seq";
		
		String day = DateComFunc.getCurDay();

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set jhdid=jhdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "JH" + day + "-" + curId;
	}
	
	
	
	/**
	 * 根据客户名称取客户应付款列表
	 * @param client_id
	 * @return
	 */
	public List getYfkListByClientId(String client_id){
		String sql = "select * from jhd where state='已提交' and fklx<>'已付' and gysbh='" + client_id + "' order by cg_date";
		
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	
	/**
	 * 更新进货单付款类型及付款金额
	 * @param id
	 * @param fklx
	 * @param fkje
	 */
	public void updateJhdFklxAndFkje(String id,String fklx,double fkje){
		String sql = "update jhd set fklx='" + fklx  +  "',fkje=" + fkje + " where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 更新进货单状态
	 * @param id
	 * @param state
	 */
	public void updateJhdState(String id,String state){
		String sql = "update jhd set state='" + state + "',cz_date=now() where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 入库单退回后修改进货单状态
	 * @param id
	 * @param state
	 * @param th_flag
	 */
	public void updateJhdAfterRkdTh(String id,String state,String th_flag){
		String sql = "update jhd set state='" + state + "',th_flag='" + th_flag + "' where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 添加进货单商品
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
	 * 更新采购订单实际成交金额
	 * @param xsd_id
	 * @param sjcjje
	 */
	public void updateJhdSjcjje(String jhd_id,double sjcjje){
		String sql = "update jhd set sjcjje=" + sjcjje + " where id='" + jhd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 更新采购订单商品实际成交数
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
	 * 删除进货单关联的所有商品
	 * @param jhd_id
	 */
	private void delJhdProduct(String jhd_id){
		String sql = "delete from jhd_product where jhd_id='" + jhd_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据进货单编号查看进货单是否已经提交
	 * @param xsd_id
	 * @return
	 */
	public boolean isJhdSubmit(String jhd_id){
		boolean is = false;
		String sql = "select count(*) as counts from jhd where id='" + jhd_id + "' and state<>'已保存'";
		
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
}
