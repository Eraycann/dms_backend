package org.gokdemir.dms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.gokdemir.dms.entity.Company;
import org.gokdemir.dms.entity.Document;
import org.gokdemir.dms.mapper.DocumentMapper;
import org.gokdemir.dms.repository.CompanyRepository;
import org.gokdemir.dms.repository.DocumentRepository;
import org.gokdemir.dms.service.IDocumentService;
import org.gokdemir.dms.util.DocumentUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.gokdemir.dms.exception.BaseException;
import org.gokdemir.dms.exception.ErrorMessage;
import org.gokdemir.dms.exception.MessageType;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements IDocumentService {

    private final DocumentRepository documentRepository;

    private final CompanyRepository companyRepository;

    private final DocumentMapper documentMapper;



    private Company getCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));
    }

    private Document getDocument(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));
    }

    private void validateNotArchived(Document document) {
        if (!document.isActive()) {
            throw new RuntimeException("Document is already archived");
        }
    }

    private void validateNotActive(Document document) {
        if (document.isActive()) {
            throw new RuntimeException("Document is already active");
        }
    }


    @Transactional
    public DtoDocument uploadDocument(MultipartFile file, DtoDocumentIU dtoDocumentIU) throws IOException {
        Company company = getCompany(dtoDocumentIU.getCompanyId());

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new RuntimeException("Invalid file name: Extension is missing");
        }

        String uniqueFilename = DocumentUtils.generateUniqueFilename(originalFilename);
        Path filePath = Path.of(company.getFolderPath(), uniqueFilename);
        DocumentUtils.saveFile(file, filePath);

        // Yeni oluşturulan Document nesnesine DTO'dan gelen bilgileri de set ediyoruz.
        Document document = documentMapper.toEntity(dtoDocumentIU);
        document.setName(uniqueFilename);
        document.setCompany(company);
        document.setActive(true);


        Document savedDocument = documentRepository.save(document);
        return documentMapper.toDto(savedDocument);
    }

    @Transactional
    public String archiveDocument(Long documentId) {
        Document document = documentRepository.findById(documentId)
            .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));

        // Eğer doküman zaten arşivlenmişse hata fırlat
        if (!document.isActive()) {
            throw new RuntimeException("Document is already archived");
        }

        try {
            Company company = document.getCompany();
            String archivedName = "archived_" + document.getName();
            
            // Dosyayı fiziksel olarak taşı
            Path oldPath = Path.of(company.getFolderPath(), document.getName());
            Path newPath = Path.of(company.getFolderPath(), archivedName);
            
            try {
                DocumentUtils.moveFile(oldPath, newPath);
            } catch (IOException e) {
                throw new RuntimeException("Error while moving file: " + e.getMessage());
            }

            // Dokümanı arşivle ve ismini güncelle
            document.setName(archivedName);
            document.setActive(false);
            documentRepository.save(document);

            return "Document archived successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while archiving document: " + e.getMessage());
        }
    }

    @Transactional
    public String restoreDocument(Long documentId) {
        Document document = documentRepository.findById(documentId)
            .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));

        // Eğer doküman zaten aktifse hata fırlat
        if (document.isActive()) {
            throw new RuntimeException("Document is already active");
        }

        try {
            Company company = document.getCompany();
            // "archived_" önekini kaldır
            String originalName = document.getName().replaceFirst("^archived_", "");
            
            // Dosyayı fiziksel olarak taşı
            Path oldPath = Path.of(company.getFolderPath(), document.getName());
            Path newPath = Path.of(company.getFolderPath(), originalName);
            
            try {
                DocumentUtils.moveFile(oldPath, newPath);
            } catch (IOException e) {
                throw new RuntimeException("Error while moving file: " + e.getMessage());
            }

            // Dokümanı aktif et ve ismini güncelle
            document.setName(originalName);
            document.setActive(true);
            documentRepository.save(document);

            return "Document restored successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while restoring document: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Page<DtoDocument> getActiveDocumentsByCompany(Long companyId, Pageable pageable) {
        Page<Document> documents = documentRepository.findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(companyId, pageable);
        return documents.map(documentMapper::toDto);
    }

    @Override
    @Transactional
    public Page<DtoDocument> getArchivedDocumentsByCompany(Long companyId, Pageable pageable) {
        Page<Document> documents = documentRepository.findByCompanyIdAndIsActiveFalseOrderByCreatedAtDesc(companyId, pageable);
        return documents.map(documentMapper::toDto);
    }

    @Override
    @Transactional
    public Page<DtoDocument> searchActiveDocumentsByCompanyAndDocumentNo(Long companyId, String documentNo, Pageable pageable) {
        Page<Document> documents = documentRepository.searchActiveDocumentsByCompanyAndDocumentNo(companyId, documentNo, pageable);
        return documents.map(documentMapper::toDto);
    }

    @Override
    @Transactional
    public Page<DtoDocument> searchArchivedDocumentsByCompanyAndDocumentNo(Long companyId, String documentNo, Pageable pageable) {
        Page<Document> documents = documentRepository.searchArchivedDocumentsByCompanyAndDocumentNo(companyId, documentNo, pageable);
        return documents.map(documentMapper::toDto);
    }

    @Transactional
    public void deleteDocumentPermanently(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));

        Company company = document.getCompany();
        if (company == null) {
            throw new RuntimeException("Company not found for document ID: " + documentId);
        }

        Path filePath = Paths.get(company.getFolderPath()).resolve(document.getName());
        try {
            Files.deleteIfExists(filePath);
            documentRepository.delete(document);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting document file: " + document.getName(), e);
        }
    }


    @Override
    public Resource getDocumentAsResource(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, documentId.toString())));

        Company company = document.getCompany();
        if (company == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Company not found for document ID: " + documentId));
        }

        Path filePath = Paths.get(company.getFolderPath()).resolve(document.getName());
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new BaseException(new ErrorMessage(MessageType.FILE_NOT_FOUND, "Document file not found: " + document.getName()));
            }
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.FILE_NOT_FOUND, "Could not read file: " + document.getName()));
        }
    }



}