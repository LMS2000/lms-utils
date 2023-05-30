package com.lms.page;

/**
 * 为了保证线程安全，维护一个线程副本
 */
public class PageContext {

    private static ThreadLocal<PageStorage> PAGE_INFO = new ThreadLocal<>();


    protected static void setPageInfo(PageStorage pageStorage) {
        PAGE_INFO.set(pageStorage);
    }

    public static PageStorage getPageInfo() {
        return PAGE_INFO.get();
    }

    //清除当前线程资源副本
    public static void clear() {
        PAGE_INFO.remove();
    }

}
