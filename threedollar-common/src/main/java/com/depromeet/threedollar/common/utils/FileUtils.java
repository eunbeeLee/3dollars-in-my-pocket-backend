package com.depromeet.threedollar.common.utils;

import com.depromeet.threedollar.common.exception.model.validation.ValidationFileTypeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ValidationFileTypeException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
        }
    }

}
