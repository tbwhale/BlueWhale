package com.bluewhale.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt验证
 *
 * @author curtin 2020/3/21 6:35 PM
 */
public class JwtUtils {

    //对token进行签名和验签时需要用到
    //jwt签发者
    private static final String TOKEN_ISSUER = "BlueWhaleCore";
    //jwt请求者
    private static final String TOKEN_AUDIENCE = "BlueWhaleWeb";
    //签名密钥
    private static final String KEY_STRING = "BlueWhaleCoreGateway";

    //设置过期时间 1天=24小时，1小时=3600秒，1秒=1000毫秒，1天=24×3600×1000=86400000毫秒
    private static final long TTL_MILLIS = 1000 * 3600 * 24;

    /**
     * 签发JWT
     *
     * @param id id信息
     * @param subject 可以是JSON数据 尽可能少
     * @return String
     */
    public static String generateToken(String id, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + TTL_MILLIS;
        Date expDate = new Date(expMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(id) //jwt的唯一身份表示，主要用来作为一次性token，从而回避重放攻击
                .setSubject(subject)   // 主题 jwt所面向的用户
                .setAudience(TOKEN_AUDIENCE) //用户 接收jwt的一方
                .setIssuer(TOKEN_ISSUER)     // jwt签发者
                .setIssuedAt(now)      // jwt的签发时间
                .setExpiration(expDate) //jwt的过期时间，这个时间必须要大于签发时间
                .signWith(SignatureAlgorithm.HS256, KEY_STRING); // 签名算法以及密匙
        return builder.compact();
    }

    /**
     * 验证JWT
     * validateJWT
     *
     * @param jwtStr Token字符串
     * @return Map&lt;String,Object&gt
     */
    public static Map<String, Object> decodeToken(String jwtStr) {
        Map<String, Object> rstMap = new HashMap<>();
        Boolean statusFlag;
        String msg = "Your token verification failed ";
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            statusFlag = Boolean.TRUE;
            msg = "Succ";
        } catch (ExpiredJwtException e) {
            statusFlag = Boolean.FALSE;
            //你的令牌已过期
//            msg = "[TOKEN-FAIL] Your token was Expired. \n\r" + CommonsUtil.getExceptionStackMsg(e);
            msg = "[TOKEN-FAIL] Your token was Expired.";
        } catch (UnsupportedJwtException e) {
            statusFlag = Boolean.FALSE;
            //不支持您的令牌
//            msg = "[TOKEN-FAIL] Your token was Unsupported. \n\r" + CommonsUtil.getExceptionStackMsg(e);
            msg = "[TOKEN-FAIL] Your token was Unsupported.";

        } catch (MalformedJwtException e) {
            statusFlag = Boolean.FALSE;
            //令牌格式不正确
//            msg = "[TOKEN-FAIL] Your token was Malformed. \n\r" + CommonsUtil.getExceptionStackMsg(e);
            msg = "[TOKEN-FAIL] Your token was Malformed.";
        } catch (SignatureException e) {
            statusFlag = Boolean.FALSE;
            //令牌的签名未通过验证
//            msg = "[TOKEN-FAIL] The Signature of Your token was not pass through verify. \n\r" + CommonsUtil.getExceptionStackMsg(e);
            msg = "[TOKEN-FAIL] The Signature of Your token was not pass through verify.";
        } catch (IllegalArgumentException e) {
            statusFlag = Boolean.FALSE;
            //令牌格式不正确
//            msg = "[TOKEN-FAIL] Your token was not in correct format:[" + jwtStr + "]. \n\r" + CommonsUtil.getExceptionStackMsg(e);
            msg = "[TOKEN-FAIL] Your token was not in correct format:[" + jwtStr + "] .";
        } catch (Exception e) {
            statusFlag = Boolean.FALSE;
//            msg = "[TOKEN-FAIL] Found Errer when decode token. \n\r" + CommonsUtil.getExceptionStackMsg(e);
            msg = "[TOKEN-FAIL] Found Errer when decode token.";
        }
        rstMap.put("status", statusFlag);
        rstMap.put("msg", msg);
        rstMap.put("body", claims);
        return rstMap;
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt Token字符串
     * @return Claims
     * @throws Exception 异常信息
     */
    private static Claims parseJWT(String jwt) throws Exception {
        return Jwts.parser()
                .setSigningKey(KEY_STRING)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
