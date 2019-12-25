package com.tomasky.departure.bo.wx.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Auther: imike
 * @Date: 2018/9/10 17:56
 * @Description:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizerInfoResponse {

    private AuthorizerInfoBean authorizer_info;
    private AuthorizationInfoBean authorization_info;

    public AuthorizerInfoBean getAuthorizer_info() {
        return authorizer_info;
    }

    public void setAuthorizer_info(AuthorizerInfoBean authorizer_info) {
        this.authorizer_info = authorizer_info;
    }

    public AuthorizationInfoBean getAuthorization_info() {
        return authorization_info;
    }

    public void setAuthorization_info(AuthorizationInfoBean authorization_info) {
        this.authorization_info = authorization_info;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AuthorizerInfoBean {

        private String nick_name;
        private String head_img;
        private String user_name;
        private String alias;
        private String qrcode_url;
        private BusinessInfoBean business_info;
        private String principal_name;
        private String signature;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getQrcode_url() {
            return qrcode_url;
        }

        public void setQrcode_url(String qrcode_url) {
            this.qrcode_url = qrcode_url;
        }

        public BusinessInfoBean getBusiness_info() {
            return business_info;
        }

        public void setBusiness_info(BusinessInfoBean business_info) {
            this.business_info = business_info;
        }

        public String getPrincipal_name() {
            return principal_name;
        }

        public void setPrincipal_name(String principal_name) {
            this.principal_name = principal_name;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class BusinessInfoBean {
            /**
             * open_pay : 0
             * open_shake : 0
             * open_scan : 0
             * open_card : 0
             * open_store : 0
             */

            private int open_pay;
            private int open_shake;
            private int open_scan;
            private int open_card;
            private int open_store;

            public int getOpen_pay() {
                return open_pay;
            }

            public void setOpen_pay(int open_pay) {
                this.open_pay = open_pay;
            }

            public int getOpen_shake() {
                return open_shake;
            }

            public void setOpen_shake(int open_shake) {
                this.open_shake = open_shake;
            }

            public int getOpen_scan() {
                return open_scan;
            }

            public void setOpen_scan(int open_scan) {
                this.open_scan = open_scan;
            }

            public int getOpen_card() {
                return open_card;
            }

            public void setOpen_card(int open_card) {
                this.open_card = open_card;
            }

            public int getOpen_store() {
                return open_store;
            }

            public void setOpen_store(int open_store) {
                this.open_store = open_store;
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AuthorizationInfoBean {

        private String authorizer_appid;

        private String mchId;

        @JsonIgnore
        private String mchKey;

        public String getAuthorizer_appid() {
            return authorizer_appid;
        }

        public void setAuthorizer_appid(String authorizer_appid) {
            this.authorizer_appid = authorizer_appid;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getMchKey() {
            return mchKey;
        }

        public void setMchKey(String mchKey) {
            this.mchKey = mchKey;
        }
    }
}
