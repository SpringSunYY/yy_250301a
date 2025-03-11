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
 * 补单明细对象 tb_replacement_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@TableName("tb_replacement_order_info")
@Data
public class ReplacementOrderInfo implements Serializable {
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
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    @TableField(exist = false)
    private String storeName;
    private Long storeId;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd", prompt = "时间格式：yyyy-MM-dd 年-月-日")
    private Date dateTime;

    /**
     * 微信号
     */
    @Excel(name = "微信号")
    private String wxNumber;

    /**
     * 旺旺号
     */
    @Excel(name = "旺旺号")
    private String tbNumber;

    /**
     * 实付金额
     */
    @Excel(name = "实付金额",scale = 20,prompt = "精确到两位小数")
    private BigDecimal actuallyPrice;

    /**
     * 佣金
     */
    @Excel(name = "佣金", prompt = "精确到两位小数",scale = 20)
    private BigDecimal commission;

    /**
     * 合计金额
     */
    @Excel(name = "合计金额", prompt = "精确到两位小数",scale = 20,type = Excel.Type.EXPORT)
    private BigDecimal totalPrice;

    /**
     * 返款状态(0=已返 1=未返)
     */
    @Excel(name = "返款状态", readConverterExp = "1=已返,2=未返", prompt = "只能填入：已返、未返")
    private String returnStatus;

    /**
     * 创建人
     */
    @Excel(name = "客服")
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
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
