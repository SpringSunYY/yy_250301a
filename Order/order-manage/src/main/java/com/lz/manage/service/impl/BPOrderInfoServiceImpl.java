package com.lz.manage.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.model.domain.ReturnOrderInfo;
import com.lz.manage.model.domain.StoreInfo;
import com.lz.manage.model.enums.CommonWhetherEnum;
import com.lz.manage.service.IPurchaseOrderInfoService;
import com.lz.manage.service.IReturnOrderInfoService;
import com.lz.manage.service.IStoreInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.BPOrderInfoMapper;
import com.lz.manage.model.domain.BPOrderInfo;
import com.lz.manage.service.IBPOrderInfoService;
import com.lz.manage.model.dto.bPOrderInfo.BPOrderInfoQuery;
import com.lz.manage.model.vo.bPOrderInfo.BPOrderInfoVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 白嫖订单信息Service业务层处理
 *
 * @author YY
 * @date 2025-03-03
 */
@Slf4j
@Service
public class BPOrderInfoServiceImpl extends ServiceImpl<BPOrderInfoMapper, BPOrderInfo> implements IBPOrderInfoService {
    @Resource
    private BPOrderInfoMapper bPOrderInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IPurchaseOrderInfoService orderInfoService;

    @Resource
    @Lazy
    private IReturnOrderInfoService returnOrderInfoService;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private IStoreInfoService storeInfoService;

    //region mybatis代码

    /**
     * 查询白嫖订单信息
     *
     * @param id 白嫖订单信息主键
     * @return 白嫖订单信息
     */
    @Override
    public BPOrderInfo selectBPOrderInfoById(Long id) {
        return bPOrderInfoMapper.selectBPOrderInfoById(id);
    }

    /**
     * 查询白嫖订单信息列表
     *
     * @param bPOrderInfo 白嫖订单信息
     * @return 白嫖订单信息
     */
    @Override
    public List<BPOrderInfo> selectBPOrderInfoList(BPOrderInfo bPOrderInfo) {
        List<BPOrderInfo> bpOrderInfos = bPOrderInfoMapper.selectBPOrderInfoList(bPOrderInfo);
        for (BPOrderInfo info : bpOrderInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
            StoreInfo storeInfo = storeInfoService.selectStoreInfoById(info.getStoreId());
            if (StringUtils.isNotNull(storeInfo)) {
                info.setStoreName(storeInfo.getStoreName());
            }
        }
        return bpOrderInfos;
    }

    /**
     * 新增白嫖订单信息
     *
     * @param bPOrderInfo 白嫖订单信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertBPOrderInfo(BPOrderInfo bPOrderInfo) {
        BPOrderInfo bpOrderInfo = this.selectBPOrderInfoByOrderNumber(bPOrderInfo.getOrderNumber());
        if (StringUtils.isNotNull(bpOrderInfo)) {
            throw new ServiceException("白嫖订单已存在" + bPOrderInfo.getOrderNumber());
        }
        PurchaseOrderInfo orderInfo = checkBPOrder(bPOrderInfo);

        bPOrderInfo.setCreateTime(DateUtils.getNowDate());
        int i = bPOrderInfoMapper.insertBPOrderInfo(bPOrderInfo);
        orderInfoService.updatePurchaseOrderInfo(orderInfo);
        return i;
    }

    /**
     * description: 校验白嫖订单
     * author: YY
     * method: checkBPOrder
     * date: 2025/3/6 09:15
     * param:
     * param: bpOrderInfo
     * return: com.lz.manage.model.domain.PurchaseOrderInfo
     **/
    private PurchaseOrderInfo checkBPOrder(BPOrderInfo bpOrderInfo) {
        PurchaseOrderInfo orderInfo = orderInfoService.selectPurchaseOrderInfoByOrderNumber(bpOrderInfo.getOrderNumber());
        if (StringUtils.isNull(orderInfo)) {
            throw new ServiceException("订单不存在" + bpOrderInfo.getOrderNumber());
        }
        orderInfo.setHasBP(CommonWhetherEnum.COMMON_WHETHER_1.getValue());
        bpOrderInfo.setStoreId(orderInfo.getStoreId());
        bpOrderInfo.setDeptId(orderInfo.getDeptId());
        bpOrderInfo.setUserId(orderInfo.getUserId());
        bpOrderInfo.setOrderType(orderInfo.getOrderType());
        return orderInfo;
    }

    /**
     * 修改白嫖订单信息
     *
     * @param bPOrderInfo 白嫖订单信息
     * @return 结果
     */
    @Override
    public int updateBPOrderInfo(BPOrderInfo bPOrderInfo) {
        BPOrderInfo old = this.selectBPOrderInfoByOrderNumber(bPOrderInfo.getOrderNumber());
        if (StringUtils.isNull(old)) {
            throw new ServiceException("白嫖订单不存在" + bPOrderInfo.getOrderNumber());
        }
        if (!old.getId().equals(bPOrderInfo.getId())) {
            throw new ServiceException("不可以修改订单编号" + bPOrderInfo.getOrderNumber());
        }
        PurchaseOrderInfo orderInfo = checkBPOrder(bPOrderInfo);
        bPOrderInfo.setUpdateBy(bPOrderInfo.getUserName());
        bPOrderInfo.setUpdateTime(DateUtils.getNowDate());
        int i = bPOrderInfoMapper.updateBPOrderInfo(bPOrderInfo);
        orderInfoService.updatePurchaseOrderInfo(orderInfo);
        return i;
    }

    /**
     * 批量删除白嫖订单信息
     *
     * @param ids 需要删除的白嫖订单信息主键
     * @return 结果
     */
    @Override
    public int deleteBPOrderInfoByIds(Long[] ids) {
        List<PurchaseOrderInfo> orderInfos = new ArrayList<>(ids.length);
        for (Long id : ids) {
            BPOrderInfo bpOrderInfo = this.selectBPOrderInfoById(id);
            if (StringUtils.isNotNull(bpOrderInfo)) {
                PurchaseOrderInfo orderInfo = orderInfoService.selectPurchaseOrderInfoByOrderNumber(bpOrderInfo.getOrderNumber());
                if (StringUtils.isNotNull(orderInfo)) {
                    orderInfo.setHasReturn(CommonWhetherEnum.COMMON_WHETHER_2.getValue());
                    orderInfos.add(orderInfo);
                }
            }
        }
        transactionTemplate.execute(item -> {
            bPOrderInfoMapper.deleteBPOrderInfoByIds(ids);
            for (PurchaseOrderInfo orderInfo : orderInfos) {
                orderInfoService.updatePurchaseOrderInfo(orderInfo);
            }
            return 1;
        });
        return 1;
    }

    /**
     * 删除白嫖订单信息信息
     *
     * @param id 白嫖订单信息主键
     * @return 结果
     */
    @Override
    public int deleteBPOrderInfoById(Long id) {
        return bPOrderInfoMapper.deleteBPOrderInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<BPOrderInfo> getQueryWrapper(BPOrderInfoQuery bPOrderInfoQuery) {
        QueryWrapper<BPOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = bPOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String orderNumber = bPOrderInfoQuery.getOrderNumber();
        queryWrapper.eq(StringUtils.isNotEmpty(orderNumber), "order_number", orderNumber);

        String orderType = bPOrderInfoQuery.getOrderType();
        queryWrapper.eq(StringUtils.isNotEmpty(orderType), "order_type", orderType);

        Long storeId = bPOrderInfoQuery.getStoreId();
        queryWrapper.eq(StringUtils.isNotNull(storeId), "store_id", storeId);

        Date bPTime = bPOrderInfoQuery.getBPTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginBPTime")) && StringUtils.isNotNull(params.get("endBPTime")), "b_p_time", params.get("beginBPTime"), params.get("endBPTime"));

        Date afterSaleTime = bPOrderInfoQuery.getAfterSaleTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginAfterSaleTime")) && StringUtils.isNotNull(params.get("endAfterSaleTime")), "after_sale_time", params.get("beginAfterSaleTime"), params.get("endAfterSaleTime"));

        Long userId = bPOrderInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = bPOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = bPOrderInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = bPOrderInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        Long deptId = bPOrderInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        return queryWrapper;
    }

    @Override
    public List<BPOrderInfoVo> convertVoList(List<BPOrderInfo> bPOrderInfoList) {
        if (StringUtils.isEmpty(bPOrderInfoList)) {
            return Collections.emptyList();
        }
        return bPOrderInfoList.stream().map(BPOrderInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public BPOrderInfo selectBPOrderInfoByOrderNumber(String orderNumber) {
        return this.getOne(new LambdaQueryWrapper<BPOrderInfo>().eq(BPOrderInfo::getOrderNumber, orderNumber));
    }

    @Transactional
    @Override
    public int addOrUpdateBPOrderInfo(BPOrderInfo bPOrderInfo) {
        PurchaseOrderInfo orderInfo = checkBPOrder(bPOrderInfo);
        this.saveOrUpdate(bPOrderInfo);
        return orderInfoService.updatePurchaseOrderInfo(orderInfo);
    }

    @Override
    public String importBPOrderInfo(List<BPOrderInfo> bpOrderInfoList) {
        if (StringUtils.isEmpty(bpOrderInfoList)) {
            return "导入的文件没有数据";
        }
        Date nowDate = DateUtils.getNowDate();
        //先校验是否已经存在订单了
        // 获取所有的订单编号 然后根据订单编号校验是否已经有存在的订单
        List<String> orderNumbers = new ArrayList<>(bpOrderInfoList.size());
        for (int i = 0; i < bpOrderInfoList.size(); i++) {
            BPOrderInfo info = bpOrderInfoList.get(i);
            if (StringUtils.isEmpty(info.getOrderNumber())) {
                return StringUtils.format("第{}行订单编号不能为空", i + 1);
            }
            if (StringUtils.isNull(info.getBPPrice())) {
                info.setBPPrice(BigDecimal.ZERO);
            }
            if (StringUtils.isNull(info.getAfterSalePrice())) {
                info.setAfterSalePrice(BigDecimal.ZERO);
            }
            info.setCreateTime(nowDate);

            orderNumbers.add(info.getOrderNumber());
        }

        //根据订单编号列表查询退货订单
        List<BPOrderInfo> bpOrderInfos = this.list(new LambdaQueryWrapper<BPOrderInfo>().in(BPOrderInfo::getOrderNumber, orderNumbers));
        if (StringUtils.isNotEmpty(bpOrderInfos)) {
            return StringUtils.format("订单编号{}已存在", bpOrderInfos.get(0).getOrderNumber());
        }

        List<PurchaseOrderInfo> orderInfos = new ArrayList<>(bpOrderInfoList.size());
        for (BPOrderInfo info : bpOrderInfoList) {

            //未退货订单赋值并查询订单信息
            PurchaseOrderInfo orderInfo = checkBPOrder(info);
            ReturnOrderInfo returnOrderInfo = returnOrderInfoService.selectReturnOrderByOrderNumber(orderInfo.getOrderNumber());
            orderInfo = orderInfoService.getOrderProfit(orderInfo, returnOrderInfo, info);
            orderInfos.add(orderInfo);
        }

        transactionTemplate.execute(item -> {
            try {
                this.saveBatch(bpOrderInfoList);
                return orderInfoService.updateBatchById(orderInfos);
            } catch (Exception e) {
                log.error("导入白嫖订单数据失败，原因：", e);
                throw new ServiceException("导入数据失败，请检查数据结构是否正确,例如是否导入了重复的订单编号,数据格式是否和描述相同！！！");
            }
        });
        return StringUtils.format("导入成功，成功导入{}条数据", bpOrderInfoList.size());
    }

}
