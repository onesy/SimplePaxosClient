package org.onesy.InstructionFlows;

import java.util.concurrent.ConcurrentHashMap;

public class InstructionFlowTypeMapper {
	/**
	 * 指令编号转换到指令名称
	 */
	private static ConcurrentHashMap<Integer, String> Code2InstructionName = new ConcurrentHashMap<Integer, String>();
	/**
	 * 指令名称转换到指令编号
	 */
	private static ConcurrentHashMap<String, Integer> InstructionName2Code = new ConcurrentHashMap<String, Integer>();
	/**
	 * 取得指令编号对应的指令名称
	 * @param ICode
	 * @return
	 */
	public static String getInstructionNameByCode(Integer ICode){
		return InstructionFlowTypeMapper.Code2InstructionName.get(ICode);
	}
	/**
	 * 取得指令名称对应的编号 
	 * @param IName
	 * @return
	 */
	public static Integer getCodeByInstructionName(String IName){
		if(InstructionFlowTypeMapper.InstructionName2Code.get(IName) == null){
			System.err.println("the instruction Flow is not exist!");
		}
		return InstructionFlowTypeMapper.InstructionName2Code.get(IName);
	}
	/**
	 * 新增新的指令-编号映射
	 * @param ICode
	 * @param IName
	 */
	public static void SetInstructionNameCodePair(Integer ICode, String IName){
		InstructionFlowTypeMapper.Code2InstructionName.put(ICode, IName);
		InstructionFlowTypeMapper.InstructionName2Code.put(IName, ICode);
	}
	public static synchronized void LoadInstructionInfo(String[] instructionInfos){
		InstructionName2Code.clear();
		Code2InstructionName.clear();
		for(String instructionInfo : instructionInfos){
			InstructionFlowTypeMapper.SetInstructionNameCodePair(Integer.parseInt(instructionInfo.split(",")[0]), instructionInfo.split(",")[1]);
		}
	}
}
