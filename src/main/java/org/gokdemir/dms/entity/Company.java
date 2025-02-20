package org.gokdemir.dms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company extends BaseEntity{

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "folder_path")
    private String folderPath;
}
