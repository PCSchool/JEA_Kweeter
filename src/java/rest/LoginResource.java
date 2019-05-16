package rest;

import entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import net.minidev.json.JSONObject;
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
    public Response loginUser(User user) {
        try{
            User returnUser = userService.validateUser(user.getUsername(), user.getPassword());
            Map<String, Object> map = new HashMap<>();
            map.put("username", returnUser.getUsername());
            map.put("id", returnUser.getId());
            String token = issueToken(Long.toString(returnUser.getIdUser()), returnUser.getIdUser());
            JSONObject jsonToken = new JSONObject();
            jsonToken.put("access_token", "Bearer " + token);
            return Response.ok(jsonToken).header(AUTHORIZATION, token).build();
        }catch(Exception e){
            return Response.noContent().build();
        }
    }

    @POST
    @Path("/loginAuth")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response loginAuthUser(User user) {
        try{
            User returnUser = userService.validateUser(user.getUsername(), user.getPassword());
            return Response.ok().entity(returnUser).build();
        }catch(Exception e){
            return Response.noContent().build();
        }
    }

    private String issueToken(String login, Long id ) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
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
        return jwtToken;
    }
}
