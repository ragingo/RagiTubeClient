package com.ragingo.ragitube.models.services.youtube

import android.content.Context
import android.content.pm.PackageManager
import com.ragingo.ragitube.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class YouTubeApiInterceptor(
    private val context: Context
) : Interceptor {
    private companion object {
        val lock = ReentrantLock()
        var packageFingerprint: String = ""

        fun isYouTube(url: HttpUrl): Boolean {
            if (url.host != "www.googleapis.com") {
                return false
            }
            val segments = url.pathSegments
            if (segments.size < 3) {
                return false
            }
            return segments[0] == "youtube" && segments[1] == "v3"
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        if (!isYouTube(req.url)) {
            return chain.proceed(req)
        }
        val newReq =
            req.newBuilder()
                .addHeader(
                    "X-Android-Package",
                    BuildConfig.APPLICATION_ID
                )
                .addHeader("X-Android-Cert", getSha1())
                .build()
        return chain.proceed(newReq)
    }

    private fun getSha1(): String {
        lock.withLock {
            if (packageFingerprint.isNotEmpty()) {
                return packageFingerprint
            }
            // TODO: 直す
            val signatures =
                context.packageManager
                    .getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES)
                    .signatures
            if (signatures.isEmpty()) {
                return ""
            }
            val md = MessageDigest.getInstance("SHA-1")
            md.update(signatures.first().toByteArray())
            packageFingerprint =
                md.digest().map { String.format("%02X", it) }.reduce { a, b -> "${a}${b}" }
            return packageFingerprint
        }
    }
}
