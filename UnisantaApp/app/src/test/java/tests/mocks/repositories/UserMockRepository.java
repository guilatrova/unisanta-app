package tests.mocks.repositories;

import si.unisanta.tcc.unisantaapp.domain.model.IUserRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public class UserMockRepository implements IUserRepository{
    User user;

    @Override
    public void deleteAll() {
        user = null;
    }

    @Override
    public boolean retrieve() {
        return true;
    }

    @Override
    public void save(User user) {
        this.user = user;
    }
}
