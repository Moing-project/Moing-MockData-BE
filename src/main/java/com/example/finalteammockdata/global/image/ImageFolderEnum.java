package com.example.finalteammockdata.global.image;

public enum ImageFolderEnum {

    IMAGE("image"), PROFILE("profile");

    private final String folderName;

    ImageFolderEnum(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
