package com.lz.manage.model.vo.replacementOrderInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ReplacementOrderInfo;
/**
 * 补单明细Vo对象 tb_replacement_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class ReplacementOrderInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 采购编号 */
    @Excel(name = "采购编号")
    private String orderNumber;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private Long storeId;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateTime;

    /** 微信号 */
    @Excel(name = "微信号")
    private String wxNumber;

    /** 旺旺号 */
    @Excel(name = "旺旺号")
    private String tbNumber;

    /** 实付金额 */
    @Excel(name = "实付金额")
    private BigDecimal actuallyPrice;

    /** 佣金 */
    @Excel(name = "佣金")
    private BigDecimal commission;

    /** 合计金额 */
    @Excel(name = "合计金额")
    private BigDecimal totalPrice;

    /** 返款状态(0=已返 1=未返) */
    @Excel(name = "返款状态(0=已返 1=未返)")
    private String returnStatus;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;


     /**
     * 对象转封装类
     *
     * @param replacementOrderInfo ReplacementOrderInfo实体对象
     * @return ReplacementOrderInfoVo
     */
    public static ReplacementOrderInfoVo objToVo(ReplacementOrderInfo replacementOrderInfo) {
        if (replacementOrderInfo == null) {
            return null;
        }
        ReplacementOrderInfoVo replacementOrderInfoVo = new ReplacementOrderInfoVo();
        BeanUtils.copyProperties(replacementOrderInfo, replacementOrderInfoVo);
        return replacementOrderInfoVo;
    }
}
