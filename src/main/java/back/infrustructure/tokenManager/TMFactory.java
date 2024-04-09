package back.infrustructure.tokenManager;

public class TMFactory {
    private static ITokenManager instance = null;

    public static ITokenManager createTokenManager() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }
}
