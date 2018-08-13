package com.haxwell.apps.quizki.controllers;

import java.io.IOException;	
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haxwell.apps.quizki.entities.User;
import com.haxwell.apps.quizki.repositories.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value = { "/api/verifyCredentials" })
public class VerifyCredentialsController {

	private final UserRepository ur;
	
	@Autowired
	VerifyCredentialsController(UserRepository ur) {
		this.ur = ur;
	}

	/**
	 * Given the Authorization header that CORS (see corsFilter.java) would expect,
	 * returns the ID of the user belonging to the base64-encoded user and password.
	 * 
	 * The client calls this to do a pseudo-login. They don't actually log in, but
	 * before letting the user do other stuff in the app, they have to get a valid
	 * response from here, or in other words, a valid username and password.
	 * 
	 * Regardless, if they were to just call an api without checking here first, 
	 * they would have to have a valid password anyway. Security is enabled in 
	 * Spring that way.
	 * 
	 * SO, this is basically an echo to the login page of the client, to say, yes,
	 * dear caller, you may continue to expect that I (the server) will respond if
	 * you pass me said username and password.
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public User method1(HttpServletRequest request, Model model) throws Exception {
		String[] arr = extractAndDecodeHeader(request.getHeader("Authorization"));
		
		Optional<User> u = ur.findByName(arr[0]);
		User user = null;
		
		if (u.isPresent()) {
			user = u.get();
			user.password = "";
//			user.name = "";
		}
		
		return user != null ? user : null;
	}
	
	/**
	 * Decodes the header into a username and password.
	 * 
	 * Very Inspired by Spring Security's BasicAuthenticationFilter.
	 *
	 * @throws BadCredentialsException if the Basic header is not present or is not valid
	 * Base64
	 */
	private String[] extractAndDecodeHeader(String header)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded);

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}
	
}