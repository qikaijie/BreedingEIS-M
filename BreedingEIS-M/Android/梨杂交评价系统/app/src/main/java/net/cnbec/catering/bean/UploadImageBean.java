package net.cnbec.catering.bean;

import java.io.Serializable;

public class UploadImageBean implements Serializable {

    /**
     * 文件类型
     * 0    图片
     * 1    视频
     */
    private int fileType;

    //本地文件名
    private String localName;
    //获取拍摄的图片路径，如果是录制视频则是视频的第一帧图片路径
    private String imageLocalPath;
    //获取拍摄的视频路径
    private String videoLocalPath;
    //是否上传
    private Boolean isUpload;
    private String urlPath;

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getImageLocalPath() {
        return imageLocalPath;
    }

    public void setImageLocalPath(String imageLocalPath) {
        this.imageLocalPath = imageLocalPath;
    }

    public String getVideoLocalPath() {
        return videoLocalPath;
    }

    public void setVideoLocalPath(String videoLocalPath) {
        this.videoLocalPath = videoLocalPath;
    }

    public Boolean getUpload() {
        return isUpload;
    }

    public void setUpload(Boolean upload) {
        isUpload = upload;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public UploadImageBean(){}

    public UploadImageBean(Boolean isUpload){
        this.isUpload = isUpload;
    }
}
