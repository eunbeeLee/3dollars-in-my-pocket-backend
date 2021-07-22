package com.depromeet.threedollar.admin.config.resolver

import com.depromeet.threedollar.admin.service.token.TokenService
import com.depromeet.threedollar.common.exception.UnAuthorizedException
import com.depromeet.threedollar.domain.domain.admin.AdminRepository
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthInterceptor(
    private val tokenService: TokenService,
    private val adminRepository: AdminRepository
) : HandlerInterceptorAdapter() {

    companion object {
        const val TOKEN_PREFIX = "Bearer "
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (StringUtils.hasText(header) && header.startsWith(TOKEN_PREFIX)) {
            val token = header.split(TOKEN_PREFIX)[1]
            val accountToken = tokenService.decode(token)
            val admin = adminRepository.findAdminById(accountToken.adminId)
                ?: throw UnAuthorizedException("잘못된 토큰(${token})입니다 다시 로그인해주세요.")
            request.setAttribute("adminId", admin.id)
            return true
        }
        throw UnAuthorizedException("잘못된 토큰(${header})입니다 다시 로그인해주세요.")
    }

}
