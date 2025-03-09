package com.lz.manage.model.dto.returnOrderInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ReturnOrderInfo;

import javax.validation.constraints.NotNull;

/**
 * 退货订单信息Vo对象 tb_return_order_info
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
public class ReturnOrderInfoInsert implements Serializable {
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
    private Long storeId;

    /**
     * 退货状态
     */
    private String returnStatus;

    /**
     * 客户退货金额
     */
    @NotNull(message = "客户退款金额不能为空")
    private BigDecimal returnPrice;

    /**
     * 上家退款金额
     */
    @NotNull(message = "上家退款金额不能为空")
    private BigDecimal lastReturnPrice;

    /**
     * 退货完成日期
     */
    @NotNull(message = "退货完成日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnAccomplishTime;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param returnOrderInfoInsert 插入对象
     * @return ReturnOrderInfoInsert
     */
    public static ReturnOrderInfo insertToObj(ReturnOrderInfoInsert returnOrderInfoInsert) {
        if (returnOrderInfoInsert == null) {
            return null;
        }
        ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
        BeanUtils.copyProperties(returnOrderInfoInsert, returnOrderInfo);
        return returnOrderInfo;
    }
}
