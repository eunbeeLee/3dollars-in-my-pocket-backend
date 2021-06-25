package com.depromeet.threedollar.domain.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWithdrawalUser is a Querydsl query type for WithdrawalUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWithdrawalUser extends EntityPathBase<WithdrawalUser> {

    private static final long serialVersionUID = -36565593L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWithdrawalUser withdrawalUser = new QWithdrawalUser("withdrawalUser");

    public final com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity _super = new com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath name = createString("name");

    public final QSocialInfo socialInfo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final DateTimePath<java.time.LocalDateTime> userCreatedAt = createDateTime("userCreatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QWithdrawalUser(String variable) {
        this(WithdrawalUser.class, forVariable(variable), INITS);
    }

    public QWithdrawalUser(Path<? extends WithdrawalUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWithdrawalUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWithdrawalUser(PathMetadata metadata, PathInits inits) {
        this(WithdrawalUser.class, metadata, inits);
    }

    public QWithdrawalUser(Class<? extends WithdrawalUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.socialInfo = inits.isInitialized("socialInfo") ? new QSocialInfo(forProperty("socialInfo")) : null;
    }

}

