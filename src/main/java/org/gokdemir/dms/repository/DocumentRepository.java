package org.gokdemir.dms.repository;

import org.gokdemir.dms.entity.Document;
import org.gokdemir.dms.enums.DocumentCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByCompanyId(Long companyId);

    Page<Document> findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(Long companyId, Pageable pageable);
    Page<Document> findByCompanyIdAndIsActiveFalseOrderByCreatedAtDesc(Long companyId, Pageable pageable);





    @Query("SELECT d FROM Document d WHERE d.company.id = :companyId AND d.isActive = :active " +
            "AND LOWER(d.documentNo) LIKE LOWER(CONCAT('%', COALESCE(:documentNo, ''), '%')) " +
            "AND d.createdAt >= COALESCE(:startDate, d.createdAt) " +
            "AND d.createdAt <= COALESCE(:endDate, d.createdAt) " +
            "AND d.category = COALESCE(:category, d.category) " +
            "AND LOWER(d.description) LIKE LOWER(CONCAT('%', COALESCE(:description, ''), '%'))")
    Page<Document> filterDocuments(@Param("companyId") Long companyId,
                                   @Param("active") boolean active,
                                   @Param("documentNo") String documentNo,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate,
                                   @Param("category") DocumentCategory category,
                                   @Param("description") String description,
                                   Pageable pageable);

}