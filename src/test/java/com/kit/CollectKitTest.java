package com.kit;

import com.luo.kit.CollectKit;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Created by luoyuanq on 2018/11/19.
 */
public class CollectKitTest {


    class User {
        String name;
        int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }


    }

    @Test
    public void distinctByKey() {
        List<User> users = Arrays.asList(new User("lisa", 30), new User("lisa", 30), new User("amy", 30), new User("amy", 30));
        //按名字去重
        assertTrue(users.stream().filter(CollectKit.distinctByKey(User::getName)).count() == 2);
        //先按名字去重，再按年龄去重
        assertTrue(users.stream().filter(CollectKit.distinctByKey(User::getName)).filter(CollectKit.distinctByKey(User::getAge)).count() == 1);
    }


    public static void main(String[] args) {

    }




}
