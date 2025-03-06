package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ReplacementOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.vo.replacementOrderInfo.ReplacementOrderCountVo;

/**
 * 补单明细Mapper接口
 * 
 * @author YY
 * @date 2025-03-03
 */
public interface ReplacementOrderInfoMapper extends BaseMapper<ReplacementOrderInfo>
{
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
     * 删除补单明细
     * 
     * @param id 补单明细主键
     * @return 结果
     */
    public int deleteReplacementOrderInfoById(Long id);

    /**
     * 批量删除补单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReplacementOrderInfoByIds(Long[] ids);

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
