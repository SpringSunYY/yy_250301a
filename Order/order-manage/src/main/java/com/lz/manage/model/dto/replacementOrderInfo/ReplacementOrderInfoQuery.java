package com.lz.manage.model.dto.replacementOrderInfo;

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
import com.lz.manage.model.domain.ReplacementOrderInfo;
/**
 * 补单明细Query对象 tb_replacement_order_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class ReplacementOrderInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;
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

    /** 返款状态(0=已返 1=未返) */
    private String returnStatus;

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
     * @param replacementOrderInfoQuery 查询对象
     * @return ReplacementOrderInfo
     */
    public static ReplacementOrderInfo queryToObj(ReplacementOrderInfoQuery replacementOrderInfoQuery) {
        if (replacementOrderInfoQuery == null) {
            return null;
        }
        ReplacementOrderInfo replacementOrderInfo = new ReplacementOrderInfo();
        BeanUtils.copyProperties(replacementOrderInfoQuery, replacementOrderInfo);
        return replacementOrderInfo;
    }
}
