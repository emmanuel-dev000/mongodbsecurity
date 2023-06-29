package com.pangan.mongodbsecurity.dto;

public record AuthResponseDto(String accessToken, String tokenType) {

    private AuthResponseDto(AuthResponseDtoBuilder authResponseDtoBuilder) {
        this(authResponseDtoBuilder.accessToken, authResponseDtoBuilder.tokenType);
    }

    // Source of Builder Pattern inside a record: https://stackoverflow.com/a/70466958
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
