package org.gokdemir.dms.service;

import org.gokdemir.dms.dto.request.DtoDocumentFilter;
import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.request.DtoDocumentUpdateIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IDocumentService {

    DtoDocument uploadDocument(MultipartFile file, DtoDocumentIU dtoDocumentIU) throws IOException;

    String archiveDocument(Long documentId);

    String restoreDocument(Long documentId);

    Page<DtoDocument> getActiveDocumentsByCompany(Long companyId, Pageable pageable);
    Page<DtoDocument> getArchivedDocumentsByCompany(Long companyId, Pageable pageable);

    Resource getDocumentAsResource(Long documentId);

    void deleteDocumentPermanently(Long documentId);


    Page<DtoDocument> filterActiveDocuments(DtoDocumentFilter filter, int page, int size);
    Page<DtoDocument> filterInactiveDocuments(DtoDocumentFilter filter, int page, int size);

    void updateDocument(Long id, DtoDocumentUpdateIU dto);
    }