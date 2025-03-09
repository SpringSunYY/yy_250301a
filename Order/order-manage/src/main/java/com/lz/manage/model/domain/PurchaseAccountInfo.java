package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 采购账号信息对象 tb_purchase_account_info
 *
 * @author YY
 * @date 2025-03-03
 */
@TableName("tb_purchase_account_info")
@Data
public class PurchaseAccountInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
//    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String nickName;

    /** 账号类型(1=线上 2=线下) */
    @Excel(name = "账号类型",readConverterExp = "1=线上,2=线下",prompt = "只能线上、线下")
    private String accountType;

    /** 采购渠道 */
    @Excel(name = "采购渠道",type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String purchaseChannelsName;
    @Excel(name = "采购渠道",prompt = "采购渠道编号")
    private Long purchaseChannelsId;
    @TableField(exist = false)
    private List<Long> purchaseChannelsIds;

    /** 采购账号 */
    @Excel(name = "采购账号")
    private String purchaseAccount;

    /** 创建人 */
    @Excel(name = "客服")
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd",type = Excel.Type.EXPORT)
    private Date createTime;

    /** 更新人 */
//    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 部门 */
    @Excel(name = "部门",type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String deptName;
    private Long deptId;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;


    @TableField(exist = false)
    private List<Long> deptIds;
}
