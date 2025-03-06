package com.lz.manage.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.BPOrderInfo;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.model.domain.StoreInfo;
import com.lz.manage.model.enums.CommonWhetherEnum;
import com.lz.manage.service.IBPOrderInfoService;
import com.lz.manage.service.IPurchaseOrderInfoService;
import com.lz.manage.service.IStoreInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ReturnOrderInfoMapper;
import com.lz.manage.model.domain.ReturnOrderInfo;
import com.lz.manage.service.IReturnOrderInfoService;
import com.lz.manage.model.dto.returnOrderInfo.ReturnOrderInfoQuery;
import com.lz.manage.model.vo.returnOrderInfo.ReturnOrderInfoVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 退货订单信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Service
public class ReturnOrderInfoServiceImpl extends ServiceImpl<ReturnOrderInfoMapper, ReturnOrderInfo> implements IReturnOrderInfoService {
    @Resource
    private ReturnOrderInfoMapper returnOrderInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IPurchaseOrderInfoService orderInfoService;

    @Resource
    private IStoreInfoService storeInfoService;

    @Resource
    private IBPOrderInfoService bpOrderInfoService;

    @Resource
    private TransactionTemplate transactionTemplate;

    //region mybatis代码

    /**
     * 查询退货订单信息
     *
     * @param id 退货订单信息主键
     * @return 退货订单信息
     */
    @Override
    public ReturnOrderInfo selectReturnOrderInfoById(Long id) {
        return returnOrderInfoMapper.selectReturnOrderInfoById(id);
    }

    /**
     * 查询退货订单信息列表
     *
     * @param returnOrderInfo 退货订单信息
     * @return 退货订单信息
     */
    @DataScope(userAlias = "tb_return_order_info", deptAlias = "tb_return_order_info")
    @Override
    public List<ReturnOrderInfo> selectReturnOrderInfoList(ReturnOrderInfo returnOrderInfo) {
        List<ReturnOrderInfo> returnOrderInfos = returnOrderInfoMapper.selectReturnOrderInfoList(returnOrderInfo);
        for (ReturnOrderInfo info : returnOrderInfos) {
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
        return returnOrderInfos;
    }

    /**
     * 新增退货订单信息
     *
     * @param returnOrderInfo 退货订单信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertReturnOrderInfo(ReturnOrderInfo returnOrderInfo) {
        //根据订单编号查询退货订单是否存在
        ReturnOrderInfo old = selectReturnOrderByOrderNumber(returnOrderInfo.getOrderNumber());
        if (StringUtils.isNotNull(old)) {
            throw new ServiceException("退货订单已存在" + old.getOrderNumber());
        }
        PurchaseOrderInfo orderInfo = checkReturnOrder(returnOrderInfo);
        //更新订单信息
        int i = returnOrderInfoMapper.insertReturnOrderInfo(returnOrderInfo);
        orderInfoService.updatePurchaseOrderInfo(orderInfo);
        return i;
    }


    private PurchaseOrderInfo checkReturnOrder(ReturnOrderInfo returnOrderInfo) {
        PurchaseOrderInfo orderInfo = orderInfoService.selectPurchaseOrderInfoByOrderNumber(returnOrderInfo.getOrderNumber());
        if (StringUtils.isNull(orderInfo)) {
            throw new ServiceException("订单不存在" + returnOrderInfo.getOrderNumber());
        }
        orderInfo.setHasReturn(CommonWhetherEnum.COMMON_WHETHER_1.getValue());
        returnOrderInfo.setStoreId(orderInfo.getStoreId());
        returnOrderInfo.setDeptId(orderInfo.getDeptId());
        returnOrderInfo.setUserId(orderInfo.getUserId());
        returnOrderInfo.setOrderType(orderInfo.getOrderType());
        return orderInfo;
    }

    /**
     * 修改退货订单信息
     *
     * @param returnOrderInfo 退货订单信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateReturnOrderInfo(ReturnOrderInfo returnOrderInfo) {
        ReturnOrderInfo old = this.selectReturnOrderByOrderNumber(returnOrderInfo.getOrderNumber());
        if (StringUtils.isNotNull(old) && !old.getOrderNumber().equals(returnOrderInfo.getOrderNumber())) {
            throw new ServiceException("不可以修改订单编号");
        }
        PurchaseOrderInfo orderInfo = checkReturnOrder(returnOrderInfo);
        returnOrderInfo.setUpdateBy(returnOrderInfo.getUserName());
        returnOrderInfo.setUpdateTime(DateUtils.getNowDate());
        //更新订单信息
        int i = returnOrderInfoMapper.updateReturnOrderInfo(returnOrderInfo);
        orderInfoService.updatePurchaseOrderInfo(orderInfo);
        return i;
    }

    /**
     * 批量删除退货订单信息
     *
     * @param ids 需要删除的退货订单信息主键
     * @return 结果
     */
    @Override
    public int deleteReturnOrderInfoByIds(Long[] ids) {
        List<PurchaseOrderInfo> orderInfos = new ArrayList<>(ids.length);
        for (Long id : ids) {
            ReturnOrderInfo returnOrderInfo = this.selectReturnOrderInfoById(id);
            if (StringUtils.isNotNull(returnOrderInfo)) {
                PurchaseOrderInfo orderInfo = orderInfoService.selectPurchaseOrderInfoByOrderNumber(returnOrderInfo.getOrderNumber());
                if (StringUtils.isNotNull(orderInfo)) {
                    orderInfo.setHasReturn(CommonWhetherEnum.COMMON_WHETHER_2.getValue());
                    orderInfos.add(orderInfo);
                }
            }
        }
        transactionTemplate.execute(item -> {
            returnOrderInfoMapper.deleteReturnOrderInfoByIds(ids);
            for (PurchaseOrderInfo orderInfo : orderInfos) {
                orderInfoService.updatePurchaseOrderInfo(orderInfo);
            }
            return 1;
        });
        return 1;
    }

    /**
     * 删除退货订单信息信息
     *
     * @param id 退货订单信息主键
     * @return 结果
     */
    @Override
    public int deleteReturnOrderInfoById(Long id) {
        return returnOrderInfoMapper.deleteReturnOrderInfoById(id);
    }

    @Override
    public ReturnOrderInfo selectReturnOrderByOrderNumber(String orderNumber) {
        return this.getOne(new LambdaQueryWrapper<ReturnOrderInfo>().eq(ReturnOrderInfo::getOrderNumber, orderNumber));
    }

    @Override
    public String importReturnOrderInfo(List<ReturnOrderInfo> list) {
        if (StringUtils.isEmpty(list)) {
            return "导入的文件没有数据";
        }
        //先校验是否已经存在订单了
        // 获取所有的订单编号 然后根据订单编号校验是否已经有存在的订单
        List<String> orderNumbers = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            ReturnOrderInfo info = list.get(i);
            if (StringUtils.isEmpty(info.getOrderNumber())) {
                return StringUtils.format("第{}行订单编号不能为空", i + 1);
            }
            if (StringUtils.isNull(info.getReturnPrice())) {
                info.setReturnPrice(BigDecimal.ZERO);
            }
            if (StringUtils.isNull(info.getLastReturnPrice())) {
                info.setLastReturnPrice(BigDecimal.ZERO);
            }
            orderNumbers.add(info.getOrderNumber());
        }
        //根据订单编号列表查询退货订单
        List<ReturnOrderInfo> returnOrderInfos = this.list(new LambdaQueryWrapper<ReturnOrderInfo>().in(ReturnOrderInfo::getOrderNumber, orderNumbers));
        //如果有则提示订单已存在
        if (StringUtils.isNotEmpty(returnOrderInfos)) {
            return StringUtils.format("订单编号{}已存在", returnOrderInfos.get(0).getOrderNumber());
        }
        List<PurchaseOrderInfo> orderInfos = new ArrayList<>(list.size());
        Date nowDate = DateUtils.getNowDate();
        //如果都通过，根据订单重新赋值
        for (ReturnOrderInfo info : list) {
            info.setCreateTime(nowDate);

            //未退货订单赋值并查询订单信息
            PurchaseOrderInfo orderInfo = checkReturnOrder(info);
            BPOrderInfo bpOrderInfo = bpOrderInfoService.selectBPOrderInfoByOrderNumber(orderInfo.getOrderNumber());
            orderInfo = orderInfoService.getOrderProfit(orderInfo, info, bpOrderInfo);
            orderInfos.add(orderInfo);
        }
        transactionTemplate.execute(item -> {
            try {
                returnOrderInfoMapper.insert(list);
                return orderInfoService.updateBatchById(orderInfos);
            } catch (Exception e) {
                log.error("导入退货订单数据失败，原因：", e);
                throw new ServiceException("导入数据失败，请检查数据结构是否正确,例如是否导入了重复的订单编号,数据格式是否和描述相同！！！");
            }
        });
        return StringUtils.format("导入成功，成功导入{}条数据", list.size());
    }

    @Transactional
    @Override
    public int mySaveOrUpdate(ReturnOrderInfo returnOrderInfo) {
        PurchaseOrderInfo orderInfo = checkReturnOrder(returnOrderInfo);
//        if (StringUtils.isNull(this.selectReturnOrderByOrderNumber(returnOrderInfo.getOrderNumber()))) {
//            returnOrderInfo.setCreateTime(DateUtils.getNowDate());
//        }
        this.saveOrUpdate(returnOrderInfo);
        return orderInfoService.updatePurchaseOrderInfo(orderInfo);
    }

    //endregion
    @Override
    public QueryWrapper<ReturnOrderInfo> getQueryWrapper(ReturnOrderInfoQuery returnOrderInfoQuery) {
        QueryWrapper<ReturnOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = returnOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String orderNumber = returnOrderInfoQuery.getOrderNumber();
        queryWrapper.eq(StringUtils.isNotEmpty(orderNumber), "order_number", orderNumber);

        String orderType = returnOrderInfoQuery.getOrderType();
        queryWrapper.eq(StringUtils.isNotEmpty(orderType), "order_type", orderType);

        Long storeId = returnOrderInfoQuery.getStoreId();
        queryWrapper.eq(StringUtils.isNotNull(storeId), "store_id", storeId);

        String returnStatus = returnOrderInfoQuery.getReturnStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(returnStatus), "return_status", returnStatus);

        Date returnAccomplishTime = returnOrderInfoQuery.getReturnAccomplishTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginReturnAccomplishTime")) && StringUtils.isNotNull(params.get("endReturnAccomplishTime")), "return_accomplish_time", params.get("beginReturnAccomplishTime"), params.get("endReturnAccomplishTime"));

        Long userId = returnOrderInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = returnOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = returnOrderInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = returnOrderInfoQuery.getUpdateTime();
        queryWrapper.eq(StringUtils.isNotNull(updateTime), "update_time", updateTime);

        Long deptId = returnOrderInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        return queryWrapper;
    }

    @Override
    public List<ReturnOrderInfoVo> convertVoList(List<ReturnOrderInfo> returnOrderInfoList) {
        if (StringUtils.isEmpty(returnOrderInfoList)) {
            return Collections.emptyList();
        }
        return returnOrderInfoList.stream().map(ReturnOrderInfoVo::objToVo).collect(Collectors.toList());
    }

}
