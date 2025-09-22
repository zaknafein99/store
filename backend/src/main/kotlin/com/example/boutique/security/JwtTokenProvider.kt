package com.example.boutique.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider {

    @Value("\${app.jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${app.jwt.expiration-in-ms}")
    private lateinit var jwtExpirationInMs: String

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))
    }

    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetails
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInMs.toLong())

        return Jwts.builder()
            .subject(userPrincipal.username)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }

    fun getUsernameFromJWT(token: String): String {
        val claims: Claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
        return claims.subject
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(authToken)
            return true
        } catch (ex: Exception) {
            // Can log different exceptions for better debugging:
            // SignatureException, MalformedJwtException, ExpiredJwtException, etc.
        }
        return false
    }
}
