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
import com.lz.manage.model.domain.PurchaseChannelInfo;
import com.lz.manage.model.vo.purchaseChannelInfo.PurchaseChannelInfoVo;
import com.lz.manage.model.dto.purchaseChannelInfo.PurchaseChannelInfoQuery;
import com.lz.manage.model.dto.purchaseChannelInfo.PurchaseChannelInfoInsert;
import com.lz.manage.model.dto.purchaseChannelInfo.PurchaseChannelInfoEdit;
import com.lz.manage.service.IPurchaseChannelInfoService;
import com.lz.common.utils.poi.ExcelUtil;

/**
 * 采购渠道信息Controller
 *
 * @author YY
 * @date 2025-03-08
 */
@RestController
@RequestMapping("/manage/purchaseChannelInfo")
public class PurchaseChannelInfoController extends BaseController
{
    @Resource
    private IPurchaseChannelInfoService purchaseChannelInfoService;

    /**
     * 查询采购渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseChannelInfo:list')")
    @GetMapping("/list")
    public AjaxResult list(PurchaseChannelInfoQuery purchaseChannelInfoQuery)
    {
        PurchaseChannelInfo purchaseChannelInfo = PurchaseChannelInfoQuery.queryToObj(purchaseChannelInfoQuery);
        List<PurchaseChannelInfo> list = purchaseChannelInfoService.selectPurchaseChannelInfoList(purchaseChannelInfo);
        List<PurchaseChannelInfoVo> listVo= list.stream().map(PurchaseChannelInfoVo::objToVo).collect(Collectors.toList());
        return success(listVo);
    }

    /**
     * 导出采购渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseChannelInfo:export')")
    @Log(title = "采购渠道信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseChannelInfoQuery purchaseChannelInfoQuery)
    {
        PurchaseChannelInfo purchaseChannelInfo = PurchaseChannelInfoQuery.queryToObj(purchaseChannelInfoQuery);
        List<PurchaseChannelInfo> list = purchaseChannelInfoService.selectPurchaseChannelInfoList(purchaseChannelInfo);
        ExcelUtil<PurchaseChannelInfo> util = new ExcelUtil<PurchaseChannelInfo>(PurchaseChannelInfo.class);
        util.exportExcel(response, list, "采购渠道信息数据");
    }

    /**
     * 获取采购渠道信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseChannelInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        PurchaseChannelInfo purchaseChannelInfo = purchaseChannelInfoService.selectPurchaseChannelInfoById(id);
        return success(PurchaseChannelInfoVo.objToVo(purchaseChannelInfo));
    }

    /**
     * 新增采购渠道信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseChannelInfo:add')")
    @Log(title = "采购渠道信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseChannelInfoInsert purchaseChannelInfoInsert)
    {
        PurchaseChannelInfo purchaseChannelInfo = PurchaseChannelInfoInsert.insertToObj(purchaseChannelInfoInsert);
        return toAjax(purchaseChannelInfoService.insertPurchaseChannelInfo(purchaseChannelInfo));
    }

    /**
     * 修改采购渠道信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseChannelInfo:edit')")
    @Log(title = "采购渠道信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseChannelInfoEdit purchaseChannelInfoEdit)
    {
        PurchaseChannelInfo purchaseChannelInfo = PurchaseChannelInfoEdit.editToObj(purchaseChannelInfoEdit);
        return toAjax(purchaseChannelInfoService.updatePurchaseChannelInfo(purchaseChannelInfo));
    }

    /**
     * 删除采购渠道信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseChannelInfo:remove')")
    @Log(title = "采购渠道信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(purchaseChannelInfoService.deletePurchaseChannelInfoByIds(ids));
    }
}
