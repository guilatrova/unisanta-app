package si.unisanta.tcc.unisantaapp.domain.model;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public interface IUserRepository extends IDeletable {
    boolean retrieve();
    void save(User user);
}
