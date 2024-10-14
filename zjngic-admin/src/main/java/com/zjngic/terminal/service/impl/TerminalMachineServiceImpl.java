package com.zjngic.terminal.service.impl;

import java.util.List;
import com.zjngic.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjngic.terminal .mapper.TerminalMachineMapper;
import com.zjngic.terminal .domain.TerminalMachine;
import com.zjngic.terminal .service.ITerminalMachineService;

/**
 * 终端机器Service业务层处理
 * 
 * @author zjngic
 * @date 2024-10-14
 */
@Service
public class TerminalMachineServiceImpl implements ITerminalMachineService 
{
    @Autowired
    private TerminalMachineMapper terminalMachineMapper;

    /**
     * 查询终端机器
     * 
     * @param id 终端机器主键
     * @return 终端机器
     */
    @Override
    public TerminalMachine selectTerminalMachineById(Long id)
    {
        return terminalMachineMapper.selectTerminalMachineById(id);
    }

    /**
     * 查询终端机器列表
     * 
     * @param terminalMachine 终端机器
     * @return 终端机器
     */
    @Override
    public List<TerminalMachine> selectTerminalMachineList(TerminalMachine terminalMachine)
    {
        return terminalMachineMapper.selectTerminalMachineList(terminalMachine);
    }

    /**
     * 新增终端机器
     * 
     * @param terminalMachine 终端机器
     * @return 结果
     */
    @Override
    public int insertTerminalMachine(TerminalMachine terminalMachine)
    {
        terminalMachine.setCreateTime(DateUtils.getNowDate());
        return terminalMachineMapper.insertTerminalMachine(terminalMachine);
    }

    /**
     * 修改终端机器
     * 
     * @param terminalMachine 终端机器
     * @return 结果
     */
    @Override
    public int updateTerminalMachine(TerminalMachine terminalMachine)
    {
        return terminalMachineMapper.updateTerminalMachine(terminalMachine);
    }

    /**
     * 批量删除终端机器
     * 
     * @param ids 需要删除的终端机器主键
     * @return 结果
     */
    @Override
    public int deleteTerminalMachineByIds(Long[] ids)
    {
        return terminalMachineMapper.deleteTerminalMachineByIds(ids);
    }

    /**
     * 删除终端机器信息
     * 
     * @param id 终端机器主键
     * @return 结果
     */
    @Override
    public int deleteTerminalMachineById(Long id)
    {
        return terminalMachineMapper.deleteTerminalMachineById(id);
    }
}
