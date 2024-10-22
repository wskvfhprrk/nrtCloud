package com.zjngic.terminal.mapper;

import java.util.List;
import com.zjngic.terminal.domain.OriginalOrder;

/**
 * 原始订单Mapper接口
 * 
 * @author zjngic
 * @date 2024-10-22
 */
public interface OriginalOrderMapper 
{
    /**
     * 查询原始订单
     * 
     * @param id 原始订单主键
     * @return 原始订单
     */
    public OriginalOrder selectOriginalOrderById(Long id);

    /**
     * 查询原始订单列表
     * 
     * @param originalOrder 原始订单
     * @return 原始订单集合
     */
    public List<OriginalOrder> selectOriginalOrderList(OriginalOrder originalOrder);

    /**
     * 新增原始订单
     * 
     * @param originalOrder 原始订单
     * @return 结果
     */
    public int insertOriginalOrder(OriginalOrder originalOrder);

    /**
     * 修改原始订单
     * 
     * @param originalOrder 原始订单
     * @return 结果
     */
    public int updateOriginalOrder(OriginalOrder originalOrder);

    /**
     * 删除原始订单
     * 
     * @param id 原始订单主键
     * @return 结果
     */
    public int deleteOriginalOrderById(Long id);

    /**
     * 批量删除原始订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOriginalOrderByIds(Long[] ids);
}
