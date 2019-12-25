package com.tomasky.departure.common.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tomasky.cache.api.Cache;
import com.tomasky.departure.bo.wx.response.AuthorizerInfoResponse;
import com.tomasky.departure.cons.Constants;
import com.tomasky.departure.enums.MiniAppAuthorizationCodeEnum;
import com.tomasky.departure.common.spring.SpringContextHolder;
import com.tomasky.departure.vo.QrCodeVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @Author momo
 * @Date 2018/9/5 17:26
 */
public class CacheUtil {

    private static Cache cache;

    static {
        cache = SpringContextHolder.getBean("cache");
    }

    /**
     * 置入缓存 expire (单位：秒)
     *
     * @param key
     * @param expire
     * @param content
     * @return
     */
    public static boolean set(String key, Object content, int expire) {
        boolean result = false;
        if (StringUtils.isBlank(key) || null == content) {
            LogUtil.error(CacheUtil.class, "缓存的key以及value不能为空！");
            return result;
        }
        try {
            if (content instanceof String) {
                result = cache.put(Constants.CACHE_SYS_TAG + key, content.toString(), expire);
            } else {
                result = cache.put(Constants.CACHE_SYS_TAG + key, JSON.toJSONString(content), expire);
            }
        } catch (Exception e) {
            LogUtil.error(CacheUtil.class, "缓存Key:" + key + "对应的对象出错：" + JSON.toJSONString(content), e);
        }
        return result;
    }

    /**
     * 置入缓存,默认有效时间为30天
     *
     * @param key
     * @param obj
     * @return
     */
    public static boolean set(String key, Object obj) {
        return set(key, obj, Constants.CACHE_MAX_AGE);
    }

    /**
     * 根据key获取缓存中的字符串
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        String content = null;
        if (StringUtils.isBlank(key)) {
            return content;
        }
        try {
            content = (String) cache.get(Constants.CACHE_SYS_TAG + key);
        } catch (Exception e) {
            LogUtil.error(CacheUtil.class, "获取Key:" + key + "的缓存对象出错！", e);
        }
        return content;
    }

    /**
     * 根据key获取缓存中的对象
     *
     * @param key
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> T get(String key, Class<T> cla) {
        String jsonStr = get(key);
        return Optional.ofNullable(jsonStr).map(r -> JSON.parseObject(jsonStr, cla)).orElse(null);
    }

    /**
     * 根据key删除缓存
     *
     * @param key
     * @return
     */
    public static boolean del(String key) {
        return cache.del(key);
    }

    /**
     * 根据微信原始ID  获取调用接口的token
     *
     * @param weiXinId
     * @return
     */
    public static String getWeiXinToken(String weiXinId) {
        return get(Constants.CACHE_WEIXIN_TOKEN + weiXinId);
    }

    public static void setWeiXinToken(String weiXinId, String token, Integer time) {
        set(Constants.CACHE_WEIXIN_TOKEN + weiXinId, token, time);
    }

    /**
     * 根据微信原始ID  获取调用接口的JsTicket
     *
     * @param weiXinId
     * @return
     */
    public static String getJsTicket(String weiXinId) {
        return get(Constants.CACHE_WEIXIN_JSAPI + weiXinId);
    }

    public static void setJsTicket(String weiXinId, String token, Integer time) {
        set(Constants.CACHE_WEIXIN_JSAPI + weiXinId, token, time);
    }

    /**
     * 根据微信原始ID与pms用户id  获取对应的二维码实体
     *
     * @param adminId
     * @param weiXinId
     * @return
     */
    public static QrCodeVo getQrCode(int adminId, String weiXinId) {
        String result = get(Constants.CACHE_WEIXIN_QRCODE + weiXinId + "_" + adminId);
        return (StringUtils.isBlank(result)) ? null : JSON.parseObject(result, QrCodeVo.class);
    }

    public static void setQrCode(String weiXinId, int adminId, String result) {
        set(Constants.CACHE_WEIXIN_QRCODE + weiXinId + "_" + adminId, result);
    }

    /**
     * 根据第三方平台的appId获取相应类型的授权token
     *
     * @param type
     * @param appId
     * @return
     */
    public static String getToken(String type, String appId) {
        return get(Constants.CACHE_MINIAPP_PREFIX + type + "_" + appId);
    }

    /**
     * 缓存对应类型的授权token
     *
     * @param type
     * @param appId
     * @param token
     * @param time
     */
    public static void setToken(String type, String appId, String token, Integer time) {
        set(Constants.CACHE_MINIAPP_PREFIX + type + "_" + appId, token, time);
        LogUtil.info(CacheUtil.class, "缓存" + type + "成功", "key:" + Constants.CACHE_SYS_TAG + Constants.CACHE_MINIAPP_PREFIX
                + type + "_" + appId + ";value:" + token + ";time:" + time);
    }

    /**
     * 根据授权方原始id获取授权方对象
     *
     * @param userName
     * @return
     */
    public static AuthorizerInfoResponse getAuthorizerInfoByUserName(String userName) {
        return get(Constants.CACHE_MINIAPP_PREFIX + MiniAppAuthorizationCodeEnum.AUTHORIZER_USER_INFO.getCode() + "_" + userName, AuthorizerInfoResponse.class);
    }

    /**
     * 根据授权方appId获取授权方对象
     *
     * @param authorizerAppid
     * @return
     */
    public static AuthorizerInfoResponse getAuthorizerInfoByAppId(String authorizerAppid) {
        return get(Constants.CACHE_MINIAPP_PREFIX + MiniAppAuthorizationCodeEnum.AUTHORIZER_USER_INFO.getCode() + "_" + authorizerAppid, AuthorizerInfoResponse.class);
    }

    /**
     * 缓存授权方对象
     *
     * @param userNameOrAppId
     * @param authorizerInfo
     */
    public static void setAuthorizerInfo(String userNameOrAppId, AuthorizerInfoResponse authorizerInfo) {
        set(Constants.CACHE_MINIAPP_PREFIX + MiniAppAuthorizationCodeEnum.AUTHORIZER_USER_INFO.getCode() + "_" + userNameOrAppId, authorizerInfo);
        LogUtil.info(CacheUtil.class, "缓存" + userNameOrAppId + "对应的授权方信息成功", "key:" + Constants.CACHE_SYS_TAG +
                Constants.CACHE_MINIAPP_PREFIX + MiniAppAuthorizationCodeEnum.AUTHORIZER_USER_INFO.getCode() + "_" + userNameOrAppId);
    }

    /**
     * 获取所有缓存了刷新令牌的授权方appid
     *
     * @return
     */
    public static List<String> getAuthorizerAppIdByKeysOfRefreshTokenPrefix() {
        List<String> authorizerAppids = Lists.newArrayList();
        String prefix = Constants.CACHE_SYS_TAG + Constants.CACHE_MINIAPP_PREFIX + MiniAppAuthorizationCodeEnum.AUTHORIZER_REFRESH_TOKEN.getCode() + "_";
        Set<String> sets = cache.keysByPrefix(prefix);
        if (sets.isEmpty()) {
            return authorizerAppids;
        }
        for (String s : sets) {
            authorizerAppids.add(s.substring(s.lastIndexOf("_") + 1));
        }
        return authorizerAppids;
    }



}
