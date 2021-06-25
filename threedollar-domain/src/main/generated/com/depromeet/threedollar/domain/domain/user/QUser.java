package com.depromeet.threedollar.domain.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1736174610L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity _super = new com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QSocialInfo socialInfo;

    public final BooleanPath state = createBoolean("state");

    public final EnumPath<UserStatusType> status = createEnum("status", UserStatusType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.socialInfo = inits.isInitialized("socialInfo") ? new QSocialInfo(forProperty("socialInfo")) : null;
    }

}

