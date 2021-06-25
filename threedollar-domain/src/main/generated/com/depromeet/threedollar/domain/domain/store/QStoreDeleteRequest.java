package com.depromeet.threedollar.domain.domain.store;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoreDeleteRequest is a Querydsl query type for StoreDeleteRequest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStoreDeleteRequest extends EntityPathBase<StoreDeleteRequest> {

    private static final long serialVersionUID = -1306940180L;

    public static final QStoreDeleteRequest storeDeleteRequest = new QStoreDeleteRequest("storeDeleteRequest");

    public final com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity _super = new com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<DeleteReasonType> reason = createEnum("reason", DeleteReasonType.class);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QStoreDeleteRequest(String variable) {
        super(StoreDeleteRequest.class, forVariable(variable));
    }

    public QStoreDeleteRequest(Path<? extends StoreDeleteRequest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreDeleteRequest(PathMetadata metadata) {
        super(StoreDeleteRequest.class, metadata);
    }

}

