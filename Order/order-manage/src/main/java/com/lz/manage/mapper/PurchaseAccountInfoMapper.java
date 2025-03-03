package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.PurchaseAccountInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 采购账号信息Mapper接口
 * 
 * @author YY
 * @date 2025-03-03
 */
public interface PurchaseAccountInfoMapper extends BaseMapper<PurchaseAccountInfo>
{
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
     * 删除采购账号信息
     * 
     * @param id 采购账号信息主键
     * @return 结果
     */
    public int deletePurchaseAccountInfoById(Long id);

    /**
     * 批量删除采购账号信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseAccountInfoByIds(Long[] ids);
}
