package com.depromeet.threedollar.api.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageType {

    STORE("store");

    private final String directory;

    public String getFileNameWithDirectory(String fileName) {
        return this.directory.concat("/").concat(fileName);
    }

}