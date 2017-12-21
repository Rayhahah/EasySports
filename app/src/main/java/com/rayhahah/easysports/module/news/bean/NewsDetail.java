package com.rayhahah.easysports.module.news.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a on 2017/6/22.
 */

public class NewsDetail {

    /**
     * code : 0
     * data : {"newsId":"20170607029892","title":"大宝贝怒怼老里：他迟早卷铺盖走人","abstract":"北京时间6月7日，据CBS体育报道，近日\u201c大宝贝\u201d格伦-戴维...","url":"http://nbachina.qq.com/a/20170607/029892.htm","imgurl":"http://inews.gtimg.com/newsapp_ls/0/1638703745_640330/0","imgurl1":"http://inews.gtimg.com/newsapp_ls/0/1638703745_640470/0","imgurl2":"http://inews.gtimg.com/newsapp_ls/0/1638703745_150120/0","pub_time":"2017-06-07 12:10:48","publishTime":"1496808648","currentTime":"1498100442","pub_time_new":"06-07 12:10","pub_time_detail":"2017年6月7日 12:10","atype":"0","atypeName":"文章","newsAppId":"2","source":"NBA官网","commentsNum":"18","commentId":"1973275478","content":[{"info":"北京时间6月7日，据CBS体育报道，近日\u201c大宝贝\u201d格伦-戴维斯曾在一次采访中对自己的前队友、快船后卫奥斯汀-里弗斯破口大骂，他称小里只不过是活在自己老爸庇荫下无欲无求的浪荡子。小里闻讯很快予以回击，并表示戴维斯的描述纯属捏造。然而这场骂战还远没有停息，近日戴维斯再度重申自己的说法绝对公平公正。","type":"text"},{"info":"\u201c首先，我承认自己本不应该那么说，因为我不该因某些人而让自己失态，但归根结底你都得以事实说话，\u201d戴维斯在采访中说道，\u201c如果你想以球员与专业的角度评述我，我敢说自己从没有迟到或不了解战术的情况。我是本不该那样说，但是他也不能如此振振有词。我相信他肯定不需要我的道歉。\u201d","type":"text"},{"info":"接着，戴维斯又把矛头瞄向了老里，他表示快船应该还会给道格-里弗斯一个机会。","type":"text"},{"info":"\u201c我认为他（老里）在今年倒还算安全，但我觉得他到了明年肯定就会如坐针毡，因为他正在现学现卖，\u201d戴维斯说道，\u201c布雷克（格里芬）和德安德鲁（乔丹）在他来到这里之前就已经在这儿了，所以这算是他第一次把自己的时运与这些大个子们系在一起，我认为他们（快船）还会给他一个机会，然后再让他卷铺盖走人。\u201d","type":"text"},{"info":"本赛季常规赛，奥斯汀-里弗斯总共代表快船出场74次。场均出战27.8分钟，可以拿到12分2.2篮板2.8助攻。","type":"text"}],"targetId":"1973275478","isDisabled":"0","cmsTags":"","tag_key":"","cmsColumn":"","cmsSite":"chinanba","shareUrl":"","dataFrom":"net","upNum":"28","isCollect":"-1"}
     * version : 79136f6ca1d71940551376fa33713d61
     */

    private int code;
    private DataBean data;
    private String version;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class DataBean {
        /**
         * newsId : 20170607029892
         * title : 大宝贝怒怼老里：他迟早卷铺盖走人
         * abstract : 北京时间6月7日，据CBS体育报道，近日“大宝贝”格伦-戴维...
         * url : http://nbachina.qq.com/a/20170607/029892.htm
         * imgurl : http://inews.gtimg.com/newsapp_ls/0/1638703745_640330/0
         * imgurl1 : http://inews.gtimg.com/newsapp_ls/0/1638703745_640470/0
         * imgurl2 : http://inews.gtimg.com/newsapp_ls/0/1638703745_150120/0
         * pub_time : 2017-06-07 12:10:48
         * publishTime : 1496808648
         * currentTime : 1498100442
         * pub_time_new : 06-07 12:10
         * pub_time_detail : 2017年6月7日 12:10
         * atype : 0
         * atypeName : 文章
         * newsAppId : 2
         * source : NBA官网
         * commentsNum : 18
         * commentId : 1973275478
         * content : [{"info":"北京时间6月7日，据CBS体育报道，近日\u201c大宝贝\u201d格伦-戴维斯曾在一次采访中对自己的前队友、快船后卫奥斯汀-里弗斯破口大骂，他称小里只不过是活在自己老爸庇荫下无欲无求的浪荡子。小里闻讯很快予以回击，并表示戴维斯的描述纯属捏造。然而这场骂战还远没有停息，近日戴维斯再度重申自己的说法绝对公平公正。","type":"text"},{"info":"\u201c首先，我承认自己本不应该那么说，因为我不该因某些人而让自己失态，但归根结底你都得以事实说话，\u201d戴维斯在采访中说道，\u201c如果你想以球员与专业的角度评述我，我敢说自己从没有迟到或不了解战术的情况。我是本不该那样说，但是他也不能如此振振有词。我相信他肯定不需要我的道歉。\u201d","type":"text"},{"info":"接着，戴维斯又把矛头瞄向了老里，他表示快船应该还会给道格-里弗斯一个机会。","type":"text"},{"info":"\u201c我认为他（老里）在今年倒还算安全，但我觉得他到了明年肯定就会如坐针毡，因为他正在现学现卖，\u201d戴维斯说道，\u201c布雷克（格里芬）和德安德鲁（乔丹）在他来到这里之前就已经在这儿了，所以这算是他第一次把自己的时运与这些大个子们系在一起，我认为他们（快船）还会给他一个机会，然后再让他卷铺盖走人。\u201d","type":"text"},{"info":"本赛季常规赛，奥斯汀-里弗斯总共代表快船出场74次。场均出战27.8分钟，可以拿到12分2.2篮板2.8助攻。","type":"text"}]
         * targetId : 1973275478
         * isDisabled : 0
         * cmsTags :
         * tag_key :
         * cmsColumn :
         * cmsSite : chinanba
         * shareUrl :
         * dataFrom : net
         * upNum : 28
         * isCollect : -1
         */

        private String newsId;
        private String title;
        @SerializedName("abstract")
        private String abstractX;
        private String url;
        private String imgurl;
        private String imgurl1;
        private String imgurl2;
        private String pub_time;
        private String publishTime;
        private String currentTime;
        private String pub_time_new;
        private String pub_time_detail;
        private String atype;
        private String atypeName;
        private String newsAppId;
        private String source;
        private String commentsNum;
        private String commentId;
        private String targetId;
        private String isDisabled;
        private String tag_key;
        private String cmsColumn;
        private String cmsSite;
        private String shareUrl;
        private String dataFrom;
        private String upNum;
        private String isCollect;
        private List<ContentBean> content;

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getImgurl1() {
            return imgurl1;
        }

        public void setImgurl1(String imgurl1) {
            this.imgurl1 = imgurl1;
        }

        public String getImgurl2() {
            return imgurl2;
        }

        public void setImgurl2(String imgurl2) {
            this.imgurl2 = imgurl2;
        }

        public String getPub_time() {
            return pub_time;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(String currentTime) {
            this.currentTime = currentTime;
        }

        public String getPub_time_new() {
            return pub_time_new;
        }

        public void setPub_time_new(String pub_time_new) {
            this.pub_time_new = pub_time_new;
        }

        public String getPub_time_detail() {
            return pub_time_detail;
        }

        public void setPub_time_detail(String pub_time_detail) {
            this.pub_time_detail = pub_time_detail;
        }

        public String getAtype() {
            return atype;
        }

        public void setAtype(String atype) {
            this.atype = atype;
        }

        public String getAtypeName() {
            return atypeName;
        }

        public void setAtypeName(String atypeName) {
            this.atypeName = atypeName;
        }

        public String getNewsAppId() {
            return newsAppId;
        }

        public void setNewsAppId(String newsAppId) {
            this.newsAppId = newsAppId;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getCommentsNum() {
            return commentsNum;
        }

        public void setCommentsNum(String commentsNum) {
            this.commentsNum = commentsNum;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getIsDisabled() {
            return isDisabled;
        }

        public void setIsDisabled(String isDisabled) {
            this.isDisabled = isDisabled;
        }

        public String getTag_key() {
            return tag_key;
        }

        public void setTag_key(String tag_key) {
            this.tag_key = tag_key;
        }

        public String getCmsColumn() {
            return cmsColumn;
        }

        public void setCmsColumn(String cmsColumn) {
            this.cmsColumn = cmsColumn;
        }

        public String getCmsSite() {
            return cmsSite;
        }

        public void setCmsSite(String cmsSite) {
            this.cmsSite = cmsSite;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getDataFrom() {
            return dataFrom;
        }

        public void setDataFrom(String dataFrom) {
            this.dataFrom = dataFrom;
        }

        public String getUpNum() {
            return upNum;
        }

        public void setUpNum(String upNum) {
            this.upNum = upNum;
        }

        public String getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(String isCollect) {
            this.isCollect = isCollect;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * info : 北京时间6月7日，据CBS体育报道，近日“大宝贝”格伦-戴维斯曾在一次采访中对自己的前队友、快船后卫奥斯汀-里弗斯破口大骂，他称小里只不过是活在自己老爸庇荫下无欲无求的浪荡子。小里闻讯很快予以回击，并表示戴维斯的描述纯属捏造。然而这场骂战还远没有停息，近日戴维斯再度重申自己的说法绝对公平公正。
             * type : text
             */

            private String info;
            private String type;

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
