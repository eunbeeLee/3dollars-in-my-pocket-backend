package com.depromeet.threedollar.domain.domain.faq;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    indexes = @Index(name = "idx_faq_1", columnList = "category")
)
public class Faq extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private FaqCategory category;

    @Column(nullable = false, length = 100)
    private String question;

    @Column(nullable = false, length = 200)
    private String answer;

    private Faq(FaqCategory category, String question, String answer) {
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public static Faq newInstance(FaqCategory category, String question, String answer) {
        return new Faq(category, question, answer);
    }

    public void update(String question, String answer, FaqCategory category) {
        this.question = question;
        this.answer = answer;
        this.category = category;
    }

}
