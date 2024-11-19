package com.codingland.security.oauth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "카카오 로그인 요청")
public record KakaoLoginRequest(
        @NotBlank(message = "Access_Token 은 필수입니다.")
        @Schema(description = "Access 토큰", example = "eyJraWQiOiI5ZjI1MmRhZGQ1ZjIzM2Y5M2QyZmE1MjhkMTJmZWEiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI1YWM4M2E1YmVjZmUwOWU5MjE4Njk3NDI5MzJjMmU3ZiIsInN1YiI6IjMyNDM1MjIyMDciLCJhdXRoX3RpbWUiOjE3MDM2NTc1MzQsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwiZXhwIjoxNzAzNzAwNzM0LCJpYXQiOjE3MDM2NTc1MzR9.ipmJnjEkrvPQMdI1MfyPWu7X6tfMbDoV5g1Vq2bkT1bRhEfX-IzmrqfVabgLM_3x-9jyw7idn6qIdccsObMCpRuWw1kZPae5kTColr2vPWxyzMl2oBwSrS6lWx9tPYmBrthhQGZkO3eXW9ZsIMOixBkWFECKw3RPlylCPI-DNfgVBLEeg8gH7w40NuHbrcACC57Pm2AQGFT8hpv5XWZDLMG8hry2vSGcjbg51k74mLwJIdIxBNa1ZJjYY64rEI9VMUOVziX1HLaDeNIHQXIWbQSGRuF3Ko70O8_7gY90Kab9ccjW5576cCkDD22X8mdNZ0L6Co3a_Ktqj0vhvE2BZA", defaultValue = "eyJraWQiOiI5ZjI1MmRhZGQ1ZjIzM2Y5M2QyZmE1MjhkMTJmZWEiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI1YWM4M2E1YmVjZmUwOWU5MjE4Njk3NDI5MzJjMmU3ZiIsInN1YiI6IjMyNDM1MjIyMDciLCJhdXRoX3RpbWUiOjE3MDM2NTc1MzQsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwiZXhwIjoxNzAzNzAwNzM0LCJpYXQiOjE3MDM2NTc1MzR9.ipmJnjEkrvPQMdI1MfyPWu7X6tfMbDoV5g1Vq2bkT1bRhEfX-IzmrqfVabgLM_3x-9jyw7idn6qIdccsObMCpRuWw1kZPae5kTColr2vPWxyzMl2oBwSrS6lWx9tPYmBrthhQGZkO3eXW9ZsIMOixBkWFECKw3RPlylCPI-DNfgVBLEeg8gH7w40NuHbrcACC57Pm2AQGFT8hpv5XWZDLMG8hry2vSGcjbg51k74mLwJIdIxBNa1ZJjYY64rEI9VMUOVziX1HLaDeNIHQXIWbQSGRuF3Ko70O8_7gY90Kab9ccjW5576cCkDD22X8mdNZ0L6Co3a_Ktqj0vhvE2BZA")
        String idToken
) {
}
