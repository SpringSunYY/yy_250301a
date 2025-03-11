package com.lz.manage.model.dto.afterSaleOrderInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.annotation.Excel;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.AfterSaleOrderInfo;

import javax.validation.constraints.NotNull;

/**
 * 售后订单信息Vo对象 tb_after_sale_order_info
 *
 * @author YY
 * @date 2025-03-09
 */
@Data
public class AfterSaleOrderInfoInsert implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 采购编号
     */
    private String orderNumber;

    /**
     * 类型(1线上 2线下)
     */
    private String orderType;

    /**
     * 店铺名称
     */
    private Long storeId;

    /**
     * 售后金额
     */
    @NotNull(message = "售后金额不能为空")
    private BigDecimal afterSalePrice;

    /**
     * 售后日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "售后日期不能为空")
    private Date afterSaleTime;

    /**
     * 售后凭证
     */
    @NotNull(message = "售后凭证不能为空")
    private String afterSaleImage;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param afterSaleOrderInfoInsert 插入对象
     * @return AfterSaleOrderInfoInsert
     */
    public static AfterSaleOrderInfo insertToObj(AfterSaleOrderInfoInsert afterSaleOrderInfoInsert) {
        if (afterSaleOrderInfoInsert == null) {
            return null;
        }
        AfterSaleOrderInfo afterSaleOrderInfo = new AfterSaleOrderInfo();
        BeanUtils.copyProperties(afterSaleOrderInfoInsert, afterSaleOrderInfo);
        return afterSaleOrderInfo;
    }
}
