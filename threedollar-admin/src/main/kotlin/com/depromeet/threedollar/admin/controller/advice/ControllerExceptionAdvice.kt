package com.depromeet.threedollar.admin.controller.advice

import com.depromeet.threedollar.application.common.dto.ApiResponse
import com.depromeet.threedollar.common.exception.ErrorCode.*
import com.depromeet.threedollar.common.exception.ConflictException
import com.depromeet.threedollar.common.exception.ForbiddenException
import com.depromeet.threedollar.common.exception.NotFoundException
import com.depromeet.threedollar.common.exception.BadGatewayException
import com.depromeet.threedollar.common.exception.ServiceUnAvailableException
import com.depromeet.threedollar.common.exception.UnAuthorizedException
import com.depromeet.threedollar.common.exception.ValidationException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionAdvice {

    private val logger = KotlinLogging.logger {}

    /**
     * 400 BAD Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    private fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(VALIDATION_EXCEPTION, e.bindingResult.allErrors[0].defaultMessage)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    private fun handleBadRequest(e: BindException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(VALIDATION_EXCEPTION, e.bindingResult.allErrors[0].defaultMessage)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException::class)
    private fun handleMissingRequestHeaderException(e: MissingRequestHeaderException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(VALIDATION_EXCEPTION, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException::class)
    private fun handleInvalidFormatException(e: InvalidFormatException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(VALIDATION_EXCEPTION)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException::class)
    private fun handleValidationException(e: ValidationException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 401 UnAuthorized
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException::class)
    private fun handleUnAuthorizedException(e: UnAuthorizedException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 403 Forbidden
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException::class)
    private fun handleConflictException(e: ForbiddenException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 404 NotFound
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    private fun handleNotFoundException(e: NotFoundException): ApiResponse<Nothing> {
        logger.error{ e }
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 405 Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    private fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(METHOD_NOT_ALLOWED_EXCEPTION)
    }

    /**
     * 409 Conflict
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException::class)
    private fun handleConflictException(e: ConflictException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 502 Bad Gateway
     */
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(BadGatewayException::class)
    private fun handleBadGatewayException(e: BadGatewayException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 503 Service Unavailable
     */
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnAvailableException::class)
    private fun handleServiceUnAvailableException(e: ServiceUnAvailableException): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 500 Internal Server
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    private fun handleInternalServerException(e: Exception): ApiResponse<Nothing> {
        logger.error { e }
        return ApiResponse.error(INTERNAL_SERVER_EXCEPTION)
    }

}
