package com.lz.manage.service;

import java.util.List;

import com.lz.manage.model.domain.AfterSaleOrderInfo;
import com.lz.manage.model.vo.afterSaleOrderInfo.AfterSaleOrderCountVo;
import com.lz.manage.model.vo.afterSaleOrderInfo.AfterSaleOrderInfoVo;
import com.lz.manage.model.dto.afterSaleOrderInfo.AfterSaleOrderInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 售后订单信息Service接口
 *
 * @author YY
 * @date 2025-03-09
 */
public interface IAfterSaleOrderInfoService extends IService<AfterSaleOrderInfo> {
    //region mybatis代码

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
     * 批量删除售后订单信息
     *
     * @param ids 需要删除的售后订单信息主键集合
     * @return 结果
     */
    public int deleteAfterSaleOrderInfoByIds(Long[] ids);

    /**
     * 删除售后订单信息信息
     *
     * @param id 售后订单信息主键
     * @return 结果
     */
    public int deleteAfterSaleOrderInfoById(Long id);
    //endregion

    /**
     * description:  根据订单号查询售后订单信息
     * author: YY
     * method: selectAfterSaleOrderInfoByOrderNumber
     * date: 2025/3/9 14:16
     * param:
     * param: orderNumber
     * return: com.lz.manage.model.domain.AfterSaleOrderInfo
     **/
    public AfterSaleOrderInfo selectAfterSaleOrderInfoByOrderNumber(String orderNumber);

    /**
     * 获取查询条件
     *
     * @param afterSaleOrderInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<AfterSaleOrderInfo> getQueryWrapper(AfterSaleOrderInfoQuery afterSaleOrderInfoQuery);

    /**
     * 转换vo
     *
     * @param afterSaleOrderInfoList AfterSaleOrderInfo集合
     * @return AfterSaleOrderInfoVO集合
     */
    List<AfterSaleOrderInfoVo> convertVoList(List<AfterSaleOrderInfo> afterSaleOrderInfoList);

    /**
     * description: 新增或者修改售后订单信息
     * author: YY
     * method: addOrUpdateAfterSaleOrderInfo
     * date: 2025/3/9 15:36
     * param:
     * param: afterSaleOrderInfo
     * return: int
     **/
    int addOrUpdateAfterSaleOrderInfo(AfterSaleOrderInfo afterSaleOrderInfo);

    /**
     * description: 获取售后订单统计
     * author: YY
     * method: getAfterSaleOrderCount
     * date: 2025/3/9 16:06
     * param:
     * param: afterSaleOrderInfo
     * return: com.lz.manage.model.vo.afterSaleOrderInfo.AfterSaleOrderCountVo
     **/
    AfterSaleOrderCountVo getAfterSaleOrderCount(AfterSaleOrderInfo afterSaleOrderInfo);
}
