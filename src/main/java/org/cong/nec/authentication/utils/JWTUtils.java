package org.cong.nec.authentication.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.cong.nec.authentication.enums.Role;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtils {

    public static final SecretKey TOKEN_SECRET_KEY = Jwts.SIG.HS256.key().build() ;
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String TOKEN_CLAIM_USERNAME = "username";
    public static final String TOKEN_CLAIM_TERRITORY_NUMBER = "territoryNumber";
    public static final String TOKEN_CLAIM_ROLES = "roles";
    public static final long TOKEN_EXPIRATION = 14_400_000;

    public static String generateToken(final Long userId, final String username, String territoryNumber, final Role userRole) {
        final Date now = new Date();
        final Date exp = new Date(now.getTime() + TOKEN_EXPIRATION);

        return Jwts.builder()
                .subject(userId.toString())
                .claim(TOKEN_CLAIM_USERNAME, username)
                .claim(TOKEN_CLAIM_ROLES, userRole)
                .claim(TOKEN_CLAIM_TERRITORY_NUMBER, territoryNumber)
                .issuedAt(now)
                .notBefore(now)
                .expiration(exp)
                .signWith(TOKEN_SECRET_KEY)
                .compact();
    }

    public static String generateLongTermExpiringToken(final Long userId, final String username, String territoryNumber, final Role userRole, final Long loginTime, final Date expiration) {
        Date issuedAt = new Date(loginTime);
        return Jwts.builder()
                .subject(userId.toString())
                .claim(TOKEN_CLAIM_USERNAME, username)
                .claim(TOKEN_CLAIM_ROLES, userRole)
                .claim(TOKEN_CLAIM_TERRITORY_NUMBER, territoryNumber)
                .issuedAt(issuedAt)
                .notBefore(issuedAt)
                .expiration(expiration)
                .signWith(TOKEN_SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts
                .parser()
                .verifyWith(TOKEN_SECRET_KEY)
                .build()
                .parseSignedClaims(token.replace(TOKEN_PREFIX, "").trim())
                .getPayload();
    }
}
