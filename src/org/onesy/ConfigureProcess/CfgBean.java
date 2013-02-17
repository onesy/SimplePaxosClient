package org.onesy.ConfigureProcess;

import java.util.Properties;

public class CfgBean {

	public String host = null;

	public int port = 0;

	public String pubchannel = null;

	public String subchannel = null;

	public int db = 0;

	public String password = null;
	
	public int timeout = 0;

	private CfgBean(String host, String port, String pubchannel,
			String subchannel, String db, String password,String timeout) {
		this.host = host;
		this.port = Integer.parseInt(port);
		this.pubchannel = pubchannel;
		this.subchannel = subchannel;
		this.db = Integer.parseInt(db);
		this.timeout = Integer.parseInt(timeout);
	}

	public static CfgBean getInstance(Properties properties) {

		if (!(properties.containsKey("host") && properties.containsKey("port")
				&& properties.containsKey("pubchannel")
				&& properties.containsKey("subchannel")
				&& properties.containsKey("db")
				&& properties.containsKey("password")
				&& properties.containsKey("timeout"))) {
			return null;
		}

		return new CfgBean(properties.getProperty("host"),
				properties.getProperty("port"),
				properties.getProperty("pubchannel"),
				properties.getProperty("subchannel"),
				properties.getProperty("db"),
				properties.getProperty("password"),
				properties.getProperty("timeout"));
	}

}
