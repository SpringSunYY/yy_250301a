package com.lz.manage.model.dto.purchaseOrderInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购发货信息Query对象 tb_purchase_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
@Getter
public class PurchaseOrderInfoAndStoreQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 销售类型（0=其他 1=线上 2=线下）
     */
    private String orderType;

    /**
     * 采购日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseTime;

    /**
     * 店铺名称
     */
    private Long storeId;

    /**
     * 买家
     */
    private String buyerNumber;

    /**
     * 采购渠道分类
     */
    private String purchaseChannelType;

    /**
     * 采购渠道
     */
    private Long purchaseChannelsId;

    /**
     * 采购账号
     */
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
     * 部门
     */
    private Long deptId;

    /** 主管 */
    private Long principalId;

    /** 运营 */
    private Long operationId;

    /** 客服 */
    private Long serviceId;

    /** 店铺名称 */
    private String storeName;

    /** 店铺状态（0=其他1=正常 2=地址异常 3=高产 4=优化正常 5=竞争力不足 6=异常 7=好店 8=到期 9=提前到期） */
    private String status;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    private List<Long> purchaseChannelsIds;

    private List<Long> deptIds;

    /**
     * 对象转封装类
     *
     * @param purchaseOrderInfoQuery 查询对象
     * @return PurchaseOrderInfo
     */
    public static PurchaseOrderInfo queryToObj(PurchaseOrderInfoAndStoreQuery purchaseOrderInfoQuery) {
        if (purchaseOrderInfoQuery == null) {
            return null;
        }
        PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
        BeanUtils.copyProperties(purchaseOrderInfoQuery, purchaseOrderInfo);
        return purchaseOrderInfo;
    }
}
