package com.lz.manage.model.dto.purchaseAccountInfo;

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
import com.lz.manage.model.domain.PurchaseAccountInfo;

/**
 * 采购账号信息Query对象 tb_purchase_account_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class PurchaseAccountInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 名称
     */
    private String nickName;

    /**
     * 账号类型(1=线上 2=线下)
     */
    private String accountType;

    /**
     * 采购渠道
     */
    private Long purchaseChannelsId;

    /**
     * 采购账号
     */
    private String purchaseAccount;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 部门
     */
    private Long deptId;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param purchaseAccountInfoQuery 查询对象
     * @return PurchaseAccountInfo
     */
    public static PurchaseAccountInfo queryToObj(PurchaseAccountInfoQuery purchaseAccountInfoQuery) {
        if (purchaseAccountInfoQuery == null) {
            return null;
        }
        PurchaseAccountInfo purchaseAccountInfo = new PurchaseAccountInfo();
        BeanUtils.copyProperties(purchaseAccountInfoQuery, purchaseAccountInfo);
        return purchaseAccountInfo;
    }
}
