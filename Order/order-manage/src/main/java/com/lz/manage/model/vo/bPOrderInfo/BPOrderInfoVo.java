package com.lz.manage.model.vo.bPOrderInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.BPOrderInfo;
/**
 * 白嫖订单信息Vo对象 tb_b_p_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class BPOrderInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 采购编号 */
    @Excel(name = "采购编号")
    private String orderNumber;

    /** 类型 */
    @Excel(name = "类型")
    private String orderType;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private Long storeId;

    /** 白嫖退款金额 */
    @Excel(name = "白嫖退款金额")
    private BigDecimal bPPrice;

    /** 白嫖退款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "白嫖退款日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bPTime;

    /** 售后金额 */
    @Excel(name = "售后金额")
    private BigDecimal afterSalePrice;

    /** 售后日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "售后日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date afterSaleTime;

    /** 售后凭证 */
    @Excel(name = "售后凭证")
    private String afterSaleImage;

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
     * @param bPOrderInfo BPOrderInfo实体对象
     * @return BPOrderInfoVo
     */
    public static BPOrderInfoVo objToVo(BPOrderInfo bPOrderInfo) {
        if (bPOrderInfo == null) {
            return null;
        }
        BPOrderInfoVo bPOrderInfoVo = new BPOrderInfoVo();
        BeanUtils.copyProperties(bPOrderInfo, bPOrderInfoVo);
        return bPOrderInfoVo;
    }
}
