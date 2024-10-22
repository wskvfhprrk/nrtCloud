package com.zjngic.terminal.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zjngic.common.annotation.Log;
import com.zjngic.common.core.controller.BaseController;
import com.zjngic.common.core.domain.AjaxResult;
import com.zjngic.common.enums.BusinessType;
import com.zjngic.terminal.domain.OriginalOrder;
import com.zjngic.terminal.service.IOriginalOrderService;
import com.zjngic.common.utils.poi.ExcelUtil;
import com.zjngic.common.core.page.TableDataInfo;

/**
 * 原始订单Controller
 * 
 * @author zjngic
 * @date 2024-10-22
 */
@RestController
@RequestMapping("/terminal/order")
public class OriginalOrderController extends BaseController
{
    @Autowired
    private IOriginalOrderService originalOrderService;

    /**
     * 查询原始订单列表
     */
    @PreAuthorize("@ss.hasPermi('terminal :order:list')")
    @GetMapping("/list")
    public TableDataInfo list(OriginalOrder originalOrder)
    {
        startPage();
        List<OriginalOrder> list = originalOrderService.selectOriginalOrderList(originalOrder);
        return getDataTable(list);
    }

    /**
     * 导出原始订单列表
     */
    @PreAuthorize("@ss.hasPermi('terminal :order:export')")
    @Log(title = "原始订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OriginalOrder originalOrder)
    {
        List<OriginalOrder> list = originalOrderService.selectOriginalOrderList(originalOrder);
        ExcelUtil<OriginalOrder> util = new ExcelUtil<OriginalOrder>(OriginalOrder.class);
        util.exportExcel(response, list, "原始订单数据");
    }

    /**
     * 获取原始订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('terminal :order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(originalOrderService.selectOriginalOrderById(id));
    }

    /**
     * 新增原始订单
     */
    @PreAuthorize("@ss.hasPermi('terminal :order:add')")
    @Log(title = "原始订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OriginalOrder originalOrder)
    {
        return toAjax(originalOrderService.insertOriginalOrder(originalOrder));
    }

    /**
     * 修改原始订单
     */
    @PreAuthorize("@ss.hasPermi('terminal :order:edit')")
    @Log(title = "原始订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OriginalOrder originalOrder)
    {
        return toAjax(originalOrderService.updateOriginalOrder(originalOrder));
    }

    /**
     * 删除原始订单
     */
    @PreAuthorize("@ss.hasPermi('terminal :order:remove')")
    @Log(title = "原始订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(originalOrderService.deleteOriginalOrderByIds(ids));
    }
}
