package org.gokdemir.dms.dto.request;

import lombok.Data;
import org.gokdemir.dms.enums.DocumentCategory;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class DtoDocumentFilter {

    private Long companyId;

    private String documentNo;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    private DocumentCategory category;
}
