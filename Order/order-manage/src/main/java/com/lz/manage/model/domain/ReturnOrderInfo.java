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
 * 退货订单信息对象 tb_return_order_info
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@TableName("tb_return_order_info")
@Data
public class ReturnOrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
//    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderNumber;

    /**
     * 类型
     */
    @Excel(name = "类型", readConverterExp = "1=其他,2=线上,3=线下", prompt = "只能线上、线下",type = Excel.Type.EXPORT)
    private String orderType;

    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称",type= Excel.Type.EXPORT)
    @TableField(exist = false)
    private String storeName;
    private Long storeId;

    /**
     * 退货状态
     */
    @Excel(name = "退货状态", readConverterExp = "1=未完成,2=已完成", prompt = "只能填入：未完成、已完成")
    private String returnStatus;

    /**
     * 客户退货金额
     */
    @Excel(name = "客户退货金额", prompt = "精确到两位小数", scale = 20)
    private BigDecimal returnPrice;

    /**
     * 上家退款金额
     */
    @Excel(name = "上家退款金额", prompt = "精确到两位小数", scale = 20)
    private BigDecimal lastReturnPrice;

    /**
     * 退货完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退货完成日期", width = 30, dateFormat = "yyyy-MM-dd", prompt = "时间格式：yyyy-MM-dd")
    private Date returnAccomplishTime;

    /**
     * 创建人
     */
    @Excel(name = "创建人", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
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
     * 部门
     */
    @Excel(name = "部门", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String deptName;
    private Long deptId;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    @TableField(exist = false)
    private List<Long> deptIds;
}
