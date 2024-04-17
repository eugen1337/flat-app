package back.infrastructure.tokenManager;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TokenManager implements ITokenManager {
    private static final byte[] secretBytes = "SecretKeySecretKeySecretKeySecretKey".getBytes();
    private static final SecretKey secretKey = new SecretKeySpec(secretBytes, 0, secretBytes.length, "HmacSHA256");

    @Override
    public String generateToken(String login, String password) {
        return Jwts.builder()
                .subject(login)
                .claim("password", password)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public Map<String, String> getTokenInfo(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        String login = (String) claims.get("sub");
        String password = (String) claims.get("password");

        Map<String, String> map = new HashMap<>();
        map.put("login", login);
        map.put("pasword", password);

        return map;
    }

}
