package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.EmptyBagCourierHistoryInfo;
import com.lz.manage.model.vo.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryInfoVo;
import com.lz.manage.model.dto.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 空包/快递充值记录Service接口
 * 
 * @author YY
 * @date 2025-03-03
 */
public interface IEmptyBagCourierHistoryInfoService extends IService<EmptyBagCourierHistoryInfo>
{
    //region mybatis代码
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
     * 批量删除空包/快递充值记录
     * 
     * @param ids 需要删除的空包/快递充值记录主键集合
     * @return 结果
     */
    public int deleteEmptyBagCourierHistoryInfoByIds(Long[] ids);

    /**
     * 删除空包/快递充值记录信息
     * 
     * @param id 空包/快递充值记录主键
     * @return 结果
     */
    public int deleteEmptyBagCourierHistoryInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param emptyBagCourierHistoryInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<EmptyBagCourierHistoryInfo> getQueryWrapper(EmptyBagCourierHistoryInfoQuery emptyBagCourierHistoryInfoQuery);

    /**
     * 转换vo
     *
     * @param emptyBagCourierHistoryInfoList EmptyBagCourierHistoryInfo集合
     * @return EmptyBagCourierHistoryInfoVO集合
     */
    List<EmptyBagCourierHistoryInfoVo> convertVoList(List<EmptyBagCourierHistoryInfo> emptyBagCourierHistoryInfoList);
}
