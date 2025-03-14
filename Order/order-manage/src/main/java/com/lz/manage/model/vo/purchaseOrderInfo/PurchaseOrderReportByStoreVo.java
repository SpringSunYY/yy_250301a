package com.lz.manage.model.vo.purchaseOrderInfo;

import com.lz.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Project: Order
 * Package: com.lz.manage.model.vo.purchaseOrderInfo
 * Author: YY
 * CreateTime: 2025-03-09  20:57
 * Description: PurchaseOrderReportVo
 * 采购订单根据店铺报表
 * Version: 1.0
 */
@Data
public class PurchaseOrderReportByStoreVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺
     */
    private Long storeId;

    /**
     * 部门名称
     */
    private String deptName;


    /**
     * 店铺名称
     */
    private String storeName;


    /**
     * 主管
     */
    private String principalName;
    private Long principalId;

    /**
     * 运营
     */
    private String operationName;
    private Long operationId;

    /**
     * 客服
     */
    private String serviceName;
    private Long serviceId;


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
