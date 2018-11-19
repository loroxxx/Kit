package com.luo.kit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by luoyuanq on 2018/11/19.
 */
public class Logkit {

    public static Logger getLog(Class cls) {
        return LoggerFactory.getLogger(cls);
    }

}
