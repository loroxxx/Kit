package com.luo.kit;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by luoyuanq on 2018/11/19.
 */
public class CollectKit {

    /**
     * 对列表中的某个对象的属性去重；
     * 辅助Stream的filter方法，类似于sql的distinct方法
     *
     * @param keyExtractor 调用对象的get方法
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }


    public static boolean isEmpty(Collection<?> c) {
        return null == c || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return null != c && !c.isEmpty();
    }


}
