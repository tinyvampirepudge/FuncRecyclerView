package com.tinytongtong.funcrecyclerview.recyclerview.test;

import com.tinytongtong.funcrecyclerview.recyclerview.FuncBaseBean;

/**
 * @Description: TODO
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/24 8:54 PM
 * @Version TODO
 */
public class Test1FuncBean implements FuncBaseBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return 5;
    }
}
