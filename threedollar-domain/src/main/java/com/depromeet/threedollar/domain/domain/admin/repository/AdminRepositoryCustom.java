package com.depromeet.threedollar.domain.domain.admin.repository;

import com.depromeet.threedollar.domain.domain.admin.Admin;

public interface AdminRepositoryCustom {

    Admin findAdminById(Long id);

    Admin findAdminByEmail(String email);

}
