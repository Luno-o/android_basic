package com.skillbox.github.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user,repo"

    const val CLIENT_ID = "3fec507e7f3278a3d336"
    const val CLIENT_SECRET = "b9e7da4cb54fea7218bf7e372a8823a5bf715ae7"
    const val CALLBACK_URL = "skillbox://skillbox.ru/callback"
}