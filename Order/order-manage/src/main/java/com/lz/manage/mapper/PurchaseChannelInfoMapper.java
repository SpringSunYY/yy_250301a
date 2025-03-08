package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.PurchaseChannelInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 采购渠道信息Mapper接口
 * 
 * @author YY
 * @date 2025-03-08
 */
public interface PurchaseChannelInfoMapper extends BaseMapper<PurchaseChannelInfo>
{
    /**
     * 查询采购渠道信息
     * 
     * @param id 采购渠道信息主键
     * @return 采购渠道信息
     */
    public PurchaseChannelInfo selectPurchaseChannelInfoById(Long id);

    /**
     * 查询采购渠道信息列表
     * 
     * @param purchaseChannelInfo 采购渠道信息
     * @return 采购渠道信息集合
     */
    public List<PurchaseChannelInfo> selectPurchaseChannelInfoList(PurchaseChannelInfo purchaseChannelInfo);

    /**
     * 新增采购渠道信息
     * 
     * @param purchaseChannelInfo 采购渠道信息
     * @return 结果
     */
    public int insertPurchaseChannelInfo(PurchaseChannelInfo purchaseChannelInfo);

    /**
     * 修改采购渠道信息
     * 
     * @param purchaseChannelInfo 采购渠道信息
     * @return 结果
     */
    public int updatePurchaseChannelInfo(PurchaseChannelInfo purchaseChannelInfo);

    /**
     * 删除采购渠道信息
     * 
     * @param id 采购渠道信息主键
     * @return 结果
     */
    public int deletePurchaseChannelInfoById(Long id);

    /**
     * 批量删除采购渠道信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseChannelInfoByIds(Long[] ids);
}
