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
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestValueException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionAdvice {

    private val log = LoggerFactory.getLogger(ControllerExceptionAdvice::class.java)

    /**
     * 400 BAD Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    private fun handleBadRequest(e: BindException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(VALIDATION_EXCEPTION, e.bindingResult.allErrors[0].defaultMessage)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        InvalidFormatException::class,
        MissingRequestValueException::class,
        ServletRequestBindingException::class
    )
    private fun handleMethodArgumentNotValidException(e: Exception): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(VALIDATION_EXCEPTION)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException::class)
    private fun handleValidationException(e: ValidationException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 401 UnAuthorized
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException::class)
    private fun handleUnAuthorizedException(e: UnAuthorizedException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 403 Forbidden
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException::class)
    private fun handleConflictException(e: ForbiddenException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 404 NotFound
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    private fun handleNotFoundException(e: NotFoundException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 405 Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    private fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(METHOD_NOT_ALLOWED_EXCEPTION)
    }

    /**
     * 409 Conflict
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException::class)
    private fun handleConflictException(e: ConflictException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 415 UnSupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeException::class)
    private fun handleHttpMediaTypeException(e: HttpMediaTypeException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(UNSUPPORTED_MEDIA_TYPE)
    }

    /**
     * 502 Bad Gateway
     */
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(BadGatewayException::class)
    private fun handleBadGatewayException(e: BadGatewayException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 503 Service Unavailable
     */
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnAvailableException::class)
    private fun handleServiceUnAvailableException(e: ServiceUnAvailableException): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(e.errorCode)
    }

    /**
     * 500 Internal Server
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    private fun handleInternalServerException(e: Exception): ApiResponse<Nothing> {
        log.error(e.message, e)
        return ApiResponse.error(INTERNAL_SERVER_EXCEPTION)
    }

}
