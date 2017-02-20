package io.happylrd.smartassistant.entity;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {

    private Integer age;
    private Boolean sex;
    private String description;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
