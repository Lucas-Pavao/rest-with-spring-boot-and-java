package br.com.lucas.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication()
public class RestWithSpringBootAndJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestWithSpringBootAndJavaApplication.class, args);


		 Map<String, PasswordEncoder> encoders = new HashMap<>();

		 encoders.put("pbkdf2", new Pbkdf2PasswordEncoder(	"secret", // Secret
				 16,       // Salt length
				 185000,   // Iterations
				 Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256 // SecretKeyFactoryAlgorithm
		 ));
		 DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		 passwordEncoder.setDefaultPasswordEncoderForMatches(new Pbkdf2PasswordEncoder(	"secret", // Secret
				 16,       // Salt length
				 185000,   // Iterations
				 Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256 // SecretKeyFactoryAlgorithm
		 ));

		 String result = passwordEncoder.encode("lucas123");
		 System.out.println("My hash " + result);

	}


}
