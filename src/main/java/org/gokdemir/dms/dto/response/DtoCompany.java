package org.gokdemir.dms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DtoCompany {

    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private String folderPath;
}
