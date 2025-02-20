package org.gokdemir.dms.controller;

import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

public interface IRestCompanyController {

    public RootEntity<DtoCompany> saveCompany(DtoCompanyIU dtoCompanyIU);

    public RootEntity<DtoCompany> updateCompany(Long id, DtoCompanyIU dtoCompanyIU);

    public RootEntity<Page<DtoCompany>> getActiveCompanies(@PageableDefault(size = 10) Pageable pageable);

    public RootEntity<Page<DtoCompany>> getInactiveCompanies(@PageableDefault(size = 10) Pageable pageable);

    public RootEntity<String> deactivateCompany(Long id);

    public RootEntity<String> activateCompany(Long id);

    public RootEntity<Page<DtoCompany>> searchActiveCompaniesByName(String name, @PageableDefault(size = 10) Pageable pageable);

    public RootEntity<Page<DtoCompany>> searchInactiveCompaniesByName(String name, @PageableDefault(size = 10) Pageable pageable);

    public RootEntity<DtoCompany> getCompanyById(Long id);

}
