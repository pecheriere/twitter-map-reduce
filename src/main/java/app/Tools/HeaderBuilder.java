package main.java.app.Tools;

/*
 * Copyright 2007 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.scribe.model.OAuthRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HeaderBuilder {

    public static final String ENCODING = "UTF-8";

    /**
     * Miscellaneous constants, methods and types.
     * From http://oauth.googlecode.com/svn/code/java/core/commons/src/main/java/net/oauth/OAuth.java
     *
     * @author John Kristian
     */
    public static String percentEncode(String s) {
        if (s == null) {
            return "";
        }
        try {
            return URLEncoder.encode(s, ENCODING)
                    // OAuth encodes some characters differently:
                    .replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~");
            // This could be done faster with more hand-crafted code.
        } catch (UnsupportedEncodingException wow) {
            throw new RuntimeException(wow.getMessage(), wow);
        }
    }

    public static String createPair(String key, String value) {
        String result = "";

        result += percentEncode(key);
        result += "=";
        result += "\"";
        result += percentEncode(value);
        result += "\"";

        return result;
    }

    public static String getOAuthHeader(OAuthRequest request) {
        String headers = "OAuth ";
        headers += createPair("oauth_consumer_key", request.getOauthParameters().get("oauth_consumer_key")) + ", ";
        headers += createPair("oauth_nonce", request.getOauthParameters().get("oauth_nonce")) + ", ";
        headers += createPair("oauth_signature", request.getOauthParameters().get("oauth_signature")) + ", ";
        headers += createPair("oauth_signature_method", request.getOauthParameters().get("oauth_signature_method")) + ", ";
        headers += createPair("oauth_timestamp", request.getOauthParameters().get("oauth_timestamp")) + ", ";
        headers += createPair("oauth_token", request.getOauthParameters().get("oauth_token")) + ", ";
        headers += createPair("oauth_version", request.getOauthParameters().get("oauth_version"));

        return headers;
    }

}