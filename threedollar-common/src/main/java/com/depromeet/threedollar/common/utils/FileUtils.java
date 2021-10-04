package com.depromeet.threedollar.common.utils;

import com.depromeet.threedollar.common.exception.model.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.depromeet.threedollar.common.exception.ErrorCode.VALIDATION_FILE_TYPE_EXCEPTION;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ValidationException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName), VALIDATION_FILE_TYPE_EXCEPTION);
        }
    }

}
