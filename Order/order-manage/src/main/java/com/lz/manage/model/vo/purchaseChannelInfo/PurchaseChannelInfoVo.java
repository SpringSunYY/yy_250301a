package com.lz.manage.model.vo.purchaseChannelInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.PurchaseChannelInfo;
/**
 * 采购渠道信息Vo对象 tb_purchase_channel_info
 *
 * @author YY
 * @date 2025-03-08
 */
@Data
public class PurchaseChannelInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 父级 */
    @Excel(name = "父级")
    private Long parentId;

    /** 名称 */
    @Excel(name = "名称")
    private String channelName;

    /** 渠道类型（1=线上 2=线下） */
    @Excel(name = "渠道类型", readConverterExp = "1=线上,2=线下")
    private String channelType;

    /** 创建人 */
    private String userName;
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;


    /**
     * 对象转封装类
     *
     * @param purchaseChannelInfo PurchaseChannelInfo实体对象
     * @return PurchaseChannelInfoVo
     */
    public static PurchaseChannelInfoVo objToVo(PurchaseChannelInfo purchaseChannelInfo) {
        if (purchaseChannelInfo == null) {
            return null;
        }
        PurchaseChannelInfoVo purchaseChannelInfoVo = new PurchaseChannelInfoVo();
        BeanUtils.copyProperties(purchaseChannelInfo, purchaseChannelInfoVo);
        return purchaseChannelInfoVo;
    }
}
