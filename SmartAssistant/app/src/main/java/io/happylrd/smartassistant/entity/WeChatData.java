package io.happylrd.smartassistant.entity;

public class WeChatData {

    private String title;
    private String source;
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "WeChatData{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
