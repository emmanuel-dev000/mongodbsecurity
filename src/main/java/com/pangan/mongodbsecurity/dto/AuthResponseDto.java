package com.pangan.mongodbsecurity.dto;

public record AuthResponseDto(String accessToken, String tokenType) {

    private AuthResponseDto(AuthResponseDtoBuilder authResponseDtoBuilder) {
        this(authResponseDtoBuilder.accessToken, authResponseDtoBuilder.tokenType);
    }

    public static class AuthResponseDtoBuilder {
        private String accessToken;
        private String tokenType = "Bearer ";

        public AuthResponseDtoBuilder() {
        }

        public AuthResponseDtoBuilder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public AuthResponseDtoBuilder setTokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public AuthResponseDto build() {
            return new AuthResponseDto(accessToken, tokenType);
        }
    }
}
