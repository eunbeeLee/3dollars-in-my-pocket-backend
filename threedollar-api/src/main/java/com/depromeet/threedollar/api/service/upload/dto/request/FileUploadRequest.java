package com.depromeet.threedollar.api.service.upload.dto.request;

import com.depromeet.threedollar.common.utils.type.ImageType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUploadRequest {

    @NotNull(message = "{image.type.notnull}")
    private ImageType type;

    public static FileUploadRequest of(ImageType type) {
        return new FileUploadRequest(type);
    }

}
