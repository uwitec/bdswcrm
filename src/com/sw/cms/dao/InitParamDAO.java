package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

/**
 * ϵͳ��ʼ����
 * ÿ���0:0:30ִ��
 * @author liyt
 *
 */

public class InitParamDAO extends JdbcBaseDAO {
	
	/**
	 * ����ҵ����ز���Ϊ1
	 *
	 */
	public void updateParam(){
		String sql = "update cms_all_seq set  bxdid=1, jhdid=1,cgyfkid=1,rkdid=1,kcpdid=1,ckdid=1,xsdid=1,yskid=1,lsdid=1," +
				"pzid=1,thdid=1,dbsqid=1,kfdbid=1,cgthdid=1,lsyskid=1,chtjid=1,qtsrid=1,qtzcid=1,nbzzid=1,lsthdid=1,yushoutoyingshouid=1,yufutoyingfuid=1,fysqid=1,txfkid=1,txfkdescid=1,jjdid=1,bxfhdid=1,fhkhdid=1,wxrkdid=1,sfdid=1,pgdid=1,wxcldid=1,ykrkid=1,ykckid=1";
		this.getJdbcTemplate().update(sql);
		log.info("��ʼ��ҵ������ɹ���");
	}
	
	
	/**
	 * �����˻��ڳ�
	 *
	 */
	public void genAccountQc(){
		String qc_date = DateComFunc.getToday();
		
		String sql = "delete from account_qc where qc_date='" + qc_date + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into account_qc (select 0 as seq_id,id,'" + qc_date + "' as qc_date,dqje from accounts)";
		this.getJdbcTemplate().update(sql);
		
		log.info("�����˺��ڳ��ɹ�");
	}
	
	
	/**
	 * ���ɿ���ڳ�
	 * �����ʣ����Ϊ������ڳ����
	 *
	 */
	public void genKcQc(){
		String qc_date = DateComFunc.getToday();
		String sql = "delete from product_kc_qc where cdate='" + qc_date + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into product_kc_qc (select 0 as seq_id,a.product_id,a.store_id,a.nums,'" + qc_date + "' as cdate,b.price as price from product_kc a left join product b on b.product_id=a.product_id)";
		this.getJdbcTemplate().update(sql);
		
		log.info("���ɿ���ڳ��ɹ�");
	}
	
	/**
	 * ���ɿͻ������ڳ�
	 * ������ڳ���������������ڽ�����ڳ�
	 *
	 */
	public void genYsQc(){
		String today = DateComFunc.getToday();
		
		List clientList = getClientList();
		if(clientList != null && clientList.size()>0){
			for(int i=0;i<clientList.size();i++){
				Map map = (Map)clientList.get(i);
				String client_name = StringUtils.nullToStr(map.get("id"));
				this.insertQc(client_name, this.getQcys(client_name), this.getQcyf(client_name), today);
			}
		}
		log.info("����Ӧ���ڳ��ɹ�");
	}
	
	
	private void insertQc(String client_name,double ysqc,double yfqc,String cdate){
		this.delQc(client_name, cdate);
		String sql = "insert into client_qc(client_name,ysqc,yfqc,cdate) values(?,?,?,?)";
		
		Object[] param = new Object[4];
		
		param[0] = client_name;
		param[1] = ysqc;
		param[2] = yfqc;
		param[3] = cdate;
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	private void delQc(String client_name,String cdate){
		String sql = "delete from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ�����ڳ�Ӧ��
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	private double getQcys(String client_name){
		String cdate = DateComFunc.getYestoday();
		double qcys = 0;
		
		//�����ڳ�Ӧ�� = �����ڳ�Ӧ�� + ���շ���Ӧ�� - ��������
		qcys = this.getClientYsqc(client_name, cdate) + this.getSjysje(client_name, cdate) - this.getSjyishouje(client_name, cdate);
		
		return qcys;
	}
	
	
	/**
	 * ȡ�ͻ�Ӧ�յ�ǰӦ��
	 * @param client_name �ͻ����
	 * @return
	 */
	public double getClientYinshou(String client_name){
		String cdate = DateComFunc.getToday();
		return this.getClientYsqc(client_name, cdate) + this.getSjysje(client_name, cdate) - this.getSjyishouje(client_name, cdate);
	}
	
	
	/**
	 * ȡ�ͻ���ǰӦ��
	 * @param client_name �ͻ����
	 * @return
	 */
	public double getClientYinfu(String client_name){
		String cdate = DateComFunc.getToday();
		return this.getClientYfqc(client_name, cdate) + this.getSjyfje(client_name, cdate) - this.getSjyifuje(client_name, cdate);
	}
	
	
	private double getQcyf(String client_name){
		String cdate = DateComFunc.getYestoday();
		double qcyf = 0;
		
		//�����ڳ�Ӧ�� = �����ڳ�Ӧ�� + ���շ���Ӧ�� - �����Ѹ�
		qcyf = this.getClientYfqc(client_name, cdate) + this.getSjyfje(client_name, cdate) - this.getSjyifuje(client_name, cdate);
		
		return qcyf;
	}
	
	
	/**
	 * �������������ͻ��б�
	 * @return
	 */
	private List getClientList(){
		String sql = "select * from clients";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ���ݿͻ���ż�����ȡ�ڳ�Ӧ�ն�
	 * @param client_name
	 * @param cdate
	 * @return  �ڳ�Ӧ�ն���޷���0
	 */
	private double getClientYsqc(String client_name,String cdate){
		double ysqc = 0;
		
		String sql = "select ysqc from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			ysqc = map.get("ysqc")==null?0:((Double)map.get("ysqc")).doubleValue();
		}
		
		return ysqc;
	}
	
	
	/**
	 * ���ݿͻ���ż�����ȡ�ڳ�Ӧ����
	 * @param client_name
	 * @param cdate
	 * @return  �ڳ�Ӧ������޷���0
	 */
	private double getClientYfqc(String client_name,String cdate){
		double yfqc = 0;
		
		String sql = "select yfqc from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			yfqc = map.get("yfqc")==null?0:((Double)map.get("yfqc")).doubleValue();
		}
		
		return yfqc;
	}
	
	
	/**
	 * ʵ�ʷ���Ӧ�ս��
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	public double getFsYingshouJe(String client_name,String cdate){
		return this.getSjysje(client_name, cdate);
	}
	
	
	/**
	 * ʵ�ʷ������ս��
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	public double getFsYishouJe(String client_name,String cdate){
		return this.getSjyishouje(client_name, cdate);
	}
	
	
	/**
	 * ʵ�ʷ���Ӧ�����
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	public double getFsYingfuJe(String client_name,String cdate){
		return this.getSjyfje(client_name, cdate);
	}
	
	
	/**
	 * ʵ�ʷ����Ѹ����
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	public double getFsYifuJe(String client_name,String cdate){
		return this.getSjyifuje(client_name, cdate);
	}
	
	
	/**
	 * ���ݿͻ���ż�����ȡʵ�ʷ���Ӧ�ն�
	 * �˻�ֻ�ӼӸ�ֵ���ɣ���Ӱ������
	 * �˻�ʱֻ������������ֽ��˻��������������ֱ��Ӱ��ͻ�ʵ�ʷ���Ӧ�ն�
	 * @param xsd_id
	 * @param ckd_id
	 * @return ʵ�ʷ���Ӧ�ն���޷���0
	 */
	private double getSjysje(String client_name,String cdate){
		double sjysje = 0;
		
		//���������۵����
		String sql = "select sum(sjcjje) as xsdje from xsd where state='�ѳ���' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjysje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		}
		
		//�������˻������
		sql = "select sum((0-thdje)) as thdje from thd where state='�����' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjysje += map.get("thdje")==null?0:((Double)map.get("thdje")).doubleValue();
		}
		
		//�����������
		sql = "select sum(pzje) as je from pz where state='���ύ' and type='Ӧ��' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map tzMap = (Map)list .get(0);
			sjysje += tzMap.get("je")==null?0:((Double)tzMap.get("je")).doubleValue();
		}
		
		return sjysje;
	}
	
	
	/**
	 * ���ݿͻ���ż�����ȡʵ�ʷ���Ӧ����
	 * @param xsd_id
	 * @param ckd_id
	 * @return ʵ�ʷ���Ӧ������޷���0
	 */
	private double getSjyfje(String client_name,String cdate){
		double sjyfje = 0;
		
		//�����������
		String sql = "select sum(total) as total from jhd where state='�����' and gysbh='" + client_name + "'  and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjyfje = map.get("total")==null?0:((Double)map.get("total")).doubleValue();
		}
		
		//�˻��������
		sql = "select sum(0-tkzje) as tkzje from cgthd where state='�ѳ���' and provider_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjyfje += map.get("tkzje")==null?0:((Double)map.get("tkzje")).doubleValue();
		}
		
		//�����������
		sql = "select sum(pzje) as je from pz where state='���ύ' and type='Ӧ��' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map tzMap = (Map)list .get(0);
			sjyfje += tzMap.get("je")==null?0:((Double)tzMap.get("je")).doubleValue();
		}
		
		return sjyfje;
	}
	
	
	
	/**
	 * ���ݿͻ���ż�����ȡʵ�����ս��
	 * @param xsd_id
	 * @return ���ս��,���޷���0
	 */
	private double getSjyishouje(String client_name,String cdate){
		
		//�������տ�
		double syys = 0;
		String sql = "select sum(skje) as skje from xssk where client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "' and state='���ύ'";		
		List list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			syys = map.get("skje")==null?0:((Double)map.get("skje")).doubleValue();
		}
		
		return syys;
	}
	
	
	/**
	 * ���ݿͻ���ż�����ȡʵ���Ѹ����
	 * @param xsd_id
	 * @return �Ѹ����,���޷���0
	 */
	private double getSjyifuje(String client_name,String cdate){
		double sjyifuje = 0;
		String sql = "select sum(fkje) as fkje from cgfk where state='���ύ' and gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjyifuje = map.get("fkje")==null?0:((Double)map.get("fkje")).doubleValue();
		}
		
		return sjyifuje;
	}
	
	
	/**
	 * ɾ�������޵���Ϣ
	 *
	 */
	public void delExpireMsg(){
		String sql = "delete FROM sys_msg WHERE TO_DAYS(NOW())-TO_DAYS(read_time) > " + Constant.MSG_EXPIRE_DAY;
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ���ص�ǰ�����˻�
	 * @return
	 */
	public List getAllAccounts(){
		String sql = "select * from accounts";
		return this.getResultList(sql);
	}
	
	
	//����Ϊϵͳ�����ڳ�ʧ�ܺ���õ����ڳ�ʱ�õ��ĺ���
	
	/**
	 * ����ǰһ��ͻ�Ӧ��Ӧ���ڳ�ֵ ����ĳһ����ڳ�ֵ<BR>
	 * ʹ����ĳһ��ͻ��ڳ���������ʱ����ǰһ��Ļ����������������<BR>
	 * ���磺2008-09-09���ڳ������⣬��2008-09-08��ֵ�Ƕԣ���Ӧ����cdate=2008-09-09,cdat_1=2008-09-08<BR>
	 * @param cdate  �ڳ�����
	 * @param cdat_1 �ڳ�ǰһ��
	 */
	public void genCleintWlqc(String cdate,String cdat_1){
		List clientList = getClientList();
		if(clientList != null && clientList.size()>0){
			for(int i=0;i<clientList.size();i++){
				Map map = (Map)clientList.get(i);
				String client_name = StringUtils.nullToStr(map.get("id"));
							
				//�����ڳ�Ӧ�� = �����ڳ�Ӧ�� + ���շ���Ӧ�� - ��������
				double qcys = this.getClientYsqc(client_name, cdat_1) + this.getSjysje(client_name, cdat_1) - this.getSjyishouje(client_name, cdat_1);
				
				//�����ڳ�Ӧ�� = �����ڳ�Ӧ�� + ���շ���Ӧ�� - �����Ѹ�
				double qcyf = this.getClientYfqc(client_name, cdat_1) + this.getSjyfje(client_name, cdat_1) - this.getSjyifuje(client_name, cdat_1);
				
				this.insertQc(client_name, qcys, qcyf, cdate);
			}
		}
		log.info("����" + cdate + "�����ڳ��ɹ�");
	}
	
	
	
	/**
	 * ����ǰһ���˻����ڳ�ֵ�����ɵ�ǰ����ڳ�ֵ
	 * @param cdate  Ҫ�����ڳ�������
	 * @param cdat_1  �ڳ�ǰһ��
	 */
	public void genAccountQc(String cdate,String cdat_1){
		
		List accountList = getAllAccounts();
		
		if(accountList != null && accountList.size() > 0){
			for(int i=0;i<accountList.size();i++){
				Map map = (Map)accountList.get(i);
				
				String account_id = StringUtils.nullToStr(map.get("id"));
				
				//ȡǰһ���˻����ڳ�ֵ
				double pre_qc_je = 0;
				String sql = "select * from account_qc where qc_date='" + cdat_1 + "' and account_id='" + account_id + "'";				
				Map qcMap = this.getResultMap(sql);
				if(qcMap != null){
					pre_qc_je = qcMap.get("qcje")==null?0:((Double)qcMap.get("qcje")).doubleValue();
				}
				
				
				//ȡ���׽��ϼ�
				double cur_day_jyje = 0;
				sql = "select * from account_dzd where account_id='" + account_id + "' and jy_date>='" + cdat_1 + "' and jy_date<='" + (cdat_1+" 23:59:59") + "'";
				List mxList = this.getResultList(sql);
				if(mxList != null && mxList.size()>0){
					for(int k=0;k<mxList.size();k++){
						Map mxMap = (Map)mxList.get(k);
						double jyje = mxMap.get("jyje")==null?0:((Double)mxMap.get("jyje")).doubleValue(); //���׽��						
						cur_day_jyje += jyje;
					}
				}
				
				//�˻������ڳ�ֵ
				double cur_qc_je = pre_qc_je + cur_day_jyje;
				
				sql = "insert into account_qc(account_id,qc_date,qcje) values('" + account_id + "','" + cdate + "'," + cur_qc_je + ")";
				this.getJdbcTemplate().update(sql);
				
				
			}
		}
		log.info("�����˻� "+ cdate + " �ڳ��ɹ�");
	}
	
	/**
	 * �����ۺ�����Ʒ�ڿ�����
	 *
	 */
	public void updateShkcProductDay()
	{
		String sql = "update shkc set day_num=day_num+1";
        this.getJdbcTemplate().update(sql);
        log.info("�����ۺ�����Ʒ�ڿ������ɹ���");
	}

}
