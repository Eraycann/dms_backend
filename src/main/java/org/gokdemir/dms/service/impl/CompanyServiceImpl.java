package org.gokdemir.dms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gokdemir.dms.entity.Document;
import org.gokdemir.dms.exception.BaseException;
import org.gokdemir.dms.exception.ErrorMessage;
import org.gokdemir.dms.exception.MessageType;
import org.gokdemir.dms.mapper.CompanyMapper;
import org.gokdemir.dms.repository.DocumentRepository;
import org.gokdemir.dms.util.CompanyFolderUtils;
import org.springframework.beans.factory.annotation.Value;
import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.gokdemir.dms.entity.Company;
import org.gokdemir.dms.repository.CompanyRepository;
import org.gokdemir.dms.service.ICompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.nio.file.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private final DocumentRepository documentRepository;


    @Value("${company.base.folder}")
    private String baseFolderPath ;

    private Company createCompany(Company company) {
        validateCompanyName(company.getName());
        String companyFolderPath = CompanyFolderUtils.createCompanyFolder(baseFolderPath, company.getName());
        company.setFolderPath(companyFolderPath);
        return company;
    }

    private void validateCompanyName(String name) {
        if (companyRepository.existsByName(name)) {
            throw new BaseException(new ErrorMessage(MessageType.COMPANY_ALREADY_EXISTS, name));
        }
    }

    private Company getCompany(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));
    }

    @Transactional
    public DtoCompany saveCompany(DtoCompanyIU dtoCompanyIU) {
        Company company = createCompany(companyMapper.toEntity(dtoCompanyIU));
        companyRepository.save(company);
        return companyMapper.toDto(company);
    }


    @Transactional
    public DtoCompany updateCompany(Long id, DtoCompanyIU dtoCompanyIU) {
        Company company = getCompany(id);

        if (!company.getName().equals(dtoCompanyIU.getName())) {
            validateCompanyName(dtoCompanyIU.getName());

            // Klasör adı değişikliği
            String updatedFolderPath = CompanyFolderUtils.renameCompanyFolderPath(
                    company.getFolderPath(),
                    company.getName(),  // Eski isim
                    dtoCompanyIU.getName()  // Yeni isim
            );

            company.setFolderPath(updatedFolderPath);  // Yeni klasör yolunu set et
        }

        // Firmanın ismini güncelle
        company.setName(dtoCompanyIU.getName());
        companyRepository.save(company);

        return companyMapper.toDto(company);
    }


    @Override
    @Transactional
    public Page<DtoCompany> getActiveCompanies(Pageable pageable) {
        Page<Company> companies = companyRepository.findAllActiveCompanies(pageable);
        return companies.map(companyMapper::toDto);
    }

    @Override
    @Transactional
    public Page<DtoCompany> getInactiveCompanies(Pageable pageable) {
        Page<Company> companies = companyRepository.findAllInactiveCompanies(pageable);
        return companies.map(companyMapper::toDto);
    }

    @Transactional
    public String deactivateCompany(Long id) {
        Company company = getCompany(id);
        checkIfCompanyIsInactive(company);  // Firma zaten pasif mi kontrol et

        try {
            String archivedFolderPath = CompanyFolderUtils.renameCompanyFolder(
                    company.getFolderPath(),
                    CompanyFolderUtils.getArchivedFolderPath(company.getFolderPath()),
                    company.getName()
            );

            company.setFolderPath(archivedFolderPath);
            company.setActive(false);
            companyRepository.save(company);

            return "Company deactivated successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while deactivating company: " + e.getMessage());
        }
    }

    @Transactional
    public String activateCompany(Long id) {
        Company company = getCompany(id);
        checkIfCompanyIsActive(company);

        String activeFolderPath = CompanyFolderUtils.renameCompanyFolder(
                company.getFolderPath(),
                CompanyFolderUtils.removeArchivedPrefix(company.getFolderPath()),
                company.getName()
        );

        company.setFolderPath(activeFolderPath);
        company.setActive(true);
        companyRepository.save(company);
        return "Company activated successfully";
    }

    @Override
    @Transactional
    public Page<DtoCompany> searchActiveCompaniesByName(String name, Pageable pageable) {
        Page<Company> companies = companyRepository.findActiveCompaniesByName(name, pageable);
        return companies.map(companyMapper::toDto);
    }

    @Override
    @Transactional
    public Page<DtoCompany> searchInactiveCompaniesByName(String name, Pageable pageable) {
        Page<Company> companies = companyRepository.findInactiveCompaniesByName(name, pageable);
        return companies.map(companyMapper::toDto);
    }

    @Transactional
    public DtoCompany getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));
        return companyMapper.toDto(company);
    }

    private void checkIfCompanyIsActive(Company company) {
        if (company.isActive()) {
            throw new BaseException(new ErrorMessage(MessageType.COMPANY_ALREADY_ACTIVE, company.getName()));
        }
    }

    private void checkIfCompanyIsInactive(Company company) {
        if (!company.isActive()) {
            throw new BaseException(new ErrorMessage(MessageType.COMPANY_ALREADY_INACTIVE, company.getName()));
        }
    }

    @Override
    @Transactional
    public String deleteCompanyPermanently(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));

        List<Document> documents = documentRepository.findByCompanyId(companyId);

        for (Document document : documents) {
            Path filePath = Paths.get(company.getFolderPath()).resolve(document.getName());
            try {
                Files.deleteIfExists(filePath);
                documentRepository.delete(document);
            } catch (Exception e) {
                throw new RuntimeException("Error deleting document file: " + document.getName(), e);
            }
        }

        Path companyFolderPath = Paths.get(company.getFolderPath());
        try {
            Files.deleteIfExists(companyFolderPath);
            companyRepository.delete(company);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting company folder: " + company.getFolderPath(), e);
        }

        return "Company deleted successfully";
    }

}