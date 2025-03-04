package com.lz.manage.model.dto.purchaseOrderInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.PurchaseOrderInfo;
/**
 * 采购发货信息Vo对象 tb_purchase_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class PurchaseOrderInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 采购编号 */
    private String orderNumber;

    /** 销售类型（0=其他 1=线上 2=线下） */
    private String orderType;

    /** 订单利润 */
    private BigDecimal orderProfit;

    /** 利润率 */
    private BigDecimal orderProfitRate;

    /** 采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseTime;

    /** 店铺名称 */
    private Long storeId;

    /** 买家 */
    private String buyerNumber;

    /** 销售量 */
    private Long salesNumber;

    /** 销售价 */
    private BigDecimal salesPrice;

    /** 采购渠道分类 */
    private String purchaseChannelType;

    /** 采购渠道 */
    private String purchaseChannelDetail;

    /** 采购账号 */
    private Long purchaseAccountId;

    /** 采购订单编号 */
    private String purchaseOrder;

    /** 供应商名称 */
    private String supplierName;

    /** 采购进价 */
    private BigDecimal purchasePrice;

    /** 采购补价 */
    private BigDecimal purchasePremium;

    /** 发货单号 */
    private String shipmentsOrder;

    /** 是否退货 */
    private String hasReturn;

    /** 是否白嫖 */
    private String hasBP;

    /** 创建人 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param purchaseOrderInfoInsert 插入对象
     * @return PurchaseOrderInfoInsert
     */
    public static PurchaseOrderInfo insertToObj(PurchaseOrderInfoInsert purchaseOrderInfoInsert) {
        if (purchaseOrderInfoInsert == null) {
            return null;
        }
        PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
        BeanUtils.copyProperties(purchaseOrderInfoInsert, purchaseOrderInfo);
        return purchaseOrderInfo;
    }
}
