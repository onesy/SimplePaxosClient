package org.onesy.mainpower;

import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.InstructionFlows.InstructionFlowDefiner;
import org.onesy.InstructionFlows.InstructionFlowTypeMapper;
import org.onesy.NodesManage.NodeDictionary;
import org.onesy.Util.SPSDebugHelper;
import org.onesy.Util.ThreadOp;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 初始化参数
		CfgCenter cfgCenter = CfgCenter.getInstance();
		SPSDebugHelper.Speaker("初始化参数完毕", 1);
		ThreadOp threadOp = new ThreadOp();
		threadOp.StartUp();
		// 初始化命令映射
		InstructionFlowTypeMapper.LoadInstructionInfo(InstructionFlowDefiner.ICodeIFNamePair);
		SPSDebugHelper.Speaker("命令映射初始化完毕", 1);
		// 节点信息初始化
		NodeDictionary.LoadNodes();
		SPSDebugHelper.Speaker("节点信息初始化完毕", 1);
	}
}
