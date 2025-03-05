package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;

import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ReplacementOrderInfoMapper;
import com.lz.manage.model.domain.ReplacementOrderInfo;
import com.lz.manage.service.IReplacementOrderInfoService;
import com.lz.manage.model.dto.replacementOrderInfo.ReplacementOrderInfoQuery;
import com.lz.manage.model.vo.replacementOrderInfo.ReplacementOrderInfoVo;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 补单明细Service业务层处理
 * 
 * @author YY
 * @date 2025-03-03
 */
@Service
public class ReplacementOrderInfoServiceImpl extends ServiceImpl<ReplacementOrderInfoMapper, ReplacementOrderInfo> implements IReplacementOrderInfoService
{
    @Resource
    private ReplacementOrderInfoMapper replacementOrderInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private TransactionTemplate transactionTemplate;

    //region mybatis代码
    /**
     * 查询补单明细
     * 
     * @param id 补单明细主键
     * @return 补单明细
     */
    @Override
    public ReplacementOrderInfo selectReplacementOrderInfoById(Long id)
    {
        return replacementOrderInfoMapper.selectReplacementOrderInfoById(id);
    }

    /**
     * 查询补单明细列表
     * 
     * @param replacementOrderInfo 补单明细
     * @return 补单明细
     */
    @Override
    public List<ReplacementOrderInfo> selectReplacementOrderInfoList(ReplacementOrderInfo replacementOrderInfo)
    {
        List<ReplacementOrderInfo> orderInfos = replacementOrderInfoMapper.selectReplacementOrderInfoList(replacementOrderInfo);
        for (ReplacementOrderInfo info : orderInfos) {

        }
        return orderInfos;
    }

    /**
     * 新增补单明细
     * 
     * @param replacementOrderInfo 补单明细
     * @return 结果
     */
    @Override
    public int insertReplacementOrderInfo(ReplacementOrderInfo replacementOrderInfo)
    {
        replacementOrderInfo.setCreateTime(DateUtils.getNowDate());
        return replacementOrderInfoMapper.insertReplacementOrderInfo(replacementOrderInfo);
    }

    /**
     * 修改补单明细
     * 
     * @param replacementOrderInfo 补单明细
     * @return 结果
     */
    @Override
    public int updateReplacementOrderInfo(ReplacementOrderInfo replacementOrderInfo)
    {
        replacementOrderInfo.setUpdateTime(DateUtils.getNowDate());
        return replacementOrderInfoMapper.updateReplacementOrderInfo(replacementOrderInfo);
    }

    /**
     * 批量删除补单明细
     * 
     * @param ids 需要删除的补单明细主键
     * @return 结果
     */
    @Override
    public int deleteReplacementOrderInfoByIds(Long[] ids)
    {
        return replacementOrderInfoMapper.deleteReplacementOrderInfoByIds(ids);
    }

    /**
     * 删除补单明细信息
     * 
     * @param id 补单明细主键
     * @return 结果
     */
    @Override
    public int deleteReplacementOrderInfoById(Long id)
    {
        return replacementOrderInfoMapper.deleteReplacementOrderInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<ReplacementOrderInfo> getQueryWrapper(ReplacementOrderInfoQuery replacementOrderInfoQuery){
        QueryWrapper<ReplacementOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = replacementOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String orderNumber = replacementOrderInfoQuery.getOrderNumber();
        queryWrapper.like(StringUtils.isNotEmpty(orderNumber) ,"order_number",orderNumber);

        Long storeId = replacementOrderInfoQuery.getStoreId();
        queryWrapper.eq( StringUtils.isNotNull(storeId),"store_id",storeId);

        Date dateTime = replacementOrderInfoQuery.getDateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginDateTime"))&&StringUtils.isNotNull(params.get("endDateTime")),"date_time",params.get("beginDateTime"),params.get("endDateTime"));

        String wxNumber = replacementOrderInfoQuery.getWxNumber();
        queryWrapper.like(StringUtils.isNotEmpty(wxNumber) ,"wx_number",wxNumber);

        String tbNumber = replacementOrderInfoQuery.getTbNumber();
        queryWrapper.like(StringUtils.isNotEmpty(tbNumber) ,"tb_number",tbNumber);

        String returnStatus = replacementOrderInfoQuery.getReturnStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(returnStatus) ,"return_status",returnStatus);

        Long userId = replacementOrderInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = replacementOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = replacementOrderInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = replacementOrderInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        Long deptId = replacementOrderInfoQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        return queryWrapper;
    }

    @Override
    public List<ReplacementOrderInfoVo> convertVoList(List<ReplacementOrderInfo> replacementOrderInfoList) {
        if (StringUtils.isEmpty(replacementOrderInfoList)) {
            return Collections.emptyList();
        }
        return replacementOrderInfoList.stream().map(ReplacementOrderInfoVo::objToVo).collect(Collectors.toList());
    }

}
