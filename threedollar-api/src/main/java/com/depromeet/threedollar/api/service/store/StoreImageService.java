package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.store.dto.response.StoreImageResponse;
import com.depromeet.threedollar.api.service.upload.FileUploadService;
import com.depromeet.threedollar.api.service.upload.dto.request.FileUploadRequest;
import com.depromeet.threedollar.common.utils.type.ImageType;
import com.depromeet.threedollar.domain.domain.store.StoreImage;
import com.depromeet.threedollar.domain.domain.store.StoreImageRepository;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreImageService {

    private final StoreRepository storeRepository;
    private final StoreImageRepository storeImageRepository;
    private final FileUploadService fileUploadService;

    public StoreImageResponse addStoreImage(Long storeId, MultipartFile image, Long userId) {
        StoreServiceUtils.validateExistsStore(storeRepository, storeId);
        String imageUrl = fileUploadService.uploadImage(FileUploadRequest.of(ImageType.STORE), image);
        return StoreImageResponse.of(storeImageRepository.save(StoreImage.newInstance(storeId, userId, imageUrl)));
    }

    @Transactional
    public void deleteStoreImage(Long imageId) {
        StoreImage storeImage = StoreImageServiceUtils.findStoreImageById(storeImageRepository, imageId);
        storeImage.delete();
        // TODO 트랜잭션 커밋시 변경감지가 작동안하는데 이유 찾아봐야함
        storeImageRepository.save(storeImage);
    }

    @Transactional(readOnly = true)
    public List<StoreImageResponse> getStoreImages(Long storeId) {
        List<StoreImage> storeImages = storeImageRepository.findAllByStoreId(storeId);
        return storeImages.stream()
            .map(StoreImageResponse::of)
            .collect(Collectors.toList());
    }

}
