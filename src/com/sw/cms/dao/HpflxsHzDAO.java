package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 货品分类销售汇总
 * @author liyt
 *
 */
public class HpflxsHzDAO extends JdbcBaseDAO {

	
	/**
	 * 按货品类别等级统计销售类别销售
	 * @param start_date
	 * @param end_date
	 * @param dj
	 * @return
	 */
	public List getHzResults(String start_date,String end_date,String xsry,String client_name,String dept,String[] xwType,int dj){
		
		String sql = "select c.product_kind,sum(nums) as nums,sum(hjje) as hjje from product_sale_flow a inner join sys_user b on b.user_id=a.xsry inner join product c on c.product_id=a.product_id where 1=1";
		
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'"; 
		}
		if(!xsry.equals("")){
			sql += " and a.xsry='" + xsry + "'";
		}
		if(!client_name.equals("")){
			sql += " and a.client_name='" + client_name + "'"; 
		}
		if(!dept.equals("")){
			sql += " and b.dept like '" + dept + "%'";
		}
		if(xwType == null || xwType.length == 0){
			return null;
		}else{
			sql += " and(";
			for(int i=0;i<xwType.length;i++){
				if(i==0){
					sql += "a.yw_type='" + xwType[i] + "'";
				}else{
					sql += " or a.yw_type='" + xwType[i] + "'";
				}
			}
			sql += ")";
		}
		sql += " group by c.product_kind";
		
		sql = "select y.id,y.name," +
				"(select sum(nums) from (" + sql + ") x where x.product_kind like concat(y.id,'%')) as nums," +
				"(select sum(hjje) from (" + sql + ") x where x.product_kind like concat(y.id,'%')) as hjje " +
				"from product_kind y where LENGTH(y.id)<=" + (dj*2);
		
		List list = this.getResultList(sql);
		
		return list;
	}
	
	
	
	/**
	 * 根据货品销售分类汇总明细信息
	 * @param product_kind   商品类别
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param client_name    客户名称
	 * @param xsry_id        销售人员
	 * @return
	 */
	public List getMxResults(String product_kind,String start_date,String end_date,String client_name,String dept,String xsry){
		
		String sql = "select a.product_id,c.product_name,c.product_xh,sum(a.nums) as nums,sum(a.hjje) as hjje from product_sale_flow a left join sys_user b on b.user_id=a.xsry left join product c on c.product_id=a.product_id where 1=1";
		
		//处理商品类别
		if(!product_kind.equals("")){
			sql += " and c.product_kind like '" + product_kind + "%'";
		}
		
		//开始时间
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		
		//结束时间
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'";
		}
		
		//客户编号
		if(!client_name.equals("")){
			sql += " and a.client_name='" + client_name + "'";
		}
		
		//销售人员
		if(!xsry.equals("")){
			sql += " and a.xsry='" + xsry + "'";
		}
		
		if(!dept.equals("")){
			sql += " and b.dept like '" + dept + "%'";
		}
		
		sql += " group by a.product_id,c.product_name,c.product_xh";
		
		return this.getResultList(sql);
	}
	
}
