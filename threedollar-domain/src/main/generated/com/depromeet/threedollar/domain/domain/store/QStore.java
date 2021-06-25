package com.depromeet.threedollar.domain.domain.store;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 646656088L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity _super = new com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<StoreImage, QStoreImage> images = this.<StoreImage, QStoreImage>createList("images", StoreImage.class, QStoreImage.class, PathInits.DIRECT2);

    public final com.depromeet.threedollar.domain.domain.common.QLocation location;

    public final QRating rating;

    public final StringPath storeName = createString("storeName");

    public final EnumPath<StoreType> storeType = createEnum("storeType", StoreType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.location = inits.isInitialized("location") ? new com.depromeet.threedollar.domain.domain.common.QLocation(forProperty("location")) : null;
        this.rating = inits.isInitialized("rating") ? new QRating(forProperty("rating")) : null;
    }

}

