package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
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
import com.lz.manage.model.domain.ReplacementOrderInfo;
import com.lz.manage.model.vo.replacementOrderInfo.ReplacementOrderInfoVo;
import com.lz.manage.model.dto.replacementOrderInfo.ReplacementOrderInfoQuery;
import com.lz.manage.model.dto.replacementOrderInfo.ReplacementOrderInfoInsert;
import com.lz.manage.model.dto.replacementOrderInfo.ReplacementOrderInfoEdit;
import com.lz.manage.service.IReplacementOrderInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 补单明细Controller
 *
 * @author YY
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/manage/replacementOrderInfo")
public class ReplacementOrderInfoController extends BaseController
{
    @Resource
    private IReplacementOrderInfoService replacementOrderInfoService;

    /**
     * 查询补单明细列表
     */
    @PreAuthorize("@ss.hasPermi('manage:replacementOrderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ReplacementOrderInfoQuery replacementOrderInfoQuery)
    {
        ReplacementOrderInfo replacementOrderInfo = ReplacementOrderInfoQuery.queryToObj(replacementOrderInfoQuery);
        startPage();
        List<ReplacementOrderInfo> list = replacementOrderInfoService.selectReplacementOrderInfoList(replacementOrderInfo);
        List<ReplacementOrderInfoVo> listVo= list.stream().map(ReplacementOrderInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出补单明细列表
     */
    @PreAuthorize("@ss.hasPermi('manage:replacementOrderInfo:export')")
    @Log(title = "补单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReplacementOrderInfoQuery replacementOrderInfoQuery)
    {
        ReplacementOrderInfo replacementOrderInfo = ReplacementOrderInfoQuery.queryToObj(replacementOrderInfoQuery);
        List<ReplacementOrderInfo> list = replacementOrderInfoService.selectReplacementOrderInfoList(replacementOrderInfo);
        ExcelUtil<ReplacementOrderInfo> util = new ExcelUtil<ReplacementOrderInfo>(ReplacementOrderInfo.class);
        util.exportExcel(response, list, "补单明细数据");
    }

    /**
     * 获取补单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:replacementOrderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ReplacementOrderInfo replacementOrderInfo = replacementOrderInfoService.selectReplacementOrderInfoById(id);
        return success(ReplacementOrderInfoVo.objToVo(replacementOrderInfo));
    }

    /**
     * 新增补单明细
     */
    @PreAuthorize("@ss.hasPermi('manage:replacementOrderInfo:add')")
    @Log(title = "补单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReplacementOrderInfoInsert replacementOrderInfoInsert)
    {
        ReplacementOrderInfo replacementOrderInfo = ReplacementOrderInfoInsert.insertToObj(replacementOrderInfoInsert);
        return toAjax(replacementOrderInfoService.insertReplacementOrderInfo(replacementOrderInfo));
    }

    /**
     * 修改补单明细
     */
    @PreAuthorize("@ss.hasPermi('manage:replacementOrderInfo:edit')")
    @Log(title = "补单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReplacementOrderInfoEdit replacementOrderInfoEdit)
    {
        ReplacementOrderInfo replacementOrderInfo = ReplacementOrderInfoEdit.editToObj(replacementOrderInfoEdit);
        return toAjax(replacementOrderInfoService.updateReplacementOrderInfo(replacementOrderInfo));
    }

    /**
     * 删除补单明细
     */
    @PreAuthorize("@ss.hasPermi('manage:replacementOrderInfo:remove')")
    @Log(title = "补单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(replacementOrderInfoService.deleteReplacementOrderInfoByIds(ids));
    }
}
