package com.sw.cms.sendsms;

import org.smslib.OutboundMessage;

import com.sw.cms.model.Fsendsms;

/**
 * 封装发送短信回调类
 * @author sw
 *
 */
public class MyOutboundMessage extends OutboundMessage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private   Fsendsms mag;
	
   public MyOutboundMessage(Fsendsms mags)
   {
	   super(mags.getYddh(),mags.getRemark());
	   this.mag=mags;
	  
   }

public Fsendsms getMag() {
	return mag;
}

public void setMag(Fsendsms mag) {
	this.mag = mag;
}

   

}
