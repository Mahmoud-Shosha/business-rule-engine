package org.gs1eg.business_rule_engine.rule.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "rules")
public class RuleEntity {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RuleType type;

    @Column(name = "condition")
    private String condition;

    @Column(name = "action")
    private String action;

    @Column(name = "priority")
    private Integer priority;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

}
