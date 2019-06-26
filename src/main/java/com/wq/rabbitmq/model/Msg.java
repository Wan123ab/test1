package com.wq.rabbitmq.model;

import java.io.Serializable;
import java.util.Date;

public class Msg implements Serializable {

    private String id;

    private String content;

    private Date createDate;

    public Msg() {
    }

    public Msg(String id, String content, Date createDate) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


}
