package org.gokdemir.dms.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.gokdemir.dms.enums.DocumentCategory;
import org.gokdemir.dms.enums.DocumentFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class DtoDocumentUpdateIU {

    private String documentNo;

    private DocumentCategory category;

    private String description;

    private LocalDateTime documentDateTime;
}