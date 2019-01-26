package com.wxbc.datasource;

/**
 * Created by zhaochen on 2019/1/26.
 */
public class DynamicDataSourceHolder {
    //写库对应的数据源key
    public static final String MASTER = "master";

    //读库对应的数据源key
    public static final String SLAVE = "slave";

    //使用ThreadLocal记录当前线程的数据源key
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 设置数据源key
     *
     * @param key
     */
    private static void putDataSourceKey(String key) {
        holder.set(key);
    }

    /**
     * 获取数据源key
     *
     * @return
     */
    public static String getDataSourceKey() {
        return holder.get();
    }

    /**
     * 标记写库
     */
    public static void markMaster() {
        putDataSourceKey(MASTER);
    }

    /**
     * 标记读库
     */
    public static void markSlave() {
        putDataSourceKey(SLAVE);
    }

    public static boolean isMaster() {
        if (MASTER.equals(getDataSourceKey())) {
            return true;
        }
        return false;
    }
}
