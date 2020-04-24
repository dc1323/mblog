package com.mtons.mblog.weixin.handler;

import com.alibaba.fastjson.JSONObject;
import com.mtons.mblog.base.utils.HttpUtils;
import com.mtons.mblog.config.Constant;
import com.mtons.mblog.web.formatter.JsonUtils;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;
import java.util.Map;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //可以选择将消息保存到本地
        }

        this.logger.info("\n收到信息内容，{}", JsonUtils.toJson(wxMessage));
        if (wxMessage.getContent().equals("文章")) {

            return null;
        }

        JSONObject json = HttpUtils.httpGet(Constant.API_XIAO_HUA.replace("#MSG#", wxMessage.getContent()));
        String content = "";
        if (null != json) {
            content = json.getString("content");
        }
        return WxMpXmlOutMessage.TEXT().content(content)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
    }
}
