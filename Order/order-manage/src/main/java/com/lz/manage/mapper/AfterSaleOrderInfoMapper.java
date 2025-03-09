package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.AfterSaleOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 售后订单信息Mapper接口
 * 
 * @author YY
 * @date 2025-03-09
 */
public interface AfterSaleOrderInfoMapper extends BaseMapper<AfterSaleOrderInfo>
{
    /**
     * 查询售后订单信息
     * 
     * @param id 售后订单信息主键
     * @return 售后订单信息
     */
    public AfterSaleOrderInfo selectAfterSaleOrderInfoById(Long id);

    /**
     * 查询售后订单信息列表
     * 
     * @param afterSaleOrderInfo 售后订单信息
     * @return 售后订单信息集合
     */
    public List<AfterSaleOrderInfo> selectAfterSaleOrderInfoList(AfterSaleOrderInfo afterSaleOrderInfo);

    /**
     * 新增售后订单信息
     * 
     * @param afterSaleOrderInfo 售后订单信息
     * @return 结果
     */
    public int insertAfterSaleOrderInfo(AfterSaleOrderInfo afterSaleOrderInfo);

    /**
     * 修改售后订单信息
     * 
     * @param afterSaleOrderInfo 售后订单信息
     * @return 结果
     */
    public int updateAfterSaleOrderInfo(AfterSaleOrderInfo afterSaleOrderInfo);

    /**
     * 删除售后订单信息
     * 
     * @param id 售后订单信息主键
     * @return 结果
     */
    public int deleteAfterSaleOrderInfoById(Long id);

    /**
     * 批量删除售后订单信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAfterSaleOrderInfoByIds(Long[] ids);
}
