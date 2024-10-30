package br.com.lucas.demo.services;

import br.com.lucas.demo.data.vo.v1.security.jwt.AccountCredentialsVO;
import br.com.lucas.demo.data.vo.v1.security.jwt.TokenVO;
import br.com.lucas.demo.repositories.UserRepository;
import br.com.lucas.demo.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<TokenVO> signin(AccountCredentialsVO data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            logger.info("Trying to authenticate user: {}", username);

            var user = repository.findByUserName(username);
            if (user == null) {
                logger.error("Username {} not found", username);
                throw new UsernameNotFoundException("Username " + username + " not found");
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                logger.error("Invalid password for username {}", username);
                throw new BadCredentialsException("Invalid username/password supplied");
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());

            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            logger.error("Authentication failed for username {}: {}", data.getUsername(), e.getMessage());
            throw new BadCredentialsException("Invalid username/password supplied", e);
        }
    }
    public ResponseEntity<TokenVO> refreshToken(String username, String refreshtoken) {

            var user = repository.findByUserName(username);
            var tokenResponse = new TokenVO();

        if(user != null) {
             tokenResponse = tokenProvider.refreshToken(refreshtoken);
        } else {
          throw new UsernameNotFoundException("Username" + username + "not found");
        }
            return ResponseEntity.ok(tokenResponse);

    }
}
