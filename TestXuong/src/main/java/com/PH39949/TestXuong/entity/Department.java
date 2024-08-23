package com.PH39949.TestXuong.entity;

import com.PH39949.TestXuong.utils.LocalDateTimeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "department")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    @Column(name = "id",columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

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
