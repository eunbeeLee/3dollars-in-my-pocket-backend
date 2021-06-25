package com.depromeet.threedollar.domain.domain.menu;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = -130365294L;

    public static final QMenu menu = new QMenu("menu");

    public final com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity _super = new com.depromeet.threedollar.domain.domain.common.QAuditingTimeEntity(this);

    public final EnumPath<MenuCategoryType> category = createEnum("category", MenuCategoryType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath price = createString("price");

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMenu(String variable) {
        super(Menu.class, forVariable(variable));
    }

    public QMenu(Path<? extends Menu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenu(PathMetadata metadata) {
        super(Menu.class, metadata);
    }

}

