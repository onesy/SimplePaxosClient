package org.onesy.Beans;

public class ClientBeans {
	
	public String sign;
	
	public String host;
	
	public int listenport;
	
	public int sendport;
	
	public ClientBeans(String sign){
		String[] clientinfo = sign.split("_");
		this.sign = sign;
		this.host = clientinfo[0];
		this.listenport = Integer.parseInt(clientinfo[1]);
		this.sendport = Integer.parseInt(clientinfo[2]);
	}
	
}
