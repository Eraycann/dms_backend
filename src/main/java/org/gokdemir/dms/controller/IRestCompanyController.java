package org.gokdemir.dms.controller;

import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;

import java.util.List;

public interface IRestCompanyController {

    public RootEntity<DtoCompany> saveCompany(DtoCompanyIU dtoCompanyIU);

    public RootEntity<DtoCompany> updateCompany(Long id, DtoCompanyIU dtoCompanyIU);

    public RootEntity<List<DtoCompany>> getActiveCompanies();

    public RootEntity<List<DtoCompany>> getInactiveCompanies();

    public RootEntity<String> deactivateCompany(Long id);

    public RootEntity<String> activateCompany(Long id);

    public RootEntity<List<DtoCompany>> searchActiveCompaniesByName(String name);

    public RootEntity<List<DtoCompany>> searchInactiveCompaniesByName(String name);

    public RootEntity<DtoCompany> getCompanyById(Long id);



}
