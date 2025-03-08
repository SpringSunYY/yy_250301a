package com.lz.manage.model.dto.purchaseChannelInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.PurchaseChannelInfo;
/**
 * 采购渠道信息Query对象 tb_purchase_channel_info
 *
 * @author YY
 * @date 2025-03-08
 */
@Data
public class PurchaseChannelInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 名称 */
    private String channelName;

    /** 渠道类型（1=线上 2=线下） */
    private String channelType;

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

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param purchaseChannelInfoQuery 查询对象
     * @return PurchaseChannelInfo
     */
    public static PurchaseChannelInfo queryToObj(PurchaseChannelInfoQuery purchaseChannelInfoQuery) {
        if (purchaseChannelInfoQuery == null) {
            return null;
        }
        PurchaseChannelInfo purchaseChannelInfo = new PurchaseChannelInfo();
        BeanUtils.copyProperties(purchaseChannelInfoQuery, purchaseChannelInfo);
        return purchaseChannelInfo;
    }
}
