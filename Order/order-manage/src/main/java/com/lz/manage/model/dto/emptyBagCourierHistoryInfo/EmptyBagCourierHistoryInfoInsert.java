package com.lz.manage.model.dto.emptyBagCourierHistoryInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.EmptyBagCourierHistoryInfo;
/**
 * 空包/快递充值记录Vo对象 tb_empty_bag_courier_history_info
 *
 * @author YY
 * @date 2025-03-03
 */
@Data
public class EmptyBagCourierHistoryInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateTime;

    /** 摘要 */
    private String digest;

    /** 金额 */
    private BigDecimal price;

    /** 创建人 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param emptyBagCourierHistoryInfoInsert 插入对象
     * @return EmptyBagCourierHistoryInfoInsert
     */
    public static EmptyBagCourierHistoryInfo insertToObj(EmptyBagCourierHistoryInfoInsert emptyBagCourierHistoryInfoInsert) {
        if (emptyBagCourierHistoryInfoInsert == null) {
            return null;
        }
        EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo = new EmptyBagCourierHistoryInfo();
        BeanUtils.copyProperties(emptyBagCourierHistoryInfoInsert, emptyBagCourierHistoryInfo);
        return emptyBagCourierHistoryInfo;
    }
}
