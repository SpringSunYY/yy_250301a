package com.lz.manage.service;

import java.util.List;

import com.lz.manage.model.domain.ReturnOrderInfo;
import com.lz.manage.model.vo.returnOrderInfo.ReturnOrderInfoVo;
import com.lz.manage.model.dto.returnOrderInfo.ReturnOrderInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 退货订单信息Service接口
 *
 * @author ruoyi
 * @date 2025-03-03
 */
public interface IReturnOrderInfoService extends IService<ReturnOrderInfo> {
    //region mybatis代码

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
     * 批量删除退货订单信息
     *
     * @param ids 需要删除的退货订单信息主键集合
     * @return 结果
     */
    public int deleteReturnOrderInfoByIds(Long[] ids);

    /**
     * 删除退货订单信息信息
     *
     * @param id 退货订单信息主键
     * @return 结果
     */
    public int deleteReturnOrderInfoById(Long id);
    //endregion

    /**
     * 获取查询条件
     *
     * @param returnOrderInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ReturnOrderInfo> getQueryWrapper(ReturnOrderInfoQuery returnOrderInfoQuery);

    /**
     * 转换vo
     *
     * @param returnOrderInfoList ReturnOrderInfo集合
     * @return ReturnOrderInfoVO集合
     */
    List<ReturnOrderInfoVo> convertVoList(List<ReturnOrderInfo> returnOrderInfoList);

    /**
     * description:  根据订单号查询退货订单信息
     * author: YY
     * method: selectReturnOrderByOrderNumber
     * date: 2025/3/5 21:05
     * param:
     * param: orderNumber 订单号
     * return: com.lz.manage.model.domain.ReturnOrderInfo
     **/
    ReturnOrderInfo selectReturnOrderByOrderNumber(String orderNumber);

    /**
     * description:  导入退货订单信息
     * author: YY
     * method: importReturnOrderInfo
     * date: 2025/3/5 21:05
     * param:
     * param: list 退货订单信息集合
     * return: java.lang.String
     **/
    String importReturnOrderInfo(List<ReturnOrderInfo> list);
}

