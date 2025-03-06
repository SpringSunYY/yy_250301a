package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.mapper.PurchaseOrderInfoMapper;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.PurchaseAccountInfoMapper;
import com.lz.manage.model.domain.PurchaseAccountInfo;
import com.lz.manage.service.IPurchaseAccountInfoService;
import com.lz.manage.model.dto.purchaseAccountInfo.PurchaseAccountInfoQuery;
import com.lz.manage.model.vo.purchaseAccountInfo.PurchaseAccountInfoVo;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 采购账号信息Service业务层处理
 *
 * @author YY
 * @date 2025-03-03
 */
@Slf4j
@Service
public class PurchaseAccountInfoServiceImpl extends ServiceImpl<PurchaseAccountInfoMapper, PurchaseAccountInfo> implements IPurchaseAccountInfoService {
    @Resource
    private PurchaseAccountInfoMapper purchaseAccountInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private PurchaseOrderInfoMapper purchaseOrderInfoMapper;


    //region mybatis代码

    /**
     * 查询采购账号信息
     *
     * @param id 采购账号信息主键
     * @return 采购账号信息
     */
    @Override
    public PurchaseAccountInfo selectPurchaseAccountInfoById(Long id) {
        return purchaseAccountInfoMapper.selectPurchaseAccountInfoById(id);
    }

    /**
     * 查询采购账号信息列表
     *
     * @param purchaseAccountInfo 采购账号信息
     * @return 采购账号信息
     */
    @Override
    public List<PurchaseAccountInfo> selectPurchaseAccountInfoList(PurchaseAccountInfo purchaseAccountInfo) {
        List<PurchaseAccountInfo> purchaseAccountInfos = purchaseAccountInfoMapper.selectPurchaseAccountInfoList(purchaseAccountInfo);
        for (PurchaseAccountInfo info : purchaseAccountInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
        }
        return purchaseAccountInfos;
    }

    /**
     * 新增采购账号信息
     *
     * @param purchaseAccountInfo 采购账号信息
     * @return 结果
     */
    @Override
    public int insertPurchaseAccountInfo(PurchaseAccountInfo purchaseAccountInfo) {
        if (StringUtils.isNull(purchaseAccountInfo.getUserId())) {
            purchaseAccountInfo.setUserId(SecurityUtils.getUserId());
        }
        SysUser user = userService.selectUserById(purchaseAccountInfo.getUserId());
        if (StringUtils.isNull(user)) {
            throw new ServiceException("用户不存在!!!");
        }
        purchaseAccountInfo.setDeptId(user.getDeptId());
        purchaseAccountInfo.setCreateTime(DateUtils.getNowDate());
        return purchaseAccountInfoMapper.insertPurchaseAccountInfo(purchaseAccountInfo);
    }

    /**
     * 修改采购账号信息
     *
     * @param purchaseAccountInfo 采购账号信息
     * @return 结果
     */
    @Override
    public int updatePurchaseAccountInfo(PurchaseAccountInfo purchaseAccountInfo) {
        SysUser user = userService.selectUserById(purchaseAccountInfo.getUserId());
        if (StringUtils.isNull(user)) {
            throw new ServiceException("用户不存在!!!");
        }
        purchaseAccountInfo.setDeptId(user.getDeptId());
        purchaseAccountInfo.setUpdateBy(SecurityUtils.getUsername());
        purchaseAccountInfo.setUpdateTime(DateUtils.getNowDate());
        return purchaseAccountInfoMapper.updatePurchaseAccountInfo(purchaseAccountInfo);
    }

    /**
     * 批量删除采购账号信息
     *
     * @param ids 需要删除的采购账号信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseAccountInfoByIds(Long[] ids) {
        //ids不能为空
        if (ArrayUtils.isEmpty(ids)) {
            throw new ServiceException("删除失败，请选择要删除的数据！！！");
        }
        //查询是否绑定订单
        purchaseOrderInfoMapper.selectList(new QueryWrapper<PurchaseOrderInfo>().lambda().in(PurchaseOrderInfo::getPurchaseAccountId, ids)).forEach(item -> {
            if (StringUtils.isNotNull(item)) {
                throw new ServiceException("存在订单绑定该账号，请先解除绑定再删除");
            }
        });
        return purchaseAccountInfoMapper.deletePurchaseAccountInfoByIds(ids);
    }

    /**
     * 删除采购账号信息信息
     *
     * @param id 采购账号信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseAccountInfoById(Long id) {
        return purchaseAccountInfoMapper.deletePurchaseAccountInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<PurchaseAccountInfo> getQueryWrapper(PurchaseAccountInfoQuery purchaseAccountInfoQuery) {
        QueryWrapper<PurchaseAccountInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = purchaseAccountInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String nickName = purchaseAccountInfoQuery.getNickName();
        queryWrapper.like(StringUtils.isNotEmpty(nickName), "nick_name", nickName);

        String purchaseAccount = purchaseAccountInfoQuery.getPurchaseAccount();
        queryWrapper.like(StringUtils.isNotEmpty(purchaseAccount), "purchase_account", purchaseAccount);

        Long userId = purchaseAccountInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = purchaseAccountInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = purchaseAccountInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = purchaseAccountInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        Long deptId = purchaseAccountInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        return queryWrapper;
    }

    @Override
    public List<PurchaseAccountInfoVo> convertVoList(List<PurchaseAccountInfo> purchaseAccountInfoList) {
        if (StringUtils.isEmpty(purchaseAccountInfoList)) {
            return Collections.emptyList();
        }
        return purchaseAccountInfoList.stream().map(PurchaseAccountInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public String importPurchaseAccountInfo(List<PurchaseAccountInfo> purchaseAccountInfoList) {
        Date nowDate = DateUtils.getNowDate();
        for (PurchaseAccountInfo info : purchaseAccountInfoList) {
            SysUser user = userService.selectUserByUserName(info.getUserName());
            if (StringUtils.isNull(user)) {
                return "用户:" + info.getUserName() + "不存在！！！";
            }
            //查询账号是否存在
            PurchaseAccountInfo purchaseAccountInfo = purchaseAccountInfoMapper.selectOne(new QueryWrapper<PurchaseAccountInfo>().eq("purchase_account", info.getPurchaseAccount()));
            if (StringUtils.isNotNull(purchaseAccountInfo)) {
                return "账号：" + purchaseAccountInfo.getPurchaseAccount() + "已存在！！！";
            }
            info.setUserId(user.getUserId());
            info.setCreateTime(nowDate);
            info.setDeptId(user.getDeptId());
        }
        Boolean execute = transactionTemplate.execute(item -> {
            try {
                return this.saveBatch(purchaseAccountInfoList);
            } catch (Exception e) {
                log.error("导入采购账号数据失败，原因：", e);
                throw new ServiceException("导入数据失败，请检查数据结构是否正确,数据格式是否和描述相同！！！");
            }
        });
        if (Boolean.TRUE.equals(execute)) {
            return "导入成功：" + purchaseAccountInfoList.size() + "条数据";
        } else {
            return "导入数据失败，请检查数据结构是否正确！！！";
        }
    }

    @Override
    public PurchaseAccountInfo selectPurchaseAccountInfoByAccount(String purchaseAccount) {
        return this.getOne(new LambdaQueryWrapper<PurchaseAccountInfo>().eq(PurchaseAccountInfo::getPurchaseAccount, purchaseAccount));
    }

}
