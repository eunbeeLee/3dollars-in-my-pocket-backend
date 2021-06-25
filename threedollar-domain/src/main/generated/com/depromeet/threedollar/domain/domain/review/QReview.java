package com.depromeet.threedollar.domain.domain.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -1985993550L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity _super = new com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity(this);

    public final EnumPath<com.depromeet.threedollar.domain.domain.menu.MenuCategoryType> category = createEnum("category", com.depromeet.threedollar.domain.domain.menu.MenuCategoryType.class);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.depromeet.threedollar.domain.domain.store.QRating rating;

    public final EnumPath<ReviewStatus> status = createEnum("status", ReviewStatus.class);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public final StringPath storeName = createString("storeName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rating = inits.isInitialized("rating") ? new com.depromeet.threedollar.domain.domain.store.QRating(forProperty("rating")) : null;
    }

}

