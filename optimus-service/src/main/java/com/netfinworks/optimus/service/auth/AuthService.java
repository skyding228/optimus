package com.netfinworks.optimus.service.auth;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.Cipher;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.meidusa.fastjson.JSONObject;
import com.meidusa.fastmark.feature.Feature;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.domain.UserInfo;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.utils.LambdaUtil;
import com.netfinworks.util.JSONUtil;
import com.netfinworks.util.RSA;

@Service
public class AuthService {
    // 报文头中加密 key
    public static final String HEADER_CRYPTOGRAPH = "cryptograph";
    public static final String HEADER_AUTH_ID = "auth-id";

    private static Logger log = LoggerFactory.getLogger(AuthService.class);

    private static RestTemplate restTmpl = new RestTemplate();

    private static ConfigService configService;

    /**
     * 根据 授权id去获取平台编码
     *
     * @param auth_id
     * @return
     */
    public String getPlatNo(String auth_id) {
        return configService.getValue(auth_id);
    }

    @Resource
    public void setConfigService(ConfigService configService) {
        AuthService.configService = configService;
    }

    /**
     * 验证token
     *
     * @param token
     * @param platNo
     * @param url    完整地址
     * @return
     */
    public static UserInfo validateToken(TokenBean bean) {
        return LambdaUtil.function(AuthService::validate, bean, "进行token验证");
    }

    private static UserInfo validate(TokenBean bean) {
        String token = bean.getToken();
        String platNo = bean.getPlatNo();
        String url = bean.getUrl();

        Map<String, String> param = new HashMap<String, String>();
        param.put("token", token);
        UserInfo user = null;

        HttpEntity<String> formEntity = restFormEntity(platNo, param);

        String resp = restTmpl.postForObject(url, formEntity, String.class);
        log.info("调用第三方API-token验证:{}-{}", JSONUtil.toJson(formEntity), resp);
        user = JSONUtil.fromJson(resp, UserInfo.class);
        if (user != null && user.getMemberId() == null) {
            log.error("验证token失败,响应数据:{}", JSONUtil.toJson(user));
            user = null;
        }

        return user;
    }

    public static HttpEntity<String> restFormEntity(String platNo,
                                                    Map<String, String> param) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType
                .parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add(HEADER_CRYPTOGRAPH, sha1(platNo, param));
        headers.add(HEADER_AUTH_ID,
                configService.getPlatValue(platNo, ConfigService.PLAT_AUTH_ID));
        HttpEntity<String> formEntity = new HttpEntity<String>(
                JSONUtil.toJson(param), headers);
        log.info("rest-请求参数:{}", formEntity.toString());
        return formEntity;
    }

    /**
     * 对参数进行加密 返回加密后的密文
     *
     * @param platNo
     * @param params
     * @return
     */
    public static String sha1(String platNo, Object params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("plat", platNo);
        map.put("params", params);

        // 根据加密算法进行加密 比对
        String text = JSONUtil.toJson(map);
        String sha1 = DigestUtils.sha1Hex(text);
        log.info("明文:{},密文:{}", text, sha1);
        return sha1;
    }

    public static String rsa() throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA",
                new BouncyCastleProvider());

        byte[] bytes = Base64
                .getDecoder()
                .decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1gr+rIfYlaNUNLiFsK/Knb54nQrCRTRMOdujTwkpqKLo4pNYj2StmryWETeFxOCFtCt/7ixTUrU2RGGjkIOlYC3144h0dJKDtPXw9+mFyW1VwWvtfoiSUeKTEbz1tSHghEcdEvVq6qlSQukiLAEZabiwfEE30TQ6g979X6YXhnQIDAQAB");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        RSAPublicKey publicKey = (RSAPublicKey) factory.generatePublic(spec);
        System.out.println(publicKey.getPublicExponent().toString(16));
        System.out.println(publicKey.getModulus().toString(16));

        byte[] pirvateBytes = Base64
                .getDecoder()
                .decode("MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBALWCv6sh9iVo1Q0uIWwr8qdvnidCsJFNEw526NPCSmooujik1iPZK2avJYRN4XE4IW0K3/uLFNStTZEYaOQg6VgLfXjiHR0koO09fD36YXJbVXBa+1+iJJR4pMRvPW1IeCERx0S9WrqqVJC6SIsARlpuLB8QTfRNDqD3v1fpheGdAgMBAAECgYEArmr1w3zfCxOxpvitJUUV589aKl/rS7TEmyGomdQZrel1CPlczRXinsmvQ3OTLzjA5geNNCpx2eyunL7YDF+T2WgcvpPlM+TUrBnTI9gKYwLXiWPuWou8oZolHaTuQQuLfnJTZ+6cYjHA3S4xnrJaEvvs8xgy6/UAJWXUpm/dQAECQQDlW9LPOrWaaVFuZqftVTwhTjhVmBZ/AeuXcmmmF2KBGaujbXJuVHVE5rzW9/6TQoWinuK4J/UOLcFl3VTTEPgZAkEAypggJwgYq3uz0KX8YScHV/gUZa56l5gRofMrIpiek4uvt2JM1kqgIq9T9PIOnv7dz7buRk2GXi3GLgEuhDr2JQJBAJPAypZ7QMBfdnkDosyOqzTdegcR+fQJ3aZrq0m3KNr4GY0nlZ8jw4QGjMKDcjmVkhdH+dAe1Ywzx7ICmoF6HgkCQQCTo+lKiIvx7GROWahi5J5lbVTwBQcyEpBHBX8Z5z8pJ1MWwXxdbmTk4gC9MOmW1QWwqg9bDIQvfgw+2n2bv5xBAkEAhUaiDfOywrIB3VUZWTqeIlVfqXHd8a9AcellTgIjK8WIu9gNGIfkWUVoVeOq0GCFTVqwO5tlac+oeDHCCimLyQ==");
        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(pirvateBytes);
        PrivateKey privateKey = factory.generatePrivate(privateSpec);

        byte[] encode = RSA.encrypt("wch".getBytes(), privateKey);
        System.out.println(Base64.getEncoder().encodeToString(encode));
        System.out.println(new String(RSA.decrypt(encode, publicKey)));

        byte[] publicEncode = RSA.encrypt("pubic".getBytes(), publicKey);
        System.out.println(Base64.getEncoder().encodeToString(publicEncode));
        System.out.println(new String(RSA.decrypt(publicEncode, privateKey)));
        System.out
                .println(new String(
                        RSA.decrypt(
                                Hex.decode("b0677020d5fcbf78e9d97f81d9005fedc2d405590190b60b1680f4d9969b4f80fd31e100969b41e54c68de56584236a3f90770d8c0b9806baf0ce3b8a207452a0beb607d364eceb94d2d971c591a14ad49b2bcc6275a0d7e394c1fc584f452209358227aa8976035950939bc45f2321326fc7fd338433f22b62e005f34a31fe8"),
                                privateKey)));

        byte[] sign = RSA.sign("wch".getBytes(), privateKey);
        System.out.println(Base64.getEncoder().encodeToString(sign));

        System.out.println(RSA.verify("wch".getBytes(), sign, publicKey));
        return null;
    }

    public static void main(String[] args) {

        try {
            PrivateKey privateKey = RSA
                    .getPrivateKey("MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBALWCv6sh9iVo1Q0uIWwr8qdvnidCsJFNEw526NPCSmooujik1iPZK2avJYRN4XE4IW0K3/uLFNStTZEYaOQg6VgLfXjiHR0koO09fD36YXJbVXBa+1+iJJR4pMRvPW1IeCERx0S9WrqqVJC6SIsARlpuLB8QTfRNDqD3v1fpheGdAgMBAAECgYEArmr1w3zfCxOxpvitJUUV589aKl/rS7TEmyGomdQZrel1CPlczRXinsmvQ3OTLzjA5geNNCpx2eyunL7YDF+T2WgcvpPlM+TUrBnTI9gKYwLXiWPuWou8oZolHaTuQQuLfnJTZ+6cYjHA3S4xnrJaEvvs8xgy6/UAJWXUpm/dQAECQQDlW9LPOrWaaVFuZqftVTwhTjhVmBZ/AeuXcmmmF2KBGaujbXJuVHVE5rzW9/6TQoWinuK4J/UOLcFl3VTTEPgZAkEAypggJwgYq3uz0KX8YScHV/gUZa56l5gRofMrIpiek4uvt2JM1kqgIq9T9PIOnv7dz7buRk2GXi3GLgEuhDr2JQJBAJPAypZ7QMBfdnkDosyOqzTdegcR+fQJ3aZrq0m3KNr4GY0nlZ8jw4QGjMKDcjmVkhdH+dAe1Ywzx7ICmoF6HgkCQQCTo+lKiIvx7GROWahi5J5lbVTwBQcyEpBHBX8Z5z8pJ1MWwXxdbmTk4gC9MOmW1QWwqg9bDIQvfgw+2n2bv5xBAkEAhUaiDfOywrIB3VUZWTqeIlVfqXHd8a9AcellTgIjK8WIu9gNGIfkWUVoVeOq0GCFTVqwO5tlac+oeDHCCimLyQ==");
            Cipher cipher = Cipher.getInstance("RSA/NONE/NoPadding",
                    new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            System.out
                    .println(new String(
                            cipher.doFinal(Hex
                                    .decode("b0677020d5fcbf78e9d97f81d9005fedc2d405590190b60b1680f4d9969b4f80fd31e100969b41e54c68de56584236a3f90770d8c0b9806baf0ce3b8a207452a0beb607d364eceb94d2d971c591a14ad49b2bcc6275a0d7e394c1fc584f452209358227aa8976035950939bc45f2321326fc7fd338433f22b62e005f34a31fe8"))));
        } catch (Exception e1) {

            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out
                .println("1---"
                        + Base64.getEncoder()
                        .encodeToString(
                                new BigInteger(
                                        "b0677020d5fcbf78e9d97f81d9005fedc2d405590190b60b1680f4d9969b4f80fd31e100969b41e54c68de56584236a3f90770d8c0b9806baf0ce3b8a207452a0beb607d364eceb94d2d971c591a14ad49b2bcc6275a0d7e394c1fc584f452209358227aa8976035950939bc45f2321326fc7fd338433f22b62e005f34a31fe8",
                                        16).toByteArray()));
        try {
            rsa();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main3(String[] args) {
        UserInfo user = new UserInfo();
        user.setMemberId("123");
        user.setEmail("123@qq.com");

        System.out.println(JSONUtil.toJson(user));

        System.out.println(JSONUtil.toJson(JSONUtil.fromJson(
                "{\"memberId\":\"123\"}", UserInfo.class)));

        System.out.println(JSONUtil.toJson(JSONObject.parseObject(
                "{\"memberId\":\"123\",\"member\":\"123\"}", UserInfo.class,
                Feature.IgnoreNotMatch)));
    }
}
