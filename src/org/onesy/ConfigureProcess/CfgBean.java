package org.onesy.ConfigureProcess;

import java.util.Properties;

import org.onesy.NodesManage.NodeDictionary;

public class CfgBean {

	public String host = null;

	/**
	 * redis port
	 */
	public int port = 0;

	/**
	 * pub port default 10200
	 */
	public int PubPort = 10200;

	/**
	 * sub port default 10201
	 */
	public int SubPort = 10201;

	public String pubchannel = null;

	public String subchannel = null;

	public int db = 0;

	public String password = null;

	public int timeout = 0;

	// host_port_pubchannel_subchannel_db
	public String sign = null;

	public long voteSeriNo = 0;

	private CfgBean(String host, String port, String pubchannel,
			String subchannel, String db, String password, String timeout,
			String vote, String pubport, String subport) {
		this.host = host;
		this.port = Integer.parseInt(port);
		this.pubchannel = pubchannel;
		this.subchannel = subchannel;
		this.db = Integer.parseInt(db);
		this.timeout = Integer.parseInt(timeout);
		this.sign = host + "_" + port + "_" + pubchannel + "_" + subchannel
				+ "_" + db;
		this.voteSeriNo = Long.parseLong(vote);
		this.SubPort = Integer.parseInt(subport);
		this.PubPort = Integer.parseInt(pubport);
		NodeDictionary.NodesDictionary.put(sign, this);
	}

	public static CfgBean getInstance(Properties properties) {

		if (!(properties.containsKey("host") && properties.containsKey("port")
				&& properties.containsKey("pubchannel")
				&& properties.containsKey("subchannel")
				&& properties.containsKey("db")
				&& properties.containsKey("password")
				&& properties.containsKey("timeout") && properties
					.containsKey("vote"))
				&& properties.containsKey("pubport")
				&& properties.containsKey("subport")) {
			System.err.println("配置文件错误");
			return null;
		}

		return new CfgBean(properties.getProperty("host"),
				properties.getProperty("port"),
				properties.getProperty("pubchannel"),
				properties.getProperty("subchannel"),
				properties.getProperty("db"),
				properties.getProperty("password"),
				properties.getProperty("timeout"),
				properties.getProperty("vote"),
				properties.getProperty("pubport"),
				properties.getProperty("subport"));
	}

	/**
	 * 重载的方法，用以分辨两个cfgbean是否是针对一个实体的
	 * 
	 * @param cfgBean
	 * @return
	 */
	public boolean equals(CfgBean cfgBean) {
		if (this.sign.equals(cfgBean.sign)) {
			return true;
		}
		return false;
	}

}
