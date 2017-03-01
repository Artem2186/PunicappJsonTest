package com.punicapp.testtask.utils.picasso;

import android.content.Context;
import android.net.Uri;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Prototype for current class is OkHttpDownloader
 *
 * */
public class OkHttp3Downloader implements Downloader {


    public static final class Builder {
        private Context context;
        private File cacheDir;
        private long maxSize;

        public Builder(Context context) {
            this.context = context;
            this.cacheDir = PicassoUtils.createDefaultCacheDir(context);
            this.maxSize = PicassoUtils.calculateDiskCacheSize(cacheDir);
        }

        public Builder cacheDir(File cacheDir) {
            this.cacheDir = cacheDir;
            return this;
        }

        public Builder maxSize(long maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public OkHttp3Downloader build() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(PicassoUtils.DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .readTimeout(PicassoUtils.DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .writeTimeout(PicassoUtils.DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .cache(new Cache(cacheDir, maxSize));

            OkHttp3Downloader result = new OkHttp3Downloader(builder.build());
            return result;
        }
    }


    private final OkHttpClient client;

    /**
     * Create a new downloader that uses the specified OkHttp instance. A response cache will not be
     * automatically configured.
     */
    public OkHttp3Downloader(OkHttpClient client) {
        this.client = client;
    }

    protected final OkHttpClient getClient() {
        return client;
    }

    @Override
    public Response load(Uri uri, int networkPolicy) throws IOException {
        CacheControl cacheControl = null;
        if (networkPolicy != 0) {
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                cacheControl = CacheControl.FORCE_CACHE;
            } else {
                CacheControl.Builder builder = new CacheControl.Builder();
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.noCache();
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    builder.noStore();
                }
                cacheControl = builder.build();
            }
        }

        Request.Builder builder = new Request.Builder().url(uri.toString());
        if (cacheControl != null) {
            builder.cacheControl(cacheControl);
        }

        okhttp3.Response response = client.newCall(builder.build()).execute();
        int responseCode = response.code();
        if (responseCode >= 300) {
            response.body().close();
            throw new ResponseException(responseCode + " " + response.message(), networkPolicy,
                    responseCode);
        }

        boolean fromCache = response.cacheResponse() != null;

        ResponseBody responseBody = response.body();
        return new Response(responseBody.byteStream(), fromCache, responseBody.contentLength());
    }

    @Override
    public void shutdown() {
        Cache cache = client.cache();
        if (cache != null) {
            try {
                cache.close();
            } catch (IOException ignored) {
            }
        }
    }
}
