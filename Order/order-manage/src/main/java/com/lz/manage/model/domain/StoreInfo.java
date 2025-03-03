package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 店铺信息对象 tb_store_info
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@TableName("tb_store_info")
@Data
public class StoreInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
//    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 主管
     */
    @Excel(name = "主管",
            prompt = "主管账号")
    @TableField(exist = false)
    private String principalName;
    private Long principalId;

    /**
     * 运营
     */
    @Excel(name = "运营",
            prompt = "运营账号")
    @TableField(exist = false)
    private String operationName;
    private Long operationId;

    /**
     * 客服
     */
    @Excel(name = "客服",
            prompt = "客服账号")
    @TableField(exist = false)
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
    @Excel(name = "店铺状态",
            readConverterExp = "0=其他,1=正常,2=地址异常,3=高产,4=优化正常,5=竞争力不足,6=异常,7=好店,8=到期,9=提前到期",
            prompt = "状态只能为：其他、正常、地址异常、高产、优化正常、竞争力不足、异常、好店、到期、提前到期")
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
    @Excel(name = "服务器费用",
    scale = 20,
    prompt = "精确到两位小数")
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
    @Excel(name = "开通金额",
            scale = 20,
            prompt = "精确到两位小数")
    private BigDecimal openPrice;

    /**
     * 是否结佣（1=是 2=否）
     */
    @Excel(name = "是否结佣",
            readConverterExp = "1=是,2=否",
            prompt = "只能填入：是、否")
    private String isCommissionSettlement;

    /**
     * 下店时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下店时间", width = 30, dateFormat = "yyyy-MM-dd", prompt = "时间格式：yyyy-MM-dd 年-月-日")
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
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd", prompt = "时间格式：yyyy-MM-dd 年-月-日")
    private Date expireTime;

    /**
     * 店铺订单是否处理完毕（1=是 2=否）
     */
    @Excel(name = "店铺订单是否处理完毕",
            readConverterExp = "1=是,2=否",
            prompt = "只能填入：是、否")
    private String isOrderAccomplish;

    /**
     * 诚意赊是否关闭（1=是 2=否）
     */
    @Excel(name = "诚意赊是否关闭",
            readConverterExp = "1=是,2=否",
            prompt = "只能填入：是、否")
    private String isBonaFideRedemption;

    /**
     * 保证金是否退出（1=是 2=否）
     */
    @Excel(name = "保证金是否退出",
            readConverterExp = "1=是,2=否",
            prompt = "只能填入：是、否")
    private String isBail;

    /**
     * 支付宝是否解绑（1=是 2=否）
     */
    @Excel(name = "支付宝是否解绑",
            readConverterExp = "1=是,2=否",
            prompt = "只能填入：是、否")
    private String isAlipay;

    /**
     * 部门
     */
    @Excel(name = "部门", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String deptName;
    @Excel(name = "部门编号", type = Excel.Type.IMPORT)
    private Long deptId;

    /**
     * 创建人
     */
    @Excel(name = "创建人", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date createTime;

    /**
     * 更新人
     */
//    @Excel(name = "更新人", type = Excel.Type.EXPORT)
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    @TableField(exist = false)
    private List<Long> deptIds;
}
