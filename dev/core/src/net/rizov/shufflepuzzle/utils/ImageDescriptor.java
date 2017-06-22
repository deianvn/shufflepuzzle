package net.rizov.shufflepuzzle.utils;

public class ImageDescriptor {

    private String imageId;

    private String shortImageId;

    private String uri;

    public ImageDescriptor(String imageId) {
        this.imageId = imageId;
        int extensionIndex = imageId.lastIndexOf('.');
        extensionIndex = extensionIndex == -1 ? imageId.length() : extensionIndex;
        this.shortImageId = imageId.substring(0, extensionIndex);
        uri = "pack/" + imageId;
    }

    public String getUri() {
        return uri;
    }

    public String getShortFileName() {
        return shortImageId;
    }

}
