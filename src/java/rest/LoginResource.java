package rest;

import entities.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import net.minidev.json.JSONObject;
import services.UserService;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/login")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
public class LoginResource {
    static final long ONE_MINUTE_IN_MILLIS=60000;

    @Inject
    private UserService userService;

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response loginUser(JSONObject jsonObject) {
        String username = jsonObject.getAsString("username");
        String password = jsonObject.getAsString("password");
        try{
            if(username != null && password != null) {
                User returnUser = userService.validateUser(username, password);
                return Response.ok(returnUser).build();
            }else{
                return Response.noContent().build();
            }
        }catch(Exception e){
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/loginAuth/{username}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response loginAuthUser(@PathParam("username") String username) {
        try{
                User returnUser = userService.findSingleUser(username);
                String token = issueToken(username);
                return Response.ok(returnUser).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            }catch(Exception e){
                System.out.println(e.getMessage());
                return Response.noContent().build();
        }
    }

    private String issueToken(String login) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String originalInput = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        JwtBuilder builder =
                Jwts.builder().
                        setSubject(login).
                        setIssuer("Kwetter").
                        signWith(SignatureAlgorithm.HS512, encodedString);
        return builder.compact();

        /*Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Date issuedAt = new Date();
        long time = issuedAt.getTime();
        Date expirationDate = new Date(time + (15 * ONE_MINUTE_IN_MILLIS));

        System.out.println(key);

        String jwtToken = Jwts.builder()
                .setId(Long.toString(id))
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .setIssuer("jwt")
                .signWith(key)
                .compact();
        System.out.println("create token : " + jwtToken);
        return jwtToken;*/
    }
}
