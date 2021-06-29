package com.depromeet.threedollar.domain.domain.faq;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Faq extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String question;

    @Column(nullable = false, length = 200)
    private String answer;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private FaqCategory category;

}
