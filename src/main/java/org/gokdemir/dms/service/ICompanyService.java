package org.gokdemir.dms.service;

import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICompanyService {

    public DtoCompany saveCompany(DtoCompanyIU dtoCompanyIU);

    public DtoCompany updateCompany(Long id, DtoCompanyIU dtoCompanyIU);

    public String deactivateCompany(Long id);

    public String activateCompany(Long id);

    public DtoCompany getCompanyById(Long id);

    String deleteCompanyPermanently(Long companyId);

    Page<DtoCompany> getActiveCompanies(Pageable pageable);
    Page<DtoCompany> getInactiveCompanies(Pageable pageable);
    Page<DtoCompany> searchActiveCompaniesByName(String name, Pageable pageable);
    Page<DtoCompany> searchInactiveCompaniesByName(String name, Pageable pageable);

}