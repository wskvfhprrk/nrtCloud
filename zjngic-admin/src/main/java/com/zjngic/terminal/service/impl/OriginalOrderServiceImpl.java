package com.zjngic.terminal.service.impl;

import java.util.List;
import com.zjngic.common.utils.DateUtils;
import com.zjngic.terminal.domain.OriginalOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjngic.terminal.mapper.OriginalOrderMapper;
import com.zjngic.terminal.service.IOriginalOrderService;

/**
 * 原始订单Service业务层处理
 * 
 * @author zjngic
 * @date 2024-10-14
 */
@Service
public class OriginalOrderServiceImpl implements IOriginalOrderService {
    @Autowired
    private OriginalOrderMapper originalOrderMapper;

    /**
     * 查询原始订单
     * 
     * @param id 原始订单主键
     * @return 原始订单
     */
    @Override
    public OriginalOrder selectOriginalOrderById(Long id)
    {
        return originalOrderMapper.selectOriginalOrderById(id);
    }

    /**
     * 查询原始订单列表
     * 
     * @param originalOrder 原始订单
     * @return 原始订单
     */
    @Override
    public List<OriginalOrder> selectOriginalOrderList(OriginalOrder originalOrder)
    {
        return originalOrderMapper.selectOriginalOrderList(originalOrder);
    }

    /**
     * 新增原始订单
     * 
     * @param originalOrder 原始订单
     * @return 结果
     */
    @Override
    public int insertOriginalOrder(OriginalOrder originalOrder)
    {
        originalOrder.setCreateTime(DateUtils.getNowDate());
        return originalOrderMapper.insertOriginalOrder(originalOrder);
    }

    /**
     * 修改原始订单
     * 
     * @param originalOrder 原始订单
     * @return 结果
     */
    @Override
    public int updateOriginalOrder(OriginalOrder originalOrder)
    {
        return originalOrderMapper.updateOriginalOrder(originalOrder);
    }

    /**
     * 批量删除原始订单
     * 
     * @param ids 需要删除的原始订单主键
     * @return 结果
     */
    @Override
    public int deleteOriginalOrderByIds(Long[] ids)
    {
        return originalOrderMapper.deleteOriginalOrderByIds(ids);
    }

    /**
     * 删除原始订单信息
     * 
     * @param id 原始订单主键
     * @return 结果
     */
    @Override
    public int deleteOriginalOrderById(Long id)
    {
        return originalOrderMapper.deleteOriginalOrderById(id);
    }
}
