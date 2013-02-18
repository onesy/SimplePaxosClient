package org.onesy.MsgProcessor;

public class MsgBuildFactory {

	public static String MsgBuilder(MsgBean msgBean){
		return msgBean.voteSerialNo + ":" + msgBean.sign + ":" + msgBean.Msg;
	}
}
