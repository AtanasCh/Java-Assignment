package com.financial.demo.dao;

import com.financial.demo.model.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ConversionDAO extends PagingAndSortingRepository<Conversion, Long> {
    Page<Conversion> findAllByTransactionDate(Date date, Pageable pageable);
    Page<Conversion> findAllById(Long id, Pageable pageable);
}
