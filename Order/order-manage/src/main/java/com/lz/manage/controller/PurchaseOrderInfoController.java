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
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoVo;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoQuery;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoInsert;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoEdit;
import com.lz.manage.service.IPurchaseOrderInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 采购发货信息Controller
 *
 * @author YY
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/manage/purchaseOrderInfo")
public class PurchaseOrderInfoController extends BaseController
{
    @Resource
    private IPurchaseOrderInfoService purchaseOrderInfoService;

    /**
     * 查询采购发货信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseOrderInfoQuery purchaseOrderInfoQuery)
    {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoQuery.queryToObj(purchaseOrderInfoQuery);
        startPage();
        List<PurchaseOrderInfo> list = purchaseOrderInfoService.selectPurchaseOrderInfoList(purchaseOrderInfo);
        List<PurchaseOrderInfoVo> listVo= list.stream().map(PurchaseOrderInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出采购发货信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:export')")
    @Log(title = "采购发货信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseOrderInfoQuery purchaseOrderInfoQuery)
    {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoQuery.queryToObj(purchaseOrderInfoQuery);
        List<PurchaseOrderInfo> list = purchaseOrderInfoService.selectPurchaseOrderInfoList(purchaseOrderInfo);
        ExcelUtil<PurchaseOrderInfo> util = new ExcelUtil<PurchaseOrderInfo>(PurchaseOrderInfo.class);
        util.exportExcel(response, list, "采购发货信息数据");
    }

    /**
     * 获取采购发货信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        PurchaseOrderInfo purchaseOrderInfo = purchaseOrderInfoService.selectPurchaseOrderInfoById(id);
        return success(PurchaseOrderInfoVo.objToVo(purchaseOrderInfo));
    }

    /**
     * 新增采购发货信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:add')")
    @Log(title = "采购发货信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseOrderInfoInsert purchaseOrderInfoInsert)
    {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoInsert.insertToObj(purchaseOrderInfoInsert);
        return toAjax(purchaseOrderInfoService.insertPurchaseOrderInfo(purchaseOrderInfo));
    }

    /**
     * 修改采购发货信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:edit')")
    @Log(title = "采购发货信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseOrderInfoEdit purchaseOrderInfoEdit)
    {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoEdit.editToObj(purchaseOrderInfoEdit);
        return toAjax(purchaseOrderInfoService.updatePurchaseOrderInfo(purchaseOrderInfo));
    }

    /**
     * 删除采购发货信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:remove')")
    @Log(title = "采购发货信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(purchaseOrderInfoService.deletePurchaseOrderInfoByIds(ids));
    }
}
