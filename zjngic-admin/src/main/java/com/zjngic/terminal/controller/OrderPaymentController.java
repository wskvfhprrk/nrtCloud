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
import com.zjngic.terminal.domain.OrderPayment;
import com.zjngic.terminal.service.IOrderPaymentService;
import com.zjngic.common.utils.poi.ExcelUtil;
import com.zjngic.common.core.page.TableDataInfo;

/**
 * 订单支付Controller
 * 
 * @author zjngic
 * @date 2024-10-14
 */
@RestController
@RequestMapping("/terminal/pay")
public class OrderPaymentController extends BaseController
{
    @Autowired
    private IOrderPaymentService orderPaymentService;

    /**
     * 查询订单支付列表
     */
    @PreAuthorize("@ss.hasPermi('terminal:pay:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderPayment orderPayment)
    {
        startPage();
        List<OrderPayment> list = orderPaymentService.selectOrderPaymentList(orderPayment);
        return getDataTable(list);
    }

    /**
     * 导出订单支付列表
     */
    @PreAuthorize("@ss.hasPermi('terminal:pay:export')")
    @Log(title = "订单支付", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderPayment orderPayment)
    {
        List<OrderPayment> list = orderPaymentService.selectOrderPaymentList(orderPayment);
        ExcelUtil<OrderPayment> util = new ExcelUtil<OrderPayment>(OrderPayment.class);
        util.exportExcel(response, list, "订单支付数据");
    }

    /**
     * 获取订单支付详细信息
     */
    @PreAuthorize("@ss.hasPermi('terminal:pay:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderPaymentService.selectOrderPaymentById(id));
    }

    /**
     * 新增订单支付
     */
    @PreAuthorize("@ss.hasPermi('terminal:pay:add')")
    @Log(title = "订单支付", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderPayment orderPayment)
    {
        return toAjax(orderPaymentService.insertOrderPayment(orderPayment));
    }

    /**
     * 修改订单支付
     */
    @PreAuthorize("@ss.hasPermi('terminal:pay:edit')")
    @Log(title = "订单支付", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderPayment orderPayment)
    {
        return toAjax(orderPaymentService.updateOrderPayment(orderPayment));
    }

    /**
     * 删除订单支付
     */
    @PreAuthorize("@ss.hasPermi('terminal:pay:remove')")
    @Log(title = "订单支付", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderPaymentService.deleteOrderPaymentByIds(ids));
    }
}
