package back.infrastructure.in.rest.endpoint;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import com.google.gson.Gson;

import back.DTO.UserDTO;
import back.app.api.IApp;
import back.infrastructure.utils.qualifiers.Built;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/login")
public class LoginController {
    @Inject
    @Built
    private IApp app;

    @GET
    // @Produces("text/plain")
    public Response login(@QueryParam("login") String login, @QueryParam("password") String password) {
        try {
            String token = app.login(new UserDTO(login, password));
            if (token != null) {
                return Response.ok(token).build();
            } else {
                throw new Exception("invalid credentials");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    // @Consumes("application/json")
    // @Produces("text/plain")
    public Response register(String strJSON) {
        Gson gson = new Gson();
        try {
            UserDTO user = gson.fromJson(strJSON, UserDTO.class);
            String token = app.register(user);
            if (token != null) {
                return Response.ok(token).build();
            } else {
                throw new Exception("invalid credentials");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
