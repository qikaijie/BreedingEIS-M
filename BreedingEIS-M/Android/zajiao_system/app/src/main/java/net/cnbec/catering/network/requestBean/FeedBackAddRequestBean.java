package net.cnbec.catering.network.requestBean;

import java.io.Serializable;

public class FeedBackAddRequestBean implements Serializable {

    private String content;
    private Integer channel;
    private String username;

    public FeedBackAddRequestBean(String content,String username){
        this.content = content;
        this.username = username;
        this.channel = 1;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
