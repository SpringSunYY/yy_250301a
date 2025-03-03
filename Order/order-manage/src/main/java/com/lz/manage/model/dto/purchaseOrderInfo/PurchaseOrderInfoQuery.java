package com.lz.manage.model.dto.purchaseOrderInfo;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.PurchaseOrderInfo;
/**
 * 采购发货信息Query对象 tb_purchase_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class PurchaseOrderInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 采购编号 */
    private String orderNumber;

    /** 销售类型（0=其他 1=线上 2=线下） */
    private String orderType;

    /** 采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseTime;

    /** 店铺名称 */
    private Long storeId;

    /** 买家 */
    private String buyerNumber;

    /** 采购渠道分类 */
    private String purchaseChannelType;

    /** 采购渠道 */
    private String purchaseChannelDetail;

    /** 采购账号 */
    private String purchaseAccount;

    /** 采购订单编号 */
    private String purchaseOrder;

    /** 供应商名称 */
    private String supplierName;

    /** 发货单号 */
    private String shipmentsOrder;

    /** 是否退货 */
    private String hasReturn;

    /** 是否白嫖 */
    private String hasBP;

    /** 创建人 */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 部门 */
    private Long deptId;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param purchaseOrderInfoQuery 查询对象
     * @return PurchaseOrderInfo
     */
    public static PurchaseOrderInfo queryToObj(PurchaseOrderInfoQuery purchaseOrderInfoQuery) {
        if (purchaseOrderInfoQuery == null) {
            return null;
        }
        PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
        BeanUtils.copyProperties(purchaseOrderInfoQuery, purchaseOrderInfo);
        return purchaseOrderInfo;
    }
}
