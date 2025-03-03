package com.lz.manage.model.dto.emptyBagCourierHistoryInfo;

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
import com.lz.manage.model.domain.EmptyBagCourierHistoryInfo;
/**
 * 空包/快递充值记录Query对象 tb_empty_bag_courier_history_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class EmptyBagCourierHistoryInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateTime;

    /** 摘要 */
    private String digest;

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

    /** 部门 */
    private Long deptId;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param emptyBagCourierHistoryInfoQuery 查询对象
     * @return EmptyBagCourierHistoryInfo
     */
    public static EmptyBagCourierHistoryInfo queryToObj(EmptyBagCourierHistoryInfoQuery emptyBagCourierHistoryInfoQuery) {
        if (emptyBagCourierHistoryInfoQuery == null) {
            return null;
        }
        EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo = new EmptyBagCourierHistoryInfo();
        BeanUtils.copyProperties(emptyBagCourierHistoryInfoQuery, emptyBagCourierHistoryInfo);
        return emptyBagCourierHistoryInfo;
    }
}
