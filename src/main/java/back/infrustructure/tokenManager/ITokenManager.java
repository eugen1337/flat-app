package back.infrustructure.tokenManager;

import java.util.Map;

public interface ITokenManager {
    public String generateToken(String username, String passwd);

    public Map<String, String> getTokenInfo(String token);
}
