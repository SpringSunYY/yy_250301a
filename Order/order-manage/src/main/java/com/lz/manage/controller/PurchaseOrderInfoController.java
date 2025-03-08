package com.lz.manage.controller;

import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.core.page.TableDataInfo;
import com.lz.common.enums.BusinessType;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.manage.model.domain.BPOrderInfo;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.model.domain.ReturnOrderInfo;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoEdit;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoInsert;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoQuery;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderAllVo;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoCountVo;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoVo;
import com.lz.manage.service.IBPOrderInfoService;
import com.lz.manage.service.IPurchaseChannelInfoService;
import com.lz.manage.service.IPurchaseOrderInfoService;
import com.lz.manage.service.IReturnOrderInfoService;
import com.lz.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 采购发货信息Controller
 *
 * @author YY
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/manage/purchaseOrderInfo")
public class PurchaseOrderInfoController extends BaseController {
    @Resource
    private IPurchaseOrderInfoService purchaseOrderInfoService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IReturnOrderInfoService returnOrderInfoService;

    @Resource
    private IBPOrderInfoService bpOrderInfoService;

    @Resource
    private IPurchaseChannelInfoService channelInfoService;

    /**
     * 查询采购发货信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseOrderInfoQuery purchaseOrderInfoQuery) {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoQuery.queryToObj(purchaseOrderInfoQuery);
        if (StringUtils.isNotNull(purchaseOrderInfo.getDeptId())) {
            List<Long> deptIds = deptService.selectDeptByIdReturnIds(purchaseOrderInfo.getDeptId());
            purchaseOrderInfo.setDeptIds(deptIds);
        }
        if (StringUtils.isNotNull(purchaseOrderInfo.getPurchaseChannelsId())) {
            purchaseOrderInfo.setPurchaseChannelsIds(channelInfoService.selectPurchaseChannelInfoReturnIds(purchaseOrderInfo.getPurchaseChannelsId()));
        }
        startPage();
        List<PurchaseOrderInfo> list = purchaseOrderInfoService.selectPurchaseOrderInfoList(purchaseOrderInfo);
        List<PurchaseOrderAllVo> listVo = list.stream().map(PurchaseOrderAllVo::objToVo).collect(Collectors.toList());
        for (PurchaseOrderAllVo vo : listVo) {
            ReturnOrderInfo returnOrderInfo = returnOrderInfoService.selectReturnOrderByOrderNumber(vo.getOrderNumber());
            if (StringUtils.isNotNull(returnOrderInfo)) {
                vo.setReturnStatus(returnOrderInfo.getReturnStatus());
                vo.setReturnPrice(returnOrderInfo.getReturnPrice());
                vo.setLastReturnPrice(returnOrderInfo.getLastReturnPrice());
                vo.setReturnAccomplishTime(returnOrderInfo.getReturnAccomplishTime());
            }
            BPOrderInfo bpOrderInfo = bpOrderInfoService.selectBPOrderInfoByOrderNumber(vo.getOrderNumber());
            if (StringUtils.isNotNull(bpOrderInfo)) {
                vo.setBPPrice(bpOrderInfo.getBPPrice());
                vo.setBPTime(bpOrderInfo.getBPTime());
                vo.setAfterSalePrice(bpOrderInfo.getAfterSalePrice());
                vo.setAfterSaleTime(bpOrderInfo.getAfterSaleTime());
                vo.setAfterSaleImage(bpOrderInfo.getAfterSaleImage());
            }
        }
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
    public void export(HttpServletResponse response, PurchaseOrderInfoQuery purchaseOrderInfoQuery) {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoQuery.queryToObj(purchaseOrderInfoQuery);
        List<PurchaseOrderInfo> list = purchaseOrderInfoService.selectPurchaseOrderInfoList(purchaseOrderInfo);
        ExcelUtil<PurchaseOrderInfo> util = new ExcelUtil<PurchaseOrderInfo>(PurchaseOrderInfo.class);
        util.exportExcel(response, list, "采购发货信息数据");
    }

    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:export')")
    @Log(title = "采购发货详细信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export/detail")
    public void exportDetail(HttpServletResponse response, PurchaseOrderInfoQuery purchaseOrderInfoQuery) {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoQuery.queryToObj(purchaseOrderInfoQuery);
        List<PurchaseOrderAllVo> listVo = purchaseOrderInfoService.selectPurchaseOrderInfoList(purchaseOrderInfo).stream().map(PurchaseOrderAllVo::objToVo).collect(Collectors.toList());
        for (PurchaseOrderAllVo vo : listVo) {
            ReturnOrderInfo returnOrderInfo = returnOrderInfoService.selectReturnOrderByOrderNumber(vo.getOrderNumber());
            if (StringUtils.isNotNull(returnOrderInfo)) {
                vo.setReturnStatus(returnOrderInfo.getReturnStatus());
                vo.setReturnPrice(returnOrderInfo.getReturnPrice());
                vo.setLastReturnPrice(returnOrderInfo.getLastReturnPrice());
                vo.setReturnAccomplishTime(returnOrderInfo.getReturnAccomplishTime());
            }
            BPOrderInfo bpOrderInfo = bpOrderInfoService.selectBPOrderInfoByOrderNumber(vo.getOrderNumber());
            if (StringUtils.isNotNull(bpOrderInfo)) {
                vo.setBPPrice(bpOrderInfo.getBPPrice());
                vo.setBPTime(bpOrderInfo.getBPTime());
                vo.setAfterSalePrice(bpOrderInfo.getAfterSalePrice());
                vo.setAfterSaleTime(bpOrderInfo.getAfterSaleTime());
                vo.setAfterSaleImage(bpOrderInfo.getAfterSaleImage());
            }
        }
        ExcelUtil<PurchaseOrderAllVo> util = new ExcelUtil<PurchaseOrderAllVo>(PurchaseOrderAllVo.class);
        util.exportExcel(response, listVo, "采购发货信息数据");
    }

    /**
     * 获取采购发货信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        PurchaseOrderInfo purchaseOrderInfo = purchaseOrderInfoService.selectPurchaseOrderInfoById(id);
        return success(PurchaseOrderInfoVo.objToVo(purchaseOrderInfo));
    }

    /**
     * 新增采购发货信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:add')")
    @Log(title = "采购发货信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseOrderInfoInsert purchaseOrderInfoInsert) {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoInsert.insertToObj(purchaseOrderInfoInsert);
        return toAjax(purchaseOrderInfoService.insertPurchaseOrderInfo(purchaseOrderInfo));
    }

    /**
     * 修改采购发货信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:edit')")
    @Log(title = "采购发货信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseOrderInfoEdit purchaseOrderInfoEdit) {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoEdit.editToObj(purchaseOrderInfoEdit);
        return toAjax(purchaseOrderInfoService.updatePurchaseOrderInfo(purchaseOrderInfo));
    }

    /**
     * 删除采购发货信息
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:remove')")
    @Log(title = "采购发货信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(purchaseOrderInfoService.deletePurchaseOrderInfoByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:import')")
    @Log(title = "导入采购订单", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<PurchaseOrderInfo> util = new ExcelUtil<PurchaseOrderInfo>(PurchaseOrderInfo.class);
        List<PurchaseOrderInfo> list = util.importExcel(file.getInputStream());
        String message = purchaseOrderInfoService.importPurchaseOrderInfo(list);
        return success(message);
    }

    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<PurchaseOrderInfo> util = new ExcelUtil<PurchaseOrderInfo>(PurchaseOrderInfo.class);
        util.importTemplateExcel(response, "采购订单数据");
    }

    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:list')")
    @GetMapping("/getPurchaseOrderInfoCount")
    public AjaxResult getPurchaseOrderInfoCount(PurchaseOrderInfoQuery purchaseOrderInfoQuery) {
        PurchaseOrderInfo purchaseOrderInfo = PurchaseOrderInfoQuery.queryToObj(purchaseOrderInfoQuery);
        if (StringUtils.isNotNull(purchaseOrderInfo.getDeptId())) {
            List<Long> deptIds = deptService.selectDeptByIdReturnIds(purchaseOrderInfo.getDeptId());
            purchaseOrderInfo.setDeptIds(deptIds);
        }
        if (StringUtils.isNotNull(purchaseOrderInfo.getPurchaseChannelsId())) {
            purchaseOrderInfo.setPurchaseChannelsIds(channelInfoService.selectPurchaseChannelInfoReturnIds(purchaseOrderInfo.getPurchaseChannelsId()));
        }
        PurchaseOrderInfoCountVo purchaseOrderInfoCountVo = purchaseOrderInfoService.getPurchaseOrderInfoCount(purchaseOrderInfo);
        return success(purchaseOrderInfoCountVo);
    }
}
