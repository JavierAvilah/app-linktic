package com.linktic.app.model;


import com.linktic.app.model.enumeration.Accessibility;
import com.linktic.app.model.superclass.AuditedEntity;
import com.linktic.app.model.superclass.AuditedEntityNullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;


import java.util.Base64;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "base64_data")
public class Base64Data extends AuditedEntityNullable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private UUID id;

    @Lob
    @JdbcTypeCode(SqlTypes.BLOB)
    @Column(name = "data", nullable = false)
    private byte[] data;

    @Column(name = "length", nullable = false)
    private Integer length;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "accessibility", nullable = false, length = 20)
    private Accessibility accessibility;

    public void setBase64(@NonNull String base64) {
        data = Base64.getDecoder().decode(base64);
        length = data.length;
    }
}