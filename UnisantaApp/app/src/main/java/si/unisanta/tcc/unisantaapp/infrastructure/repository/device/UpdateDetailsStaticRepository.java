package si.unisanta.tcc.unisantaapp.infrastructure.repository.device;


import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.model.IUpdateDetailsRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.AppVersion;

public class UpdateDetailsStaticRepository implements IUpdateDetailsRepository{
    public static final String[] VERSIONS = new String[] { "<3", "3", "3.1", "3.2", "3.3", "3.4", "3.5", "4.0"};

    @Override
    public String find(AppVersion from) {
        int id = UnisantaApplication.getStringId("v" + from.toString());
        if (id == -1 || id == 0) {
            UnisantaApplication.Log_e("Não foi encontrado novidades da versão: " + from.toString());
            return "";
        }
        return UnisantaApplication.getInstance().getString(id);
    }

    @Override
    public String findAfter(AppVersion from) {
        return findBetween(from, new AppVersion(UnisantaApplication.getVersionName()));
    }

    @Override
    public String findBetween(AppVersion from, AppVersion til) {
        AppVersion[] all = getAllVersions();

        int start = 0;
        while (from.isAfter(all[start]) || from.equals(all[start])) {
            start++;
        }

        int end = VERSIONS.length-1;
        while (til.isBefore(all[end])) {
            end--;
        }

        String concat = "";
        for (int i = start; i <= end; i++) {
            concat += find(all[i]) + "\n";
        }
        return concat;
    }

    private static AppVersion[] getAllVersions() {
        List<AppVersion> appVersionList = new ArrayList<>();
        for (String vName : VERSIONS) {
            appVersionList.add(new AppVersion(vName));
        }

        return appVersionList.toArray(new AppVersion[appVersionList.size()]);
    }
}
