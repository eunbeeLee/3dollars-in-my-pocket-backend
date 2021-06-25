
package com.depromeet.threedollar.domain.domain.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatusType {

	ACTIVE("사용중인 사용자"),
	INACTIVE("탈퇴한 사용자");

	private final String status;

}