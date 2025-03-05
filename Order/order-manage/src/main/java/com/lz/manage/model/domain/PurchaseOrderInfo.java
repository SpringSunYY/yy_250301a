package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.math.BigDecimal;
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
 * 采购发货信息对象 tb_purchase_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@TableName("tb_purchase_order_info")
@Data
public class PurchaseOrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
//    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 采购编号
     */
    @Excel(name = "采购编号")
    private String orderNumber;

    /**
     * 销售类型（0=其他 1=线上 2=线下）
     */
    @Excel(name = "销售类型",
            readConverterExp = "0=其他,1=线上,2=线下",
            prompt = "只能线上、线下")
    private String orderType;

    /**
     * 订单利润
     */
    @Excel(name = "订单利润",
            prompt = "精确到两位小数",
            scale = 20,
            type = Excel.Type.EXPORT
    )
    private BigDecimal orderProfit;

    /**
     * 利润率
     */
    @Excel(name = "利润率",
            prompt = "精确到两位小数",
            scale = 20,
            type = Excel.Type.EXPORT)
    private BigDecimal orderProfitRate;

    /**
     * 采购日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购日期", width = 30, dateFormat = "yyyy-MM-dd", prompt = "时间格式：yyyy-MM-dd")
    private Date purchaseTime;

    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    @TableField(exist = false)
    private String storeName;
    private Long storeId;

    /**
     * 买家
     */
    @Excel(name = "买家")
    private String buyerNumber;

    /**
     * 销售量
     */
    @Excel(name = "销售量",
            prompt = "必须为整数")
    private Long salesNumber;

    /**
     * 销售价
     */
    @Excel(name = "销售价",
            prompt = "只能精确到两位小数",
            scale = 20)
    private BigDecimal salesPrice;

    /**
     * 采购渠道分类
     */
    @Excel(name = "采购渠道分类",
            readConverterExp = "0=其他,1=线上,2=线下",
            prompt = "只能线上、线下")
    private String purchaseChannelType;

    /**
     * 采购渠道
     */
    @Excel(name = "采购渠道")
    private String purchaseChannelDetail;

    /**
     * 采购账号
     */
    @Excel(name = "采购账号", prompt = "采购账号")
    @TableField(exist = false)
    private String purchaseAccount;
    private Long purchaseAccountId;

    /**
     * 采购订单编号
     */
    @Excel(name = "采购订单编号")
    private String purchaseOrder;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    private String supplierName;

    /**
     * 采购进价
     */
    @Excel(name = "采购进价",
            prompt = "只能精确到两位小数",
            scale = 20)
    private BigDecimal purchasePrice;

    /**
     * 采购补价
     */
    @Excel(name = "采购补价",
            prompt = "只能精确到两位小数",
            scale = 20)
    private BigDecimal purchasePremium;

    /**
     * 发货单号
     */
    @Excel(name = "发货单号")
    private String shipmentsOrder;

    /**
     * 是否退货
     */
    @Excel(name = "是否退货",
            readConverterExp = "1=是,2=否",
            prompt = "只能填入：是、否",
            type = Excel.Type.EXPORT)
    private String hasReturn;

    /**
     * 是否白嫖
     */
    @Excel(name = "是否白嫖",
            readConverterExp = "1=是,2=否",
            prompt = "只能填入：是、否",
            type = Excel.Type.EXPORT)
    private String hasBP;

    /**
     * 创建人
     */
    @Excel(name = "创建人", prompt = "客服账号")
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date createTime;

    /**
     * 更新人
     */
//    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 部门
     */
    @Excel(name = "部门", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String deptName;
    private Long deptId;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
