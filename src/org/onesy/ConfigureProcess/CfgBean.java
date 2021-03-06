package org.onesy.ConfigureProcess;

import java.util.HashMap;
import java.util.Properties;

import org.onesy.NodesManage.NodeDictionary;
import org.onesy.Util.SPSDebugHelper;

import com.sun.xml.internal.bind.v2.model.core.ID;

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
//			System.err.println("配置文件错误");
			SPSDebugHelper.Speaker("配置文件错误", 2);
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
	public static CfgBean getInstance(HashMap<String, String> cfginfo) {

		if(cfginfo == null){
			SPSDebugHelper.Speaker("hash 为空", 2);
			return null ;
		}
		
		if (!(cfginfo.containsKey("host") && cfginfo.containsKey("port")
				&& cfginfo.containsKey("pubchannel")
				&& cfginfo.containsKey("subchannel")
				&& cfginfo.containsKey("db")
				&& cfginfo.containsKey("password")
				&& cfginfo.containsKey("timeout") && cfginfo
					.containsKey("vote"))
				&& cfginfo.containsKey("pubport")
				&& cfginfo.containsKey("subport")) {
//			System.err.println("配置文件错误");
			SPSDebugHelper.Speaker("hash中缺少必备元素", 2);
			return null;
		}

		return new CfgBean(cfginfo.get("host"),
				cfginfo.get("port"),
				cfginfo.get("pubchannel"),
				cfginfo.get("subchannel"),
				cfginfo.get("db"),
				cfginfo.get("password"),
				cfginfo.get("timeout"),
				cfginfo.get("vote"),
				cfginfo.get("pubport"),
				cfginfo.get("subport"));
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
