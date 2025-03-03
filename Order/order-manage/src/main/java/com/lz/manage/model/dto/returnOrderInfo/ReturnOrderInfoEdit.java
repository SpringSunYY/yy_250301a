package com.lz.manage.model.dto.returnOrderInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ReturnOrderInfo;
/**
 * 退货订单信息Vo对象 tb_return_order_info
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
public class ReturnOrderInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 订单编号 */
    private String orderNumber;

    /** 类型 */
    private String orderType;

    /** 店铺名称 */
    private Long storeId;

    /** 退货状态 */
    private String returnStatus;

    /** 客户退货金额 */
    private BigDecimal returnPrice;

    /** 上家退款金额 */
    private BigDecimal lastReturnPrice;

    /** 退货完成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnAccomplishTime;

    /** 创建人 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param returnOrderInfoEdit 编辑对象
     * @return ReturnOrderInfo
     */
    public static ReturnOrderInfo editToObj(ReturnOrderInfoEdit returnOrderInfoEdit) {
        if (returnOrderInfoEdit == null) {
            return null;
        }
        ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
        BeanUtils.copyProperties(returnOrderInfoEdit, returnOrderInfo);
        return returnOrderInfo;
    }
}
