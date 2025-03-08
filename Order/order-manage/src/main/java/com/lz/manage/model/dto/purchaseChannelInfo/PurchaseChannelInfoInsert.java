package com.lz.manage.model.dto.purchaseChannelInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.PurchaseChannelInfo;
/**
 * 采购渠道信息Vo对象 tb_purchase_channel_info
 *
 * @author YY
 * @date 2025-03-08
 */
@Data
public class PurchaseChannelInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 父级 */
    private Long parentId;

    /** 名称 */
    private String channelName;

    /** 渠道类型（1=线上 2=线下） */
    private String channelType;

    /** 排序 */
    private Long orderNum;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param purchaseChannelInfoInsert 插入对象
     * @return PurchaseChannelInfoInsert
     */
    public static PurchaseChannelInfo insertToObj(PurchaseChannelInfoInsert purchaseChannelInfoInsert) {
        if (purchaseChannelInfoInsert == null) {
            return null;
        }
        PurchaseChannelInfo purchaseChannelInfo = new PurchaseChannelInfo();
        BeanUtils.copyProperties(purchaseChannelInfoInsert, purchaseChannelInfo);
        return purchaseChannelInfo;
    }
}
