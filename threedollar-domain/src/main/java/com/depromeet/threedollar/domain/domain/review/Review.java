package com.depromeet.threedollar.domain.domain.review;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    indexes = {
        @Index(name = "idx_review_1", columnList = "storeId"),
        @Index(name = "idx_review_2", columnList = "userId"),
        @Index(name = "idx_review_3", columnList = "status")
    }
)
public class Review extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private Long userId;

    @Column(length = 200)
    private String contents;

    @Embedded
    private Rating rating;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private Review(Long storeId, Long userId, String contents, int rating) {
        this.storeId = storeId;
        this.userId = userId;
        this.contents = contents;
        this.rating = Rating.of(rating);
        this.status = ReviewStatus.POSTED;
    }

    public static Review of(Long storeId, Long userId, String contents, int rating) {
        return new Review(storeId, userId, contents, rating);
    }

    public void delete() {
        this.status = ReviewStatus.DELETED;
    }

    public void update(String content, int rating) {
        this.contents = content;
        this.rating = Rating.of(rating);
    }

    public int getRating() {
        return this.rating.getRating();
    }

}
