package com.depromeet.threedollar.domain.config.querydsl;

import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

/**
 * GROUP BY시 묵시적으로 해당 필드로 ORDER BY가 수행되는데, 정렬이 필요 없는 경우, 약간의 성능 향상을 목적으로 ORDER BY NULL을 명시적으로 실행함으로써 정렬이 되지 않도록 하도록 설정.
 * (MySQL의 경우 8.0부터 묵시적으로 ORDER BY 수행하지 않도록 개선됨)
 * - mariadb docs
 * By default, if a GROUP BY clause is present, the rows in the output will be sorted by the expressions used in the GROUP BY.
 * If you want the rows to be sorted by another field, you can add an explicit ORDER BY. If you don't want the result to be ordered, you can add ORDER BY NULL.
 */
public class OrderByNull extends OrderSpecifier {

    public static final OrderByNull DEFAULT = new OrderByNull();

    private OrderByNull() {
        super(Order.ASC, NullExpression.DEFAULT, NullHandling.Default);
    }

}
