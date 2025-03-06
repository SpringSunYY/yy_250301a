package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoCountVo;

/**
 * 采购发货信息Mapper接口
 * 
 * @author YY
 * @date 2025-03-03
 */
public interface PurchaseOrderInfoMapper extends BaseMapper<PurchaseOrderInfo>
{
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
     * 修改采购发货信息
     * 
     * @param purchaseOrderInfo 采购发货信息
     * @return 结果
     */
    public int updatePurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo);

    /**
     * 删除采购发货信息
     * 
     * @param id 采购发货信息主键
     * @return 结果
     */
    public int deletePurchaseOrderInfoById(Long id);

    /**
     * 批量删除采购发货信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseOrderInfoByIds(Long[] ids);

    PurchaseOrderInfoCountVo getPurchaseOrderInfoCount(PurchaseOrderInfo purchaseOrderInfo);
}
