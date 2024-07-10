package org.example.authserver.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.authserver.client.UserQueryClient;
import org.example.authserver.domain.UserDetail;
import org.example.authserver.domain.request.AuthenticateRequest;
import org.example.authserver.domain.response.AuthenticateResponse;
import org.example.authserver.service.AuthenticateService;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {

    private final PasswordEncoder passwordEncoder;
    private final UserQueryClient queryClient;
    private final ModelMapper modelMapper;

    @Override
    public WrapperResponse authenticate(AuthenticateRequest authRequest) {
        if (authRequest == null
                || authRequest.getUsername() == null
                || authRequest.getPassword() == null
                || authRequest.getUsername().isBlank() || authRequest.getPassword().isBlank()) {
            return WrapperResponse.fail(
                    "Login Fail!", HttpStatus.BAD_REQUEST
            );
        }


        UserDetail userDetail = queryClient.getUserDetailsByUsername(authRequest.getUsername());


        if (userDetail == null
                || !passwordEncoder.matches(authRequest.getPassword(), userDetail.getPassword())) {

            return WrapperResponse.fail(
                    "Username or Password Are Incorrect!", HttpStatus.BAD_REQUEST
            );
        }
        AuthenticateResponse response = new AuthenticateResponse();
        response.setToken(getToken(userDetail));

        return WrapperResponse.success(
                HttpStatus.OK, response
        );
    }

    @Override
    public ResponseEntity<?> validateToken(String token) {
        try {
            String jwt = token.substring(7);
            String userUsername = extractUsername(jwt);
            if (userUsername != null) {
                UserDetail userDetail = modelMapper
                        .map(queryClient.getUserDetailsByUsername(userUsername), UserDetail.class);

                if (userDetail != null && isTokenValid(jwt, userDetail)) {
                    UserDetail userDetailsResponse = new UserDetail(userUsername);
                    return ResponseEntity.ok(userDetailsResponse);
                }
            }
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String getToken(UserDetails userDetails) {
        return generateToken(userDetails);
    }

    private String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        String SECRET_KEY = "kVJpUBqivo2EAZHtiIUOBrN//1qdagLBiMbipktTouw=";
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
