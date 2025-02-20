package org.gokdemir.dms.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.gokdemir.dms.enums.DocumentCategory;
import org.gokdemir.dms.enums.DocumentFormat;

@Getter
@Setter
public class DtoDocumentIU {

    private String name;

    private DocumentFormat type;

    private String documentNo;

    private DocumentCategory category;

    private String description;

    private Long companyId;
}
