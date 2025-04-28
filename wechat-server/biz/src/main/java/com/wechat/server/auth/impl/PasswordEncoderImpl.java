package com.wechat.server.auth.impl;

import cn.dev33.satoken.secure.BCrypt;
import com.edu.platform.auth.PasswordEncoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Log4j2
@Service
public class PasswordEncoderImpl implements PasswordEncoder {

    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
    }

    @Override
    public boolean matches(CharSequence requestPassword, String dbPassword) {
        if (requestPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        if (dbPassword == null || dbPassword.length() == 0) {
            log.warn("Empty encoded password");
            return false;
        }
        if (!BCRYPT_PATTERN.matcher(dbPassword).matches()) {
            log.warn("Encoded password does not look like BCrypt");
            return false;
        }
        return BCrypt.checkpw(requestPassword.toString(), dbPassword);
    }
}
