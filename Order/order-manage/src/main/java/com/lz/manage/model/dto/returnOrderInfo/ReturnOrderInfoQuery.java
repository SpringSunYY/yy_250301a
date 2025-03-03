package com.lz.manage.model.dto.returnOrderInfo;

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
import com.lz.manage.model.domain.ReturnOrderInfo;
/**
 * 退货订单信息Query对象 tb_return_order_info
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
public class ReturnOrderInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 订单编号 */
    private String orderNumber;

    /** 类型 */
    private String orderType;

    /** 店铺名称 */
    private Long storeId;

    /** 退货状态 */
    private String returnStatus;

    /** 退货完成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnAccomplishTime;

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
     * @param returnOrderInfoQuery 查询对象
     * @return ReturnOrderInfo
     */
    public static ReturnOrderInfo queryToObj(ReturnOrderInfoQuery returnOrderInfoQuery) {
        if (returnOrderInfoQuery == null) {
            return null;
        }
        ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
        BeanUtils.copyProperties(returnOrderInfoQuery, returnOrderInfo);
        return returnOrderInfo;
    }
}
