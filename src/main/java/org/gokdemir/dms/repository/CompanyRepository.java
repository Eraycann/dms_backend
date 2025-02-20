package org.gokdemir.dms.repository;

import jakarta.transaction.Transactional;
import org.gokdemir.dms.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByName(String name);

    @Query("SELECT c FROM Company c WHERE c.isActive = true")
    Page<Company> findAllActiveCompanies(Pageable pageable);

    @Query("SELECT c FROM Company c WHERE c.isActive = false")
    Page<Company> findAllInactiveCompanies(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Company c SET c.isActive = false WHERE c.id = :id")
    void deactivateCompany(@Param("id") Long id);

    @Query("SELECT c FROM Company c WHERE c.isActive = true AND LOWER(c.name) LIKE LOWER(concat('%', :name, '%'))")
    Page<Company> findActiveCompaniesByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT c FROM Company c WHERE c.isActive = false AND LOWER(c.name) LIKE LOWER(concat('%', :name, '%'))")
    Page<Company> findInactiveCompaniesByName(@Param("name") String name, Pageable pageable);
}
