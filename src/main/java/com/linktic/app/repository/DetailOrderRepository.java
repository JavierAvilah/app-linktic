package com.linktic.app.repository;

import com.linktic.app.model.DetailOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long>, JpaSpecificationExecutor<DetailOrder> {
    Optional<DetailOrder> findByIdAndOrder_Id(Long id, Long idOrder);

    Page<DetailOrder> findByOrder_Id(Long id, Pageable pageable);

}