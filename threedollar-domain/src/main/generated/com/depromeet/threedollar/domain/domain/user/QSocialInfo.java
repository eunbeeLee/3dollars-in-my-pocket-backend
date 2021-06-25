package com.depromeet.threedollar.domain.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSocialInfo is a Querydsl query type for SocialInfo
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSocialInfo extends BeanPath<SocialInfo> {

    private static final long serialVersionUID = 1546051778L;

    public static final QSocialInfo socialInfo = new QSocialInfo("socialInfo");

    public final StringPath socialId = createString("socialId");

    public final EnumPath<UserProviderType> socialType = createEnum("socialType", UserProviderType.class);

    public QSocialInfo(String variable) {
        super(SocialInfo.class, forVariable(variable));
    }

    public QSocialInfo(Path<? extends SocialInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSocialInfo(PathMetadata metadata) {
        super(SocialInfo.class, metadata);
    }

}

