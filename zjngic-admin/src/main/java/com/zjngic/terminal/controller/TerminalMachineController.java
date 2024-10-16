package com.zjngic.terminal.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
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
import com.zjngic.terminal .domain.TerminalMachine;
import com.zjngic.terminal .service.ITerminalMachineService;
import com.zjngic.common.utils.poi.ExcelUtil;
import com.zjngic.common.core.page.TableDataInfo;

/**
 * 终端机器Controller
 * 
 * @author zjngic
 * @date 2024-10-14
 */
@RestController
@RequestMapping("/terminal/terminal")
public class TerminalMachineController extends BaseController
{
    @Autowired
    private ITerminalMachineService terminalMachineService;

    /**
     * 查询终端机器列表
     */
    @PreAuthorize("@ss.hasPermi('terminal:terminal:list')")
    @GetMapping("/list")
    public TableDataInfo list(TerminalMachine terminalMachine)
    {
        startPage();
        List<TerminalMachine> list = terminalMachineService.selectTerminalMachineList(terminalMachine);
        return getDataTable(list);
    }
    /**
     * 获取、更新密钥
     * @param terminalMachine id和code都可以，
     * @return
     */
    @PreAuthorize("@ss.hasPermi('terminal:terminal:getKey')")
    @GetMapping("/getKey")
    public String getKey(TerminalMachine terminalMachine){
        return terminalMachineService.getKey(terminalMachine);
    }
    /**
     * 导出终端机器列表
     */
    @PreAuthorize("@ss.hasPermi('terminal:terminal:export')")
    @Log(title = "终端机器", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TerminalMachine terminalMachine)
    {
        List<TerminalMachine> list = terminalMachineService.selectTerminalMachineList(terminalMachine);
        ExcelUtil<TerminalMachine> util = new ExcelUtil<TerminalMachine>(TerminalMachine.class);
        util.exportExcel(response, list, "终端机器数据");
    }

    /**
     * 获取终端机器详细信息
     */
    @PreAuthorize("@ss.hasPermi('terminal:terminal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(terminalMachineService.selectTerminalMachineById(id));
    }

    /**
     * 新增终端机器
     */
    @PreAuthorize("@ss.hasPermi('terminal:terminal:add')")
    @Log(title = "终端机器", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TerminalMachine terminalMachine)
    {
        return toAjax(terminalMachineService.insertTerminalMachine(terminalMachine));
    }

    /**
     * 修改终端机器
     */
    @PreAuthorize("@ss.hasPermi('terminal:terminal:edit')")
    @Log(title = "终端机器", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TerminalMachine terminalMachine)
    {
        return toAjax(terminalMachineService.updateTerminalMachine(terminalMachine));
    }

    /**
     * 删除终端机器
     */
    @PreAuthorize("@ss.hasPermi('terminal:terminal:remove')")
    @Log(title = "终端机器", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(terminalMachineService.deleteTerminalMachineByIds(ids));
    }
}
