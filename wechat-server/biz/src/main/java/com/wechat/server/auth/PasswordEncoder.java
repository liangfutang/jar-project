package com.wechat.server.auth;

/**
 * Created with IntelliJ IDEA.
 * 密码服务
 *
 * @author Ji MingHao
 * @since 2023-06-30 11:08
 */
public interface PasswordEncoder {

    /**
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or
     * greater hash combined with an 8-byte or greater randomly generated salt.
     */
    String encode(CharSequence rawPassword);

    /**
     * Verify the encoded password obtained from storage matches the submitted raw
     * password after it too is encoded. Returns true if the passwords match, false if
     * they do not. The stored password itself is never decoded.
     * @param requestPassword the raw password to encode and match
     * @param dbPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    boolean matches(CharSequence requestPassword, String dbPassword);
}
