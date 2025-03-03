package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ReplacementOrderInfo;
import com.lz.manage.model.vo.replacementOrderInfo.ReplacementOrderInfoVo;
import com.lz.manage.model.dto.replacementOrderInfo.ReplacementOrderInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 补单明细Service接口
 * 
 * @author YY
 * @date 2025-03-03
 */
public interface IReplacementOrderInfoService extends IService<ReplacementOrderInfo>
{
    //region mybatis代码
    /**
     * 查询补单明细
     * 
     * @param id 补单明细主键
     * @return 补单明细
     */
    public ReplacementOrderInfo selectReplacementOrderInfoById(Long id);

    /**
     * 查询补单明细列表
     * 
     * @param replacementOrderInfo 补单明细
     * @return 补单明细集合
     */
    public List<ReplacementOrderInfo> selectReplacementOrderInfoList(ReplacementOrderInfo replacementOrderInfo);

    /**
     * 新增补单明细
     * 
     * @param replacementOrderInfo 补单明细
     * @return 结果
     */
    public int insertReplacementOrderInfo(ReplacementOrderInfo replacementOrderInfo);

    /**
     * 修改补单明细
     * 
     * @param replacementOrderInfo 补单明细
     * @return 结果
     */
    public int updateReplacementOrderInfo(ReplacementOrderInfo replacementOrderInfo);

    /**
     * 批量删除补单明细
     * 
     * @param ids 需要删除的补单明细主键集合
     * @return 结果
     */
    public int deleteReplacementOrderInfoByIds(Long[] ids);

    /**
     * 删除补单明细信息
     * 
     * @param id 补单明细主键
     * @return 结果
     */
    public int deleteReplacementOrderInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param replacementOrderInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ReplacementOrderInfo> getQueryWrapper(ReplacementOrderInfoQuery replacementOrderInfoQuery);

    /**
     * 转换vo
     *
     * @param replacementOrderInfoList ReplacementOrderInfo集合
     * @return ReplacementOrderInfoVO集合
     */
    List<ReplacementOrderInfoVo> convertVoList(List<ReplacementOrderInfo> replacementOrderInfoList);
}
