package com.depromeet.threedollar.api.common.dto;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuditingTimeResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    protected LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
