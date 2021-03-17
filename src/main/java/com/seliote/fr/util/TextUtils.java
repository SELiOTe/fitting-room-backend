package com.seliote.fr.util;

import com.seliote.fr.exception.UtilException;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 文本工具类
 *
 * @author seliote
 */
@Log4j2
public class TextUtils {

    /**
     * Base 64 编码字节数组
     *
     * @param bytes 原始数据字节数组
     * @return Base 64 编码后的字节数组
     */
    @NonNull
    public static String base64Encode(@NonNull byte[] bytes) {
        var result = Base64.getEncoder().encodeToString(bytes);
        log.trace("TextUtils.base64Encode(byte[]) for: {}, result: {}", bytes, result);
        return result;
    }

    /**
     * Base 64 解码字符串
     *
     * @param base64 Base 64 编码后的字节数组
     * @return 原始数据字节数组
     */
    @NonNull
    public static byte[] base64Decode(@NonNull String base64) {
        var result = Base64.getDecoder().decode(base64);
        log.trace("TextUtils.base64Encode(String) for: {}, result: {}", base64, result);
        return result;
    }

    /**
     * 执行 SHA-1 摘要算法
     *
     * @param input 输入
     * @return 输出的 16 进制字符串
     */
    @NonNull
    public static String sha1(@NonNull byte[] input) {
        final var algorithm = "SHA-1";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException exception) {
            log.error("No algorithm: {}", algorithm);
            throw new UtilException(exception);
        }
        var output = messageDigest.digest(input);
        var integer = new BigInteger(1, output);
        var hexStr = integer.toString(16);
        log.trace("TextUtils.sha1(byte[]) for: {}, result: {}", input, hexStr);
        return hexStr;
    }


    /**
     * 执行 SHA-1 摘要算法，输入会以 UTF-8 格式编码
     *
     * @param input 输入
     * @return 输出的 16 进制字符串
     */
    @NonNull
    public static String sha1(@NonNull String input) {
        var output = TextUtils.sha1(input.getBytes(StandardCharsets.UTF_8));
        log.trace("TextUtils.sha1(String) for: {}, result: {}", input, output);
        return output;
    }
}
