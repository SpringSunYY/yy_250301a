package com.lz.manage.service;

import java.util.List;

import com.lz.manage.model.domain.ReplacementOrderInfo;
import com.lz.manage.model.vo.replacementOrderInfo.ReplacementOrderCountVo;
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
public interface IReplacementOrderInfoService extends IService<ReplacementOrderInfo> {
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

    /**
     * description: 根据订单编号查询补单
     * author: YY
     * method: selectReplacementOrderInfoByOrderNumber
     * date: 2025/3/6 11:30
     * param:
     * param: orderNumber
     * return: com.lz.manage.model.domain.ReplacementOrderInfo
     **/
    ReplacementOrderInfo selectReplacementOrderInfoByOrderNumber(String orderNumber);

    /**
     * description: 导入补单明细
     * author: YY
     * method: importReplacementOrderInfo
     * date: 2025/3/6 11:43
     * param:
     * param: replacementOrderInfoList
     * return: java.lang.String
     **/
    String importReplacementOrderInfo(List<ReplacementOrderInfo> replacementOrderInfoList);

    /**
     * description: 获取总数
     * author: YY
     * method: getReplacementOrderCount
     * date: 2025/3/6 20:56
     * param:
     * param: replacementOrderInfo
     * return: com.lz.manage.model.vo.replacementOrderInfo.ReplacementOrderCountVo
     **/
    ReplacementOrderCountVo getReplacementOrderCount(ReplacementOrderInfo replacementOrderInfo);
}
