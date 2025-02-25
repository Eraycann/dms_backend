package org.gokdemir.dms.mapper;

import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.request.DtoDocumentUpdateIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.gokdemir.dms.entity.Document;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface DocumentMapper {
    @Mapping(target = "id", source = "id")
    DtoDocument toDto(Document document);
    
    List<DtoDocument> toDtoList(List<Document> documentList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "company", ignore = true)
    Document toEntity(DtoDocumentIU dtoDocumentIU);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateDocumentFromDto(DtoDocumentUpdateIU dtoDocumentUpdateIU, @MappingTarget Document document);
}





