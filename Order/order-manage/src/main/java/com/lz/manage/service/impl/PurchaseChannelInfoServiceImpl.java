package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.PurchaseChannelInfoMapper;
import com.lz.manage.model.domain.PurchaseChannelInfo;
import com.lz.manage.service.IPurchaseChannelInfoService;
import com.lz.manage.model.dto.purchaseChannelInfo.PurchaseChannelInfoQuery;
import com.lz.manage.model.vo.purchaseChannelInfo.PurchaseChannelInfoVo;

/**
 * 采购渠道信息Service业务层处理
 *
 * @author YY
 * @date 2025-03-08
 */
@Service
public class PurchaseChannelInfoServiceImpl extends ServiceImpl<PurchaseChannelInfoMapper, PurchaseChannelInfo> implements IPurchaseChannelInfoService {
    @Resource
    private PurchaseChannelInfoMapper purchaseChannelInfoMapper;

    @Resource
    private ISysUserService userService;

    //region mybatis代码

    /**
     * 查询采购渠道信息
     *
     * @param id 采购渠道信息主键
     * @return 采购渠道信息
     */
    @Override
    public PurchaseChannelInfo selectPurchaseChannelInfoById(Long id) {
        return purchaseChannelInfoMapper.selectPurchaseChannelInfoById(id);
    }

    /**
     * 查询采购渠道信息列表
     *
     * @param purchaseChannelInfo 采购渠道信息
     * @return 采购渠道信息
     */
    @Override
    public List<PurchaseChannelInfo> selectPurchaseChannelInfoList(PurchaseChannelInfo purchaseChannelInfo) {
        List<PurchaseChannelInfo> infos = purchaseChannelInfoMapper.selectPurchaseChannelInfoList(purchaseChannelInfo);
        for (PurchaseChannelInfo info : infos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
        }
        return infos;
    }

    /**
     * 新增采购渠道信息
     *
     * @param purchaseChannelInfo 采购渠道信息
     * @return 结果
     */
    @Override
    public int insertPurchaseChannelInfo(PurchaseChannelInfo purchaseChannelInfo) {
        purchaseChannelInfo.setUserId(SecurityUtils.getUserId());
        purchaseChannelInfo.setCreateTime(DateUtils.getNowDate());
        purchaseChannelInfo.setAncestors(getChannelAncestors(purchaseChannelInfo.getParentId(), new StringBuffer()).toString());
        return purchaseChannelInfoMapper.insertPurchaseChannelInfo(purchaseChannelInfo);
    }

    /**
     * 修改采购渠道信息
     *
     * @param purchaseChannelInfo 采购渠道信息
     * @return 结果
     */
    @Override
    public int updatePurchaseChannelInfo(PurchaseChannelInfo purchaseChannelInfo) {
        purchaseChannelInfo.setUpdateBy(SecurityUtils.getUsername());
        purchaseChannelInfo.setUpdateTime(DateUtils.getNowDate());
        purchaseChannelInfo.setAncestors(getChannelAncestors(purchaseChannelInfo.getParentId(), new StringBuffer()).toString());
        return purchaseChannelInfoMapper.updatePurchaseChannelInfo(purchaseChannelInfo);
    }

    /**
     * description: 获取渠道祖级列表
     * author: YY
     * method:
     * date: 2025/3/8 22:16
     * param:
     * param: null
     * return:
     **/
    private StringBuffer getChannelAncestors(Long parentId, StringBuffer ancestors) {
        //根据父类id查询渠道信息
        PurchaseChannelInfo parentChannelInfo = purchaseChannelInfoMapper.selectPurchaseChannelInfoById(parentId);
        //如果有数据，则添加至祖级列表
        if (StringUtils.isNotNull(parentChannelInfo)) {
            //添加至祖级列表
            ancestors.insert(0, parentChannelInfo.getId() + ",");
            //递归查询父类信息
            getChannelAncestors(parentChannelInfo.getParentId(), ancestors);
        }
        //顶级父类
        if (StringUtils.isNull(parentChannelInfo)) {
            ancestors.append(0);
        }
        return ancestors;
    }

    /**
     * 批量删除采购渠道信息
     *
     * @param ids 需要删除的采购渠道信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseChannelInfoByIds(Long[] ids) {
        return purchaseChannelInfoMapper.deletePurchaseChannelInfoByIds(ids);
    }

    /**
     * 删除采购渠道信息信息
     *
     * @param id 采购渠道信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseChannelInfoById(Long id) {
        return purchaseChannelInfoMapper.deletePurchaseChannelInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<PurchaseChannelInfo> getQueryWrapper(PurchaseChannelInfoQuery purchaseChannelInfoQuery) {
        QueryWrapper<PurchaseChannelInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = purchaseChannelInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }

        String channelName = purchaseChannelInfoQuery.getChannelName();
        queryWrapper.like(StringUtils.isNotEmpty(channelName), "channel_name", channelName);

        String channelType = purchaseChannelInfoQuery.getChannelType();
        queryWrapper.eq(StringUtils.isNotEmpty(channelType), "channel_type", channelType);

        Long userId = purchaseChannelInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = purchaseChannelInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = purchaseChannelInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = purchaseChannelInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<PurchaseChannelInfoVo> convertVoList(List<PurchaseChannelInfo> purchaseChannelInfoList) {
        if (StringUtils.isEmpty(purchaseChannelInfoList)) {
            return Collections.emptyList();
        }
        return purchaseChannelInfoList.stream().map(PurchaseChannelInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public List<Long> selectPurchaseChannelInfoReturnIds(Long purchaseChannelsId) {
        return purchaseChannelInfoMapper.selectPurchaseChannelInfoReturnIds(purchaseChannelsId);
    }

}
