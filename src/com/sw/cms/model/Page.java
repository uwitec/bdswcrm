package com.sw.cms.model;

import java.util.List;

/**
 * 封状的分页对象
 * @author liyt
 *
 */

public class Page {
	
	private int totalNum;  //总记录数
	private int curPage;   //当前页
	private int perPage;   //每页记录数
	private int totalPage; //总页数
	
	private List results;   //查询结果
	private String formName; //分面所在form名称
	
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List getResults() {
		return results;
	}
	public void setResults(List results) {
		this.results = results;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	/**
	 * 返回分面脚本
	 * @param formName
	 * @return
	 */
	public String getPageScript(String formName){
		String pageScript = "";
		
		pageScript = "<script type=\"text/javascript\">\n";
		
		pageScript += "function goPage(pageNum){\n";
		pageScript += "		document." + formName + ".curPage.value = pageNum;\n";
		pageScript += "		document." + formName + ".submit();\n";
		pageScript += "}\n";
		
		pageScript += "function OnlyNumber(){\n";
		pageScript += "		if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) || (window.event.keyCode == 13) || (window.event.keyCode == 46) || (window.event.keyCode == 45))){\n";
		pageScript += "			window.event.keyCode = 0 ;\n";
		pageScript += "	    }\n";
		pageScript += "}\n";		
		
		pageScript += "function jumping(){\n";
		pageScript += "		var max = " + this.totalPage + ";\n";
		pageScript += "		if(document." + formName + ".showPage.value.length==0){\n";
		pageScript += "			alert(\"请输入跳转页面\");\n";
		pageScript += "			return;\n";
		pageScript += "		}else if(document." + formName + ".showPage.value>max){\n";
		pageScript += "			alert(\"输入跳转页面错误\");\n";
		pageScript += "			return;\n";		
		pageScript += "		}else if(document." + formName + ".showPage.value <= \"0\"){\n";
		pageScript += "			alert(\"输入跳转页面错误\");\n";
		pageScript += "			return;\n";
		pageScript += "		}\n";
		pageScript += "		document." + formName + ".curPage.value = document." + formName + ".showPage.value;\n";		
		pageScript += "		document." + formName + ".submit();\n";
		pageScript += "}\n";
		
		pageScript += "</script>\n";
				
		pageScript += "<input  name=\"curPage\" type=\"hidden\" value=\"" + this.curPage + "\">&nbsp;";
		pageScript += "跳到第 <input  name=\"showPage\" type=\"text\" size=\"3\" onkeypress=\"OnlyNumber()\">页&nbsp;";
		pageScript += "<img src=\"./images/button_go.gif\" width=\"35\" height=\"20\" align=\"absmiddle\" onclick=\"javascript:jumping()\" style=\"cursor:hand\">&nbsp;&nbsp;&nbsp;&nbsp;";
		
		pageScript += "共" + this.totalNum + "条，每页" + this.perPage+ "条     &nbsp;&nbsp;第" + this.curPage +"页/共" + this.totalPage + "页&nbsp;&nbsp";
		
		if(this.curPage==1){ //如果当前是第一页
			if(this.totalPage>1){  //如果总页数大于1,显示下一页和末页，否则不显示任何信息
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + (this.curPage+1) + "\")'>下一页</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + this.totalPage + "\")'>末&nbsp;&nbsp;页</a>";
			}		
		}else{ //如果当前不是第一页
			if(this.curPage == this.totalPage){  //如果当前页是最后一页
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(1)'>首&nbsp;&nbsp;页</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + (this.curPage-1) + "\")'>上一页</a>";
			}else{
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(1)'>首&nbsp;&nbsp;页</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + (this.curPage-1) + "\")'>上一页</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + (this.curPage+1) + "\")'>下一页</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + this.totalPage + "\")'>末&nbsp;&nbsp;页</a>";
			}
		}
		
		return pageScript;		
	}
	
	/**
	 * 返回分页脚本，默认form为myform
	 * @return
	 */
	public String getPageScript() {
		return getPageScript("myform");
	}
	
}
