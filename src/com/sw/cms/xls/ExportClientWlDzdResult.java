package com.sw.cms.xls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;

import com.sw.cms.service.ClientWlDzdService;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xls.template.ExportXlsTemplate;

/**
 * 导出客户往来对账单
 * @author liyt
 *
 */

public class ExportClientWlDzdResult extends ExportXlsTemplate {
	
	private ClientWlDzdService clientWlDzdService; 

	public void writeExcelFile(HttpServletRequest request, WritableSheet sheet) {
		try{
			String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
			String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
			String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
			String flag = StringUtils.nullToStr(request.getParameter("flag")); //显示明细
			
			//统计条件
			String conStr = "日期：" + start_date + "至" + end_date + " 客户名称：" + StaticParamDo.getClientNameById(client_name);
			conStr += "　报表生成时间：" + DateComFunc.getCurTime();
			
			Label label = null;
			
			//写统计表标题
			sheet.mergeCells(0, 0, 11, 0);
			label = new Label(0,0,"客户往来对账单",this.getFt_title());
			sheet.addCell(label);
			
			
			//写统计条件
			sheet.mergeCells(0, 1, 11, 1);
			label = new Label(0,1,conStr,this.getFt_item_center());
			sheet.addCell(label);
					
			//写统计表头
			sheet.mergeCells(0, 2, 0, 3);
			label = new Label(0,2,"日期",this.getFt_item_center_bold());
			sheet.addCell(label);	
			
			sheet.mergeCells(1, 2, 1, 3);
			label = new Label(1,2,"业务类型",this.getFt_item_center_bold());
			sheet.addCell(label);	
			
			sheet.mergeCells(2, 2, 2, 3);
			label = new Label(2,2,"单据编号",this.getFt_item_center_bold());
			sheet.addCell(label);	
			
			sheet.mergeCells(3, 2, 3, 3);
			label = new Label(3,2,"商品名称",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			sheet.mergeCells(4, 2, 4, 3);
			label = new Label(4,2,"商品规格",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			sheet.mergeCells(5, 2, 5, 3);
			label = new Label(5,2,"数量",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			sheet.mergeCells(6, 2, 8, 2);
			label = new Label(6,2,"应收",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(6,3,"应收款",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(7,3,"收款",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(8,3,"余额",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			sheet.mergeCells(9, 2, 11, 2);
			label = new Label(9,2,"应付",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(9,3,"应付款",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(10,3,"付款",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(11,3,"余额",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			//处理期初
			Map qcMap = clientWlDzdService.getClientQcInfo(client_name,start_date);
			double ysqc = 0;
			double yfqc = 0;
			if(qcMap != null){
				ysqc = qcMap.get("ysqc")==null?0:((Double)qcMap.get("ysqc")).doubleValue();
				yfqc = qcMap.get("yfqc")==null?0:((Double)qcMap.get("yfqc")).doubleValue();
			}
			label = new Label(0,4,"期初",this.getFt_item_center());
			sheet.addCell(label);
			label = new Label(1,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(2,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(3,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(4,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(5,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(6,4,JMath.round(ysqc,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(7,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(8,4,JMath.round(ysqc,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(9,4,JMath.round(yfqc,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(10,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(11,4,JMath.round(yfqc,2),this.getFt_item_right());
			sheet.addCell(label);
			
			
			List list = clientWlDzdService.getClientWlDzdList(start_date,end_date,client_name);
			
			double hj_ys = 0; //合计应收
			double hj_sk = 0; //合计收款
			double ys_ye = ysqc; //应收余额
			double hj_yf = 0; //合计应付
			double hj_fk = 0; //合计付款
			double yf_ye = yfqc; //应付余额
			
			int k = 5;
			//写统计内容
			if(list != null && list.size()>0){				
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					
					String creatdate = StringUtils.nullToStr(map.get("creatdate"));
					String xwtype = StringUtils.nullToStr(map.get("xwtype"));
					String dj_id = StringUtils.nullToStr(map.get("dj_id"));  //单据编号
					
					double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
					if(flag.equals("是")){
					//日期
					label = new Label(0,k,creatdate,this.getFt_item_centergray());
					sheet.addCell(label);
					//业务类型
					label = new Label(1,k,xwtype,this.getFt_item_centergray());
					sheet.addCell(label);
					//单据编号
					label = new Label(2,k,dj_id,this.getFt_item_centergray());
					sheet.addCell(label);
					sheet.setColumnView(2, 40);
					//商品名称
					label = new Label(3,k,"",this.getFt_item_leftgray());
					sheet.addCell(label);
					sheet.setColumnView(3, 40);
					//商品规格
					label = new Label(4,k,"",this.getFt_item_leftgray());
					sheet.addCell(label);
					sheet.setColumnView(4, 40);
					//数量
					label = new Label(5,k,"",this.getFt_item_centergray());
					sheet.addCell(label);
					
					if(xwtype.equals("销售") || xwtype.equals("销售退货")){
						hj_ys += je;
						ys_ye += je;	
						//应收款
						label = new Label(6,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);
						//收款
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//余额
						label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
						//应付款
						label = new Label(9,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//付款
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//余额
						label = new Label(11,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("收款")){
						hj_sk += je;
						ys_ye -= je;
						//应收款
						label = new Label(6,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//收款
						label = new Label(7,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);						
						//余额
						label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
						//应付款
						label = new Label(9,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//付款
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//余额
						label = new Label(11,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("采购") || xwtype.equals("采购退货")){
						hj_yf += je;
						yf_ye += je;
						
                        //应收款
						label = new Label(6,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//收款
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);						
						//余额
						label = new Label(8,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//应付款
						label = new Label(9,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);
						//付款
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//余额
						label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("付款")){
						hj_fk += je;
						yf_ye -= je;
						
                        //应收款
						label = new Label(6,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//收款
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);						
						//余额
						label = new Label(8,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//应付款
						label = new Label(9,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//付款			
						label = new Label(10,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);
                        //余额
						label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("往来调账(应收)")){
						hj_ys += je;
						ys_ye += je;
						//应收款
						label = new Label(6,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);	
                        //收款
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);						
						//余额
						label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
                        //应付款
						label = new Label(9,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//付款			
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
                        //余额
						label = new Label(11,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("往来调账(应付)")){
						hj_yf += je;
						yf_ye += je;
                        //应收款
						label = new Label(6,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);	
                        //收款
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);						
						//余额
						label = new Label(8,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
                        //应付款
						label = new Label(9,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);	
                        //付款			
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
                        //余额
						label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
					}
					}
					else
					{
                        //日期
						label = new Label(0,k,creatdate,this.getFt_item_center());
						sheet.addCell(label);
						//业务类型
						label = new Label(1,k,xwtype,this.getFt_item_center());
						sheet.addCell(label);
						//单据编号
						label = new Label(2,k,dj_id,this.getFt_item_center());
						sheet.addCell(label);
						sheet.setColumnView(2, 40);
						//商品名称
						label = new Label(3,k,"",this.getFt_item_left());
						sheet.addCell(label);
						sheet.setColumnView(3, 40);
						//商品规格
						label = new Label(4,k,"",this.getFt_item_left());
						sheet.addCell(label);
						sheet.setColumnView(4, 40);
						//数量
						label = new Label(5,k,"",this.getFt_item_center());
						sheet.addCell(label);
						
						if(xwtype.equals("销售") || xwtype.equals("销售退货")){
							hj_ys += je;
							ys_ye += je;	
							//应收款
							label = new Label(6,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);
							//收款
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//余额
							label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_right());
							sheet.addCell(label);
							//应付款
							label = new Label(9,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//付款
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//余额
							label = new Label(11,k,"",this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("收款")){
							hj_sk += je;
							ys_ye -= je;
							//应收款
							label = new Label(6,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//收款
							label = new Label(7,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);						
							//余额
							label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_right());
							sheet.addCell(label);
							//应付款
							label = new Label(9,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//付款
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//余额
							label = new Label(11,k,"",this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("采购") || xwtype.equals("采购退货")){
							hj_yf += je;
							yf_ye += je;
							
	                        //应收款
							label = new Label(6,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//收款
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);						
							//余额
							label = new Label(8,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//应付款
							label = new Label(9,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);
							//付款
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//余额
							label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("付款")){
							hj_fk += je;
							yf_ye -= je;
							
	                        //应收款
							label = new Label(6,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//收款
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);						
							//余额
							label = new Label(8,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//应付款
							label = new Label(9,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//付款			
							label = new Label(10,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);
	                        //余额
							label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("往来调账(应收)")){
							hj_ys += je;
							ys_ye += je;
							//应收款
							label = new Label(6,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);	
	                        //收款
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);						
							//余额
							label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_right());
							sheet.addCell(label);
	                        //应付款
							label = new Label(9,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//付款			
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
	                        //余额
							label = new Label(11,k,"",this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("往来调账(应付)")){
							hj_yf += je;
							yf_ye += je;
	                        //应收款
							label = new Label(6,k,"",this.getFt_item_right());
							sheet.addCell(label);	
	                        //收款
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);						
							//余额
							label = new Label(8,k,"",this.getFt_item_right());
							sheet.addCell(label);
	                        //应付款
							label = new Label(9,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);	
	                        //付款			
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
	                        //余额
							label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_right());
							sheet.addCell(label);
						}
					}
					if(flag.equals("是")){
						if(xwtype.equals("销售") || xwtype.equals("销售退货") || xwtype.equals("采购") || xwtype.equals("采购退货")){
							List mxList = clientWlDzdService.getDjMxList(dj_id,xwtype);
							if(mxList != null && mxList.size()>0){
								for(int m=0;m<mxList.size();m++){
									Map mxMap = (Map)mxList.get(m);
									
									k++;
									double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();
									//商品名称
									label = new Label(3,k,StringUtils.nullToStr(mxMap.get("product_name")),this.getFt_item_left());
									sheet.addCell(label);	
									//商品规格
									label = new Label(4,k,StringUtils.nullToStr(mxMap.get("product_xh")),this.getFt_item_left());
									sheet.addCell(label);
									//数量
									label = new Label(5,k,StringUtils.nullToStr(mxMap.get("nums")),this.getFt_item_center());
									sheet.addCell(label);
									
									if(xwtype.equals("销售") || xwtype.equals("销售退货")){	
										//应收款
										label = new Label(6,k,JMath.round(xj,2),this.getFt_item_right());
										sheet.addCell(label);
                                        //收款
										label = new Label(7,k,"",this.getFt_item_right());
										sheet.addCell(label);						
										//余额
										label = new Label(8,k,"",this.getFt_item_right());
										sheet.addCell(label);
				                        //应付款
										label = new Label(9,k,"",this.getFt_item_right());
										sheet.addCell(label);	
				                        //付款			
										label = new Label(10,k,"",this.getFt_item_right());
										sheet.addCell(label);
				                        //余额
										label = new Label(11,k,"",this.getFt_item_right());
										sheet.addCell(label);
										
									}else if(xwtype.equals("采购") || xwtype.equals("采购退货")){
                                        //应收款
										label = new Label(6,k,"",this.getFt_item_right());
										sheet.addCell(label);
                                        //收款
										label = new Label(7,k,"",this.getFt_item_right());
										sheet.addCell(label);						
										//余额
										label = new Label(8,k,"",this.getFt_item_right());
										sheet.addCell(label);
				                        //应付款
										label = new Label(9,k,JMath.round(xj,2),this.getFt_item_right());
										sheet.addCell(label);
                                        //付款			
										label = new Label(10,k,"",this.getFt_item_right());
										sheet.addCell(label);
				                        //余额
										label = new Label(11,k,"",this.getFt_item_right());
										sheet.addCell(label);
									}
								}
							}
						}
					}
					
					k++;
				}
			}
			
			//写合计
			label = new Label(0,k,"合计",this.getFt_item_center());
			sheet.addCell(label);
			
//			业务类型
			label = new Label(1,k,"",this.getFt_item_center());
			sheet.addCell(label);
			//单据编号
			label = new Label(2,k,"",this.getFt_item_center());
			sheet.addCell(label);
			//商品名称
			label = new Label(3,k,"",this.getFt_item_left());
			sheet.addCell(label);
			//商品规格
			label = new Label(4,k,"",this.getFt_item_left());
			sheet.addCell(label);
			//数量
			label = new Label(5,k,"",this.getFt_item_center());
			sheet.addCell(label);
			
			
			label = new Label(6,k,JMath.round(hj_ys,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(7,k,JMath.round(hj_sk,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(9,k,JMath.round(hj_yf,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(10,k,JMath.round(hj_fk,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_right());
			sheet.addCell(label);
			
		}catch(Exception ex){
			log.info(ex);
		}
	}

	public ClientWlDzdService getClientWlDzdService() {
		return clientWlDzdService;
	}

	public void setClientWlDzdService(ClientWlDzdService clientWlDzdService) {
		this.clientWlDzdService = clientWlDzdService;
	}

}
