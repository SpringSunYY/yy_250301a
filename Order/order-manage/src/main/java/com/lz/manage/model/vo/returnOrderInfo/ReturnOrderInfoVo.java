package com.lz.manage.model.vo.returnOrderInfo;

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
import com.lz.manage.model.domain.ReturnOrderInfo;
/**
 * 退货订单信息Vo对象 tb_return_order_info
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
public class ReturnOrderInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 类型
     */
    private String orderType;

    /**
     * 店铺名称
     */
    private String storeName;
    private Long storeId;

    /**
     * 退货状态
     */
    private String returnStatus;

    /**
     * 客户退货金额
     */
    private BigDecimal returnPrice;

    /**
     * 上家退款金额
     */
    private BigDecimal lastReturnPrice;

    /**
     * 退货完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnAccomplishTime;

    /**
     * 创建人
     */
    private String userName;
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
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
    private String remark;

    /**
     * 部门
     */
    private String deptName;
    private Long deptId;

     /**
     * 对象转封装类
     *
     * @param returnOrderInfo ReturnOrderInfo实体对象
     * @return ReturnOrderInfoVo
     */
    public static ReturnOrderInfoVo objToVo(ReturnOrderInfo returnOrderInfo) {
        if (returnOrderInfo == null) {
            return null;
        }
        ReturnOrderInfoVo returnOrderInfoVo = new ReturnOrderInfoVo();
        BeanUtils.copyProperties(returnOrderInfo, returnOrderInfoVo);
        return returnOrderInfoVo;
    }
}
