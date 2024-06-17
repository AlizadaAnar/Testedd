package com.alibou.security.auth;

import com.alibou.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String fullName;
  private String phoneNumber;
  private String email;
  private String region;
  private String password;
  private Role role;
}
