package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.common.utils.StringUtils;
import com.lz.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.AfterSaleOrderInfo;
import com.lz.manage.model.vo.afterSaleOrderInfo.AfterSaleOrderInfoVo;
import com.lz.manage.model.dto.afterSaleOrderInfo.AfterSaleOrderInfoQuery;
import com.lz.manage.model.dto.afterSaleOrderInfo.AfterSaleOrderInfoInsert;
import com.lz.manage.model.dto.afterSaleOrderInfo.AfterSaleOrderInfoEdit;
import com.lz.manage.service.IAfterSaleOrderInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 售后订单信息Controller
 *
 * @author YY
 * @date 2025-03-09
 */
@RestController
@RequestMapping("/manage/afterSaleOrderInfo")
public class AfterSaleOrderInfoController extends BaseController
{
    @Resource
    private IAfterSaleOrderInfoService afterSaleOrderInfoService;

    @Resource
    private ISysDeptService deptService;

    /**
     * 查询售后订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSaleOrderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(AfterSaleOrderInfoQuery afterSaleOrderInfoQuery)
    {
        AfterSaleOrderInfo afterSaleOrderInfo = AfterSaleOrderInfoQuery.queryToObj(afterSaleOrderInfoQuery);
        if (StringUtils.isNotNull(afterSaleOrderInfo.getDeptId())) {
            List<Long> dept = deptService.selectDeptByIdReturnIds(afterSaleOrderInfo.getDeptId());
            afterSaleOrderInfo.setDeptIds(dept);
        }
        startPage();
        List<AfterSaleOrderInfo> list = afterSaleOrderInfoService.selectAfterSaleOrderInfoList(afterSaleOrderInfo);
        List<AfterSaleOrderInfoVo> listVo= list.stream().map(AfterSaleOrderInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出售后订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSaleOrderInfo:export')")
    @Log(title = "售后订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AfterSaleOrderInfoQuery afterSaleOrderInfoQuery)
    {
        AfterSaleOrderInfo afterSaleOrderInfo = AfterSaleOrderInfoQuery.queryToObj(afterSaleOrderInfoQuery);
        List<AfterSaleOrderInfo> list = afterSaleOrderInfoService.selectAfterSaleOrderInfoList(afterSaleOrderInfo);
        ExcelUtil<AfterSaleOrderInfo> util = new ExcelUtil<AfterSaleOrderInfo>(AfterSaleOrderInfo.class);
        util.exportExcel(response, list, "售后订单信息数据");
    }

    /**
     * 获取售后订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSaleOrderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        AfterSaleOrderInfo afterSaleOrderInfo = afterSaleOrderInfoService.selectAfterSaleOrderInfoById(id);
        return success(AfterSaleOrderInfoVo.objToVo(afterSaleOrderInfo));
    }

    /**
     * 新增售后订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSaleOrderInfo:add')")
    @Log(title = "售后订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AfterSaleOrderInfoInsert afterSaleOrderInfoInsert)
    {
        AfterSaleOrderInfo afterSaleOrderInfo = AfterSaleOrderInfoInsert.insertToObj(afterSaleOrderInfoInsert);
        return toAjax(afterSaleOrderInfoService.insertAfterSaleOrderInfo(afterSaleOrderInfo));
    }

    /**
     * 修改售后订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSaleOrderInfo:edit')")
    @Log(title = "售后订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AfterSaleOrderInfoEdit afterSaleOrderInfoEdit)
    {
        AfterSaleOrderInfo afterSaleOrderInfo = AfterSaleOrderInfoEdit.editToObj(afterSaleOrderInfoEdit);
        return toAjax(afterSaleOrderInfoService.updateAfterSaleOrderInfo(afterSaleOrderInfo));
    }

    /**
     * 删除售后订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSaleOrderInfo:remove')")
    @Log(title = "售后订单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(afterSaleOrderInfoService.deleteAfterSaleOrderInfoByIds(ids));
    }
}
