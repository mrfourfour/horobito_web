package myproject.demo.KeyCloak.service;

public interface TokenProvider {
    public TokenProvider issue(TokenRequest tokenRequest);

    public TokenProvider refresh(String refreshToken);

    public void signUp(TokenRequest tokenRequest);
}
