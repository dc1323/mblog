package com.mtons.mblog.weixin.handler;

import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.service.UserService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class UnsubscribeHandler extends AbstractHandler {
    @Autowired
    private UserService userService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        UserVO userVO = userService.getByUsername(openId);
        if (null != userVO) {
            userVO.setStatus(1);
            userService.update(userVO);
        }
        return null;
    }

}
