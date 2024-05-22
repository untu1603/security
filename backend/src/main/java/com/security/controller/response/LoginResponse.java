package com.security.controller.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends AuthenticationResponse {
    private String name;
}
