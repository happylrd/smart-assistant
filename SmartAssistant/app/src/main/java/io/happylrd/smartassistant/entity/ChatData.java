package io.happylrd.smartassistant.entity;

public class ChatData {

    private int type;
    private String info;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ChatData{" +
                "type=" + type +
                ", info='" + info + '\'' +
                '}';
    }
}
