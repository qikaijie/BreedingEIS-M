package net.cnbec.catering.bean;

import java.io.Serializable;

public class FeedbackBean implements Serializable {

    /**
     {
     "channel": 0,
     "content": "string",
     "createTime": "2021-07-17T08:20:12.678Z",
     "id": 0,
     "reply": "string",
     "status": 0,
     "userId": 0,
     "username": "string"
     }
     */

    private Integer id;
    private Integer channel;
    private Integer status;
    private Integer userId;
    private String content;
    private String reply;
    private String createTime;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
