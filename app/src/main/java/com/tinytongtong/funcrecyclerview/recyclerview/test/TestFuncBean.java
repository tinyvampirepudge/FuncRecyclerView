package com.tinytongtong.funcrecyclerview.recyclerview.test;

import com.tinytongtong.funcrecyclerview.recyclerview.FuncBaseBean;

/**
 * @Description: TODO
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/23 4:28 PM
 * @Version TODO
 */
public class TestFuncBean implements FuncBaseBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return 4;
    }
}
