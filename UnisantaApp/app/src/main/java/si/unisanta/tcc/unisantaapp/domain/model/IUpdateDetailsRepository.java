package si.unisanta.tcc.unisantaapp.domain.model;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.AppVersion;

public interface IUpdateDetailsRepository {
    String find(AppVersion from);
    String findAfter(AppVersion from);
    String findBetween(AppVersion from, AppVersion til);
}
