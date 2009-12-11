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
 * 导出库存数量汇总
 * @author liyt
 *
 */
public class ExportProductDlKcNumsResult extends ExportXlsTemplate {
	
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
			sheet.mergeCells(0, 0, 10, 0);
			label = new Label(0,0,"核心代理库存价格汇总",this.getFt_title());
			sheet.addCell(label);
			
			//写统计条件
			sheet.mergeCells(0, 1, 10, 1);
			label = new Label(0,1,conStr,this.getFt_item_center());
			sheet.addCell(label);
					
			//写统计表头
 			label = new Label(0,2,"序号",this.getFt_item_center_bold());
			sheet.addCell(label);	
			//label = new Label(0,2,"商品编码",this.getFt_item_center_bold());
			//sheet.addCell(label);			
			label = new Label(1,2,"商品名称",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(2,2,"规格",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(3,2,"库存",this.getFt_item_center_bold());
			sheet.addCell(label);
			//label = new Label(4,2,"考核成本",this.getFt_item_center_bold());
			//sheet.addCell(label);
			label = new Label(4,2,"零售报价",this.getFt_item_center_bold());
			sheet.addCell(label);
			//label = new Label(6,2,"零售限价",this.getFt_item_center_bold());
			//sheet.addCell(label);
			//label = new Label(7,2,"分销价",this.getFt_item_center_bold());
			//sheet.addCell(label);
			label = new Label(5,2,"分销限价",this.getFt_item_center_bold());
			sheet.addCell(label);
			//label = new Label(9,2,"工分",this.getFt_item_center_bold());
			//sheet.addCell(label);
			//label = new Label(10,2,"点杀",this.getFt_item_center_bold());
			//sheet.addCell(label);
			label = new Label(6,2,"产品卖点",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			List productList = kcMxReportService.getKcNumsResults(product_kind, product_name, store_id,state, flag, px);
			if(productList != null && productList.size()>0){
				int k = 3;
				for(int i=0;i<productList.size();i++){
					Map map = (Map)productList.get(i);
					
					double khcbj = map.get("khcbj")==null?0:((Double)map.get("khcbj")).doubleValue();
					double lsbj = map.get("lsbj")==null?0:((Double)map.get("lsbj")).doubleValue();
					double lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();
					double fxxj = map.get("fxxj")==null?0:((Double)map.get("fxxj")).doubleValue();
					double fxbj = map.get("fxbj")==null?0:((Double)map.get("fxbj")).doubleValue();
					double dss = map.get("dss")==null?0:((Double)map.get("dss")).doubleValue();		
					double gf = map.get("gf")==null?0:((Double)map.get("gf")).doubleValue();
					String num = StringUtils.nullToStr(map.get("kc_nums"));
					String ms=StringUtils.nullToStr(map.get("ms"));
					if(!ms.equals(""))
					{
					   if(ms.length()>10)
					   {
					     ms=ms.substring(0,10)+"...";
					   }
					}
					 
					if(num.trim().equals("")){
						num = "无";
					}
					else if(num.equals("0"))
					{
					  num = "无";
					}
					else{
					    num="有";
					}
					label = new Label(0,k,String.valueOf(i+1),this.getFt_item_left());
					sheet.addCell(label);
					//label = new Label(0,k,StringUtils.nullToStr(map.get("product_id")),this.getFt_item_center());
					//sheet.addCell(label);			
					label = new Label(1,k,StringUtils.nullToStr(map.get("product_name")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(2,k,StringUtils.nullToStr(map.get("product_xh")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(3,k,num,this.getFt_item_center());
					sheet.addCell(label);
					//label = new Label(4,k,JMath.round(khcbj,2),this.getFt_item_right());
					//sheet.addCell(label);
					label = new Label(4,k,JMath.round(lsbj,2),this.getFt_item_right());
					sheet.addCell(label);
//					label = new Label(6,k,JMath.round(lsxj,2),this.getFt_item_right());
//					sheet.addCell(label);
					label = new Label(5,k,JMath.round(fxxj,2),this.getFt_item_right());
					sheet.addCell(label);
//					label = new Label(8,k,JMath.round(fxxj,2),this.getFt_item_right());
//					sheet.addCell(label);
//					label = new Label(9,k,JMath.round(gf),this.getFt_item_right());
//					sheet.addCell(label);
//					label = new Label(10,k,JMath.round(dss),this.getFt_item_right());
//					sheet.addCell(label);
					label = new Label(6,k,ms,this.getFt_item_center());
					sheet.addCell(label);
					
					k++;
				}
			}
			
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
