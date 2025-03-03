package com.lz.manage.model.dto.storeInfo;

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
import com.lz.manage.model.domain.StoreInfo;
/**
 * 店铺信息Query对象 tb_store_info
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
public class StoreInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

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

    /** 店铺手机号 */
    private String storePhone;

    /** 支付宝账号 */
    private String alipayAccount;

    /** 支付宝手机号 */
    private String alipayPhone;

    /** 支付宝认证人 */
    private String alipayAuthenticator;

    /** 支付宝提供人 */
    private String alipayProvider;

    /** 服务器IP */
    private String serverIp;

    /** 渠道 */
    private String channels;

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
     * @param storeInfoQuery 查询对象
     * @return StoreInfo
     */
    public static StoreInfo queryToObj(StoreInfoQuery storeInfoQuery) {
        if (storeInfoQuery == null) {
            return null;
        }
        StoreInfo storeInfo = new StoreInfo();
        BeanUtils.copyProperties(storeInfoQuery, storeInfo);
        return storeInfo;
    }
}
