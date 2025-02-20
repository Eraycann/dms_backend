package org.gokdemir.dms.repository;

import org.gokdemir.dms.entity.Company;
import org.gokdemir.dms.entity.Document;
import org.gokdemir.dms.enums.DocumentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(Long companyId);
    List<Document> findByCompanyIdAndIsActiveFalseOrderByCreatedAtDesc(Long companyId);

    List<Document> findByCompanyId(Long companyId);
}