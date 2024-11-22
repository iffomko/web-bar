package org.iffomko.server.services;

import org.iffomko.server.domain.bartable.BarTable;
import org.iffomko.server.repositories.BarTableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarTableService {
    private final BarTableRepository barTableRepository;

    public BarTableService(BarTableRepository barTableRepository) {
        this.barTableRepository = barTableRepository;
    }

    public void saveBarTable(BarTable barTable) {
        barTableRepository.save(barTable);
    }

    public List<BarTable> loadBarTables() {
        return barTableRepository.findAll();
    }
}
