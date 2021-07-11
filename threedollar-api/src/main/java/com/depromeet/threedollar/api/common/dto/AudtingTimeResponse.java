package com.depromeet.threedollar.api.common.dto;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class AudtingTimeResponse {

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    protected void setBaseTime(AuditingTimeEntity auditingTimeEntity) {
        this.createdAt = auditingTimeEntity.getCreatedAt();
        this.updatedAt = auditingTimeEntity.getUpdatedAt();
    }

    protected void setBaseTime(LocalDateTime createdAt, LocalDateTime updatedAT) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAT;
    }

}
