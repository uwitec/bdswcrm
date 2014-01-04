package com.sw.cms.dao;

/**
 * 采购付款数据库操作
 * author by liyt
 * 2008-03-27
 */

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.CgfkDesc;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

public class CgfkDAO extends JdbcBaseDAO {
	
	
	/**
	 * 取采购应付款列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgfks(String con,int curPage, int rowsPerPage){
		String sql = "select a.*,b.name as gysmc from cgfk a left join clients b on b.id=a.gysbh  where 1=1";
		
		if(!con.equals("")){
			sql = sql + " " + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * 根据查询条件取所有满足条件的采购付款信息
	 * @param con
	 * @return
	 */
	public List getCgfks(String con){
		String sql = "select * from cgfk where 1=1";
		if(!con.equals("")){
			sql += con;
		}
		return this.getResultList(sql, new BeanRowMapper(Cgfk.class));
	}
	
	
	/**
	 * 根据采购付款ID取相关信息
	 * @param yfk_id
	 * @return
	 */
	public Object getCgfk(String id){
		String sql = "select * from cgfk where id='" + id + "'";
		return this.queryForObject(sql, new BeanRowMapper(Cgfk.class));
	}
	
	
	/**
	 * 根据采购付款ID取明细信息
	 * @param id
	 * @return
	 */
	public List getCgfkDesc(String id){
		String sql = "select jhd_id,fsrq,fsje,yfje,bcfk,remark from cgfk_desc where cgfk_id='" + id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据采购付款ID取明细信息
	 * @param id
	 * @return
	 */
	public List getCgfkDescObj(String id){
		String sql = "select * from cgfk_desc where cgfk_id='" + id + "'";
		return this.getResultList(sql,  new BeanRowMapper(CgfkDesc.class));
	}
	
	
	/**
	 * 根据供应商编号取所有应付款情况
	 * 包括：采购订单、期初应付、往来调账(应付)
	 * @param gysbh
	 * @return
	 */
	public List getDfkDesc(String gysbh){
		String sql = "select id as jhd_id,cg_date as fsrq,total as fsje,(total-fkje) as yfje,fkje as yifje from jhd where state='已入库' and fklx<>'已付' and gysbh='" + gysbh + "' order by id";
		
		//处理应收期初信息,如果存在期初值，则需要UNION期初值
		String init_sql = "select '期初应付' as jhd_id,DATE_FORMAT(cz_date,'%Y-%m-%d') as fsrq,yfqc as fsje,(yfqc-yifuje) as yfje,yifuje as yisk from client_wl_init where client_name='" + gysbh + "' and round(yfqc,2)<>round(yifuje,2)";
		List list = this.getResultList(init_sql);
		if(list != null && list.size()>0){
			sql = "(" + sql + ") union (" + init_sql + ")";
		}
		
		//处理应收期初信息,如果存在期初值，则需要UNION期初值
		String pz_sql = "select id as jhd_id,DATE_FORMAT(cz_date,'%Y-%m-%d') as fsrq,pzje as fsje,(pzje-jsje) as yfje,jsje as yisk from pz where state='已提交' and type='应付' and client_name='" + gysbh + "' and round(jsje,2)<>round(pzje,2)  order by id";
		List pzList = this.getResultList(pz_sql);
		if(pzList != null && pzList.size()>0){
			sql = "(" + sql + ") union (" + pz_sql + ")";
		}
		
		return this.getResultList(sql);
	}

	
	/**
	 * 删除采购付款信息根据采购付款ID
	 * @param jhd_id
	 * @return
	 */
	public void delCgfk(String id){
		String sql = "delete from cgfk where id='" + id + "'";		
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from cgfk_desc where cgfk_id='" + id + "'";		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 保存采购付款信息，包括明细信息
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void saveCgfk(Cgfk cgfk,List cgfkDescs){
		String sql = "insert into cgfk(id,fk_date,gysbh,fkzh,jsr,fkje,remark,state,is_yfk,yfk_ye,czr,cz_date,delete_key,client_all_name,bank_no,kh_lxr,fax,tel) values(?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?)";
		
		String is_yfk = StringUtils.nullToStr(cgfk.getIs_yfk());
		if(is_yfk.equals("")){
			is_yfk = "否";
		}
		
		double yfk_ye = 0;
		if(is_yfk.equals("是")){
			yfk_ye = cgfk.getFkje();
		}
		
		Object[] param = new Object[17];
		
		param[0] = cgfk.getId();
		param[1] = cgfk.getFk_date();
		param[2] = cgfk.getGysbh();
		param[3] = cgfk.getFkzh();
		param[4] = cgfk.getJsr();
		param[5] = new Double(cgfk.getFkje());
		param[6] = cgfk.getRemark();
		param[7] = cgfk.getState();
		param[8] = is_yfk;
		param[9] = new Double(yfk_ye);
		param[10] = cgfk.getCzr();
		param[11] = cgfk.getDelete_key();
		param[12] = cgfk.getClient_all_name();
		param[13] = cgfk.getBank_no();
		param[14] = cgfk.getKh_lxr();
		param[15] = cgfk.getFax();
		param[16] = cgfk.getTel();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.saveCgfkDesc(cgfkDescs, cgfk.getId());
	}
	
	
	/**
	 * 更新采购付款相关信息
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void updateCgfk(Cgfk cgfk,List cgfkDescs){
		String sql = "update cgfk set fk_date=?,gysbh=?,fkzh=?,jsr=?,fkje=?,remark=?,state=?,is_yfk=?,yfk_ye=?,czr=?,cz_date=now(),client_all_name=?,bank_no=?,kh_lxr=?,fax=?,tel=? where id=?";
		
		String is_yfk = StringUtils.nullToStr(cgfk.getIs_yfk());
		if(is_yfk.equals("")){
			is_yfk = "否";
		}
		
		double yfk_ye = 0;
		if(is_yfk.equals("是")){
			yfk_ye = cgfk.getFkje();
		}
		
		Object[] param = new Object[16];		
		
		param[0] = cgfk.getFk_date();
		param[1] = cgfk.getGysbh();
		param[2] = cgfk.getFkzh();
		param[3] = cgfk.getJsr();
		param[4] = new Double(cgfk.getFkje());
		param[5] = cgfk.getRemark();
		param[6] = cgfk.getState();
		param[7] = is_yfk;
		param[8] = new Double(yfk_ye);
		param[9] = cgfk.getCzr();
		param[10] = cgfk.getClient_all_name();
		param[11] = cgfk.getBank_no();
		param[12] = cgfk.getKh_lxr();
		param[13] = cgfk.getFax();
		param[14] = cgfk.getTel();
		param[15] = cgfk.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delCgfkDesc(cgfk.getId());
		
		this.saveCgfkDesc(cgfkDescs, cgfk.getId());
	}
	
	
	/**
	 * 更新采购付款申请单状态
	 * @param cgfk
	 */
	public void updateCgfkStat(Cgfk cgfk){
		String sql = "update cgfk set state=?,cz_date=now(),fkzh=? where id=?";
		
		Object[] param = new Object[3];
		param[0] = cgfk.getState();
		param[1] = cgfk.getFkzh();
		param[2] = cgfk.getId();
		
		this.getJdbcTemplate().update(sql, param);
	}
	
	
	/**
	 * 删除采购付款明细根据采购付款ID
	 * @param yfk_id
	 * @return
	 */
	private void delCgfkDesc(String id){
		String sql = "delete from cgfk_desc where cgfk_id='" + id + "'";		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 
	 * @param cgfkDescs
	 * @param cgfk_id
	 */
	private void saveCgfkDesc(List cgfkDescs,String cgfk_id){
		String sql = "";
		Object[] param = new Object[7];
		
		if(cgfkDescs != null && cgfkDescs.size()>0){
			for(int i =0;i<cgfkDescs.size();i++){
				
				CgfkDesc cgfkDesc = (CgfkDesc)cgfkDescs.get(i);
				if(cgfkDesc != null){
					if(cgfkDesc.getBcfk() != 0){
						sql = "insert into cgfk_desc(cgfk_id,jhd_id,bcfk,remark,fsrq,fsje,yfje) values(?,?,?,?,?,?,?)";
						
						param[0] = cgfk_id;
						param[1] = cgfkDesc.getJhd_id();
						param[2] = new Double(cgfkDesc.getBcfk());
						param[3] = cgfkDesc.getRemark();
						param[4] = cgfkDesc.getFsrq();
						param[5] = cgfkDesc.getFsje();
						param[6] = cgfkDesc.getYfje();
						
						this.getJdbcTemplate().update(sql,param); //添加付款明细
						
						
					}
				}
			}
		}
	}
	
	
	
	/**
	 * 更新进货单已付金额及付款状态
	 * @param cgfkDescs
	 */
	public void updateJhdFkje(Cgfk cgfk,List cgfkDescs){
		String sql = "";
		
		if(cgfkDescs != null && cgfkDescs.size()>0){
			for(int i =0;i<cgfkDescs.size();i++){
				
				CgfkDesc cgfkDesc = (CgfkDesc)cgfkDescs.get(i);
				if(cgfkDesc != null){
					if(cgfkDesc.getBcfk() != 0){
						if(cgfkDesc.getJhd_id().equals("期初应付")){
							//处理期初应收
							sql = "update client_wl_init set yifuje=yifuje+" + cgfkDesc.getBcfk() + "where client_name='" + cgfk.getGysbh() + "'";
							this.getJdbcTemplate().update(sql);
						}else if(cgfkDesc.getJhd_id().indexOf("PZ") != -1){
							//处理往来调账
							sql = "update pz set jsje=jsje+" + cgfkDesc.getBcfk() + " where id='" + cgfkDesc.getJhd_id() + "'";
							this.getJdbcTemplate().update(sql);
						}else{
							sql = "update jhd set fkje=fkje+" + cgfkDesc.getBcfk() + " where id='" + cgfkDesc.getJhd_id() + "'";
							this.getJdbcTemplate().update(sql);
							
							sql = "select count(1) as nums from jhd where total>fkje and id='" + cgfkDesc.getJhd_id() + "'";
							int count = this.getJdbcTemplate().queryForInt(sql);
							
							if(count > 0){
								sql = "update jhd set fklx='部分已付' where id='" + cgfkDesc.getJhd_id() + "'";
							}else{
								sql = "update jhd set fklx='已付' where id='" + cgfkDesc.getJhd_id() + "'";
							}
							this.getJdbcTemplate().update(sql);
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 判断采购付款是否已经提交
	 * @param id
	 * @return
	 */
	public boolean isCgfkSubmit(String id){
		boolean is = false;
		
		String sql = "select count(*) as nums from cgfk where id='" + id + "' and state<>'已保存' and state<>'审批不通过' and state<>'出纳退回'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		
		return is;
	}

	

	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getCgfkID() {
		String sql = "select cgyfkid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();
		
		// 将序列号加1
		sql = "update cms_all_seq set cgyfkid=cgyfkid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "FK" + day + "-" + curId;
	}
	
	
	/**
	 * 根据客户编号取所有预付款列表
	 * @param client_id
	 * @return
	 */
	public List getYfkListByClient(String client_id){
		String sql = "select * from cgfk where state='已提交' and is_yfk='是' and yfk_ye>0 and gysbh='" + client_id + "'";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 更新预付款余额
	 * @param id
	 * @param yfk_ye 
	 */
	public void updateYfkye(String id,double yfk_ye){
		String sql = "update cgfk set yfk_ye=" + yfk_ye + " where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据定义的删除关键字取采购付款
	 * @param delete_key
	 * @return
	 */
	public Cgfk getCgfkByDeleteKey(String delete_key){
		Cgfk cgfk = null;
		
		String sql = "select * from cgfk where delete_key='" + delete_key + "'";
		Object obj = this.queryForObject(sql,  new BeanRowMapper(Cgfk.class));
		
		if(obj != null){
			cgfk = (Cgfk)obj;
		}
		
		return cgfk;
	}
	
	
	/**
	 * 采购付款明细是否存在冲突(付款申请，预付冲应付)
	 * @param jhd_id
	 * @param gysbh
	 * @param cgfk_id
	 * @param bcfk
	 * @return
	 */
	public boolean isCgfkDescExist(String cgfk_id, String jhd_id,String gysbh,double bcfk){
		boolean is = false;
		
		double fkje=0;
		String sqlFkje="";
		String sql = "select count(1) as counts from cgfk_desc a join cgfk b on b.id=a.cgfk_id where b.state<>'已支付' and a.cgfk_id<>'" + cgfk_id + "' and a.jhd_id='" + jhd_id + "' and b.gysbh='" + gysbh + "'";
		int count1 = this.getJdbcTemplate().queryForInt(sql);
		
		sql = "select count(1) as count from yufu_to_yingfu_desc a join yufu_to_yingfu b on b.id=a.yw_id where b.state<>'已提交' and a.jhd_id='" + jhd_id + "' and client_name='" + gysbh + "'";
		int count2 = this.getJdbcTemplate().queryForInt(sql);
		
		if(jhd_id.equals("期初应付")){
			sqlFkje="select (yfqc-yifuje) as yfje from client_wl_init where client_name='" + gysbh + "'";	
			
		}else if(jhd_id.indexOf("PZ") != -1){
			sqlFkje="select (pzje-jsje) as yfje from pz where id='" + jhd_id + "'  and client_name='" + gysbh + "'";
			
		}else{
			sqlFkje="select (total-fkje) as yfje from jhd where  id='" + jhd_id + "' and  gysbh='" + gysbh + "'";				
		}
		
		Map map = this.getResultMap(sqlFkje);
		if(map!= null){
			fkje = map.get("yfje")==null?0:((Double)map.get("yfje")).doubleValue();;
		}
		
		fkje = (double)Math.round(fkje*100)/100;
		
		if((count1+count2) > 0){
			is = true;
		}
		
		if(bcfk>fkje){
			is = true;
		}
		
		return is;
	}

}
