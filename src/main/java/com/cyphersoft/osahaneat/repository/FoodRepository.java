package com.cyphersoft.osahaneat.repository;

import com.cyphersoft.osahaneat.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer>, JpaSpecificationExecutor<Food> {
    Page<Food> findByTitleContainingIgnoreCaseAndStatusAndCategory_Id(String keyword, int status, int cateId, Pageable pageable);
}
