package com.lz.manage.model.dto.replacementOrderInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ReplacementOrderInfo;
/**
 * 补单明细Vo对象 tb_replacement_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class ReplacementOrderInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 订单编号 */
    private String orderNumber;

    /** 店铺名称 */
    private Long storeId;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateTime;

    /** 微信号 */
    private String wxNumber;

    /** 旺旺号 */
    private String tbNumber;

    /** 实付金额 */
    private BigDecimal actuallyPrice;

    /** 佣金 */
    private BigDecimal commission;

    /** 合计金额 */
    private BigDecimal totalPrice;

    /** 返款状态(0=已返 1=未返) */
    private String returnStatus;

    /** 创建人 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param replacementOrderInfoInsert 插入对象
     * @return ReplacementOrderInfoInsert
     */
    public static ReplacementOrderInfo insertToObj(ReplacementOrderInfoInsert replacementOrderInfoInsert) {
        if (replacementOrderInfoInsert == null) {
            return null;
        }
        ReplacementOrderInfo replacementOrderInfo = new ReplacementOrderInfo();
        BeanUtils.copyProperties(replacementOrderInfoInsert, replacementOrderInfo);
        return replacementOrderInfo;
    }
}
