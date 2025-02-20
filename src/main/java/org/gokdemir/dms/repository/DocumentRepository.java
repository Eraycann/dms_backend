package org.gokdemir.dms.repository;

import org.gokdemir.dms.entity.Company;
import org.gokdemir.dms.entity.Document;
import org.gokdemir.dms.enums.DocumentCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Page<Document> findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(Long companyId, Pageable pageable);
    Page<Document> findByCompanyIdAndIsActiveFalseOrderByCreatedAtDesc(Long companyId, Pageable pageable);

    List<Document> findByCompanyId(Long companyId);
}