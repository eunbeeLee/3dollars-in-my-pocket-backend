package com.depromeet.threedollar.domain.utils;

import com.depromeet.threedollar.domain.exception.ErrorCode;
import com.depromeet.threedollar.domain.exception.ValidationException;
import com.depromeet.threedollar.domain.utils.type.ImageType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    private static final List<String> imageContentTypes = Arrays.asList("image/jpeg", "image/png");

    public static String createFileUuidNameWithExtension(ImageType type, String originalFileName) {
        String extension = getFileExtension(originalFileName);
        return type.getFileNameWithDirectory(UUID.randomUUID().toString().concat(extension));
    }

    private static String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ValidationException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName), ErrorCode.VALIDATION_FILE_TYPE_EXCEPTION);
        }
    }

    public static void validateImageFile(String contentType) {
        if (!imageContentTypes.contains(contentType)) {
            throw new ValidationException(String.format("허용되지 않은 파일 형식 (%s) 입니다", contentType), ErrorCode.VALIDATION_FILE_TYPE_EXCEPTION);
        }
    }

}
