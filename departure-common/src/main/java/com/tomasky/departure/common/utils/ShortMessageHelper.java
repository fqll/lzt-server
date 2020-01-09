package com.tomasky.departure.common.utils;

import com.tomasky.departure.bo.message.MessageSendResult;
import com.tomasky.departure.enums.SmsChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortMessageHelper {
    private static final Logger logger = LoggerFactory.getLogger(ShortMessageHelper.class);
    private static final char[] mChars = "0123456789ABCDEF".toCharArray();
    private static final String SUCCESS = "ACCEPTD";
    private static final String SINGLE_COMMAND = "MT_REQUEST";
    private static final String MULT_COMMAND = "MULTI_MT_REQUEST";

    public static void main(String[] args) {
        List<String> phoneList = new ArrayList<>();
        phoneList.add("13689047792");
        String content = "您收到一条HR发送的离职表单，请尽快填写并提交审核：http://departure2.fanqiele.com/employee/fill_departure.html";
        new ShortMessageHelper().sendShortMessage(phoneList, content, SmsChannel.SEND_TYPE_VIP);
    }

    /**
     * 根据指定手机号码发送短信
     *
     * @param phoneNum
     * @param content
     * @param channel
     * @return
     */
    public MessageSendResult sendShortMessage(String phoneNum, String content, SmsChannel channel) {
        List<String> receivers = new ArrayList<>();
        receivers.add(phoneNum);
        return sendShortMessage(receivers, content, channel);
    }

    /**
     * 根据指定手机号码集合发送短信
     *
     * @param receivers
     * @param content
     * @param channel
     * @return
     */
    public MessageSendResult sendShortMessage(List<String> receivers, String content, SmsChannel channel) {
        Map<String, Object> params = buildParams(receivers, content, channel);
        String result = HttpClientUtil.httpPost("http://esms200.10690007.net/sms/mt", params);
        logger.info("result for sending sms :" + result);
        Map<String, String> responseMap = analyzeResponse(result);
        MessageSendResult messageSendResult = null;
        if (responseMap != null) {
            messageSendResult = new MessageSendResult();
            if (SUCCESS.equals(responseMap.get("mtstat"))) {
                logger.info(new StringBuilder().append("send sms total:").append(receivers.size()).toString());
                messageSendResult.setStatus(true);
            } else {
                logger.info("sms 发送失败:" + content);
                messageSendResult.setStatus(false);
            }
            messageSendResult.setCode(new StringBuilder().append(responseMap.get("mterrcode")).append("_").append((String) responseMap.get("mtstat")).toString());
        }
        return messageSendResult;
    }

    private Map<String, Object> buildParams(List<String> receivers, String content, SmsChannel channel) {
        Map<String, Object> params = new HashMap<>();
        switch (channel) {
            case SEND_TYPE_AUTO:
                params.put("spid", "6278");
                params.put("sppassword", "fqll6278");
                break;
            case SEND_TYPE_SALE:
                params.put("spid", "9067");
                params.put("sppassword", "fq@9067");
                break;
            case SEND_TYPE_VIP:
                params.put("spid", "9088");
                params.put("sppassword", "fqll9088");
                break;
            default:
                throw new RuntimeException("不能识别的短信通道类型");
        }

        if (receivers.size() > 1) {
            params.put("command", MULT_COMMAND);
            params.put("das", spellReceivers(receivers));
        } else {
            params.put("command", SINGLE_COMMAND);
            params.put("da", spellReceivers(receivers));
        }
        params.put("dc", "15");
        try {
            params.put("sm", str2HexStr(content));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return params;
    }

    private String spellReceivers(List<String> receivers) {
        String receiverStr = null;
        if (!CollectionUtils.isEmpty(receivers)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String receiver : receivers) {
                stringBuilder.append("86");
                stringBuilder.append(receiver);
                stringBuilder.append(",");
            }
            receiverStr = stringBuilder.toString();
            receiverStr = receiverStr.substring(0, receiverStr.length() - 1);
        }
        return receiverStr;
    }

    private Map<String, String> analyzeResponse(String responseStr) {
        Map<String, String> responseMap = null;
        if (StringUtils.isNotBlank(responseStr)) {
            responseMap = new HashMap<>();
            String[] result = responseStr.split("&");
            for (String str : result) {
                String[] param = str.split("=");
                responseMap.put(param[0], param[1]);
            }
        }
        return responseMap;
    }

    private String str2HexStr(String str) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes("GBK");

        for (int i = 0; i < bs.length; i++) {
            sb.append(mChars[((bs[i] & 0xFF) >> 4)]);
            sb.append(mChars[(bs[i] & 0xF)]);
        }
        return sb.toString().trim();
    }

}