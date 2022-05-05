package org.dneversky.idea.custom;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ImageFileManager {

    private Collection<String> fileNamesToSave;
    private Collection<String> fileNamesToDelete;

    public static class Builder {

        ImageFileManager imageFileManager = new ImageFileManager();

        Builder fileNamesToSave(Collection<String> fileNamesToSave) {
            imageFileManager.fileNamesToSave = fileNamesToSave;
            return this;
        }

        Builder fileNamesToDelete(Collection<String> fileNamesToDelete) {
            imageFileManager.fileNamesToDelete = fileNamesToDelete;
            return this;
        }

        ImageFileManager build() {
            return imageFileManager;
        }
    }
}
