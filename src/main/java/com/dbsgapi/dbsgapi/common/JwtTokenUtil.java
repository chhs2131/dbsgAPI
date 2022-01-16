package com.dbsgapi.dbsgapi.common;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtTokenUtil {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
}