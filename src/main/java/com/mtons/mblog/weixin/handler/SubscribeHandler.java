package com.mtons.mblog.weixin.handler;

import java.util.Map;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.utils.MD5;
import com.mtons.mblog.config.Constant;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.weixin.builder.TextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Component
public class SubscribeHandler extends AbstractHandler {
    @Autowired
    private UserService userService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        try {
            WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                // 可以添加关注用户到本地数据库
                UserVO user = new UserVO();
                user.setUsername(userWxInfo.getOpenId());
                user.setAvatar(userWxInfo.getHeadImgUrl());
                user.setPassword(MD5.md5(Constant.DEFAULT_PASSWORD));
                user.setName(userWxInfo.getNickname());
                user.setStatus(0);
                userService.register(user);
            }
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                this.logger.info("该公众号没有获取用户信息权限！");
            }
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
        throws Exception {
        return null;
    }
}
