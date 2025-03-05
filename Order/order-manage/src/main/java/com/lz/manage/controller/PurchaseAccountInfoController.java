package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.common.utils.StringUtils;
import com.lz.manage.model.domain.StoreInfo;
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
import com.lz.manage.model.domain.PurchaseAccountInfo;
import com.lz.manage.model.vo.purchaseAccountInfo.PurchaseAccountInfoVo;
import com.lz.manage.model.dto.purchaseAccountInfo.PurchaseAccountInfoQuery;
import com.lz.manage.model.dto.purchaseAccountInfo.PurchaseAccountInfoInsert;
import com.lz.manage.model.dto.purchaseAccountInfo.PurchaseAccountInfoEdit;
import com.lz.manage.service.IPurchaseAccountInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 采购账号信息Controller
 *
 * @author YY
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/manage/purchaseAccountInfo")
public class PurchaseAccountInfoController extends BaseController
{
    @Resource
    private IPurchaseAccountInfoService purchaseAccountInfoService;

    @Resource
    private ISysDeptService deptService;

    /**
     * 查询采购账号信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseAccountInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseAccountInfoQuery purchaseAccountInfoQuery)
    {
        PurchaseAccountInfo purchaseAccountInfo = PurchaseAccountInfoQuery.queryToObj(purchaseAccountInfoQuery);
        if (StringUtils.isNotNull(purchaseAccountInfo.getDeptId())) {
            purchaseAccountInfo.setDeptIds(deptService.selectDeptByIdReturnIds(purchaseAccountInfo.getDeptId()));
        }
        startPage();
        List<PurchaseAccountInfo> list = purchaseAccountInfoService.selectPurchaseAccountInfoList(purchaseAccountInfo);
        List<PurchaseAccountInfoVo> listVo= list.stream().map(PurchaseAccountInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出采购账号信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseAccountInfo:export')")
    @Log(title = "采购账号信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseAccountInfoQuery purchaseAccountInfoQuery)
    {
        PurchaseAccountInfo purchaseAccountInfo = PurchaseAccountInfoQuery.queryToObj(purchaseAccountInfoQuery);
        List<PurchaseAccountInfo> list = purchaseAccountInfoService.selectPurchaseAccountInfoList(purchaseAccountInfo);
        ExcelUtil<PurchaseAccountInfo> util = new ExcelUtil<PurchaseAccountInfo>(PurchaseAccountInfo.class);
        util.exportExcel(response, list, "采购账号信息数据");
    }

    /**
     * 获取采购账号信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseAccountInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        PurchaseAccountInfo purchaseAccountInfo = purchaseAccountInfoService.selectPurchaseAccountInfoById(id);
        return success(PurchaseAccountInfoVo.objToVo(purchaseAccountInfo));
    }

    /**
     * 新增采购账号信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseAccountInfo:add')")
    @Log(title = "采购账号信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseAccountInfoInsert purchaseAccountInfoInsert)
    {
        PurchaseAccountInfo purchaseAccountInfo = PurchaseAccountInfoInsert.insertToObj(purchaseAccountInfoInsert);
        return toAjax(purchaseAccountInfoService.insertPurchaseAccountInfo(purchaseAccountInfo));
    }

    /**
     * 修改采购账号信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseAccountInfo:edit')")
    @Log(title = "采购账号信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseAccountInfoEdit purchaseAccountInfoEdit)
    {
        PurchaseAccountInfo purchaseAccountInfo = PurchaseAccountInfoEdit.editToObj(purchaseAccountInfoEdit);
        return toAjax(purchaseAccountInfoService.updatePurchaseAccountInfo(purchaseAccountInfo));
    }

    /**
     * 删除采购账号信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseAccountInfo:remove')")
    @Log(title = "采购账号信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(purchaseAccountInfoService.deletePurchaseAccountInfoByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('manage:purchaseAccountInfo:import')")
    @Log(title = "导入采购账号", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<PurchaseAccountInfo> util = new ExcelUtil<PurchaseAccountInfo>(PurchaseAccountInfo.class);
        List<PurchaseAccountInfo> purchaseAccountInfoList = util.importExcel(file.getInputStream());
        String message = purchaseAccountInfoService.importPurchaseAccountInfo(purchaseAccountInfoList);
        return success(message);
    }

    @PreAuthorize("@ss.hasPermi('manage:purchaseAccountInfo:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<PurchaseAccountInfo> util = new ExcelUtil<PurchaseAccountInfo>(PurchaseAccountInfo.class);
        util.importTemplateExcel(response, "采购账号数据");
    }
}
