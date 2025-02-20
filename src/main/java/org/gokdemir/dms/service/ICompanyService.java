package org.gokdemir.dms.service;

import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;

import java.util.List;

public interface ICompanyService {

    public DtoCompany saveCompany(DtoCompanyIU dtoCompanyIU);

    public DtoCompany updateCompany(Long id, DtoCompanyIU dtoCompanyIU);

    public List<DtoCompany> getActiveCompanies();

    public List<DtoCompany> getInactiveCompanies();

    public String deactivateCompany(Long id);

    public String activateCompany(Long id);

    public List<DtoCompany> searchActiveCompaniesByName(String name);

    public List<DtoCompany> searchInactiveCompaniesByName(String name);

    public DtoCompany getCompanyById(Long id);

    String deleteCompanyPermanently(Long companyId);


}
