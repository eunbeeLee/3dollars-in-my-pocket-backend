package com.depromeet.threedollar.api.service.upload;

import com.depromeet.threedollar.api.service.upload.dto.request.FileUploadRequest;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String uploadImage(FileUploadRequest request, MultipartFile file);

}
