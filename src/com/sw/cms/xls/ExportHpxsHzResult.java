package com.sw.cms.xls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;

import com.sw.cms.service.HpxsHzService;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xls.template.ExportXlsTemplate;

public class ExportHpxsHzResult extends ExportXlsTemplate {
	
	private HpxsHzService hpxsHzService;

	@Override
	public void writeExcelFile(HttpServletRequest request, WritableSheet sheet) {
		try{
			String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
			String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
			String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));

			String clientId = StringUtils.nullToStr(request.getParameter("clientId"));
			String clientName = StringUtils.nullToStr(request.getParameter("clientName"));

			String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));
			String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
			String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
			String product_xh = StringUtils.nullToStr(request.getParameter("product_xh"));

			String con = "";
			con = "日期：" + start_date + "至" + end_date;
			if(!clientId.equals("")){
				con += "  客户名称：" + clientId;
			}
			if(!xsry_id.equals("")){
				con += "  销售人员：" + StaticParamDo.getRealNameById(xsry_id);
			}
			if(!kind_name.equals("")){
				con += "  商品类别：" + kind_name;
			}
			if(!product_name.equals("")){
				con += "  商品名称：" + product_name;
			}
			if(!product_xh.equals("")){
				con += "  商品规格：" + product_xh;
			}
			con += "　报表生成时间：" + DateComFunc.getCurTime();
			
			Label label = null;
			
			//写统计表标题
			sheet.mergeCells(0, 0, 4, 0);
			label = new Label(0,0,"货品销售汇总",this.getFt_title());
			sheet.addCell(label);
			
			//写统计条件
			sheet.mergeCells(0, 1, 4, 1);
			label = new Label(0,1,con,this.getFt_item_center());
			sheet.addCell(label);
					
			//写统计表头
			label = new Label(0,2,"商品编码",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(1,2,"商品名称",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(2,2,"规格",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(3,2,"数量",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(4,2,"金额",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			List resultList = hpxsHzService.getHpxshzTjResult(product_kind, product_name, product_xh, start_date, end_date, clientName, xsry_id);
			
			int hj_nums = 0;
			double hj_je = 0;
			int k = 3;
			
			//写统计内容
			if(resultList != null && resultList.size()>0){				
				for(int i=0;i<resultList.size();i++){
					Map map = (Map)resultList.get(i);
					
					double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
					String strNums = StringUtils.nullToStr(map.get("nums"));
					
					int nums = 0;
					if(!strNums.equals("")){
						nums = new Integer(strNums).intValue();
					}
					
					hj_nums += nums;
					hj_je += je;
					
					label = new Label(0,k,StringUtils.nullToStr(map.get("product_id")),this.getFt_item_center());
					sheet.addCell(label);			
					label = new Label(1,k,StringUtils.nullToStr(map.get("product_name")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(2,k,StringUtils.nullToStr(map.get("product_xh")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(3,k,nums+"",this.getFt_item_center());
					sheet.addCell(label);
					label = new Label(4,k,JMath.round(je,2),this.getFt_item_right());
					sheet.addCell(label);
					
					k++;
				}
			}
			
			//写合计
			label = new Label(0,k,"合计",this.getFt_item_center());
			sheet.addCell(label);
			label = new Label(3,k,hj_nums+"",this.getFt_item_center());
			sheet.addCell(label);
			label = new Label(4,k,JMath.round(hj_je,2),this.getFt_item_right());
			sheet.addCell(label);
			
		}catch(Exception ex){
			log.info(ex);
		}
	}

	public HpxsHzService getHpxsHzService() {
		return hpxsHzService;
	}

	public void setHpxsHzService(HpxsHzService hpxsHzService) {
		this.hpxsHzService = hpxsHzService;
	}

}
