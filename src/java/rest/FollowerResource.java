package rest;

import entities.User;
import services.UserService;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@Path("follower")
@ApplicationScoped
public class FollowerResource {

    @Inject
    private UserService userService;
    @POST
    @Path("gained")
    public Response saveUser(User user) {
        userService.createUser(user);
        return Response.ok(user).build();
    }
}

