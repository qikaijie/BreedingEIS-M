package net.cnbec.catering.bean;

public class PreviewImageVideoBean {

    private String url;  //图片地址
    private String videoUrl;


    private Integer type;

    public PreviewImageVideoBean(Integer type,String url) {
        this.type = type;
        this.url = url;
    }
    public PreviewImageVideoBean(Integer type,String videoUrl,String url) {
        this.url = url;
        this.videoUrl = videoUrl;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
