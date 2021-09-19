package myproject.demo.KeyCloak.service;

public interface Token {
    public Token issue(TokenRequest tokenRequest);

    public Token refresh(String refreshToken);

    public void signUp(TokenRequest tokenRequest);
}
