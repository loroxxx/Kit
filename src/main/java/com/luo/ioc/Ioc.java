package com.luo.ioc;

import org.omg.CORBA.Object;

/**
 * Created by luoyuanq on 2018/11/20.
 */
public interface Ioc {


    void addBean(Object object);

    void addBean(String name, Object object);

    void addBean(Class clz);

    Object getBean(String name);


}
