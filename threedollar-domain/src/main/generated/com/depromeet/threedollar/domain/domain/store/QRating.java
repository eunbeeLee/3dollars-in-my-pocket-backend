package com.depromeet.threedollar.domain.domain.store;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRating is a Querydsl query type for Rating
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRating extends BeanPath<Rating> {

    private static final long serialVersionUID = -1474533114L;

    public static final QRating rating1 = new QRating("rating1");

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public QRating(String variable) {
        super(Rating.class, forVariable(variable));
    }

    public QRating(Path<? extends Rating> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRating(PathMetadata metadata) {
        super(Rating.class, metadata);
    }

}

