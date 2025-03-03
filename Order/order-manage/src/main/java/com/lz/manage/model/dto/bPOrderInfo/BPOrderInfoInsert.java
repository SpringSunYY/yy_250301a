package com.lz.manage.model.dto.bPOrderInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.BPOrderInfo;
/**
 * 白嫖订单信息Vo对象 tb_b_p_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class BPOrderInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 采购编号 */
    private String orderNumber;

    /** 类型 */
    private String orderType;

    /** 店铺名称 */
    private Long storeId;

    /** 白嫖退款金额 */
    private BigDecimal bPPrice;

    /** 白嫖退款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bPTime;

    /** 售后金额 */
    private BigDecimal afterSalePrice;

    /** 售后日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date afterSaleTime;

    /** 售后凭证 */
    private String afterSaleImage;

    /** 创建人 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param bPOrderInfoInsert 插入对象
     * @return BPOrderInfoInsert
     */
    public static BPOrderInfo insertToObj(BPOrderInfoInsert bPOrderInfoInsert) {
        if (bPOrderInfoInsert == null) {
            return null;
        }
        BPOrderInfo bPOrderInfo = new BPOrderInfo();
        BeanUtils.copyProperties(bPOrderInfoInsert, bPOrderInfo);
        return bPOrderInfo;
    }
}
