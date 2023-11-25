package com.alliance.springsecurity.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/auth")
public class AuthenticationController {

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
    @RequestBody RegisterRequest request
  ){
    //
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthentcationResonse> register(
    @RequestBody AuthenticationRequest request
  ){
    //
  }

}
