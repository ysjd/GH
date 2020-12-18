package com.cslx.greenheart.net

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

class SSLSocketClient {
    companion object{
        fun getSSLSocketFactory() : SSLSocketFactory{
            try {
                var sslContext : SSLContext = SSLContext.getInstance("SSL")
                sslContext.init(null, getTrustManager(), SecureRandom())
                return sslContext.getSocketFactory()
            }catch (e : Exception){
                throw RuntimeException(e)
            }
        }

        fun getTrustManager() : Array<TrustManager>{
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls<X509Certificate>(0)
                }
            })
            return trustAllCerts
        }

        fun getHostnameVerifier() : HostnameVerifier {
            return object:HostnameVerifier {
                override fun verify(s:String, sslSession:SSLSession):Boolean {
                    return true
                }
            }
        }
    }
}