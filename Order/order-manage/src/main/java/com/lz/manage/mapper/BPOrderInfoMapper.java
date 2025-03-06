package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.BPOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.vo.bPOrderInfo.BPOrderCountVo;

/**
 * 白嫖订单信息Mapper接口
 * 
 * @author YY
 * @date 2025-03-03
 */
public interface BPOrderInfoMapper extends BaseMapper<BPOrderInfo>
{
    /**
     * 查询白嫖订单信息
     * 
     * @param id 白嫖订单信息主键
     * @return 白嫖订单信息
     */
    public BPOrderInfo selectBPOrderInfoById(Long id);

    /**
     * 查询白嫖订单信息列表
     * 
     * @param bPOrderInfo 白嫖订单信息
     * @return 白嫖订单信息集合
     */
    public List<BPOrderInfo> selectBPOrderInfoList(BPOrderInfo bPOrderInfo);

    /**
     * 新增白嫖订单信息
     * 
     * @param bPOrderInfo 白嫖订单信息
     * @return 结果
     */
    public int insertBPOrderInfo(BPOrderInfo bPOrderInfo);

    /**
     * 修改白嫖订单信息
     * 
     * @param bPOrderInfo 白嫖订单信息
     * @return 结果
     */
    public int updateBPOrderInfo(BPOrderInfo bPOrderInfo);

    /**
     * 删除白嫖订单信息
     * 
     * @param id 白嫖订单信息主键
     * @return 结果
     */
    public int deleteBPOrderInfoById(Long id);

    /**
     * 批量删除白嫖订单信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBPOrderInfoByIds(Long[] ids);

    /**
     * description: 获取白嫖订单统计信息
     * author: YY
     * method: getBpOrderCount
     * date: 2025/3/6 21:18
     * param:
     * param: bPOrderInfo
     * return: com.lz.manage.model.vo.bPOrderInfo.BPOrderCountVo
     **/
    BPOrderCountVo getBpOrderCount(BPOrderInfo bPOrderInfo);
}
