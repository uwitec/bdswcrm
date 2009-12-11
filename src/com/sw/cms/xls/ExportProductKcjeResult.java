package com.sw.cms.xls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;

import com.sw.cms.service.KcMxReportService;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xls.template.ExportXlsTemplate;

/**
 * 导出库存金额汇总
 * @author liyt
 *
 */
public class ExportProductKcjeResult extends ExportXlsTemplate {

	private KcMxReportService kcMxReportService = new KcMxReportService();


	public void writeExcelFile(HttpServletRequest request, WritableSheet sheet) {
		
		try{
			String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
			String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
			String store_id = StringUtils.nullToStr(request.getParameter("store_id"));
			String flag = StringUtils.nullToStr(request.getParameter("flag"));
			String state = StringUtils.nullToStr(request.getParameter("state"));
			String px="";
			String conStr = "";
			if(!store_id.equals("")){
				conStr += "　库房：" + StaticParamDo.getStoreNameById(store_id);
			}
			if(!product_kind.equals("")){
				String[] arryItems = product_kind.split(",");
				String kind_name = "";
				if(arryItems != null && arryItems.length >0){
					for(int i=0;i<arryItems.length;i++){
						if(kind_name.equals("")){
							kind_name = StaticParamDo.getProductKindNameById(arryItems[i]);
						}else{
							kind_name += "，" + StaticParamDo.getProductKindNameById(arryItems[i]);
						}
						
					}
				}
				conStr += "　产品类别：" + kind_name;
			}
			conStr += "　报表生成时间：" + DateComFunc.getCurTime();
			
			Label label = null;
			
			//写统计表标题
			sheet.mergeCells(0, 0, 5, 0);
			label = new Label(0,0,"库存金额汇总",this.getFt_title());
			sheet.addCell(label);
			
			//写统计条件
			sheet.mergeCells(0, 1, 5, 1);
			label = new Label(0,1,conStr,this.getFt_item_center());
			sheet.addCell(label);
					
			//写统计表头
			label = new Label(0,2,"商品编码",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(1,2,"商品名称",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(2,2,"规格",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(3,2,"库存数量",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(4,2,"单位成本",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(5,2,"库存成本",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			List productList = kcMxReportService.getKcNumsResults(product_kind, product_name, store_id,state, flag,px);
			
			int hj_kcNums = 0;
			double hj_kccb = 0;
			int k = 3;
			
			//写统计内容
			if(productList != null && productList.size()>0){				
				for(int i=0;i<productList.size();i++){
					Map map = (Map)productList.get(i);
					
					double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue();		
					String num = StringUtils.nullToStr(map.get("kc_nums"));
					if(num.equals("")){
						num = "0";
					}
					int kcNums = Integer.parseInt(num);
					
					hj_kcNums += kcNums;
					hj_kccb += price*kcNums;
					
					label = new Label(0,k,StringUtils.nullToStr(map.get("product_id")),this.getFt_item_center());
					sheet.addCell(label);			
					label = new Label(1,k,StringUtils.nullToStr(map.get("product_name")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(2,k,StringUtils.nullToStr(map.get("product_xh")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(3,k,num,this.getFt_item_center());
					sheet.addCell(label);
					label = new Label(4,k,JMath.round(price,2),this.getFt_item_right());
					sheet.addCell(label);
					label = new Label(5,k,JMath.round(price*kcNums,2),this.getFt_item_right());
					sheet.addCell(label);
					
					k++;
				}
			}
			
			//写合计
			label = new Label(0,k,"合计",this.getFt_item_center());
			sheet.addCell(label);
			label = new Label(3,k,hj_kcNums+"",this.getFt_item_center());
			sheet.addCell(label);
			label = new Label(5,k,JMath.round(hj_kccb,2),this.getFt_item_right());
			sheet.addCell(label);
			
		}catch(Exception ex){
			log.info(ex);
		}

	}


	public KcMxReportService getKcMxReportService() {
		return kcMxReportService;
	}


	public void setKcMxReportService(KcMxReportService kcMxReportService) {
		this.kcMxReportService = kcMxReportService;
	}

}
