package com.tomasky.departure.utils;

/**
 * Created by sam on 2019-09-26.11:12
 */
public class GenerateAlphanum {
//    /**
//     * 方法一
//     * 生成指定长度的字母数字组合字符串
//     */
//    public static String getAlphanumString(int length) {
//        Random random = new Random();
//        StringBuffer buf = new StringBuffer();
//        String charStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        int charLength = charStr.length();
//        for (int i = 0; i < length; i++) {
//            int index = random.nextInt(charLength);
//            buf.append(charStr.charAt(index));
//        }
//        return buf.toString();
//    }
//
//    /**
//     * 方法二
//     * 生成指定长度的字母数字组合字符串
//     */
//    public static String getAlphanumString_(int length) {
//        StringBuffer buf = new StringBuffer();
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            // 输出字母还是数字
//            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
//            if ("char".equalsIgnoreCase(charOrNum)) {
//                // 字符串
//                // 取得大写字母还是小写字母
//                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
//                buf.append((char) (choice + random.nextInt(26)));
//            } else if ("num".equalsIgnoreCase(charOrNum)) {
//                // 数字
//                buf.append(random.nextInt(10));
//            }
//        }
//        return buf.toString();
//    }

    /**
     * 方法三
     * 生成指定长度的字母数字组合字符串
     */
    public static String getAlphanumString(int length) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = Math.round(Math.random()) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 字符串
                // 取得大写字母还是小写字母
                int choice = Math.round(Math.random()) % 2 == 0 ? 65 : 97;
                buf.append((char) (choice + Math.round(Math.random() * 25)));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                // 数字
                buf.append(Math.round(Math.random() * 9));
            }
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(getAlphanumString(6));
        }
    }

}
