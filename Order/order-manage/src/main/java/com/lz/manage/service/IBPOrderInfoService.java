package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.BPOrderInfo;
import com.lz.manage.model.vo.bPOrderInfo.BPOrderInfoVo;
import com.lz.manage.model.dto.bPOrderInfo.BPOrderInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 白嫖订单信息Service接口
 * 
 * @author YY
 * @date 2025-03-03
 */
public interface IBPOrderInfoService extends IService<BPOrderInfo>
{
    //region mybatis代码
    /**
     * 查询白嫖订单信息
     * 
     * @param id 白嫖订单信息主键
     * @return 白嫖订单信息
     */
    public BPOrderInfo selectBPOrderInfoById(Long id);

    /**
     * 查询白嫖订单信息列表
     * 
     * @param bPOrderInfo 白嫖订单信息
     * @return 白嫖订单信息集合
     */
    public List<BPOrderInfo> selectBPOrderInfoList(BPOrderInfo bPOrderInfo);

    /**
     * 新增白嫖订单信息
     * 
     * @param bPOrderInfo 白嫖订单信息
     * @return 结果
     */
    public int insertBPOrderInfo(BPOrderInfo bPOrderInfo);

    /**
     * 修改白嫖订单信息
     * 
     * @param bPOrderInfo 白嫖订单信息
     * @return 结果
     */
    public int updateBPOrderInfo(BPOrderInfo bPOrderInfo);

    /**
     * 批量删除白嫖订单信息
     * 
     * @param ids 需要删除的白嫖订单信息主键集合
     * @return 结果
     */
    public int deleteBPOrderInfoByIds(Long[] ids);

    /**
     * 删除白嫖订单信息信息
     * 
     * @param id 白嫖订单信息主键
     * @return 结果
     */
    public int deleteBPOrderInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param bPOrderInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<BPOrderInfo> getQueryWrapper(BPOrderInfoQuery bPOrderInfoQuery);

    /**
     * 转换vo
     *
     * @param bPOrderInfoList BPOrderInfo集合
     * @return BPOrderInfoVO集合
     */
    List<BPOrderInfoVo> convertVoList(List<BPOrderInfo> bPOrderInfoList);

    /**
     * description:  根据订单号查询白嫖订单
     * author: YY
     * method: selectBPOrderInfoByOrderNumber
     * date: 2025/3/5 21:39
     * param:
     * param: orderNumber
     * return: com.lz.manage.model.domain.BPOrderInfo
     **/
    BPOrderInfo selectBPOrderInfoByOrderNumber(String orderNumber);

    /**
     * description: 新增或者修改白嫖订单
     * author: YY
     * method: addOrUpdate
     * date: 2025/3/6 09:12
     * param:
     * param: bPOrderInfo
     * return: int
     **/
    int addOrUpdateBPOrderInfo(BPOrderInfo bPOrderInfo);

    /**
     * description: 导入白嫖订单信息
     * author: YY
     * method: importBPOrderInfo
     * date: 2025/3/6 09:46
     * param:
     * param: bpOrderInfoList
     * return: java.lang.String
     **/
    String importBPOrderInfo(List<BPOrderInfo> bpOrderInfoList);
}
