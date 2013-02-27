package org.onesy.Util;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;

import redis.clients.jedis.Jedis;

public class RedisOp {
	public static Jedis getJedis(CfgBean cfgBean){
		Jedis jedis = new Jedis(cfgBean.host, cfgBean.port, cfgBean.timeout);
		return jedis;
	}
	
	public static void LocalSetKV(String key,String value){
		Jedis jedis = getJedis(CfgCenter.selfbean);
		jedis.set(key, value);
	}
	public static void SetKV(String key,String value,CfgBean cfgBean){
		Jedis jedis = getJedis(cfgBean);
		jedis.set(key, value);
	}
	public static String LocalGetV(String key){
		Jedis jedis = getJedis(CfgCenter.selfbean);
		return jedis.get(key);
	}
	public static String getV(String key,CfgBean cfgBean){
		Jedis jedis = getJedis(cfgBean);
		return jedis.get(key);
	}
}
