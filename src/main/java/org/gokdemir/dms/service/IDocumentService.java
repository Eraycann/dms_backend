package org.gokdemir.dms.service;

import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface IDocumentService {

    public DtoDocument uploadDocument(MultipartFile file, DtoDocumentIU dtoDocumentIU) throws IOException;

    public String archiveDocument(Long documentId);

    public String restoreDocument(Long documentId);

    public List<DtoDocument> getActiveDocumentsByCompany(Long companyId);

    public List<DtoDocument> getArchivedDocumentsByCompany(Long companyId);


    Resource getDocumentAsResource(Long documentId);

    void deleteDocumentPermanently(Long documentId);
}
