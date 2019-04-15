package rest;

import entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import services.UserService;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import java.security.Key;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

//@Path("/login")
//@Produces(APPLICATION_JSON)
//@Consumes(APPLICATION_JSON)
//@Transactional
public class LoginResource {
    static final long ONE_MINUTE_IN_MILLIS=60000;

    /**@Context
    UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;

    LoginResource(){}

    public String issueToken(String username, Long id) {
        //Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        Key key = keyGenerator.generateKey();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        Date issuedAt = new Date();
        long time = issuedAt.getTime();
        Date expirationDate = new Date(time + (15 * ONE_MINUTE_IN_MILLIS));
        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(signatureAlgorithm, key)
                .setId(id.toString())
                .compact();
        return jwtToken;
    }**/
}
