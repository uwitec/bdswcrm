package com.sw.cms.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.views.jsp.ComponentTagSupport;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 分页标签
 * @author liyt
 *
 */

public class PageTag extends ComponentTagSupport {

	private static final long serialVersionUID = 3885252072670085242L;

	public Component getBean(OgnlValueStack stack, HttpServletRequest request,HttpServletResponse response) {
		return new PageComponent(stack);
	}

	private String formname = "myform";   //设定页面form名称,默认为myform
	private String value;                 //Page对象堆栈值

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	protected void populateParams() {
		super.populateParams();

		PageComponent tag = (PageComponent) component;
		tag.setValue(value);
		tag.setFormname(formname);
	}

}
