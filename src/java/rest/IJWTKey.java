package rest;

import javax.ejb.Stateless;

public interface IJWTKey {
    public String generateJWT(String text);
}
