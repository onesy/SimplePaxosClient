package org.onesy.ConfigureProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class CfgCenter {

	private static CfgCenter cfgCenter = null;

	public static CfgBean selfbean = null;

	public static Properties SelfProperties = null;

	public static Properties OtherProperties = null;

	public static ArrayList<CfgBean> cfgBeansList = new ArrayList<CfgBean>();

	private CfgCenter() {
		String selfPath = System.getProperty("user.dir") + File.separator
				+ ".." + File.separator + "self.properties";
		String otherPath = System.getProperty("user.dir") + File.separator
				+ ".." + File.separator + "other.properties";

		loadConfigure(selfPath, otherPath);

		Iterator iter = OtherProperties.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			// filename=true
			if (entry.getValue().equals("true")) {
				cfgBeansList.add(CfgBean.getInstance(LoadProperty(System
						.getProperty("user.dir")
						+ File.separator
						+ ".."
						+ File.separator + (String) entry.getKey())));
			}
		}
	}

	public static synchronized CfgCenter getInstance() {
		if(cfgCenter == null){
			cfgCenter = new CfgCenter();
		}
		return cfgCenter;
	}

	private static void loadConfigure(String selfpath, String otherProperties) {
		SelfProperties = LoadProperty(selfpath);
		OtherProperties = LoadProperty(otherProperties);
	}

	public static Properties LoadProperty(String propertyPath) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(propertyPath)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return properties;
	}
}
