package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.EmptyBagCourierHistoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.vo.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryCountVo;

/**
 * 空包/快递充值记录Mapper接口
 * 
 * @author YY
 * @date 2025-03-03
 */
public interface EmptyBagCourierHistoryInfoMapper extends BaseMapper<EmptyBagCourierHistoryInfo>
{
    /**
     * 查询空包/快递充值记录
     * 
     * @param id 空包/快递充值记录主键
     * @return 空包/快递充值记录
     */
    public EmptyBagCourierHistoryInfo selectEmptyBagCourierHistoryInfoById(Long id);

    /**
     * 查询空包/快递充值记录列表
     * 
     * @param emptyBagCourierHistoryInfo 空包/快递充值记录
     * @return 空包/快递充值记录集合
     */
    public List<EmptyBagCourierHistoryInfo> selectEmptyBagCourierHistoryInfoList(EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo);

    /**
     * 新增空包/快递充值记录
     * 
     * @param emptyBagCourierHistoryInfo 空包/快递充值记录
     * @return 结果
     */
    public int insertEmptyBagCourierHistoryInfo(EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo);

    /**
     * 修改空包/快递充值记录
     * 
     * @param emptyBagCourierHistoryInfo 空包/快递充值记录
     * @return 结果
     */
    public int updateEmptyBagCourierHistoryInfo(EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo);

    /**
     * 删除空包/快递充值记录
     * 
     * @param id 空包/快递充值记录主键
     * @return 结果
     */
    public int deleteEmptyBagCourierHistoryInfoById(Long id);

    /**
     * 批量删除空包/快递充值记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEmptyBagCourierHistoryInfoByIds(Long[] ids);

    /**
     * description: 统计总数
     * author: YY
     * method: getEmptyBagCourierHistoryCount
     * date: 2025/3/6 21:13
     * param:
     * param: emptyBagCourierHistoryInfo
     * return: com.lz.manage.model.vo.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryCountVo
     **/
    EmptyBagCourierHistoryCountVo getEmptyBagCourierHistoryCount(EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo);
}
