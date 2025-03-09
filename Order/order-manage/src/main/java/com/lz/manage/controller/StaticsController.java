package com.lz.manage.controller;

import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.model.statics.StaticsDto;
import com.lz.manage.service.IStaticsService;
import com.lz.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Project: Order
 * Package: com.lz.manage.controller
 * Author: YY
 * CreateTime: 2025-03-06  22:31
 * Description: StaticsController
 * Version: 1.0
 */
@RestController
@RequestMapping("/manage/statics")
public class StaticsController  extends BaseController {
    @Resource
    private IStaticsService staticsService;

    /**
     * 近三十日统计
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:purchaseOrderInfo:list')")
    @PostMapping("/day")
    public AjaxResult day(@RequestBody StaticsDto staticsDto) {
        return success(staticsService.day(staticsDto));
    }
}
