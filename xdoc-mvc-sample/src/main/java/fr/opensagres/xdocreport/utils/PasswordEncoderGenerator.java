package fr.opensagres.xdocreport.utils;

import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderGenerator {
	private static Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
	
	public static void main(String[] args) {

//		int i = 0;
//		while (i < 10) {
			String password = "654321";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);

			System.out.println(hashedPassword);
//			i++;
//		}
			
			if (!BCRYPT_PATTERN.matcher("$2a$10$aKVttOc.561qi6ZU.P3FLOlzes.E/HIFOWq/nUtHqRc8SPpOOKRt6").matches()) {
				System.out.println("Encoded password does not look like BCrypt");

	        }

	}
}
