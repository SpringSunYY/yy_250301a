package com.lz.manage.mapper;

import java.util.List;

import com.lz.manage.model.domain.ReturnOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.vo.returnOrderInfo.ReturnOrderCountVo;

/**
 * 退货订单信息Mapper接口
 *
 * @author ruoyi
 * @date 2025-03-03
 */
public interface ReturnOrderInfoMapper extends BaseMapper<ReturnOrderInfo> {
    /**
     * 查询退货订单信息
     *
     * @param id 退货订单信息主键
     * @return 退货订单信息
     */
    public ReturnOrderInfo selectReturnOrderInfoById(Long id);

    /**
     * 查询退货订单信息列表
     *
     * @param returnOrderInfo 退货订单信息
     * @return 退货订单信息集合
     */
    public List<ReturnOrderInfo> selectReturnOrderInfoList(ReturnOrderInfo returnOrderInfo);

    /**
     * 新增退货订单信息
     *
     * @param returnOrderInfo 退货订单信息
     * @return 结果
     */
    public int insertReturnOrderInfo(ReturnOrderInfo returnOrderInfo);

    /**
     * 修改退货订单信息
     *
     * @param returnOrderInfo 退货订单信息
     * @return 结果
     */
    public int updateReturnOrderInfo(ReturnOrderInfo returnOrderInfo);

    /**
     * 删除退货订单信息
     *
     * @param id 退货订单信息主键
     * @return 结果
     */
    public int deleteReturnOrderInfoById(Long id);

    /**
     * 批量删除退货订单信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReturnOrderInfoByIds(Long[] ids);

    /**
     * description:  查询退货订单信息统计信息
     * author: YY
     * method: getReturnOrderCount
     * date: 2025/3/6 21:09
     * param:
     * param: returnOrderInfo
     * return: com.lz.manage.model.vo.returnOrderInfo.ReturnOrderCountVo
     **/
    ReturnOrderCountVo getReturnOrderCount(ReturnOrderInfo returnOrderInfo);
}
