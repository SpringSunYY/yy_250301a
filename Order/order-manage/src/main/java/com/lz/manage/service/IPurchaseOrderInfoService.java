package com.lz.manage.service;

import java.util.List;

import com.lz.manage.model.domain.AfterSaleOrderInfo;
import com.lz.manage.model.domain.BPOrderInfo;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.model.domain.ReturnOrderInfo;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoCountVo;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoVo;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 采购发货信息Service接口
 *
 * @author YY
 * @date 2025-03-03
 */
public interface IPurchaseOrderInfoService extends IService<PurchaseOrderInfo> {
    //region mybatis代码

    /**
     * 查询采购发货信息
     *
     * @param id 采购发货信息主键
     * @return 采购发货信息
     */
    public PurchaseOrderInfo selectPurchaseOrderInfoById(Long id);

    /**
     * 查询采购发货信息列表
     *
     * @param purchaseOrderInfo 采购发货信息
     * @return 采购发货信息集合
     */
    public List<PurchaseOrderInfo> selectPurchaseOrderInfoList(PurchaseOrderInfo purchaseOrderInfo);

    /**
     * 新增采购发货信息
     *
     * @param purchaseOrderInfo 采购发货信息
     * @return 结果
     */
    public int insertPurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo);

    /**
     * 计算订单利润（包含退货和BP订单信息）
     *
     * @param purchaseOrderInfo  采购订单信息
     * @param returnOrderInfo    退货订单信息 没有则new
     * @param bpOrderInfo        BP订单信息 没有则new
     * @param afterSaleOrderInfo 售后订单信息 没有则new
     * @return 订单利润 PurchaseOrderInfo
     */
    public PurchaseOrderInfo getOrderProfit(PurchaseOrderInfo purchaseOrderInfo, ReturnOrderInfo returnOrderInfo, BPOrderInfo bpOrderInfo, AfterSaleOrderInfo afterSaleOrderInfo);

    /**
     * 修改采购发货信息
     *
     * @param purchaseOrderInfo 采购发货信息
     * @return 结果
     */
    public int updatePurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo);

    /**
     * 批量删除采购发货信息
     *
     * @param ids 需要删除的采购发货信息主键集合
     * @return 结果
     */
    public int deletePurchaseOrderInfoByIds(Long[] ids);

    /**
     * 删除采购发货信息信息
     *
     * @param id 采购发货信息主键
     * @return 结果
     */
    public int deletePurchaseOrderInfoById(Long id);
    //endregion

    /**
     * 获取查询条件
     *
     * @param purchaseOrderInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<PurchaseOrderInfo> getQueryWrapper(PurchaseOrderInfoQuery purchaseOrderInfoQuery);

    /**
     * 转换vo
     *
     * @param purchaseOrderInfoList PurchaseOrderInfo集合
     * @return PurchaseOrderInfoVO集合
     */
    List<PurchaseOrderInfoVo> convertVoList(List<PurchaseOrderInfo> purchaseOrderInfoList);

    /**
     * description: 采购订单导入
     * author: YY
     * method: importPurchaseOrderInfo
     * date: 2025/3/5 17:11
     * param:
     * param: purchaseOrderInfoList
     * return: java.lang.String
     **/
    String importPurchaseOrderInfo(List<PurchaseOrderInfo> purchaseOrderInfoList);

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderNumber 订单编号
     * @return 订单信息
     */
    PurchaseOrderInfo selectPurchaseOrderInfoByOrderNumber(String orderNumber);

    /**
     * description: 计算总数
     * author: YY
     * method: getPurchaseOrderInfoCount
     * date: 2025/3/6 20:37
     * param:
     * param: purchaseOrderInfo
     * return: com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoCountVo
     **/
    PurchaseOrderInfoCountVo getPurchaseOrderInfoCount(PurchaseOrderInfo purchaseOrderInfo);
}
