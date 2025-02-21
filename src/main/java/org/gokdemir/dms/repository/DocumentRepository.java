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

    List<Document> findByCompanyId(Long companyId);

    Page<Document> findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(Long companyId, Pageable pageable);
    Page<Document> findByCompanyIdAndIsActiveFalseOrderByCreatedAtDesc(Long companyId, Pageable pageable);

    @Query("SELECT d FROM Document d WHERE d.company.id = :companyId AND LOWER(d.documentNo) LIKE LOWER(CONCAT('%', :documentNo, '%')) AND d.isActive = true")
    Page<Document> searchActiveDocumentsByCompanyAndDocumentNo(
            @Param("companyId") Long companyId,
            @Param("documentNo") String documentNo,
            Pageable pageable);

    @Query("SELECT d FROM Document d WHERE d.company.id = :companyId AND LOWER(d.documentNo) LIKE LOWER(CONCAT('%', :documentNo, '%')) AND d.isActive = false")
    Page<Document> searchArchivedDocumentsByCompanyAndDocumentNo(
            @Param("companyId") Long companyId,
            @Param("documentNo") String documentNo,
            Pageable pageable);
}
