package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.List;
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
 * 售后订单信息对象 tb_after_sale_order_info
 *
 * @author YY
 * @date 2025-03-09
 */
@TableName("tb_after_sale_order_info")
@Data
public class AfterSaleOrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 采购编号
     */
    @Excel(name = "采购编号")
    private String orderNumber;

    /**
     * 类型(1线上 2线下)
     */
    @Excel(name = "类型", readConverterExp = "0=其他,1=线上,2=线下",
            prompt = "只能线上、线下")
    private String orderType;

    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    @TableField(exist = false)
    private String storeName;
    private Long storeId;

    /**
     * 售后金额
     */
    @Excel(name = "售后金额")
    private BigDecimal afterSalePrice;

    /**
     * 售后日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "售后日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date afterSaleTime;

    /**
     * 售后凭证
     */
//    @Excel(name = "售后凭证")
    private String afterSaleImage;

    /**
     * 创建人
     */
    @Excel(name = "客服", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /**
     * 部门
     */
    @Excel(name = "部门")
    @TableField(exist = false)
    private String deptName;
    private Long deptId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd",type = Excel.Type.EXPORT)
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
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    @TableField(exist = false)
    private List<Long> deptIds;
}
