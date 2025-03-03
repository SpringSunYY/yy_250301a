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
import com.lz.manage.model.domain.EmptyBagCourierHistoryInfo;
import com.lz.manage.model.vo.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryInfoVo;
import com.lz.manage.model.dto.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryInfoQuery;
import com.lz.manage.model.dto.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryInfoInsert;
import com.lz.manage.model.dto.emptyBagCourierHistoryInfo.EmptyBagCourierHistoryInfoEdit;
import com.lz.manage.service.IEmptyBagCourierHistoryInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 空包/快递充值记录Controller
 *
 * @author YY
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/manage/emptyBagCourierHistoryInfo")
public class EmptyBagCourierHistoryInfoController extends BaseController
{
    @Resource
    private IEmptyBagCourierHistoryInfoService emptyBagCourierHistoryInfoService;

    /**
     * 查询空包/快递充值记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:emptyBagCourierHistoryInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(EmptyBagCourierHistoryInfoQuery emptyBagCourierHistoryInfoQuery)
    {
        EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo = EmptyBagCourierHistoryInfoQuery.queryToObj(emptyBagCourierHistoryInfoQuery);
        startPage();
        List<EmptyBagCourierHistoryInfo> list = emptyBagCourierHistoryInfoService.selectEmptyBagCourierHistoryInfoList(emptyBagCourierHistoryInfo);
        List<EmptyBagCourierHistoryInfoVo> listVo= list.stream().map(EmptyBagCourierHistoryInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出空包/快递充值记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:emptyBagCourierHistoryInfo:export')")
    @Log(title = "空包/快递充值记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EmptyBagCourierHistoryInfoQuery emptyBagCourierHistoryInfoQuery)
    {
        EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo = EmptyBagCourierHistoryInfoQuery.queryToObj(emptyBagCourierHistoryInfoQuery);
        List<EmptyBagCourierHistoryInfo> list = emptyBagCourierHistoryInfoService.selectEmptyBagCourierHistoryInfoList(emptyBagCourierHistoryInfo);
        ExcelUtil<EmptyBagCourierHistoryInfo> util = new ExcelUtil<EmptyBagCourierHistoryInfo>(EmptyBagCourierHistoryInfo.class);
        util.exportExcel(response, list, "空包/快递充值记录数据");
    }

    /**
     * 获取空包/快递充值记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:emptyBagCourierHistoryInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo = emptyBagCourierHistoryInfoService.selectEmptyBagCourierHistoryInfoById(id);
        return success(EmptyBagCourierHistoryInfoVo.objToVo(emptyBagCourierHistoryInfo));
    }

    /**
     * 新增空包/快递充值记录
     */
    @PreAuthorize("@ss.hasPermi('manage:emptyBagCourierHistoryInfo:add')")
    @Log(title = "空包/快递充值记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmptyBagCourierHistoryInfoInsert emptyBagCourierHistoryInfoInsert)
    {
        EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo = EmptyBagCourierHistoryInfoInsert.insertToObj(emptyBagCourierHistoryInfoInsert);
        return toAjax(emptyBagCourierHistoryInfoService.insertEmptyBagCourierHistoryInfo(emptyBagCourierHistoryInfo));
    }

    /**
     * 修改空包/快递充值记录
     */
    @PreAuthorize("@ss.hasPermi('manage:emptyBagCourierHistoryInfo:edit')")
    @Log(title = "空包/快递充值记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EmptyBagCourierHistoryInfoEdit emptyBagCourierHistoryInfoEdit)
    {
        EmptyBagCourierHistoryInfo emptyBagCourierHistoryInfo = EmptyBagCourierHistoryInfoEdit.editToObj(emptyBagCourierHistoryInfoEdit);
        return toAjax(emptyBagCourierHistoryInfoService.updateEmptyBagCourierHistoryInfo(emptyBagCourierHistoryInfo));
    }

    /**
     * 删除空包/快递充值记录
     */
    @PreAuthorize("@ss.hasPermi('manage:emptyBagCourierHistoryInfo:remove')")
    @Log(title = "空包/快递充值记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(emptyBagCourierHistoryInfoService.deleteEmptyBagCourierHistoryInfoByIds(ids));
    }
}
