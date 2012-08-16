package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.HykJfFlow;
import com.sw.cms.model.Hykda;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.Jfgz;

/**
 * ��Ա������ˮ��
 * @author liyt
 *
 */
public class HykJfFlowDAO extends JdbcBaseDAO {
	
	/**
	 * �����Ա���ֽ�����Ϣ
	 * @param hykJfFlow
	 */
	public void saveHykJfFlow(HykJfFlow hykJfFlow){
		String sql = "insert into hyk_jf_flow(hyk_id,yw_id,xfje,jf,cz_date,czr,jsr) values(?,?,?,?,now(),?,?)";
		this.update(sql, new Object[]{hykJfFlow.getHyk_id(),hykJfFlow.getYw_id(),hykJfFlow.getXfje(),hykJfFlow.getJf(),hykJfFlow.getCzr(),hykJfFlow.getJsr()});
	}
	
	
	/**
	 * ���ݻ�Ա�����ȡ���ֹ���
	 * @param hyk_id
	 * @return
	 */
	public Jfgz getJfgz(String hyk_id) {
		String sql = "SELECT a.* FROM jfgz a join hykfl b on b.jffs=a.id join hykzz c on c.ssfl=b.id where c.hykh=?";
		return (Jfgz)this.getResultObject(sql, new Object[]{hyk_id}, new BeanRowMapper(Jfgz.class));
	}
	
	
	/**
	 * ���ݻ�Ա����ż��Ƿ����ڲ�ѯ��Ա������ˮ
	 * @param start_date
	 * @param end_date
	 * @param con_vl
	 * @return
	 */
	public List getHyjfFlowList(String start_date,String end_date,String hyk_id){
		String sql = "SELECT * from hyk_jf_flow where 1=1";
		if(!hyk_id.equals("")){
			sql += " and(hyk_id='" + hyk_id + "')";
		}
		if(!start_date.equals("")){
			sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + end_date + "'";
		}
		return this.getResultList(sql, new BeanRowMapper(HykJfFlow.class));
	}
	
	/**
	 * ���ݻ�Ա���/�ֻ���ȡ��Ա��Ϣ�б�
	 * @param hyk_id
	 * @return
	 */
	public List getHykdaList(String con_vl){
		String sql = "select * from hykda where hykh='" + con_vl + "' or mobile='" + con_vl + "'";
		
		return this.getResultList(sql, new BeanRowMapper(Hykda.class));
	}
	
	/**
	 * ���ݻ�Ա����ȡ��Ա��������Ϣ����ʼ���֣�
	 */
	public Hykzz getHykzzInfo(String hyk_id){
		String sql = "select * from hykzz where hykh='" + hyk_id + "'";
		
		return (Hykzz)this.queryForObject(sql, new BeanRowMapper(Hykzz.class));
	}
	
	/**
	 * ����ʱ��μ���Ա�����ȡ����
	 * @param hyk_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getHyjf(String hyk_id,String start_date,String end_date){
		double result = 0l;
		String sql = "select sum(jf) as hjjf from hyk_jf_flow where hyk_id='" + hyk_id + "'";
		if(!start_date.equals("")){
			sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + end_date + "'";
		}
		Map tempMap = this.getResultMap(sql);
		
		if(tempMap != null){
			result = tempMap.get("hjjf")==null?0:((Double)tempMap.get("hjjf")).doubleValue();
		}
		
		return result;
	}

}
