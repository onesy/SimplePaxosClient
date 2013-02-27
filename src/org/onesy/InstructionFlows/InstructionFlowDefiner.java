package org.onesy.InstructionFlows;

/**
 * 定义InstructuonCode和InstructionCodeName的对应
 * 
 * @author onesy
 * 
 */
public class InstructionFlowDefiner {

	/**
	 * System Instruction Code 000~099
	 * 
	 */
	/**
	 * ICode = 001 InstructionFlow = ShutDown ICode = 002 InstructionFlow =
	 * Pause ICode = 100 InstructionFlow = SetKV ICode = 101 InstructionFlow =
	 * GetKV ICode = 102 InstructionFLow = Confirm ICode = 103 InstructionFlow =
	 * ToBeArbiter
	 */

	public static String[] ICodeIFNamePair = { "001,ShutDownOrder", "002,PauseOrder",
			"100,SetKVOrder", "101,GetKVOrder", "102,ConfirmOrder", "103,ToBeArbiterOrder" };
}
