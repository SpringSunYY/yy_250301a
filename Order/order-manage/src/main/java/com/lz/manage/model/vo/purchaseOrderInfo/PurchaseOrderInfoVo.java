package com.lz.manage.model.vo.purchaseOrderInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.PurchaseOrderInfo;
/**
 * 采购发货信息Vo对象 tb_purchase_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class PurchaseOrderInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 采购编号
     */
    private String orderNumber;

    /**
     * 销售类型（0=其他 1=线上 2=线下）
     */
    private String orderType;

    /**
     * 订单利润
     */
    private BigDecimal orderProfit;

    /**
     * 利润率
     */
    private BigDecimal orderProfitRate;

    /**
     * 采购日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseTime;

    /**
     * 店铺名称
     */
    private String storeName;
    private Long storeId;

    /**
     * 买家
     */
    private String buyerNumber;

    /**
     * 销售量
     */
    private Long salesNumber;

    /**
     * 销售价
     */
    private BigDecimal salesPrice;

    /**
     * 采购渠道分类
     */
    private String purchaseChannelType;

    /**
     * 采购渠道
     */
    private String purchaseChannelsName;
    private Long purchaseChannelsId;

    /**
     * 采购账号
     */
    private String purchaseAccount;
    private Long purchaseAccountId;

    /**
     * 采购订单编号
     */
    private String purchaseOrder;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 采购进价
     */
    private BigDecimal purchasePrice;

    /**
     * 采购补价
     */
    private BigDecimal purchasePremium;

    /**
     * 发货单号
     */
    private String shipmentsOrder;

    /**
     * 是否退货
     */
    private String hasReturn;

    /**
     * 是否白嫖
     */
    private String hasBP;

    /**
     * 是否售后
     */
    private String hasAfterSale;

    /**
     * 创建人
     */
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门
     */
    private String deptName;
    private Long deptId;


     /**
     * 对象转封装类
     *
     * @param purchaseOrderInfo PurchaseOrderInfo实体对象
     * @return PurchaseOrderInfoVo
     */
    public static PurchaseOrderInfoVo objToVo(PurchaseOrderInfo purchaseOrderInfo) {
        if (purchaseOrderInfo == null) {
            return null;
        }
        PurchaseOrderInfoVo purchaseOrderInfoVo = new PurchaseOrderInfoVo();
        BeanUtils.copyProperties(purchaseOrderInfo, purchaseOrderInfoVo);
        return purchaseOrderInfoVo;
    }
}
