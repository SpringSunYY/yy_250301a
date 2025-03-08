package com.lz.manage.model.vo.purchaseAccountInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.PurchaseAccountInfo;

/**
 * 采购账号信息Vo对象 tb_purchase_account_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class PurchaseAccountInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String nickName;
    /**
     * 账号类型(1=线上 2=线下)
     */
    @Excel(name = "账号类型", readConverterExp = "只能线上、线下")
    private String accountType;
    /**
     * 采购渠道
     */
    private String purchaseChannelsName;
    private Long purchaseChannelsId;

    /**
     * 采购账号
     */
    @Excel(name = "采购账号")
    private String purchaseAccount;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String userName;
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 部门
     */
    @Excel(name = "部门")
    private String deptName;
    private Long deptId;


    /**
     * 对象转封装类
     *
     * @param purchaseAccountInfo PurchaseAccountInfo实体对象
     * @return PurchaseAccountInfoVo
     */
    public static PurchaseAccountInfoVo objToVo(PurchaseAccountInfo purchaseAccountInfo) {
        if (purchaseAccountInfo == null) {
            return null;
        }
        PurchaseAccountInfoVo purchaseAccountInfoVo = new PurchaseAccountInfoVo();
        BeanUtils.copyProperties(purchaseAccountInfo, purchaseAccountInfoVo);
        return purchaseAccountInfoVo;
    }
}
