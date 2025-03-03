package com.lz.manage.model.vo.storeInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.StoreInfo;

/**
 * 店铺信息Vo对象 tb_store_info
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
public class StoreInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private Long id;

    /**
     * 主管
     */
    @Excel(name = "主管")
    private String principalName;
    private Long principalId;

    /**
     * 运营
     */
    @Excel(name = "运营")
    private String operationName;
    private Long operationId;

    /**
     * 客服
     */
    @Excel(name = "客服")
    private String serviceName;
    private Long serviceId;

    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    private String storeName;

    /**
     * 店铺状态（0=其他1=正常 2=地址异常 3=高产 4=优化正常 5=竞争力不足 6=异常 7=好店 8=到期 9=提前到期）
     */
    @Excel(name = "店铺状态", readConverterExp = "0=其他,1=正常,2=地址异常,3=高产,4=优化正常,5=竞争力不足,6=异常,7=好店,8=到期,9=提前到期")
    private String status;

    /**
     * 店铺密码
     */
    @Excel(name = "店铺密码")
    private String storePassword;

    /**
     * 店铺手机号
     */
    @Excel(name = "店铺手机号")
    private String storePhone;

    /**
     * 支付宝账号
     */
    @Excel(name = "支付宝账号")
    private String alipayAccount;

    /**
     * 支付宝登录密码
     */
    @Excel(name = "支付宝登录密码")
    private String alipayPassword;

    /**
     * 支付宝支付密码
     */
    @Excel(name = "支付宝支付密码")
    private String alipayPayPassword;

    /**
     * 支付宝手机号
     */
    @Excel(name = "支付宝手机号")
    private String alipayPhone;

    /**
     * 支付宝认证人
     */
    @Excel(name = "支付宝认证人")
    private String alipayAuthenticator;

    /**
     * 支付宝提供人
     */
    @Excel(name = "支付宝提供人")
    private String alipayProvider;

    /**
     * 服务器IP
     */
    @Excel(name = "服务器IP")
    private String serverIp;

    /**
     * 服务器费用
     */
    @Excel(name = "服务器费用")
    private BigDecimal serverCost;

    /**
     * 服务器密码
     */
    @Excel(name = "服务器密码")
    private String serverPassword;

    /**
     * 渠道
     */
    @Excel(name = "渠道")
    private String channels;

    /**
     * 开通金额
     */
    @Excel(name = "开通金额")
    private BigDecimal openPrice;

    /**
     * 是否结佣（1=是 2=否）
     */
    @Excel(name = "是否结佣", readConverterExp = "1=是,2=否")
    private String isCommissionSettlement;

    /**
     * 下店时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下店时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date departureTime;

    /**
     * 营业执照名称
     */
    @Excel(name = "营业执照名称")
    private String businessLicenseName;

    /**
     * 法人
     */
    @Excel(name = "法人")
    private String legalPerson;

    /**
     * 到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    /**
     * 店铺订单是否处理完毕（1=是 2=否）
     */
    @Excel(name = "店铺订单是否处理完毕", readConverterExp = "1=是,2=否")
    private String isOrderAccomplish;

    /**
     * 诚意赊是否关闭（1=是 2=否）
     */
    @Excel(name = "诚意赊是否关闭", readConverterExp = "1=是,2=否")
    private String isBonaFideRedemption;

    /**
     * 保证金是否退出（1=是 2=否）
     */
    @Excel(name = "保证金是否退出", readConverterExp = "1=是,2=否")
    private String isBail;

    /**
     * 支付宝是否解绑（1=是 2=否）
     */
    @Excel(name = "支付宝是否解绑", readConverterExp = "1=是,2=否")
    private String isAlipay;

    /**
     * 部门
     */
    @Excel(name = "部门")
    private String deptName;
    private Long deptId;

    private String userName;
    /**
     * 创建人
     */
    @Excel(name = "创建人")
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
     * 对象转封装类
     *
     * @param storeInfo StoreInfo实体对象
     * @return StoreInfoVo
     */
    public static StoreInfoVo objToVo(StoreInfo storeInfo) {
        if (storeInfo == null) {
            return null;
        }
        StoreInfoVo storeInfoVo = new StoreInfoVo();
        BeanUtils.copyProperties(storeInfo, storeInfoVo);
        return storeInfoVo;
    }
}
