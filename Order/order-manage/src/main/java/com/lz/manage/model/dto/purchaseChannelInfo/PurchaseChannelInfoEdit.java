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
public class PurchaseChannelInfoEdit implements Serializable
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
     * @param purchaseChannelInfoEdit 编辑对象
     * @return PurchaseChannelInfo
     */
    public static PurchaseChannelInfo editToObj(PurchaseChannelInfoEdit purchaseChannelInfoEdit) {
        if (purchaseChannelInfoEdit == null) {
            return null;
        }
        PurchaseChannelInfo purchaseChannelInfo = new PurchaseChannelInfo();
        BeanUtils.copyProperties(purchaseChannelInfoEdit, purchaseChannelInfo);
        return purchaseChannelInfo;
    }
}
