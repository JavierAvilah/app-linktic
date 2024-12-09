package com.linktic.app.model;

import com.linktic.app.model.enumeration.TypeOrder;
import com.linktic.app.model.superclass.AuditedEntity;
import com.linktic.app.model.superclass.AuditedEntityNullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "order_product")
public class Order extends AuditedEntityNullable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number_oder", nullable = false)
    private Integer numberOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_order", nullable = false)
    private TypeOrder typeOrder;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<DetailOrder> detailOrders = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}