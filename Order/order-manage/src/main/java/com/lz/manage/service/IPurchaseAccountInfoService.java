package com.lz.manage.service;

import java.util.List;

import com.lz.manage.model.domain.PurchaseAccountInfo;
import com.lz.manage.model.vo.purchaseAccountInfo.PurchaseAccountInfoVo;
import com.lz.manage.model.dto.purchaseAccountInfo.PurchaseAccountInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 采购账号信息Service接口
 *
 * @author YY
 * @date 2025-03-03
 */
public interface IPurchaseAccountInfoService extends IService<PurchaseAccountInfo> {
    //region mybatis代码

    /**
     * 查询采购账号信息
     *
     * @param id 采购账号信息主键
     * @return 采购账号信息
     */
    public PurchaseAccountInfo selectPurchaseAccountInfoById(Long id);

    /**
     * 查询采购账号信息列表
     *
     * @param purchaseAccountInfo 采购账号信息
     * @return 采购账号信息集合
     */
    public List<PurchaseAccountInfo> selectPurchaseAccountInfoList(PurchaseAccountInfo purchaseAccountInfo);

    /**
     * 新增采购账号信息
     *
     * @param purchaseAccountInfo 采购账号信息
     * @return 结果
     */
    public int insertPurchaseAccountInfo(PurchaseAccountInfo purchaseAccountInfo);

    /**
     * 修改采购账号信息
     *
     * @param purchaseAccountInfo 采购账号信息
     * @return 结果
     */
    public int updatePurchaseAccountInfo(PurchaseAccountInfo purchaseAccountInfo);

    /**
     * 批量删除采购账号信息
     *
     * @param ids 需要删除的采购账号信息主键集合
     * @return 结果
     */
    public int deletePurchaseAccountInfoByIds(Long[] ids);

    /**
     * 删除采购账号信息信息
     *
     * @param id 采购账号信息主键
     * @return 结果
     */
    public int deletePurchaseAccountInfoById(Long id);
    //endregion

    /**
     * 获取查询条件
     *
     * @param purchaseAccountInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<PurchaseAccountInfo> getQueryWrapper(PurchaseAccountInfoQuery purchaseAccountInfoQuery);

    /**
     * 转换vo
     *
     * @param purchaseAccountInfoList PurchaseAccountInfo集合
     * @return PurchaseAccountInfoVO集合
     */
    List<PurchaseAccountInfoVo> convertVoList(List<PurchaseAccountInfo> purchaseAccountInfoList);

    /**
     * description:  导入采购账号信息
     * author: YY
     * method: importPurchaseAccountInfo
     * date: 2025/3/3 22:42
     * param:
     * param: purchaseAccountInfoList
     * return: java.lang.String
     **/
    String importPurchaseAccountInfo(List<PurchaseAccountInfo> purchaseAccountInfoList);

    /**
     * description: 根据账号查询采购账号信息
     * author: YY
     * method: selectPurchaseAccountInfoByAccount
     * date: 2025/3/5 17:32
     * param:
     * param: purchaseAccount
     * return: com.lz.manage.model.domain.PurchaseAccountInfo
     **/
    PurchaseAccountInfo selectPurchaseAccountInfoByAccount(String purchaseAccount);
}
