package tests.mocks.repositories;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.model.IDeletable;

public abstract class IRepositoryMock<T> implements IDeletable{
    protected List<T> repositoryList;

    public IRepositoryMock() {
        this.repositoryList = new ArrayList<>();
    }

    protected long save(T object) {
        repositoryList.add(object);
        return repositoryList.size();
    }

    protected void delete(T object) {
        repositoryList.remove(object);
    }

    @Override
    public void deleteAll() {
        repositoryList.clear();
    }
}
