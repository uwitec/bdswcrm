package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.GainTblDAO;

public class GainTblService {
	
	private GainTblDAO gainTblDao;
	
	/**
	 * ͳ����Ӫҵ������<BR>
	 * ���п����Ʒ���������루����-�˻���
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statZyywSr(String ny){
		return gainTblDao.statZyywSr(ny);
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
		return gainTblDao.statZyywCb(ny);
	}
	
	
	/**
	 * ͳ������ҵ������
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statQtywSr(String ny){
		return gainTblDao.statQtywSr(ny);
	}
	
	
	/**
	 * ͳ������ҵ��ɱ�
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statQtywCb(String ny){
		return gainTblDao.statQtywCb(ny);
	}
	
	
	/**
	 * Ӫҵ������
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statYywSr(String ny){
		return gainTblDao.statYywSr(ny);
	}
	
	
	/**
	 * Ӫҵ����<BR>
	 * ȡ��һ����ñ�<BR>
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statYywZc(String ny){
		return gainTblDao.statYywZc(ny);
	}
	
	
	
	/**
	 * �������<BR>
	 * ������Դ��һ����ñ�qtzc)<BR>
	 * type=02
	 * @param ny
	 * @return
	 */
	public Map statCwfy(String ny){
		return gainTblDao.statCwfy(ny);
	}
	
	
	/**
	 * �������<BR>
	 * ������Դ��һ����ñ�qtzc)<BR>
	 * type=02
	 * @param ny
	 * @return
	 */
	public Map statGlfy(String ny){
		return gainTblDao.statGlfy(ny);
	}
	
	
	/**
	 * ��Ӫҵ��˰�𼰸���<BR>
	 * ������Դ��һ����ñ�qtzc)<BR>
	 * type ��04��ͷ
	 * @param ny
	 * @return
	 */
	public Map statZyywsjjfj(String ny){
		return gainTblDao.statZyywsjjfj(ny);
	}
	
	
	/**
	 * ����˰<BR>
	 * ������Դ��һ����ñ�qtzc)<BR>
	 * type ��045��ͷ
	 * @param ny
	 * @return
	 */
	public Map statSds(String ny){
		return gainTblDao.statSds(ny);
	}
	
	
	/**
	 * ��Ʒ��������
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statSpbySr(String ny){
		return gainTblDao.statSpbySr(ny);
	}
	
	
	/**
	 * ��Ʒ����֧��
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statSpbsZc(String ny){
		return gainTblDao.statSpbsZc(ny);
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
		return gainTblDao.statWltzSr(ny);
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
		return gainTblDao.statWltzZc(ny);
	}
	
	
	/**
	 * ��̯����
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statDtfy(String ny){
		return gainTblDao.statDtfy(ny);
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
		return gainTblDao.statTjlr(ny);
	}


	public GainTblDAO getGainTblDao() {
		return gainTblDao;
	}


	public void setGainTblDao(GainTblDAO gainTblDao) {
		this.gainTblDao = gainTblDao;
	}

}
