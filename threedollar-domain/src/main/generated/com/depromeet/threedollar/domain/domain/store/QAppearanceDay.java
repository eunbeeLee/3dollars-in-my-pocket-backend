package com.depromeet.threedollar.domain.domain.store;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppearanceDay is a Querydsl query type for AppearanceDay
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAppearanceDay extends EntityPathBase<AppearanceDay> {

    private static final long serialVersionUID = 1054217487L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppearanceDay appearanceDay = new QAppearanceDay("appearanceDay");

    public final com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity _super = new com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.depromeet.threedollar.domain.domain.common.DayOfTheWeek> day = createEnum("day", com.depromeet.threedollar.domain.domain.common.DayOfTheWeek.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QStore store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAppearanceDay(String variable) {
        this(AppearanceDay.class, forVariable(variable), INITS);
    }

    public QAppearanceDay(Path<? extends AppearanceDay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppearanceDay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppearanceDay(PathMetadata metadata, PathInits inits) {
        this(AppearanceDay.class, metadata, inits);
    }

    public QAppearanceDay(Class<? extends AppearanceDay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store"), inits.get("store")) : null;
    }

}

