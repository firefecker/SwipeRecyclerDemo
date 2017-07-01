package com.fire.test;

/**
 * Created by Administrator on 2017/7/1.
 */

public class MultiEntity {

    private String name;

    private int type;

    public MultiEntity() {
    }

    public MultiEntity(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
