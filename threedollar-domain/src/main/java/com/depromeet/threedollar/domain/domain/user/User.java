package com.depromeet.threedollar.domain.domain.user;

import com.depromeet.threedollar.domain.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "uni_user_1", columnNames = {"socialId", "socialType"})
    },
    indexes = @Index(name = "idx_user_1", columnList = "name")
)
public class User extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SocialInfo socialInfo;

    @Column(length = 50)
    private String name;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private UserStatusType status;

    @Builder
    User(String socialId, UserSocialType socialType, String name, UserStatusType statusType) {
        this.socialInfo = SocialInfo.of(socialId, socialType);
        this.name = name;
        this.status = statusType;
    }

    public static User newInstance(String socialId, UserSocialType socialType, String name) {
        return new User(socialId, socialType, name, UserStatusType.ACTIVE);
    }

	public static User deletedUser() {
        return new User(null, null, "사라진 제보자", UserStatusType.INACTIVE);
	}

	public void update(String name) {
        this.name = name;
    }

    public String getSocialId() {
        return this.socialInfo.getSocialId();
    }

    public UserSocialType getSocialType() {
        return this.socialInfo.getSocialType();
    }

    public boolean isInActive() {
        return this.status.equals(UserStatusType.INACTIVE);
    }

    public String getName() {
        if (isInActive()) {
            return "사라진 제보자";
        }
        return this.name;
    }

    public WithdrawalUser signOut() {
        this.status = UserStatusType.INACTIVE;
        return WithdrawalUser.of(this);
    }

    public void rejoin(String name) {
        this.status = UserStatusType.ACTIVE;
        this.name = name;
    }

    String getOriginName() {
        return this.name;
    }

}
