package myproject.demo.KeyCloak.service;

public interface TokenProvider {
    public Token issue(TokenRequest tokenRequest);

    public Token refresh(String refreshToken);

    public void signUp(TokenRequest tokenRequest);
}
