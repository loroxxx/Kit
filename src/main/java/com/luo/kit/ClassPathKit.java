package com.luo.kit;

import java.net.URL;

/**
 * Created by luoyuanq on 2018/11/19.
 */
public class ClassPathKit {


    /**
     * @return 得到的是当前运行的classpath的URL
     */
    public static URL getClassClassPath() {
        return ClassPathKit.class.getResource("/");
    }
}
