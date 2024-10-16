package com.zjngic.terminal.service;

import java.util.List;
import com.zjngic.terminal .domain.TerminalMachine;

/**
 * 终端机器Service接口
 * 
 * @author zjngic
 * @date 2024-10-14
 */
public interface ITerminalMachineService 
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
     * 获取、更新密钥
     * @param terminalMachine id和code都可以，
     * @return
     */
    public String getKey(TerminalMachine terminalMachine);

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
     * 批量删除终端机器
     * 
     * @param ids 需要删除的终端机器主键集合
     * @return 结果
     */
    public int deleteTerminalMachineByIds(Long[] ids);

    /**
     * 删除终端机器信息
     * 
     * @param id 终端机器主键
     * @return 结果
     */
    public int deleteTerminalMachineById(Long id);
}
