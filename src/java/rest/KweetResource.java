package rest;

import entities.User;
import services.UserService;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@Path("kweet")
@ApplicationScoped
public class KweetResource {

    @GET
    public Response getTest(User user) {
        return Response.serverError().build();
    }
}
