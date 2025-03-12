package com.lz.manage.model.vo.purchaseOrderInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Project: Order
 * Package: com.lz.manage.model.vo.purchaseOrderInfo
 * Author: YY
 * CreateTime: 2025-03-09  20:57
 * Description: PurchaseOrderReportVo
 * 采购订单根据用户报表
 * Version: 1.0
 */
@Data
public class PurchaseOrderReportByUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID 客服  运营
     */
    private Long userId;


    /**
     * 用户名称
     */
    private String userName;

    private Long storeCount;

    /**
     * 订单总数
     */
    private Long orderCount;

    /**
     * 订单总利润
     */
    private BigDecimal orderProfitCount;

    /**
     * 销售数量
     */
    private Long salesNumberCount;

    /**
     * 销售总价格
     */
    private BigDecimal salesPriceCount;

    /**
     * 销售进价
     */
    private BigDecimal purchasePriceCount;

    /**
     * 采购补价
     */
    private BigDecimal purchasePremiumCount;

    /**
     * 平均订单利润率
     */
    private BigDecimal avgOrderProfitRate;
}
