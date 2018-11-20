package com.luo.kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by luoyuanq on 2018/11/20.
 */
public class ConfigKit {

    private static Logger log = LoggerFactory.getLogger(ConfigKit.class);

    private static final String PREFIX_CLASSPATH = "classpath:";
    private static final String PREFIX_FILE = "file:";
    private static final String PREFIX_URL = "url:";

    private Properties props = new Properties();

    public static ConfigKit empty() {
        return new ConfigKit();
    }

    public static ConfigKit of(Properties properties) {
        ConfigKit config = new ConfigKit();
        config.props = properties;
        return config;
    }

    public static ConfigKit of(Map<String, String> map) {
        ConfigKit config = new ConfigKit();
        map.forEach((k, v) -> config.props.setProperty(k, v));
        return config;
    }

    public static ConfigKit of(URL url) {
        try {
            return of(url.openStream());
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }


    public static ConfigKit of(File file) {
        try {
            return of(Files.newInputStream(Paths.get(file.getPath())));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static ConfigKit of(InputStream is) {
        try {
            ConfigKit config = new ConfigKit();
            config.props.load(new InputStreamReader(is, "UTF-8"));
            return config;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            IOKit.closeQuietly(is);
        }
    }

    public static ConfigKit of(String location) {
        if (location.startsWith(PREFIX_CLASSPATH)) {
            location = location.substring(PREFIX_CLASSPATH.length());
            return loadClasspath(location);
        } else if (location.startsWith(PREFIX_FILE)) {
            location = location.substring(PREFIX_FILE.length());
            return of(new File(location));
        } else if (location.startsWith(PREFIX_URL)) {
            location = location.substring(PREFIX_URL.length());
            try {
                return of(new URL(location));
            } catch (MalformedURLException e) {
                log.error("", e);
                return null;
            }
        } else {
            return loadClasspath(location);
        }
    }

    private static ConfigKit loadClasspath(String classpath) {
        String path = classpath;
        if (classpath.startsWith("/")) {
            path = classpath.substring(1);
        }
        InputStream is = getDefault().getResourceAsStream(path);
        if (null == is) {
            return new ConfigKit();
        }
        return of(is);
    }


    private static ClassLoader getDefault() {
        ClassLoader loader = null;
        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Exception ignored) {
        }
        if (loader == null) {
            loader = ConfigKit.class.getClassLoader();
            if (loader == null) {
                try {
                    loader = ClassLoader.getSystemClassLoader();
                } catch (Exception e) {
                }
            }
        }
        return loader;
    }


    public ConfigKit set(String key, Object value) {
        props.remove(key);
        props.put(key, value.toString());
        return this;
    }


    public ConfigKit add(String key, Object value) {
        return set(key, value);
    }


    public ConfigKit addAll(Map<String, String> map) {
        map.forEach((key, value) -> this.props.setProperty(key, value));
        return this;
    }

    public ConfigKit addAll(Properties props) {
        props.forEach((key, value) -> this.props.setProperty(key.toString(), value.toString()));
        return this;
    }

    public Optional<String> get(String key) {
        if (null == key) return Optional.empty();
        return Optional.ofNullable(props.getProperty(key));
    }

    public String getOrNull(String key) {
        return get(key).orElse(null);
    }

    public String get(String key, String defaultValue) {
        return get(key).orElse(defaultValue);
    }

    public Optional<Object> getObject(String key) {
        return Optional.ofNullable(props.get(key));
    }

    public Optional<Integer> getInt(String key) {
        if (null != key && getObject(key).isPresent()) {
            return Optional.of(Integer.parseInt(getObject(key).get().toString()));
        }
        return Optional.empty();
    }

    public Integer getIntOrNull(String key) {
        Optional<Integer> intVal = getInt(key);
        return intVal.orElse(null);
    }

    public Integer getInt(String key, int defaultValue) {
        if (getInt(key).isPresent()) {
            return getInt(key).get();
        }
        return defaultValue;
    }

    public Optional<Long> getLong(String key) {
        if (null != key && getObject(key).isPresent()) {
            return Optional.of(Long.parseLong(getObject(key).get().toString()));
        }
        return Optional.empty();
    }

    public Long getLongOrNull(String key) {
        Optional<Long> longVal = getLong(key);
        return longVal.orElse(null);
    }

    public Long getLong(String key, long defaultValue) {
        return getLong(key).orElse(defaultValue);
    }

    public Optional<Boolean> getBoolean(String key) {
        if (null != key && getObject(key).isPresent()) {
            return Optional.of(Boolean.parseBoolean(getObject(key).get().toString()));
        }
        return Optional.empty();
    }

    public Boolean getBooleanOrNull(String key) {
        Optional<Boolean> boolVal = getBoolean(key);
        return boolVal.orElse(null);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(key).orElse(defaultValue);
    }

    public Optional<Double> getDouble(String key) {
        if (null != key && getObject(key).isPresent()) {
            return Optional.of(Double.parseDouble(getObject(key).get().toString()));
        }
        return Optional.empty();
    }

    public Double getDoubleOrNull(String key) {
        Optional<Double> doubleVal = getDouble(key);
        return doubleVal.orElse(null);
    }

    public Double getDouble(String key, double defaultValue) {
        return getDouble(key).orElse(defaultValue);
    }

    public Optional<Date> getDate(String key, String pattern) {
        if (null != key && getObject(key).isPresent()) {
            String value = getObject(key).get().toString();
            LocalDateTime localDateTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            return Optional.ofNullable(date);
        }
        return Optional.empty();
    }

    public Date getDateOrNull(String key, String pattern) {
        Optional<Date> dateVal = getDate(key, pattern);
        return dateVal.orElse(null);
    }

    public Map<String, Object> getPrefix(String key) {
        Map map = new HashMap<>();
        if (null != key) {
            props.forEach((key_, value) -> {
                if (key_.toString().startsWith(key)) {
                    map.put(key_.toString().substring(key.length() + 1), value);
                }
            });
        }
        return map;
    }

    public Map<String, String> toMap() {
        Map map = new HashMap<String, String>(props.size());
        props.forEach((k, v) -> map.put(k.toString(), v.toString()));
        return map;
    }

    public boolean hasKey(String key) {
        if (null == key) {
            return false;
        }
        return props.containsKey(key);
    }

    public boolean hasValue(String value) {
        return props.containsValue(value);
    }

    public Properties props() {
        return props;
    }

    public int size() {
        return props.size();
    }

    public boolean isEmpty() {
        return props.isEmpty();
    }



}
