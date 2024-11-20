package org.iffomko.server.repositories;

import org.iffomko.server.domain.table.BarTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarTableRepository extends JpaRepository<BarTable, Integer> {}
