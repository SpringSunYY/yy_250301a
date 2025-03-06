package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
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
import com.lz.manage.mapper.EmptyBagCourierHistoryInfoMapper;
import com.lz.manage.model.domain.EmptyBagCourierHistoryInfo;
import com.lz.manage.service.IEmptyBagCourierHistoryInfoService;
import com.lz.manage.model.dto.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryInfoQuery;
import com.lz.manage.model.vo.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryInfoVo;

/**
 * 空包/快递充值记录Service业务层处理
 *
 * @author YY
 * @date 2025-03-03
 */
@Service
public class EmptyBagCourierHistoryInfoServiceImpl extends ServiceImpl<EmptyBagCourierHistoryInfoMapper, EmptyBagCourierHistoryInfo> implements IEmptyBagCourierHistoryInfoService {
    @Resource
    private EmptyBagCourierHistoryInfoMapper emptyBagCourierHistoryInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;
    //region mybatis代码

    /**
     * 查询空包/快递充值记录
     *
     * @param id 空包/快递充值记录主键
     * @return 空包/快递充值记录
     */
    @Override
    public EmptyBagCourierHistoryInfo selectEmptyBagCourierHistoryInfoById(Long id) {
        return emptyBagCourierHistoryInfoMapper.selectEmptyBagCourierHistoryInfoById(id);
    }

    /**
     * 查询空包/快递充值记录列表
     *
     * @param emptyBagCourierHistoryInfo 空包/快递充值记录
     * @return 空包/快递充值记录
     */
    @Override
    public List<EmptyBagCourierHistoryInfo> selectEmptyBagCourierHistoryInfoList(EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo) {
        List<EmptyBagCourierHistoryInfo> emptyBagCourierHistoryInfos = emptyBagCourierHistoryInfoMapper.selectEmptyBagCourierHistoryInfoList(emptyBagCourierHistoryInfo);
        for (EmptyBagCourierHistoryInfo info : emptyBagCourierHistoryInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
        }
        return emptyBagCourierHistoryInfos;
    }

    /**
     * 新增空包/快递充值记录
     *
     * @param emptyBagCourierHistoryInfo 空包/快递充值记录
     * @return 结果
     */
    @Override
    public int insertEmptyBagCourierHistoryInfo(EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo) {
        checkEmptyBagCourier(emptyBagCourierHistoryInfo);
        emptyBagCourierHistoryInfo.setCreateTime(DateUtils.getNowDate());
        return emptyBagCourierHistoryInfoMapper.insertEmptyBagCourierHistoryInfo(emptyBagCourierHistoryInfo);
    }

    private void checkEmptyBagCourier(EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo) {
        SysUser user = userService.selectUserById(emptyBagCourierHistoryInfo.getUserId());
        if (StringUtils.isNull(user)) {
            throw new ServiceException("用户不存在!!!");
        }
        emptyBagCourierHistoryInfo.setDeptId(user.getDeptId());
    }

    /**
     * 修改空包/快递充值记录
     *
     * @param emptyBagCourierHistoryInfo 空包/快递充值记录
     * @return 结果
     */
    @Override
    public int updateEmptyBagCourierHistoryInfo(EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo) {
        checkEmptyBagCourier(emptyBagCourierHistoryInfo);
        emptyBagCourierHistoryInfo.setUpdateBy(SecurityUtils.getUsername());
        emptyBagCourierHistoryInfo.setUpdateTime(DateUtils.getNowDate());
        return emptyBagCourierHistoryInfoMapper.updateEmptyBagCourierHistoryInfo(emptyBagCourierHistoryInfo);
    }

    /**
     * 批量删除空包/快递充值记录
     *
     * @param ids 需要删除的空包/快递充值记录主键
     * @return 结果
     */
    @Override
    public int deleteEmptyBagCourierHistoryInfoByIds(Long[] ids) {
        return emptyBagCourierHistoryInfoMapper.deleteEmptyBagCourierHistoryInfoByIds(ids);
    }

    /**
     * 删除空包/快递充值记录信息
     *
     * @param id 空包/快递充值记录主键
     * @return 结果
     */
    @Override
    public int deleteEmptyBagCourierHistoryInfoById(Long id) {
        return emptyBagCourierHistoryInfoMapper.deleteEmptyBagCourierHistoryInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<EmptyBagCourierHistoryInfo> getQueryWrapper(EmptyBagCourierHistoryInfoQuery emptyBagCourierHistoryInfoQuery) {
        QueryWrapper<EmptyBagCourierHistoryInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = emptyBagCourierHistoryInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Date dateTime = emptyBagCourierHistoryInfoQuery.getDateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginDateTime")) && StringUtils.isNotNull(params.get("endDateTime")), "date_time", params.get("beginDateTime"), params.get("endDateTime"));

        String digest = emptyBagCourierHistoryInfoQuery.getDigest();
        queryWrapper.like(StringUtils.isNotEmpty(digest), "digest", digest);

        Long userId = emptyBagCourierHistoryInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = emptyBagCourierHistoryInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = emptyBagCourierHistoryInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = emptyBagCourierHistoryInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        Long deptId = emptyBagCourierHistoryInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        return queryWrapper;
    }

    @Override
    public List<EmptyBagCourierHistoryInfoVo> convertVoList(List<EmptyBagCourierHistoryInfo> emptyBagCourierHistoryInfoList) {
        if (StringUtils.isEmpty(emptyBagCourierHistoryInfoList)) {
            return Collections.emptyList();
        }
        return emptyBagCourierHistoryInfoList.stream().map(EmptyBagCourierHistoryInfoVo::objToVo).collect(Collectors.toList());
    }

}
