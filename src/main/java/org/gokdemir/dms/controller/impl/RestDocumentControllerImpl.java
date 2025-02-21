package org.gokdemir.dms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.gokdemir.dms.controller.IRestDocumentController;
import org.gokdemir.dms.controller.RestBaseController;
import org.gokdemir.dms.controller.RootEntity;
import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.gokdemir.dms.service.IDocumentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/document")
public class RestDocumentControllerImpl extends RestBaseController implements IRestDocumentController {

    private final IDocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public RootEntity<DtoDocument> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @RequestPart("dto") DtoDocumentIU dtoDocumentIU) throws IOException {
        return ok(documentService.uploadDocument(file, dtoDocumentIU));
    }

    @PostMapping("/archive/{documentId}")
    @Override
    public RootEntity<String> archiveDocument(@PathVariable Long documentId) {
        try {
            String result = documentService.archiveDocument(documentId);
            return ok(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/restore/{documentId}")
    @Override
    public RootEntity<String> restoreDocument(@PathVariable Long documentId) {
        return ok(documentService.restoreDocument(documentId));
    }

    @GetMapping("/active/{companyId}")
    @Override
    public RootEntity<List<DtoDocument>> getActiveDocumentsByCompany(@PathVariable Long companyId) {
        return ok(documentService.getActiveDocumentsByCompany(companyId));
    }

    @GetMapping("/archived/{companyId}")
    @Override
    public RootEntity<List<DtoDocument>> getArchivedDocumentsByCompany(@PathVariable Long companyId) {
        return ok(documentService.getArchivedDocumentsByCompany(companyId));
    }

    @DeleteMapping("/delete/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocumentPermanently(documentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<Resource> getDocument(@PathVariable Long documentId) {
        Resource resource = documentService.getDocumentAsResource(documentId);

        // Dosya uzantısını al
        String contentType;
        String filename = resource.getFilename();

        if (filename != null) {
            String lowerFilename = filename.toLowerCase();
            // PDF
            if (lowerFilename.endsWith(".pdf")) {
                contentType = "application/pdf";
            }
            // Word Dokümanları
            else if (lowerFilename.endsWith(".doc")) {
                contentType = "application/msword";
            }
            else if (lowerFilename.endsWith(".docx")) {
                contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            }
            // Excel Dokümanları
            else if (lowerFilename.endsWith(".xls")) {
                contentType = "application/vnd.ms-excel";
            }
            else if (lowerFilename.endsWith(".xlsx")) {
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            }
            // PowerPoint Dokümanları
            else if (lowerFilename.endsWith(".ppt")) {
                contentType = "application/vnd.ms-powerpoint";
            }
            else if (lowerFilename.endsWith(".pptx")) {
                contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            }
            // Metin Dokümanları
            else if (lowerFilename.endsWith(".txt")) {
                contentType = "text/plain";
            }
            else if (lowerFilename.endsWith(".csv")) {
                contentType = "text/csv";
            }
            else if (lowerFilename.endsWith(".rtf")) {
                contentType = "application/rtf";
            }
            else if (lowerFilename.endsWith(".odt")) {
                contentType = "application/vnd.oasis.opendocument.text";
            }
            // Web Dokümanları
            else if (lowerFilename.endsWith(".html")) {
                contentType = "text/html";
            }
            else if (lowerFilename.endsWith(".xml")) {
                contentType = "application/xml";
            }
            else if (lowerFilename.endsWith(".json")) {
                contentType = "application/json";
            }
            // Arşiv Dosyaları
            else if (lowerFilename.endsWith(".zip")) {
                contentType = "application/zip";
            }
            else if (lowerFilename.endsWith(".rar")) {
                contentType = "application/x-rar-compressed";
            }
            else if (lowerFilename.endsWith(".tar")) {
                contentType = "application/x-tar";
            }
            else if (lowerFilename.endsWith(".gz")) {
                contentType = "application/gzip";
            }
            // Resim Dosyaları
            else if (lowerFilename.endsWith(".jpeg") || lowerFilename.endsWith(".jpg")) {
                contentType = "image/jpeg";
            }
            else if (lowerFilename.endsWith(".png")) {
                contentType = "image/png";
            }
            else if (lowerFilename.endsWith(".gif")) {
                contentType = "image/gif";
            }
            else if (lowerFilename.endsWith(".bmp")) {
                contentType = "image/bmp";
            }
            else if (lowerFilename.endsWith(".svg")) {
                contentType = "image/svg+xml";
            }
            else {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }
        } else {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }



}
