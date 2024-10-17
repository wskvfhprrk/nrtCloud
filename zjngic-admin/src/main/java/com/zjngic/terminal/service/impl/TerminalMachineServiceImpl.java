package com.zjngic.terminal.service.impl;

import java.util.List;
import java.util.UUID;

import com.zjngic.common.constant.Constants;
import com.zjngic.common.core.domain.AjaxResult;
import com.zjngic.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.zjngic.terminal.mapper.TerminalMachineMapper;
import com.zjngic.terminal.domain.TerminalMachine;
import com.zjngic.terminal.service.ITerminalMachineService;

/**
 * 终端机器Service业务层处理
 *
 * @author zjngic
 * @date 2024-10-14
 */
@Service
public class TerminalMachineServiceImpl implements ITerminalMachineService {
    @Autowired
    private TerminalMachineMapper terminalMachineMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询终端机器
     *
     * @param id 终端机器主键
     * @return 终端机器
     */
    @Override
    public TerminalMachine selectTerminalMachineById(Long id) {
        return terminalMachineMapper.selectTerminalMachineById(id);
    }

    /**
     * 查询终端机器列表
     *
     * @param terminalMachine 终端机器
     * @return 终端机器
     */
    @Override
    public List<TerminalMachine> selectTerminalMachineList(TerminalMachine terminalMachine) {
        return terminalMachineMapper.selectTerminalMachineList(terminalMachine);
    }

    /**
     * 获取、更新密钥
     * @param terminalMachine id和code都可以，
     * @return
     */
    public AjaxResult getCerts(TerminalMachine terminalMachine){
        List<TerminalMachine> terminalMachines = terminalMachineMapper.selectTerminalMachineList(terminalMachine);
        if(terminalMachines.isEmpty())return AjaxResult.error("机器码和密码错误！");
        for (TerminalMachine terminalMachine1 : terminalMachines) {
            //生成密钥
            String key = UUID.randomUUID().toString().replaceAll("-", "");
            terminalMachine1.setGeneratedKey(key);
            updateTerminalMachine(terminalMachine1);
            return AjaxResult.success(key);
        }
        return AjaxResult.error();
    }

    public TerminalMachine getTerminalMachineByCode(String code) {
        Object o = redisTemplate.opsForValue().get(Constants.REDIS_MACHINE_KEY + "::" + code);
        if (o == null) return null;
        return (TerminalMachine) o;
    }

    public void initKey() {
        List<TerminalMachine> terminalMachines = terminalMachineMapper.selectTerminalMachineList(null);
        terminalMachines.stream().forEach(terminalMachine -> saveRedis(terminalMachine));
    }

    public void saveRedis(TerminalMachine terminalMachine) {
        //缓存中添加
        redisTemplate.opsForValue().set(Constants.REDIS_MACHINE_KEY + "::" + terminalMachine.getCode(),terminalMachine);
    }

    /**
     * 新增终端机器
     *
     * @param terminalMachine 终端机器
     * @return 结果
     */
    @Override
    public int insertTerminalMachine(TerminalMachine terminalMachine) {
        //判断code有没有
        TerminalMachine dto=new TerminalMachine();
        dto.setCode(terminalMachine.getCode());
        List<TerminalMachine> terminalMachines = terminalMachineMapper.selectTerminalMachineList(dto);
        if(!terminalMachines.isEmpty()){
            return 0;
        }
        terminalMachine.setCreateTime(DateUtils.getNowDate());
        saveRedis(terminalMachine);
        return terminalMachineMapper.insertTerminalMachine(terminalMachine);
    }

    /**
     * 修改终端机器
     *
     * @param terminalMachine 终端机器
     * @return 结果
     */
    @Override
    public int updateTerminalMachine(TerminalMachine terminalMachine) {
        saveRedis(terminalMachine);
        return terminalMachineMapper.updateTerminalMachine(terminalMachine);
    }

    /**
     * 批量删除终端机器
     *
     * @param ids 需要删除的终端机器主键
     * @return 结果
     */
    @Override
    public int deleteTerminalMachineByIds(Long[] ids) {
        for (Long id : ids) {
            TerminalMachine terminalMachine = terminalMachineMapper.selectTerminalMachineById(id);
            redisTemplate.delete(Constants.REDIS_MACHINE_KEY + "::" + terminalMachine.getCode());
        }
        return terminalMachineMapper.deleteTerminalMachineByIds(ids);
    }

    /**
     * 删除终端机器信息
     *
     * @param id 终端机器主键
     * @return 结果
     */
    @Override
    public int deleteTerminalMachineById(Long id) {
        TerminalMachine terminalMachine = terminalMachineMapper.selectTerminalMachineById(id);
        redisTemplate.delete(Constants.REDIS_MACHINE_KEY + "::" + terminalMachine.getCode());
        return terminalMachineMapper.deleteTerminalMachineById(id);
    }
}
