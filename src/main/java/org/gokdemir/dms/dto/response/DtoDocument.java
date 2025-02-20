package org.gokdemir.dms.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.gokdemir.dms.enums.DocumentCategory;
import org.gokdemir.dms.enums.DocumentFormat;
import java.time.LocalDateTime;

@Getter
@Setter
public class DtoDocument {

    private Long id;

    private String name;

    private DocumentFormat type;

    private DocumentCategory category;

    private String documentNo;

    private String description;

    private LocalDateTime createdAt;

    private DtoCompany company;
}
