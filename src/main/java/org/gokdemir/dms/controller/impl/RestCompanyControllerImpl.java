package org.gokdemir.dms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.gokdemir.dms.controller.IRestCompanyController;
import org.gokdemir.dms.controller.RestBaseController;
import org.gokdemir.dms.controller.RootEntity;
import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.gokdemir.dms.service.ICompanyService;
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
    public RootEntity<List<DtoCompany>> getActiveCompanies() {
        return ok(companyService.getActiveCompanies());
    }

    @GetMapping("/inactive")
    @Override
    public RootEntity<List<DtoCompany>> getInactiveCompanies() {
        return ok(companyService.getInactiveCompanies());
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
    public RootEntity<List<DtoCompany>> searchActiveCompaniesByName(@RequestParam String name) {
        return ok(companyService.searchActiveCompaniesByName(name));
    }

    @GetMapping("/inactive/search")
    public RootEntity<List<DtoCompany>> searchInactiveCompaniesByName(@RequestParam String name) {
        return ok(companyService.searchInactiveCompaniesByName(name));
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