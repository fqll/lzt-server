package com.tomasky.departure.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

/**
 * @Auther: imike
 * @Date: 2018/9/3 10:06
 * @Description:统一业务日志输出格式工具类
 */
public class LogUtil {

    public static void debug(Class classs, String desc) {
        debug(classs, desc, "");
    }

    public static void debug(Class classs, String desc, String paraOrContent) {
        log(Level.DEBUG, classs, desc, paraOrContent, null);
    }

    public static void info(Class classs, String desc) {
        info(classs, desc, "");
    }

    public static void info(Class classs, String desc, String paraOrContent) {
        log(Level.INFO, classs, desc, paraOrContent, null);
    }

    public static void success(Class classs, String desc) {
        success(classs, desc, "");
    }

    public static void success(Class classs, String desc, String paraOrContent) {
        log(Level.INFO, classs, "✅ " + desc, paraOrContent, null);
    }

    public static void warn(Class classs, String desc) {
        warn(classs, desc, "");
    }

    public static void warn(Class classs, String desc, String paraOrContent) {
        log(Level.WARN, classs, desc, paraOrContent, null);
    }

    public static void error(Class classs, String desc) {
        error(classs, desc, "", null);
    }

    public static void error(Class classs, String desc, Throwable e) {
        error(classs, desc, "", e);
    }

    public static void error(Class classs, String desc, String paraOrContent) {
        error(classs, desc, paraOrContent, null);
    }

    public static void error(Class classs, String desc, String paraOrContent, Throwable e) {
        log(Level.ERROR, classs, desc, paraOrContent, e);
    }

    public static void log(Level level, Class classs, String desc, String paraOrContent, Throwable e) {
        Logger logger = LoggerFactory.getLogger(classs);
        switch (level) {
            case DEBUG:
                logger.debug("[{}]|[{}]", "🔍 " + desc, paraOrContent);
                break;
            case INFO:
                logger.info("[{}]|[{}]", desc, paraOrContent);
                break;
            case WARN:
                logger.warn("[{}]|[{}]", "⚠️ " + desc, paraOrContent);
                break;
            case ERROR:
                logger.error("[{}]|[{}]", "❌ " + desc, paraOrContent, e);
                break;
            default:
                logger.info("[{}]|[{}]", desc, paraOrContent);
                break;
        }

    }

}
