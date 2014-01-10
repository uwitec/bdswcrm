package com.sw.cms.xls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;

import com.sw.cms.service.ProductSerialNumCgHzService;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xls.template.ExportXlsTemplate;

public class ExportProductSerialCghzResult extends ExportXlsTemplate {
	
	private ProductSerialNumCgHzService productSerialNumCgHzService;

	@Override
	public void writeExcelFile(HttpServletRequest request, WritableSheet sheet) {
		try{
			String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
			String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
			String dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));

			String clientId = StringUtils.nullToStr(request.getParameter("clientId"));
			String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
			String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
			String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
			
			String xsry_name = StringUtils.nullToStr(request.getParameter("xsry_name"));

			String con = "";
			con = "日期：" + start_date + "至" + end_date;
			if(!clientId.equals("")){
				con += "  供应商名称：" + clientId;
			}
			if(!dept_id.equals("")){
				con += "  部门：" + StaticParamDo.getDeptNameById(dept_id);
			}
			
			if(!product_kind.equals("")){
				con += "  商品类别：" + StaticParamDo.getProductKindNameById(product_kind);
			}
			con += "　报表生成时间：" + DateComFunc.getCurTime();
			
			Label label = null;
			
			//写统计表标题
			sheet.mergeCells(0, 0, 6, 0);
			label = new Label(0,0,"商品序列号采购汇总",this.getFt_title());
			sheet.addCell(label);
			
			//写统计条件
			sheet.mergeCells(0, 1, 6, 1);
			label = new Label(0,1,con,this.getFt_item_center());
			sheet.addCell(label);
					
			//写统计表头
			label = new Label(0,2,"日期",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(1,2,"序列号",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(2,2,"商品名称",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(3,2,"商品规格",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(4,2,"供应商名称",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(5,2,"供应商电话",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(6,2,"采购员",this.getFt_item_center_bold());			
			sheet.addCell(label);
			
			List resultList = productSerialNumCgHzService.getSerialNumCgList(start_date, end_date, product_kind, product_name, dept_id, xsry_name,clientId);
			
			int hj_nums = 0;
			double hj_je = 0;
			int k = 3;
			
			//写统计内容
			if(resultList != null && resultList.size()>0){				
				for(int i=0;i<resultList.size();i++){
					Map map = (Map)resultList.get(i);
									
					label = new Label(0,k,StringUtils.nullToStr(map.get("fs_date")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(1,k,StringUtils.nullToStr(map.get("serial_num")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(2,k,StringUtils.nullToStr(map.get("product_name")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(3,k,StringUtils.nullToStr(map.get("product_xh")),this.getFt_item_left());
					sheet.addCell(label);
					label = new Label(4,k,StringUtils.nullToStr(map.get("client_name")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(5,k,StringUtils.nullToStr(map.get("gzdh")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(6,k,StringUtils.nullToStr(map.get("jsr")),this.getFt_item_left());
					sheet.addCell(label);
					
					k++;
				}
			}		

			
		}catch(Exception ex){
			log.info(ex);
		}
	}

	public ProductSerialNumCgHzService getProductSerialNumCgHzService() {
		return productSerialNumCgHzService;
	}

	public void setProductSerialNumCgHzService(ProductSerialNumCgHzService productSerialNumCgHzService) {
		this.productSerialNumCgHzService = productSerialNumCgHzService;
	}

}
