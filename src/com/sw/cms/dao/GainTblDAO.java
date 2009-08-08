package com.sw.cms.dao;

import java.util.HashMap;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * �����<BR>
 * �������¼�������ۻ�
 * @author liyt
 *
 */

public class GainTblDAO extends JdbcBaseDAO {
	

	/**
	 * ͳ����Ӫҵ������<BR>
	 * ���п����Ʒ���������루����-�˻���
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statZyywSr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(a.xj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='�ѳ���' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdMap.get("xshj")==null?0:((Double)xsdMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.xj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='���ύ' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdMap.get("lshj")==null?0:((Double)lsdMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.xj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='�����' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdMap.get("thhj")==null?0:((Double)thdMap.get("thhj")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(a.xj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='�ѳ���' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdAllMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdAllMap.get("xshj")==null?0:((Double)xsdAllMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.xj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='���ύ' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdAllMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdAllMap.get("lshj")==null?0:((Double)lsdAllMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.xj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='�����' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdAllMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdAllMap.get("thhj")==null?0:((Double)thdAllMap.get("thhj")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ͳ����Ӫҵ��ɱ�<BR>
	 * �������п����Ʒ�ϼƲɹ��ɱ�������-�˻���
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statZyywCb(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(a.nums*a.cbj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='�ѳ���' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdMap.get("xshj")==null?0:((Double)xsdMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.nums*a.cbj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='���ύ' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdMap.get("lshj")==null?0:((Double)lsdMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.nums*a.cbj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='�����' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdMap.get("thhj")==null?0:((Double)thdMap.get("thhj")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(a.nums*a.cbj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='�ѳ���' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdAllMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdAllMap.get("xshj")==null?0:((Double)xsdAllMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.nums*a.cbj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='���ύ' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdAllMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdAllMap.get("lshj")==null?0:((Double)lsdAllMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.nums*a.cbj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='�����' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdAllMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdAllMap.get("thhj")==null?0:((Double)thdAllMap.get("thhj")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ͳ������ҵ������
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statQtywSr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(a.xj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='�ѳ���' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdMap.get("xshj")==null?0:((Double)xsdMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.xj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='���ύ' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdMap.get("lshj")==null?0:((Double)lsdMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.xj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='�����' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdMap.get("thhj")==null?0:((Double)thdMap.get("thhj")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(a.xj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='�ѳ���' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdAllMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdAllMap.get("xshj")==null?0:((Double)xsdAllMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.xj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='���ύ' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdAllMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdAllMap.get("lshj")==null?0:((Double)lsdAllMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.xj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='�����' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdAllMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdAllMap.get("thhj")==null?0:((Double)thdAllMap.get("thhj")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ͳ������ҵ��ɱ�
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statQtywCb(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		
		//��������Ʒ�ɹ�������ҵ��ɱ�����
		String sql = "select sum(a.price*a.nums) as cghj  from jhd_product a join jhd b on b.id=a.jhd_id JOIN product c on c.product_id=a.product_id where b.state='�����' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map cgMap = this.getResultMap(sql);
		if(cgMap != null){
			cost += cgMap.get("cghj")==null?0:((Double)cgMap.get("cghj")).doubleValue();
		}	
		//��������Ʒ�ɹ��˻�������ҵ��ɱ�����
		sql = "select sum(a.xj) as cgthhj  from cgthd_product a join cgthd b on b.id=a.cgthd_id JOIN product c on c.product_id=a.product_id where b.state='�ѳ���' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map cgthMap = this.getResultMap(sql);
		if(cgthMap != null){
			cost -= cgthMap.get("cgthhj")==null?0:((Double)cgthMap.get("cgthhj")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		
		//��������Ʒ�ɹ�������ҵ��ɱ�����
		sql = "select sum(a.price*a.nums) as cghj  from jhd_product a join jhd b on b.id=a.jhd_id JOIN product c on c.product_id=a.product_id where b.state='�����' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map cgAllMap = this.getResultMap(sql);
		if(cgAllMap != null){
			cost += cgAllMap.get("cghj")==null?0:((Double)cgAllMap.get("cghj")).doubleValue();
		}	
		//��������Ʒ�ɹ��˻�������ҵ��ɱ�����
		sql = "select sum(a.xj) as cgthhj  from cgthd_product a join cgthd b on b.id=a.cgthd_id JOIN product c on c.product_id=a.product_id where b.state='�ѳ���' and c.prop='����/����' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map cgthAllMap = this.getResultMap(sql);
		if(cgthAllMap != null){
			cost -= cgthAllMap.get("cgthhj")==null?0:((Double)cgthAllMap.get("cgthhj")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * Ӫҵ������
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statYywSr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(skje) as income from qtsr where state='���ύ' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("income")==null?0:((Double)mapMonth.get("income")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(skje) as income from qtsr where state='���ύ' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("income")==null?0:((Double)mapAllMonth.get("income")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * Ӫҵ����<BR>
	 * ȡ��һ����ñ�<BR>
	 * Ϊ�˼�����ʷ�����´���
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statYywZc(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(zcje) as cost from qtzc where state='���ύ' and (type not like '02%' and type not like '03%' and type not like '04%' and type not like '05%') and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(zcje) as cost from qtzc where state='���ύ' and (type not like '02%' and type not like '03%' and type not like '04%' and type not like '05%')  and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * �������<BR>
	 * ������Դ��һ����ñ�qtzc)<BR>
	 * type=02
	 * @param ny
	 * @return
	 */
	public Map statCwfy(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(zcje) as cost from qtzc where state='���ύ' and type like '02%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(zcje) as cost from qtzc where state='���ύ'  and type like '02%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * �������<BR>
	 * ������Դ��һ����ñ�qtzc)<BR>
	 * type ��03��ͷ
	 * @param ny
	 * @return
	 */
	public Map statGlfy(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(zcje) as cost from qtzc where state='���ύ' and type like '03%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(zcje) as cost from qtzc where state='���ύ'  and type like '03%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ��Ӫҵ��˰�𼰸���<BR>
	 * ������Դ��һ����ñ�qtzc)<BR>
	 * type ��04��ͷ
	 * @param ny
	 * @return
	 */
	public Map statZyywsjjfj(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(zcje) as cost from qtzc where state='���ύ' and type like '04%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(zcje) as cost from qtzc where state='���ύ'  and type like '04%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}	
	
	
	/**
	 * ����˰<BR>
	 * ������Դ��һ����ñ�qtzc)<BR>
	 * type ��045��ͷ
	 * @param ny
	 * @return
	 */
	public Map statSds(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(zcje) as cost from qtzc where state='���ύ' and type like '05%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(zcje) as cost from qtzc where state='���ύ'  and type like '05%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}	
	
	
	
	/**
	 * ��Ʒ��������
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statSpbySr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(ykje) as googbysr from kcpd_yk_tbl where type='1' and fs_date>='" + ny + "-01" + "' and fs_date<='" + ny + "-31" + "'";		
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("goodbysr")==null?0:((Double)mapMonth.get("goodbysr")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(ykje) as googbysr from kcpd_yk_tbl where type='1' and fs_date>='" + this.getNdqs(ny) + "' and fs_date<='" + ny + "-31" + "'";		
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("goodbysr")==null?0:((Double)mapAllMonth.get("goodbysr")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ��Ʒ����֧��
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statSpbsZc(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(ykje) as goodbszc from kcpd_yk_tbl where type='2' and fs_date>='" + ny + "-01" + "' and fs_date<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);		
		if(mapMonth != null){
			cost = mapMonth.get("goodbszc")==null?0:((Double)mapMonth.get("goodbszc")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(ykje) as goodbszc from kcpd_yk_tbl where type='2' and fs_date>='" + this.getNdqs(ny) + "' and fs_date<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);		
		if(mapAllMonth != null){
			cost = mapAllMonth.get("goodbszc")==null?0:((Double)mapAllMonth.get("goodbszc")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ������������<BR>
	 * Ӧ�ռ�+Ӧ����<BR>
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statWltzSr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(srje) as srje from((select sum(pzje) srje from pz where type='Ӧ��' and pzje>0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')";
		sql += "union (select sum(0-pzje) srje from pz where type='Ӧ��' and pzje<0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')) x";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("srje")==null?0:((Double)mapMonth.get("srje")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(srje) as srje from((select sum(pzje) srje from pz where type='Ӧ��' and pzje>0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')";
		sql += "union all (select sum(0-pzje) srje from pz where type='Ӧ��' and pzje<0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')) x";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapAllMonth.get("srje")==null?0:((Double)mapAllMonth.get("srje")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ��������֧��<BR>
	 * Ӧ�ռ�+Ӧ����<BR>
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statWltzZc(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "select sum(0-srje) as srje from((select sum(pzje) srje from pz where type='Ӧ��' and pzje<0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')";
		sql += "union (select sum(pzje) srje from pz where type='Ӧ��' and pzje>0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')) x";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("srje")==null?0:((Double)mapMonth.get("srje")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum(0-srje) as srje from((select sum(pzje) srje from pz where type='Ӧ��' and pzje<0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')";
		sql += "union all (select sum(pzje) srje from pz where type='Ӧ��' and pzje>0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')) x";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapAllMonth.get("srje")==null?0:((Double)mapAllMonth.get("srje")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ��̯����
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statDtfy(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//����
		String sql = "SELECT sum(je) as deferredPayment FROM  txfk_desc a join txfk b on b.id=a.txfk_id where b.state='���ύ' and txrq>='" + ny + "-01" + "' and txrq<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);		
		if(mapMonth != null){
			cost = mapMonth.get("deferredPayment")==null?0:((Double)mapMonth.get("deferredPayment")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "SELECT sum(je) as deferredPayment FROM  txfk_desc a join txfk b on b.id=a.txfk_id where b.state='���ύ' and txrq>='" + this.getNdqs(ny) + "' and txrq<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);		
		if(mapAllMonth != null){
			cost = mapAllMonth.get("deferredPayment")==null?0:((Double)mapAllMonth.get("deferredPayment")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ͳ�Ƶ�������<BR>
	 * ������������<BR>
	 * �����������<BR>
	 * �˴�ֻ��������Ʒ������/��������Ʒ���ƣ�
	 * @param ny
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statTjlr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//������
		String sql = "select sum((a.tzjg-a.ysjg)*a.nums) as tjlr from chtj_desc a join chtj b on b.id=a.chtj_id join product c on c.product_id=a.product_id where b.state='���ύ' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);		
		if(mapMonth != null){
			cost = mapMonth.get("tjlr")==null?0:((Double)mapMonth.get("tjlr")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//�����ۻ�
		sql = "select sum((a.tzjg-a.ysjg)*a.nums) as tjlr from chtj_desc a join chtj b on b.id=a.chtj_id join product c on c.product_id=a.product_id where b.state='���ύ' and c.prop='�����Ʒ' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);		
		if(mapAllMonth != null){
			cost = mapAllMonth.get("tjlr")==null?0:((Double)mapAllMonth.get("tjlr")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * ������ȡ�������ʼ
	 * @param ny
	 * @return
	 */
	private String getNdqs(String ny){
		if(ny == null){
			return "2008-01-01";
		}else{
			return ny.substring(0, 4) + "-01-01";
		}
	}

}
