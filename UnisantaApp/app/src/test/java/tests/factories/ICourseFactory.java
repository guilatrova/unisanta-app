package tests.factories;

import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebClassesData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebGradesData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebSubjectsData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebUserData;

public interface ICourseFactory {
    SyncWebUserData createSyncUserData();
    SyncWebSubjectsData createSyncSubjectsData();
    SyncWebGradesData createSyncGradesData();
    SyncWebClassesData createSyncWebClassesData();
}
