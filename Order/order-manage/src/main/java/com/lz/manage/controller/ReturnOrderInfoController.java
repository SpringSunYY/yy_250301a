package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.common.utils.StringUtils;
import com.lz.manage.model.domain.PurchaseAccountInfo;
import com.lz.manage.model.vo.returnOrderInfo.ReturnOrderCountVo;
import com.lz.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
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
import com.lz.manage.model.domain.ReturnOrderInfo;
import com.lz.manage.model.vo.returnOrderInfo.ReturnOrderInfoVo;
import com.lz.manage.model.dto.returnOrderInfo.ReturnOrderInfoQuery;
import com.lz.manage.model.dto.returnOrderInfo.ReturnOrderInfoInsert;
import com.lz.manage.model.dto.returnOrderInfo.ReturnOrderInfoEdit;
import com.lz.manage.service.IReturnOrderInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 退货订单信息Controller
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/manage/returnOrderInfo")
public class ReturnOrderInfoController extends BaseController {
    @Resource
    private IReturnOrderInfoService returnOrderInfoService;

    @Resource
    private ISysDeptService deptService;

    /**
     * 查询退货订单信息列表
     */
    @PreAuthorize("@ss.hasAnyPermi('manage:returnOrderInfo:list,manage:returnOrderInfo:qiery')")
    @GetMapping("/list")
    public TableDataInfo list(ReturnOrderInfoQuery returnOrderInfoQuery) {
        ReturnOrderInfo returnOrderInfo = ReturnOrderInfoQuery.queryToObj(returnOrderInfoQuery);
        if (StringUtils.isNotNull(returnOrderInfo.getDeptId())) {
            List<Long> deptIds = deptService.selectDeptByIdReturnIds(returnOrderInfo.getDeptId());
            returnOrderInfo.setDeptIds(deptIds);
        }
        startPage();
        List<ReturnOrderInfo> list = returnOrderInfoService.selectReturnOrderInfoList(returnOrderInfo);
        List<ReturnOrderInfoVo> listVo = list.stream().map(ReturnOrderInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:list')")
    @GetMapping("/getReturnOrderCount")
    public AjaxResult getReturnOrderCount(ReturnOrderInfoQuery returnOrderInfoQuery) {
        ReturnOrderInfo returnOrderInfo = ReturnOrderInfoQuery.queryToObj(returnOrderInfoQuery);
        if (StringUtils.isNotNull(returnOrderInfo.getDeptId())) {
            List<Long> deptIds = deptService.selectDeptByIdReturnIds(returnOrderInfo.getDeptId());
            returnOrderInfo.setDeptIds(deptIds);
        }
        ReturnOrderCountVo returnOrderCountVo= returnOrderInfoService.getReturnOrderCount(returnOrderInfo);
        return success(returnOrderCountVo);
    }

    /**
     * 导出退货订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:export')")
    @Log(title = "退货订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReturnOrderInfoQuery returnOrderInfoQuery) {
        ReturnOrderInfo returnOrderInfo = ReturnOrderInfoQuery.queryToObj(returnOrderInfoQuery);
        List<ReturnOrderInfo> list = returnOrderInfoService.selectReturnOrderInfoList(returnOrderInfo);
        ExcelUtil<ReturnOrderInfo> util = new ExcelUtil<ReturnOrderInfo>(ReturnOrderInfo.class);
        util.exportExcel(response, list, "退货订单信息数据");
    }

    /**
     * 获取退货订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ReturnOrderInfo returnOrderInfo = returnOrderInfoService.selectReturnOrderInfoById(id);
        return success(ReturnOrderInfoVo.objToVo(returnOrderInfo));
    }

    /**
     * 获取退货订单信息详细信息 根据订单编号
     */
    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:query')")
    @GetMapping(value = "/orderNumber/{orderNumber}")
    public AjaxResult getInfoByOrderNumber(@PathVariable("orderNumber") String orderNumber) {
        ReturnOrderInfo returnOrderInfo = returnOrderInfoService.selectReturnOrderByOrderNumber(orderNumber);
        return success(ReturnOrderInfoVo.objToVo(returnOrderInfo));
    }

    /**
     * 新增退货订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:add')")
    @Log(title = "退货订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated ReturnOrderInfoInsert returnOrderInfoInsert) {
        ReturnOrderInfo returnOrderInfo = ReturnOrderInfoInsert.insertToObj(returnOrderInfoInsert);
        return toAjax(returnOrderInfoService.insertReturnOrderInfo(returnOrderInfo));
    }

    /**
     * 新增或修改退货订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:add')")
    @Log(title = "新增或修改退货订单信息", businessType = BusinessType.INSERT)
    @PostMapping("/addOrUpdate")
    public AjaxResult addOrUpdate(@RequestBody @Validated ReturnOrderInfoInsert returnOrderInfoInsert) {
        ReturnOrderInfo returnOrderInfo = ReturnOrderInfoInsert.insertToObj(returnOrderInfoInsert);
        return toAjax(returnOrderInfoService.mySaveOrUpdate(returnOrderInfo));
    }

    /**
     * 修改退货订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:edit')")
    @Log(title = "退货订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReturnOrderInfoEdit returnOrderInfoEdit) {
        ReturnOrderInfo returnOrderInfo = ReturnOrderInfoEdit.editToObj(returnOrderInfoEdit);
        return toAjax(returnOrderInfoService.updateReturnOrderInfo(returnOrderInfo));
    }

    /**
     * 删除退货订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:remove')")
    @Log(title = "退货订单信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(returnOrderInfoService.deleteReturnOrderInfoByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:import')")
    @Log(title = "导入退货信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<ReturnOrderInfo> util = new ExcelUtil<ReturnOrderInfo>(ReturnOrderInfo.class);
        List<ReturnOrderInfo> list = util.importExcel(file.getInputStream());
        String message = returnOrderInfoService.importReturnOrderInfo(list);
        return success(message);
    }

    @PreAuthorize("@ss.hasPermi('manage:returnOrderInfo:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<ReturnOrderInfo> util = new ExcelUtil<ReturnOrderInfo>(ReturnOrderInfo.class);
        util.importTemplateExcel(response, "退货订单数据");
    }
}
