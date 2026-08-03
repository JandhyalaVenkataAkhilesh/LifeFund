package com.akhilesh.LifeFund.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    private String name;

    private String email;

    private String phoneNumber;

    private String password;

}