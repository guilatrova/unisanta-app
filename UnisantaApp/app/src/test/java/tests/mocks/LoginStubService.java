package tests.mocks;

import si.unisanta.tcc.unisantaapp.domain.services.IUserLoginService;

public class LoginStubService implements IUserLoginService{
    @Override
    public boolean doLogin(String ra, String password) {
        return true;
    }
}
