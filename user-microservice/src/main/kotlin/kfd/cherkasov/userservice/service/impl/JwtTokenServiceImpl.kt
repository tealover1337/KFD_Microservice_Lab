package kfd.cherkasov.userservice.service.impl

import io.jsonwebtoken.*
import org.springframework.stereotype.Service
import kfd.cherkasov.userservice.config.properties.JwtProperties
import kfd.cherkasov.userservice.database.entity.RefreshTokenEntity
import kfd.cherkasov.userservice.database.repository.RefreshTokenDao
import kfd.cherkasov.userservice.service.JwtTokenService
import java.util.*
import io.jsonwebtoken.io.Decoders
import kfd.cherkasov.userservice.database.entities.User
import org.postgresql.ssl.PKCS12KeyManager
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

@Service
class JwtTokenServiceImpl(
    private val jwtProperties: JwtProperties,
    private val refreshTokenDao: kfd.cherkasov.userservice.database.repository.RefreshTokenDao
) : JwtTokenService {
    private val privateKey: PrivateKey by lazy {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.privateRsaFile.replace("\n", ""))

        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        KeyFactory.getInstance("RSA").generatePrivate(keySpec)
    }

    private val publicKey: PublicKey by lazy {

        val keyBytes = Decoders.BASE64.decode(jwtProperties.publicRsaFile.replace("\n", ""))

        val keySpec = X509EncodedKeySpec(keyBytes)
        KeyFactory.getInstance("RSA").generatePublic(keySpec)
    }


    override fun validateToken(token: String): Boolean {
        return try {
            getAllClaimsFromToken(token)
            isTokenNotExpired(token)
        } catch (ex: JwtException) {
            false
        }
    }

    override fun getUsernameFromToken(token: String): String {
        return getAllClaimsFromToken(token).subject
    }

    override fun createTokenPair(user: User): Pair<String, String> {
        val claims = mapOf("user_id" to user.id.toString())

        val accessToken = createToken(user, getAccessTokenExpirationDate(), claims)

        val refreshTokenExpiration = getRefreshTokenExpirationDate()
        val refreshToken = createToken(user, refreshTokenExpiration, claims)

        return accessToken to refreshToken
    }

    private fun createToken(user: User, expiration: Date, claims: Map<String, Any> = mapOf()): String =
        Jwts.builder()
            .claims(claims)
            .subject(user.login)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expiration)
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact()

    private fun getAccessTokenExpirationDate() =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpirationDate() =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)

    override fun getTokenExpiration(token: String): Date =
        getAllClaimsFromToken(token).expiration

    override fun isTokenNotExpired(token: String): Boolean =
        getAllClaimsFromToken(token).expiration.after(Date())

    private fun getAllClaimsFromToken(token: String): Claims =
        Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token).payload
}