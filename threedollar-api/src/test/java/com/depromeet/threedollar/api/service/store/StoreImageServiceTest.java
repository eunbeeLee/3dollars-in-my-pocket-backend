package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.UserSetUpTest;
import com.depromeet.threedollar.api.service.store.dto.response.StoreImageResponse;
import com.depromeet.threedollar.api.service.upload.FileUploadService;
import com.depromeet.threedollar.api.service.upload.dto.request.FileUploadRequest;
import com.depromeet.threedollar.common.exception.NotFoundException;
import com.depromeet.threedollar.domain.domain.store.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class StoreImageServiceTest extends UserSetUpTest {

    private StoreImageService storeImageService;

    @Autowired
    private StoreImageRepository storeImageRepository;

    @Autowired
    private StoreRepository storeRepository;

    @AfterEach
    void cleanUp() {
        super.cleanup();
        storeRepository.deleteAll();
        storeImageRepository.deleteAll();
    }

    private Store store;
    private static final String IMAGE_URL = "https://image.url";

    @BeforeEach
    void setUpStoreImageService() {
        storeImageService = new StoreImageService(storeRepository, storeImageRepository, new StubFileUploadService());

        store = StoreCreator.create(userId, "가게이름");
        storeRepository.save(store);
    }

    private static class StubFileUploadService implements FileUploadService {
        @Override
        public String uploadImage(FileUploadRequest request, MultipartFile file) {
            return IMAGE_URL;
        }
    }

    @Test
    void 가게에_새로운_이미지를_등록한다() {
        // when
        storeImageService.addStoreImage(store.getId(), new MockMultipartFile("name", new byte[]{}), userId);

        // then
        List<StoreImage> storeImageList = storeImageRepository.findAll();
        assertThat(storeImageList).hasSize(1);
        assertStoreImage(storeImageList.get(0), store.getId(), userId, IMAGE_URL, StoreImageStatus.ACTIVE);
    }

    @Test
    void 가게에_게시되어있는_특정_이미지를_삭제하면_해당_이미지가_INACTIVE로_변경된다() {
        // given
        StoreImage storeImage = StoreImage.newInstance(store.getId(), userId, IMAGE_URL);
        storeImageRepository.save(storeImage);

        // when
        storeImageService.deleteStoreImage(storeImage.getId());

        // then
        List<StoreImage> storeImageList = storeImageRepository.findAll();
        assertThat(storeImageList).hasSize(1);
        assertStoreImage(storeImageList.get(0), store.getId(), userId, IMAGE_URL, StoreImageStatus.INACTIVE);
    }

    @Test
    void 가게_이미지_삭제시_해당하는_가게_이미지가_존재하지_않을경우_NOT_FOUND_EXCEPTION_가_발생한다() {
        // when & then
        assertThatThrownBy(() -> storeImageService.deleteStoreImage(999L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 가게_이미지_삭제시_해당하는_가게_이미지가_INACTIVE_삭제일경우__NOT_FOUND_EXCEPTION_가_발생한다() {
        // given
        StoreImage storeImage = StoreImage.newInstance(store.getId(), userId, IMAGE_URL);
        storeImage.delete();
        storeImageRepository.save(storeImage);

        // when & then
        assertThatThrownBy(() -> storeImageService.deleteStoreImage(storeImage.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 해당하는_가게에_등록된_이미지들을_조회한다() {
        // given
        StoreImage storeImage = StoreImage.newInstance(store.getId(), userId, IMAGE_URL);
        storeImageRepository.save(storeImage);

        // when
        List<StoreImageResponse> responses = storeImageService.getStoreImages(store.getId());

        // then
        assertThat(responses).hasSize(1);
        asserStoreImageResponse(responses.get(0), storeImage.getId(), storeImage.getUrl());
    }

    @Test
    void 해당하는_가게에_등록된_이미지들을_조회시_삭제된_이미지는_조회되지_않는다() {
        // given
        StoreImage storeImage = StoreImage.newInstance(store.getId(), userId, IMAGE_URL);
        storeImage.delete();
        storeImageRepository.save(storeImage);

        // when
        List<StoreImageResponse> responses = storeImageService.getStoreImages(store.getId());

        // then
        assertThat(responses).isEmpty();
    }

    private void asserStoreImageResponse(StoreImageResponse storeImageResponse, Long id, String url) {
        assertThat(storeImageResponse.getImageId()).isEqualTo(id);
        assertThat(storeImageResponse.getUrl()).isEqualTo(url);
    }

    private void assertStoreImage(StoreImage storeImage, Long storeId, Long userId, String imageUrl, StoreImageStatus status) {
        assertThat(storeImage.getStoreId()).isEqualTo(storeId);
        assertThat(storeImage.getUserId()).isEqualTo(userId);
        assertThat(storeImage.getUrl()).isEqualTo(imageUrl);
        assertThat(storeImage.getStatus()).isEqualTo(status);
    }

}
