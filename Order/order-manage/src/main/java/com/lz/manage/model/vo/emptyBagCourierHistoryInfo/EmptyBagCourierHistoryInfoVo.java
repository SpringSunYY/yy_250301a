package com.lz.manage.model.vo.emptyBagCourierHistoryInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.EmptyBagCourierHistoryInfo;
/**
 * 空包/快递充值记录Vo对象 tb_empty_bag_courier_history_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class EmptyBagCourierHistoryInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateTime;

    /** 摘要 */
    @Excel(name = "摘要")
    private String digest;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal price;

    /** 创建人 */
    @Excel(name = "创建人")
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

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;


     /**
     * 对象转封装类
     *
     * @param emptyBagCourierHistoryInfo EmptyBagCourierHistoryInfo实体对象
     * @return EmptyBagCourierHistoryInfoVo
     */
    public static EmptyBagCourierHistoryInfoVo objToVo(EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo) {
        if (emptyBagCourierHistoryInfo == null) {
            return null;
        }
        EmptyBagCourierHistoryInfoVo emptyBagCourierHistoryInfoVo = new EmptyBagCourierHistoryInfoVo();
        BeanUtils.copyProperties(emptyBagCourierHistoryInfo, emptyBagCourierHistoryInfoVo);
        return emptyBagCourierHistoryInfoVo;
    }
}
