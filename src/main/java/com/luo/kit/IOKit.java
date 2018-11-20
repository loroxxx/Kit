package com.luo.kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * Created by luoyuanq on 2018/11/20.
 */
public class IOKit {

    private static Logger log = LoggerFactory.getLogger(IOKit.class);

    public static void closeQuietly(Closeable closeable) {
        try {
            if (null == closeable) {
                return;
            }
            closeable.close();
        } catch (Exception e) {
            log.error("Close closeable error", e);
        }
    }
}
