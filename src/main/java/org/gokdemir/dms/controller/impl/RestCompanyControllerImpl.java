package org.gokdemir.dms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.gokdemir.dms.controller.IRestCompanyController;
import org.gokdemir.dms.controller.RestBaseController;
import org.gokdemir.dms.controller.RootEntity;
import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.gokdemir.dms.service.ICompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/company")
public class RestCompanyControllerImpl extends RestBaseController implements IRestCompanyController {

    private final ICompanyService companyService;

    @PostMapping("/save")
    @Override
    public RootEntity<DtoCompany> saveCompany(@RequestBody DtoCompanyIU dtoCompanyIU) {
        return ok(companyService.saveCompany(dtoCompanyIU));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoCompany> updateCompany(@PathVariable Long id, @RequestBody DtoCompanyIU dtoCompanyIU) {
        return ok(companyService.updateCompany(id, dtoCompanyIU));
    }

    @GetMapping("/active")
    @Override
    public RootEntity<Page<DtoCompany>> getActiveCompanies(@PageableDefault(size = 10) Pageable pageable) {
        return ok(companyService.getActiveCompanies(pageable));
    }

    @GetMapping("/inactive")
    @Override
    public RootEntity<Page<DtoCompany>> getInactiveCompanies(@PageableDefault(size = 10) Pageable pageable) {
        return ok(companyService.getInactiveCompanies(pageable));
    }

    @PutMapping("/deactivate/{id}")
    @Override
    public RootEntity<String> deactivateCompany(@PathVariable Long id) {
        return ok(companyService.deactivateCompany(id));
    }

    @PutMapping("/activate/{id}")
    @Override
    public RootEntity<String> activateCompany(@PathVariable Long id) {
        return ok(companyService.activateCompany(id));
    }

    @GetMapping("/active/search")
    public RootEntity<Page<DtoCompany>> searchActiveCompaniesByName(@RequestParam String name, @PageableDefault(size = 10) Pageable pageable) {
        return ok(companyService.searchActiveCompaniesByName(name, pageable));
    }

    @GetMapping("/inactive/search")
    public RootEntity<Page<DtoCompany>> searchInactiveCompaniesByName(@RequestParam String name, @PageableDefault(size = 10) Pageable pageable) {
        return ok(companyService.searchInactiveCompaniesByName(name, pageable));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoCompany> getCompanyById(@PathVariable Long id) {
        return ok(companyService.getCompanyById(id));
    }

    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(companyService.deleteCompanyPermanently(companyId));
    }


}