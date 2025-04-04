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
import com.lz.manage.model.domain.StoreInfo;
import com.lz.manage.model.vo.storeInfo.StoreInfoVo;
import com.lz.manage.model.dto.storeInfo.StoreInfoQuery;
import com.lz.manage.model.dto.storeInfo.StoreInfoInsert;
import com.lz.manage.model.dto.storeInfo.StoreInfoEdit;
import com.lz.manage.service.IStoreInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 店铺信息Controller
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/manage/storeInfo")
public class StoreInfoController extends BaseController
{
    @Resource
    private IStoreInfoService storeInfoService;

    @Resource
    private ISysDeptService deptService;

    /**
     * 查询店铺信息列表
     */
    @PreAuthorize("@ss.hasAnyPermi('manage:storeInfo:list,manage:storeInfo:query')")
    @GetMapping("/list")
    public TableDataInfo list(StoreInfoQuery storeInfoQuery)
    {
        StoreInfo storeInfo = StoreInfoQuery.queryToObj(storeInfoQuery);
        if (StringUtils.isNotNull(storeInfo.getDeptId())) {
            storeInfo.setDeptIds(deptService.selectDeptByIdReturnIds(storeInfo.getDeptId()));
        }else {
            storeInfo.setDeptIds(deptService.selectDeptByIdReturnIds(getDeptId()));
        }
        startPage();
        List<StoreInfo> list = storeInfoService.selectStoreInfoList(storeInfo);
        List<StoreInfoVo> listVo= list.stream().map(StoreInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出店铺信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:storeInfo:export')")
    @Log(title = "店铺信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreInfoQuery storeInfoQuery)
    {
        StoreInfo storeInfo = StoreInfoQuery.queryToObj(storeInfoQuery);
        List<StoreInfo> list = storeInfoService.selectStoreInfoList(storeInfo);
        ExcelUtil<StoreInfo> util = new ExcelUtil<StoreInfo>(StoreInfo.class);
        util.exportExcel(response, list, "店铺信息数据");
    }

    /**
     * 获取店铺信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:storeInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        StoreInfo storeInfo = storeInfoService.selectStoreInfoById(id);
        return success(StoreInfoVo.objToVo(storeInfo));
    }

    /**
     * 新增店铺信息
     */
    @PreAuthorize("@ss.hasPermi('manage:storeInfo:add')")
    @Log(title = "店铺信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreInfoInsert storeInfoInsert)
    {
        StoreInfo storeInfo = StoreInfoInsert.insertToObj(storeInfoInsert);
        return toAjax(storeInfoService.insertStoreInfo(storeInfo));
    }

    /**
     * 修改店铺信息
     */
    @PreAuthorize("@ss.hasPermi('manage:storeInfo:edit')")
    @Log(title = "店铺信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreInfoEdit storeInfoEdit)
    {
        StoreInfo storeInfo = StoreInfoEdit.editToObj(storeInfoEdit);
        return toAjax(storeInfoService.updateStoreInfo(storeInfo));
    }

    /**
     * 删除店铺信息
     */
    @PreAuthorize("@ss.hasPermi('manage:storeInfo:remove')")
    @Log(title = "店铺信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(storeInfoService.deleteStoreInfoByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('manage:storeInfo:import')")
    @Log(title = "导入店铺", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<StoreInfo> util = new ExcelUtil<StoreInfo>(StoreInfo.class);
        List<StoreInfo> storeInfoList = util.importExcel(file.getInputStream());
        String message = storeInfoService.importStoreInfo(storeInfoList);
        return success(message);
    }

    @PreAuthorize("@ss.hasPermi('manage:storeInfo:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<StoreInfo> util = new ExcelUtil<StoreInfo>(StoreInfo.class);
        util.importTemplateExcel(response, "店铺数据");
    }
}
