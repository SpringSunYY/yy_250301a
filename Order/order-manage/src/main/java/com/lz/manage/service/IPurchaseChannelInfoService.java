package com.lz.manage.service;

import java.util.List;

import com.lz.manage.model.domain.PurchaseChannelInfo;
import com.lz.manage.model.vo.purchaseChannelInfo.PurchaseChannelInfoVo;
import com.lz.manage.model.dto.purchaseChannelInfo.PurchaseChannelInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 采购渠道信息Service接口
 *
 * @author YY
 * @date 2025-03-08
 */
public interface IPurchaseChannelInfoService extends IService<PurchaseChannelInfo> {
    //region mybatis代码

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
     * 批量删除采购渠道信息
     *
     * @param ids 需要删除的采购渠道信息主键集合
     * @return 结果
     */
    public int deletePurchaseChannelInfoByIds(Long[] ids);

    /**
     * 删除采购渠道信息信息
     *
     * @param id 采购渠道信息主键
     * @return 结果
     */
    public int deletePurchaseChannelInfoById(Long id);
    //endregion

    /**
     * 获取查询条件
     *
     * @param purchaseChannelInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<PurchaseChannelInfo> getQueryWrapper(PurchaseChannelInfoQuery purchaseChannelInfoQuery);

    /**
     * 转换vo
     *
     * @param purchaseChannelInfoList PurchaseChannelInfo集合
     * @return PurchaseChannelInfoVO集合
     */
    List<PurchaseChannelInfoVo> convertVoList(List<PurchaseChannelInfo> purchaseChannelInfoList);

    /**
     * description: 返回所有子集包括自己
     * author: YY
     * method: selectPurchaseChannelInfoReturnIds
     * date: 2025/3/8 22:41
     * param:
     * param: purchaseChannelsId
     * return: java.util.List<java.lang.Long>
     **/
    List<Long> selectPurchaseChannelInfoReturnIds(Long purchaseChannelsId);
}
