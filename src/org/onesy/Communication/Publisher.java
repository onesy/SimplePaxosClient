package org.onesy.Communication;

import org.onesy.ConfigureProcess.CfgBean;

import redis.clients.jedis.Jedis;

public class Publisher {

	/**
	 * 向单个redis publish 消息
	 * @param cfgBean
	 * @param msg
	 * @return boolean
	 */
	public boolean PubMsg(CfgBean cfgBean, String msg) {
		try {
			Jedis jedis = new Jedis(cfgBean.host, cfgBean.port, cfgBean.timeout);
			jedis.publish(cfgBean.subchannel, msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 向一组redis broadcast 消息
	 * @param cfgBeans
	 * @param msg
	 * @return int
	 */
	public int PubMsgToAll(CfgBean[] cfgBeans, String msg) {
		int flg = 0;
		for (CfgBean cfgBean : cfgBeans) {
			try {
				this.PubMsg(cfgBean, msg);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return flg;
			}
			flg ++;
		}
		return flg;
	}
	
	/**
	 * 依次发布队列中的内容
	 * @param cfgBeans
	 * @param msgs
	 * @return
	 */
	public int PubMsgs(CfgBean[] cfgBeans, String[] msgs){
		int flg = 0;
		for(int i = 0; i < cfgBeans.length&& i < msgs.length  ; i ++){
			try {
				this.PubMsg(cfgBeans[i], msgs[i]);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return flg;
			}
			flg ++;
		}
		return flg;
	}
}
