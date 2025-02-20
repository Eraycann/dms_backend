package org.gokdemir.dms.mapper;

import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.gokdemir.dms.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface DocumentMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    DtoDocument toDto(Document document);
    
    List<DtoDocument> toDtoList(List<Document> documentList);
    
    Document toEntity(DtoDocumentIU dtoDocumentIU);
}
