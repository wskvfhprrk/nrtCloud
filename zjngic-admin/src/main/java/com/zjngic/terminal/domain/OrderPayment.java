package com.zjngic.terminal.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjngic.common.annotation.Excel;
import com.zjngic.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单支付对象 order_payment
 * 
 * @author zjngic
 * @date 2024-10-14
 */
public class OrderPayment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 支付记录ID */
    private Long id;

    /** 订单ID */
    @Excel(name = "订单ID")
    private String orderId;

    /** 机器编号 */
    @Excel(name = "机器编号")
    private String machineCode;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal payAmount;

    /** 支付方式 */
    @Excel(name = "支付方式")
    private Integer payMethod;

    /** 是否退款 */
    @Excel(name = "是否退款")
    private Integer isRefunded;

    /** 退款编号 */
    @Excel(name = "退款编号")
    private String refundCode;

    /** 支付状态 */
    @Excel(name = "支付状态")
    private Integer paymentStatus;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /** 退款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date refundTime;

    /** 退款方式 */
    @Excel(name = "退款方式")
    private Integer refundMethod;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setMachineCode(String machineCode) 
    {
        this.machineCode = machineCode;
    }

    public String getMachineCode() 
    {
        return machineCode;
    }
    public void setPayAmount(BigDecimal payAmount) 
    {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount() 
    {
        return payAmount;
    }
    public void setPayMethod(Integer payMethod) 
    {
        this.payMethod = payMethod;
    }

    public Integer getPayMethod() 
    {
        return payMethod;
    }
    public void setIsRefunded(Integer isRefunded) 
    {
        this.isRefunded = isRefunded;
    }

    public Integer getIsRefunded() 
    {
        return isRefunded;
    }
    public void setRefundCode(String refundCode) 
    {
        this.refundCode = refundCode;
    }

    public String getRefundCode() 
    {
        return refundCode;
    }
    public void setPaymentStatus(Integer paymentStatus) 
    {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentStatus() 
    {
        return paymentStatus;
    }
    public void setPayTime(Date payTime) 
    {
        this.payTime = payTime;
    }

    public Date getPayTime() 
    {
        return payTime;
    }
    public void setRefundTime(Date refundTime) 
    {
        this.refundTime = refundTime;
    }

    public Date getRefundTime() 
    {
        return refundTime;
    }
    public void setRefundMethod(Integer refundMethod) 
    {
        this.refundMethod = refundMethod;
    }

    public Integer getRefundMethod() 
    {
        return refundMethod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("machineCode", getMachineCode())
            .append("payAmount", getPayAmount())
            .append("payMethod", getPayMethod())
            .append("isRefunded", getIsRefunded())
            .append("refundCode", getRefundCode())
            .append("paymentStatus", getPaymentStatus())
            .append("payTime", getPayTime())
            .append("refundTime", getRefundTime())
            .append("refundMethod", getRefundMethod())
            .toString();
    }
}
