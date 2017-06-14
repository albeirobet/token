package co.runcode.token.jwt;

import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;

import io.jsonwebtoken.*;
import java.util.Date;

public class Test {

	@Inject
	private KeyGenerator keyGenerator;

	// Sample method to construct a JWT
	private static String createJWT(String subject) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("albeiro");
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		//JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer).signWith(signatureAlgorithm, signingKey);
		
	
		String builder  = Jwts.builder()
	                .setSubject(subject)
	                .setIssuer("http://localhost:8080/token")
	                .setIssuedAt(new Date())
	                .setExpiration(toDate(LocalDateTime.now().plusMinutes(1L)))
	                .signWith(signatureAlgorithm, signingKey)
	                .compact();

	

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder;
	}

	// Sample method to validate and read the JWT
	private static void parseJWT(String jwt) {

		// This line will throw an exception if it is not a signed JWS (as
		// expected)
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("albeiro")).parseClaimsJws(jwt)
				.getBody();
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String codeJWT = createJWT("albeirobet");
		System.out.println(" Generando JWT " + codeJWT);
		try {
			//parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlYWFyIiwiaWF0IjoxNDk3NDEzMjI3LCJzdWIiOiJhbGJlaXJvYmV0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3Rva2VuIiwiZXhwIjoxNDk3NDEzNTI3fQ.qony48wS4xo9TVb3iQGLX4OgKE_6CbQPxFJ39Nj3hXx");
			parseJWT(codeJWT);
		} catch (Exception e) {
			System.out.println(" Error al decodifidcar el JWT clave no valida");
		}
	}
	
	  // ======================================
    // =          Private methods           =
    // ======================================

    private static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
