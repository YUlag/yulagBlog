package org.example;

import org.junit.Test;

public class password {
    @Test
    public void test(){
        String password = "123456";
        String encode = org.springframework.security.crypto.bcrypt.BCrypt.hashpw(password, org.springframework.security.crypto.bcrypt.BCrypt.gensalt());
        System.out.println(encode);
    }
}
