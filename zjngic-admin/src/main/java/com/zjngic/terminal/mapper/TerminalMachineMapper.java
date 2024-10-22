package com.zjngic.terminal.mapper;

import java.util.List;
import com.zjngic.terminal.domain.TerminalMachine;

/**
 * 终端机器Mapper接口
 * 
 * @author zjngic
 * @date 2024-10-14
 */
public interface TerminalMachineMapper 
{
    /**
     * 查询终端机器
     * 
     * @param id 终端机器主键
     * @return 终端机器
     */
    public TerminalMachine selectTerminalMachineById(Long id);

    /**
     * 查询终端机器列表
     * 
     * @param terminalMachine 终端机器
     * @return 终端机器集合
     */
    public List<TerminalMachine> selectTerminalMachineList(TerminalMachine terminalMachine);

    /**
     * 新增终端机器
     * 
     * @param terminalMachine 终端机器
     * @return 结果
     */
    public int insertTerminalMachine(TerminalMachine terminalMachine);

    /**
     * 修改终端机器
     * 
     * @param terminalMachine 终端机器
     * @return 结果
     */
    public int updateTerminalMachine(TerminalMachine terminalMachine);

    /**
     * 删除终端机器
     * 
     * @param id 终端机器主键
     * @return 结果
     */
    public int deleteTerminalMachineById(Long id);

    /**
     * 批量删除终端机器
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTerminalMachineByIds(Long[] ids);
}
