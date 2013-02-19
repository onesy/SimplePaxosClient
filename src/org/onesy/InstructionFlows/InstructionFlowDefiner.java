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

	public static String[] ICodeIFNamePair = { "001,ShutDown", "002,Pause",
			"100,SetKV", "101,GetKV", "102,Confirm", "103,ToBeArbiter" };
}
