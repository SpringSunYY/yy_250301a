package com.lz.manage.model.dto.afterSaleOrderInfo;

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
import com.lz.manage.model.domain.AfterSaleOrderInfo;
/**
 * 售后订单信息Query对象 tb_after_sale_order_info
 *
 * @author YY
 * @date 2025-03-09
 */
@Data
public class AfterSaleOrderInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 采购编号 */
    private String orderNumber;

    /** 类型(1线上 2线下) */
    private String orderType;

    /** 店铺名称 */
    private Long storeId;

    /** 售后金额 */
    private BigDecimal afterSalePrice;

    /** 售后日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date afterSaleTime;

    /** 售后凭证 */
    private String afterSaleImage;

    /** 创建人 */
    private Long userId;

    /** 部门 */
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param afterSaleOrderInfoQuery 查询对象
     * @return AfterSaleOrderInfo
     */
    public static AfterSaleOrderInfo queryToObj(AfterSaleOrderInfoQuery afterSaleOrderInfoQuery) {
        if (afterSaleOrderInfoQuery == null) {
            return null;
        }
        AfterSaleOrderInfo afterSaleOrderInfo = new AfterSaleOrderInfo();
        BeanUtils.copyProperties(afterSaleOrderInfoQuery, afterSaleOrderInfo);
        return afterSaleOrderInfo;
    }
}
