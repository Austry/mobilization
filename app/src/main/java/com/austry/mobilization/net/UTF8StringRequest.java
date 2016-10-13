package com.austry.mobilization.net;


import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

//переводит полученный ответ в UTF-8 и кеширует
public class UTF8StringRequest extends StringRequest {

    private static final long CACHE_HIT_BUT_REFRESHED = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
    private static final long CACHE_EXPIRED = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely

    public UTF8StringRequest(int method, String url,
                             Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
            if (cacheEntry == null) {
                cacheEntry = new Cache.Entry();
            }

            long now = System.currentTimeMillis();
            final long softExpire = now + CACHE_HIT_BUT_REFRESHED;
            final long ttl = now + CACHE_EXPIRED;
            cacheEntry.data = response.data;
            cacheEntry.softTtl = softExpire;
            cacheEntry.ttl = ttl;
            String headerValue;
            headerValue = response.headers.get("Date");
            if (headerValue != null) {
                cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
            }
            headerValue = response.headers.get("Last-Modified");
            if (headerValue != null) {
                cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
            }
            cacheEntry.responseHeaders = response.headers;
            String utf8String = new String(response.data, "UTF-8");
            return Response.success(utf8String, cacheEntry);

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


}