package com.example.frealsb.Repositories;

import com.example.frealsb.Entities.CultureCategory;
import com.example.frealsb.Entities.FoodFeatured;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CultureCategoryRepository extends JpaRepository<CultureCategory, String> {
    List<CultureCategory> findAllBy(String s, Pageable pageable);
}
