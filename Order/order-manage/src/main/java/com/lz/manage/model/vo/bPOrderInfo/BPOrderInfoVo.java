package com.lz.manage.model.vo.bPOrderInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
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
    private Long id;

    /** 采购编号 */
    @Excel(name = "采购编号")
    private String orderNumber;

    /** 类型 */
    private String orderType;

    /** 店铺名称 */
    private String storeName;
    private Long storeId;

    /** 白嫖退款金额 */
    private BigDecimal bpprice;

    /** 白嫖退款日期 */
    private Date bPTime;

    /** 创建人 */
    private String userName;
    private Long userId;

    /** 创建时间 */
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 部门 */
    private String deptName;
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
        bPOrderInfoVo.setBpprice(bPOrderInfo.getBPPrice());
        return bPOrderInfoVo;
    }
}
