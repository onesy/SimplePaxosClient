package org.onesy.MsgProcessor;

import org.onesy.ConfigureProcess.CfgCenter;

public class MsgBuildFactory {

	public static String MsgBuilder(MsgBean msgBean){
		return msgBean.VoteSerialNo + CfgCenter.SEPERATOR + msgBean.sign + CfgCenter.SEPERATOR + msgBean.Msg;
	}
}
