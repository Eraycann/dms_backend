package org.gokdemir.dms.repository;

import org.gokdemir.dms.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByCompanyId(Long companyId);

    Page<Document> findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(Long companyId, Pageable pageable);
    Page<Document> findByCompanyIdAndIsActiveFalseOrderByCreatedAtDesc(Long companyId, Pageable pageable);
}