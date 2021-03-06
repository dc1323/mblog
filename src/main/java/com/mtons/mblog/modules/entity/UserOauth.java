/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 第三方开发授权登录
 */
@Entity
@Table(name = "mto_user_oauth")
@Data
public class UserOauth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 系统中的用户ID
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * 认证类型：QQ、新浪
     */
    @Column(name = "oauth_type")
    private int oauthType;

    /**
     * 对应第三方用户ID
     */
    @Column(name = "oauth_user_id", length = 128)
    private String oauthUserId;

    /**
     * 第三方返回的code
     */
    @Column(name = "oauth_code", length = 128)
    private String oauthCode;

    /**
     * 访问令牌
     */
    @Column(name = "access_token", length = 128)
    private String accessToken;

    @Column(name = "expire_in", length = 128)
    private String expireIn;

    @Column(name = "refresh_token", length = 128)
    private String refreshToken;
}
