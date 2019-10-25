package com.lzf.login;

import java.util.List;

public class ErrorBean {

    /**
     * msgtype : text
     * text : {"content":"我就是我,  @1825718XXXX 是不一样的烟火"}
     * at : {"atMobiles":["1825718XXXX"],"isAtAll":false}
     */

    private String msgtype;
    private TextBean text;
    private AtBean at;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public TextBean getText() {
        return text;
    }

    public void setText(TextBean text) {
        this.text = text;
    }

    public AtBean getAt() {
        return at;
    }

    public void setAt(AtBean at) {
        this.at = at;
    }

    public static class TextBean {
        /**
         * content : 我就是我,  @1825718XXXX 是不一样的烟火
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class AtBean {
        /**
         * atMobiles : ["1825718XXXX"]
         * isAtAll : false
         */

        private boolean isAtAll;
        private List<String> atMobiles;

        public boolean isIsAtAll() {
            return isAtAll;
        }

        public void setIsAtAll(boolean isAtAll) {
            this.isAtAll = isAtAll;
        }

        public List<String> getAtMobiles() {
            return atMobiles;
        }

        public void setAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }
    }
}
