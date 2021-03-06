package com.jin.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//等价于 <bean id="user" class="com.jin.pojo.User"/>
//@Component 组件
@Component
public class User {

    public String name;

    //相当于 <property name="name" value="kuangshen"/>
    @Value("kuangshen")
    public void setName(String name) {
        this.name = name;
    }
}
