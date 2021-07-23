package com.depromeet.threedollar.admin.common.dto

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

abstract class AuditingTimeResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private var createdAt: LocalDateTime? = null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private var updatedAt: LocalDateTime? = null

    fun setAuditingTime(auditingTimeEntity: AuditingTimeEntity) {
        this.createdAt = auditingTimeEntity.createdAt
        this.updatedAt = auditingTimeEntity.updatedAt
    }

    fun setAuditingTime(createdAt: LocalDateTime?, updatedAt: LocalDateTime?) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }

}
