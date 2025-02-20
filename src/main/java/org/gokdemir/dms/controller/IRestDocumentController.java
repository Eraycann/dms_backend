package org.gokdemir.dms.controller;

import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.io.IOException;
import java.util.List;

public interface IRestDocumentController {

    public RootEntity<DtoDocument> uploadDocument(MultipartFile file, DtoDocumentIU dtoDocumentIU) throws IOException;

    public RootEntity<String> archiveDocument(Long documentId);

    public RootEntity<String> restoreDocument(Long documentId);

    public RootEntity<Page<DtoDocument>> getActiveDocumentsByCompany(Long companyId, @PageableDefault(size = 10) Pageable pageable);

    public RootEntity<Page<DtoDocument>> getArchivedDocumentsByCompany(Long companyId, @PageableDefault(size = 10) Pageable pageable);

}
