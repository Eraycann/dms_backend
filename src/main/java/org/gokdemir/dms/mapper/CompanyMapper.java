package org.gokdemir.dms.mapper;

import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.gokdemir.dms.entity.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    // 📌 DtoCompanyIU sınıfından Company sınıfına dönüşüm yapacak olan map metodu
    Company toEntity(DtoCompanyIU dtoCompanyIU);

    // 📌 Company sınıfından DtoCompany sınıfına dönüşüm yapacak olan map metodu
    DtoCompany toDto(Company company);

    // 📌 Company sınıfından DtoCompany sınıfına dönüşüm yapacak olan map metodu
    List<DtoCompany> toDtoList(List<Company> companyList);

}
