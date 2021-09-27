package com.depromeet.threedollar.api.service.upload;

import com.depromeet.threedollar.api.service.upload.dto.request.UploadRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    String uploadFile(UploadRequest request, MultipartFile file);

}
