package com.sw.cms.model.jasperreports;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import com.sw.cms.model.Lsd;

public class LsdDataSource implements JRDataSource {
	
	private Lsd lsd = null;

	public Object getFieldValue(JRField arg0) throws JRException {
		return null;
	}

	public boolean next() throws JRException {
		return false;
	}

	public Lsd getLsd() {
		return lsd;
	}

	public void setLsd(Lsd lsd) {
		this.lsd = lsd;
	}

}
