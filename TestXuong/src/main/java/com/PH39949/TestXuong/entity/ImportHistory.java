package com.PH39949.TestXuong.entity;

import com.PH39949.TestXuong.utils.LocalDateTimeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "import_history")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ImportHistory {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "code")
    private int code;

    @Column(name = "message")
    private String message;

    @Column(name = "method")
    private String method;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    @CreationTimestamp
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime lastModifiedDate;

    public ImportHistory(String id, int code, String message, String method, boolean status) {
        this.id = id;
        this.code = code;
        this.message = message;
        this.method = method;
        this.status = status;
    }
}
