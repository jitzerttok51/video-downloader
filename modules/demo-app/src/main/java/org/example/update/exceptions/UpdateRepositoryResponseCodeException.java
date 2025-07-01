package org.example.update.exceptions;

import okhttp3.Response;

import java.io.IOException;

public class UpdateRepositoryResponseCodeException extends UpdateRepositoryException {

    private final int code;

    private final String responseMessage;

    private final String body;

    public UpdateRepositoryResponseCodeException(String preface, int code, String responseMessage, String body) {
        super(constructMessage(preface, code, responseMessage, body));
        this.code = code;
        this.responseMessage = responseMessage;
        this.body = body;
    }

    public UpdateRepositoryResponseCodeException(String preface, Response response) {
        super(constructMessage(preface, response));
        this.code = response.code();
        this.responseMessage = response.message();
        this.body = parseBody(response);
    }

    private static String constructMessage(String preface, int code, String responseMessage, String body) {
        return preface+" ("+code+")"+responseMessage+"\nBody: "+body.trim();
    }

    private static String constructMessage(String preface, Response response) {
        return constructMessage(preface,
                response.code(),
                response.message(),
                parseBody(response));
    }

    private static String parseBody(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            return e.getLocalizedMessage();
        }
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
