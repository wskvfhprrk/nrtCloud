package com.zjngic.terminal.service.impl;

import com.zjngic.terminal.domain.TerminalMachine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TerminalMachineServiceImplTest {
    @Autowired
    TerminalMachineServiceImpl terminalMachineService;
    @Test
    public void initKey() {
        terminalMachineService.initKey();

    }

    @Test
    public void getTerminalMachineByCode(){
        TerminalMachine machine = terminalMachineService.getTerminalMachineByCode("A000001");
        System.out.println(machine.toString());
    }
}