package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.KlHzReportDAO;

public class KlHzReportService
{
	private KlHzReportDAO  klHzReportDao;
   
   public List getKlResults(String product_kind, String product_name, String store_id,String kl_day)
   {
	   return klHzReportDao.getKlResults(product_kind, product_name, store_id, kl_day);
   }
   public KlHzReportDAO getKlHzReportDao() {
		return klHzReportDao;
	}
	public void setKlHzReportDao(KlHzReportDAO klHzReportDao) {
		this.klHzReportDao = klHzReportDao;
	}
}

