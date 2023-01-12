import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithmSpi
import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureBaseRSA.SignatureRSASHA256
import io.ktor.util.*
import sun.security.util.Password
import java.nio.charset.Charset
import java.security.Signature
import java.time.Instant
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private const val SECRETE_KEY="123456"
private const val ALGIRITHM="hmacSHA1"


private val HASH_KEY= hex(SECRETE_KEY)
private val HMAC_KEY=SecretKeySpec(HASH_KEY,ALGIRITHM)

fun hash(password: String):String{


    val hmac= Mac.getInstance(ALGIRITHM)
    hmac.init(HMAC_KEY)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}

