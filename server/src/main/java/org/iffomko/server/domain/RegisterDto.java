package org.iffomko.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String phone;
    private String password;
}
