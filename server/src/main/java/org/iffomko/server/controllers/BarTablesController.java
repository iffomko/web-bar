package org.iffomko.server.controllers;

import org.iffomko.server.domain.bartable.BarTable;
import org.iffomko.server.services.BarTableService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.iffomko.server.domain.ControllerNames.BAR_TABLES_URL;

@RestController
@RequestMapping(BAR_TABLES_URL)
@CrossOrigin("http://localhost:4200/")
public class BarTablesController {
    private final BarTableService barTableService;

    public BarTablesController(BarTableService barTableService) {
        this.barTableService = barTableService;
    }

    @GetMapping
    public List<BarTable> loadTables() {
        return barTableService.loadBarTables();
    }
}
