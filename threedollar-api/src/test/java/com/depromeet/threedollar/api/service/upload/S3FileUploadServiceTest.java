package com.depromeet.threedollar.api.service.upload;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.depromeet.threedollar.api.service.upload.dto.request.ImageUploadRequest;
import com.depromeet.threedollar.common.exception.model.ValidationException;
import com.depromeet.threedollar.domain.domain.common.ImageType;
import com.depromeet.threedollar.external.client.s3.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class S3FileUploadServiceTest {

    private S3UploadService s3FileUploadService;

    @BeforeEach
    void setUp() {
        s3FileUploadService = new S3UploadService(new StubS3Service());
    }

    @Nested
    class 이미지_파일_업로드 {

        @Test
        void 성공적으로_업로드되면_파일명이_반환된다() {
            // given
            MultipartFile multipartFile = new MockMultipartFile("fileName.jpeg", "fileName.jpeg", "image/jpeg", new byte[]{});

            ImageType type = ImageType.STORE;
            ImageUploadRequest request = ImageUploadRequest.of(type);

            // when
            String result = s3FileUploadService.uploadFile(request, multipartFile);

            // then
            assertThat(result.endsWith(".jpeg")).isTrue();
        }

        @Test
        void 확장명자가_없는_파일명인경우_VALIDATION_FILE_TYPE_EXCEPTION() {
            // given
            MultipartFile multipartFile = new MockMultipartFile("fileName.jpeg", "fileName", "image/jpeg", new byte[]{});

            ImageUploadRequest request = ImageUploadRequest.of(ImageType.STORE);

            // when & then
            assertThatThrownBy(() -> s3FileUploadService.uploadFile(request, multipartFile)).isInstanceOf(ValidationException.class);
        }

        @Test
        void 허용되지않은_ContentType인경우_VALIDATION_FILE_TYPE_EXCEPTION() {
            // given
            MultipartFile multipartFile = new MockMultipartFile("fileName.jpeg", "fileName.jpeg", "wrong type", new byte[]{});

            ImageUploadRequest request = ImageUploadRequest.of(ImageType.STORE);

            // when & then
            assertThatThrownBy(() -> s3FileUploadService.uploadFile(request, multipartFile)).isInstanceOf(ValidationException.class);
        }

    }

    private static class StubS3Service implements S3Service {

        @Override
        public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        }

        @Override
        public String getFileUrl(String fileName) {
            return fileName;
        }

    }

}
