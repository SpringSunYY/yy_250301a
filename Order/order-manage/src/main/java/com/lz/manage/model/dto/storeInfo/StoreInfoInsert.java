package com.lz.manage.model.dto.storeInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.StoreInfo;
/**
 * 店铺信息Vo对象 tb_store_info
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
public class StoreInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 主管 */
    private Long principalId;

    /** 运营 */
    private Long operationId;

    /** 客服 */
    private Long serviceId;

    /** 店铺名称 */
    private String storeName;

    /** 店铺状态（0=其他1=正常 2=地址异常 3=高产 4=优化正常 5=竞争力不足 6=异常 7=好店 8=到期 9=提前到期） */
    private String status;

    /** 店铺密码 */
    private String storePassword;

    /** 店铺手机号 */
    private String storePhone;

    /** 支付宝账号 */
    private String alipayAccount;

    /** 支付宝登录密码 */
    private String alipayPassword;

    /** 支付宝支付密码 */
    private String alipayPayPassword;

    /** 支付宝手机号 */
    private String alipayPhone;

    /** 支付宝认证人 */
    private String alipayAuthenticator;

    /** 支付宝提供人 */
    private String alipayProvider;

    /** 服务商 */
    private String serviceProvider;

    /** 服务器IP */
    private String serverIp;

    /** 服务器费用 */
    private BigDecimal serverCost;

    /** 服务器密码 */
    private String serverPassword;

    /** 渠道 */
    private String channels;

    /** 开通金额 */
    private BigDecimal openPrice;

    /** 是否结佣（1=是 2=否） */
    private String isCommissionSettlement;

    /** 下店时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date departureTime;

    /** 营业执照名称 */
    private String businessLicenseName;

    /** 法人 */
    private String legalPerson;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireTime;

    /** 店铺订单是否处理完毕（1=是 2=否） */
    private String isOrderAccomplish;

    /** 诚意赊是否关闭（1=是 2=否） */
    private String isBonaFideRedemption;

    /** 保证金是否退出（1=是 2=否） */
    private String isBail;

    /** 支付宝是否解绑（1=是 2=否） */
    private String isAlipay;

    /** 部门 */
    private Long deptId;

    /** 创建人 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param storeInfoInsert 插入对象
     * @return StoreInfoInsert
     */
    public static StoreInfo insertToObj(StoreInfoInsert storeInfoInsert) {
        if (storeInfoInsert == null) {
            return null;
        }
        StoreInfo storeInfo = new StoreInfo();
        BeanUtils.copyProperties(storeInfoInsert, storeInfo);
        return storeInfo;
    }
}
