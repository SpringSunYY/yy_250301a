package com.lz.manage.model.dto.bPOrderInfo;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.BPOrderInfo;
/**
 * 白嫖订单信息Query对象 tb_b_p_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class BPOrderInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Long id;
    /** 采购编号 */
    private String orderNumber;

    /** 类型 */
    private String orderType;

    /** 店铺名称 */
    private Long storeId;

    /** 白嫖退款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bPTime;

    /** 售后日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date afterSaleTime;

    /** 创建人 */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 部门 */
    private Long deptId;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param bPOrderInfoQuery 查询对象
     * @return BPOrderInfo
     */
    public static BPOrderInfo queryToObj(BPOrderInfoQuery bPOrderInfoQuery) {
        if (bPOrderInfoQuery == null) {
            return null;
        }
        BPOrderInfo bPOrderInfo = new BPOrderInfo();
        BeanUtils.copyProperties(bPOrderInfoQuery, bPOrderInfo);
        return bPOrderInfo;
    }
}
