package com.depromeet.threedollar.domain.domain.user;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO
 * 마이그레이션 이후에, 삭제된 유저 관리를 다른 방식으로 해야할 것 같음.
 * User.state, status를 다른 방식으로 관리하는 것이 좋아보입니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class WithdrawalUser extends AuditingTimeEntity {

    @Id
    private Long userId;

    private String name;

    @Embedded
    private SocialInfo socialInfo;

    private LocalDateTime userCreatedAt;

    @Builder
    private WithdrawalUser(Long userId, String name, SocialInfo socialInfo, LocalDateTime userCreatedAt) {
        this.userId = userId;
        this.name = name;
        this.socialInfo = socialInfo;
        this.userCreatedAt = userCreatedAt;
    }

    static WithdrawalUser of(User user) {
        return WithdrawalUser.builder()
            .userId(user.getId())
            .name(user.getOriginName())
            .socialInfo(SocialInfo.of(user.getSocialId(), user.getSocialType()))
            .userCreatedAt(user.getCreatedAt())
            .build();
    }

}
