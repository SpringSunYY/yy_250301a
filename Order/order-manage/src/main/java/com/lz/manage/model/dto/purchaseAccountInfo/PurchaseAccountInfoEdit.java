package com.lz.manage.model.dto.purchaseAccountInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.PurchaseAccountInfo;
/**
 * 采购账号信息Vo对象 tb_purchase_account_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class PurchaseAccountInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 名称 */
    private String nickName;

    /** 账号类型(1=线上 2=线下) */
    private String accountType;

    /** 采购渠道 */
    private Long purchaseChannelsId;

    /** 采购账号 */
    private String purchaseAccount;

    /** 创建人 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param purchaseAccountInfoEdit 编辑对象
     * @return PurchaseAccountInfo
     */
    public static PurchaseAccountInfo editToObj(PurchaseAccountInfoEdit purchaseAccountInfoEdit) {
        if (purchaseAccountInfoEdit == null) {
            return null;
        }
        PurchaseAccountInfo purchaseAccountInfo = new PurchaseAccountInfo();
        BeanUtils.copyProperties(purchaseAccountInfoEdit, purchaseAccountInfo);
        return purchaseAccountInfo;
    }
}
