package com.lz.manage.model.vo.afterSaleOrderInfo;

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
import com.lz.manage.model.domain.AfterSaleOrderInfo;
/**
 * 售后订单信息Vo对象 tb_after_sale_order_info
 *
 * @author YY
 * @date 2025-03-09
 */
@Data
public class AfterSaleOrderInfoVo implements Serializable
{
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
    private String storeName;
    private Long storeId;

    /**
     * 售后金额
     */
    private BigDecimal afterSalePrice;

    /**
     * 售后日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date afterSaleTime;

    /**
     * 售后凭证
     */
    private String afterSaleImage;

    /**
     * 创建人
     */
    private String userName;
    private Long userId;

    /**
     * 部门
     */
    private String deptName;
    private Long deptId;

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
     * 对象转封装类
     *
     * @param afterSaleOrderInfo AfterSaleOrderInfo实体对象
     * @return AfterSaleOrderInfoVo
     */
    public static AfterSaleOrderInfoVo objToVo(AfterSaleOrderInfo afterSaleOrderInfo) {
        if (afterSaleOrderInfo == null) {
            return null;
        }
        AfterSaleOrderInfoVo afterSaleOrderInfoVo = new AfterSaleOrderInfoVo();
        BeanUtils.copyProperties(afterSaleOrderInfo, afterSaleOrderInfoVo);
        return afterSaleOrderInfoVo;
    }
}
