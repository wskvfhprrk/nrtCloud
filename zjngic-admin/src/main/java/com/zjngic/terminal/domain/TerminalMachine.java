package com.zjngic.terminal.domain;

import com.zjngic.common.annotation.Excel;
import com.zjngic.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 终端机器对象 terminal_machine
 * 
 * @author zjngic
 * @date 2024-10-14
 */
public class TerminalMachine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 编号 */
    @Excel(name = "编号")
    private String code;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 生成密钥 */
    @Excel(name = "生成密钥")
    private String generatedKey;

    /** 机器描述 */
    @Excel(name = "机器描述")
    private String description;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setGeneratedKey(String generatedKey) 
    {
        this.generatedKey = generatedKey;
    }

    public String getGeneratedKey() 
    {
        return generatedKey;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("password", getPassword())
            .append("generatedKey", getGeneratedKey())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
