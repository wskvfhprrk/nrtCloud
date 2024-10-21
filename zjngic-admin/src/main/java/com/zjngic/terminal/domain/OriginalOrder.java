package com.zjngic.terminal.domain;

import java.math.BigDecimal;
import com.zjngic.common.annotation.Excel;
import com.zjngic.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 原始订单对象 original_order
 * 
 * @author zjngic
 * @date 2024-10-21
 */
public class OriginalOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long id;

    /** 机器编号 */
    @Excel(name = "机器编号")
    private String machineCode;

    /** 订单的JSON字符串 */
    private String orderJson;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private BigDecimal amount;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private Integer orderStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMachineCode(String machineCode) 
    {
        this.machineCode = machineCode;
    }

    public String getMachineCode() 
    {
        return machineCode;
    }
    public void setOrderJson(String orderJson) 
    {
        this.orderJson = orderJson;
    }

    public String getOrderJson() 
    {
        return orderJson;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setOrderStatus(Integer orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus() 
    {
        return orderStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("machineCode", getMachineCode())
            .append("orderJson", getOrderJson())
            .append("amount", getAmount())
            .append("orderStatus", getOrderStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
