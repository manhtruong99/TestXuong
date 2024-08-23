package com.PH39949.TestXuong.entity;

import com.PH39949.TestXuong.utils.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "major_facility")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MajorFacility {
    @Id
    @Column(name = "id",columnDefinition = "VARCHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_department_facility")
    private DepartmentFacility departmentFacility;

    @ManyToOne
    @JoinColumn(name = "id_major")
    private Major major;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_date",  updatable = false)
    @CreationTimestamp
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    @CreationTimestamp
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime lastModifiedDate;
}
