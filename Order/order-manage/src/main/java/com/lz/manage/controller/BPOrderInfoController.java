package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.common.utils.StringUtils;
import com.lz.manage.model.domain.ReturnOrderInfo;
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
import com.lz.manage.model.domain.BPOrderInfo;
import com.lz.manage.model.vo.bPOrderInfo.BPOrderInfoVo;
import com.lz.manage.model.dto.bPOrderInfo.BPOrderInfoQuery;
import com.lz.manage.model.dto.bPOrderInfo.BPOrderInfoInsert;
import com.lz.manage.model.dto.bPOrderInfo.BPOrderInfoEdit;
import com.lz.manage.service.IBPOrderInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 白嫖订单信息Controller
 *
 * @author YY
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/manage/bPOrderInfo")
public class BPOrderInfoController extends BaseController {
    @Resource
    private IBPOrderInfoService bPOrderInfoService;

    @Resource
    private ISysDeptService deptService;

    /**
     * 查询白嫖订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BPOrderInfoQuery bPOrderInfoQuery) {
        BPOrderInfo bPOrderInfo = BPOrderInfoQuery.queryToObj(bPOrderInfoQuery);
        if (StringUtils.isNotNull(bPOrderInfo.getDeptId())) {
            List<Long> dept = deptService.selectDeptByIdReturnIds(bPOrderInfo.getDeptId());
            bPOrderInfo.setDeptIds(dept);
        }
        startPage();
        List<BPOrderInfo> list = bPOrderInfoService.selectBPOrderInfoList(bPOrderInfo);
        List<BPOrderInfoVo> listVo = list.stream().map(BPOrderInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出白嫖订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:export')")
    @Log(title = "白嫖订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BPOrderInfoQuery bPOrderInfoQuery) {
        BPOrderInfo bPOrderInfo = BPOrderInfoQuery.queryToObj(bPOrderInfoQuery);
        List<BPOrderInfo> list = bPOrderInfoService.selectBPOrderInfoList(bPOrderInfo);
        ExcelUtil<BPOrderInfo> util = new ExcelUtil<BPOrderInfo>(BPOrderInfo.class);
        util.exportExcel(response, list, "白嫖订单信息数据");
    }

    /**
     * 获取白嫖订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        BPOrderInfo bPOrderInfo = bPOrderInfoService.selectBPOrderInfoById(id);
        return success(BPOrderInfoVo.objToVo(bPOrderInfo));
    }

    /**
     * 获取白嫖订单信息详细信息 根据订单编号
     */
    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:query')")
    @GetMapping(value = "/orderNumber/{orderNumber}")
    public AjaxResult getInfoByOderNumber(@PathVariable("orderNumber") String orderNumber) {
        BPOrderInfo bPOrderInfo = bPOrderInfoService.selectBPOrderInfoByOrderNumber(orderNumber);
        return success(BPOrderInfoVo.objToVo(bPOrderInfo));
    }

    /**
     * 新增白嫖订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:add')")
    @Log(title = "白嫖订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BPOrderInfoInsert bPOrderInfoInsert) {
        BPOrderInfo bPOrderInfo = BPOrderInfoInsert.insertToObj(bPOrderInfoInsert);
        return toAjax(bPOrderInfoService.insertBPOrderInfo(bPOrderInfo));
    }

    /**
     * 新增白嫖订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:add')")
    @Log(title = "新增或者修改白嫖订单信息", businessType = BusinessType.INSERT)
    @PostMapping("/addOrUpdate")
    public AjaxResult addOrUpdate(@RequestBody BPOrderInfoInsert bPOrderInfoInsert) {
        BPOrderInfo bPOrderInfo = BPOrderInfoInsert.insertToObj(bPOrderInfoInsert);
        return toAjax(bPOrderInfoService.addOrUpdateBPOrderInfo(bPOrderInfo));
    }

    /**
     * 修改白嫖订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:edit')")
    @Log(title = "白嫖订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BPOrderInfoEdit bPOrderInfoEdit) {
//        System.out.println("bPOrderInfoEdit = " + bPOrderInfoEdit);
        BPOrderInfo bPOrderInfo = BPOrderInfoEdit.editToObj(bPOrderInfoEdit);
//        System.err.println(bPOrderInfo);
        return toAjax(bPOrderInfoService.updateBPOrderInfo(bPOrderInfo));
    }

    /**
     * 删除白嫖订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:remove')")
    @Log(title = "白嫖订单信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bPOrderInfoService.deleteBPOrderInfoByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:import')")
    @Log(title = "导入白嫖订单信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<BPOrderInfo> util = new ExcelUtil<BPOrderInfo>(BPOrderInfo.class);
        List<BPOrderInfo> list = util.importExcel(file.getInputStream());
        String message = bPOrderInfoService.importBPOrderInfo(list);
        return success(message);
    }

    @PreAuthorize("@ss.hasPermi('manage:bPOrderInfo:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<BPOrderInfo> util = new ExcelUtil<BPOrderInfo>(BPOrderInfo.class);
        util.importTemplateExcel(response, "白嫖订单数据");
    }
}
