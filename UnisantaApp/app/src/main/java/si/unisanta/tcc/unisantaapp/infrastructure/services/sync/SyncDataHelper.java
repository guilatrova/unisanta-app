package si.unisanta.tcc.unisantaapp.infrastructure.services.sync;

import org.apache.commons.lang3.StringEscapeUtils;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;

public class SyncDataHelper {
    public static int getNextPosition(String needle,final String haystack, int start, int howMany) throws StringNotFoundException {
        int ret = start;
        for (int i = 0; i < howMany; i++) {
            ret = getIndexOf(needle, haystack, ret+1);
        }
        return ret + needle.length();
    }

    public static int getIndexAndGoToEnd(String needle,final String haystack) throws StringNotFoundException {
        return getIndexAndGoToEnd(needle, haystack, 0);
    }

    public static int getIndexAndGoToEnd(String needle,final String haystack, int start) throws StringNotFoundException {
        int aux = getIndexOf(needle, haystack, start);
        if (aux > -1)
            return aux + needle.length();
        return -1;
    }

    public static int getIndexOf(String needle, final String haystack, int start) throws StringNotFoundException {
        int ret = haystack.indexOf(needle, start);
        if (ret == -1) {
            UnisantaApplication.Log_d(haystack);
            throw new StringNotFoundException(needle);
        }
        return ret;
    }

    public static int getIndexOf(String needle, final String haystack) throws StringNotFoundException {
        return getIndexOf(needle, haystack, 0);
    }

    public static String fixSpecialCharacters(String word) {
        return StringEscapeUtils.unescapeHtml3(word);
        /*
        return word.replace('\n', '\0')
                .replace("&#186;", "º")
                //MAIÚSCULAS
                .replace("&#199;", "Ç")
                .replace("&#195;", "Ã")
                .replace("&#193;", "Á")
                .replace("&#192;", "À")
                .replace("&#194;", "Â")
                .replace("&#201;", "É")
                .replace("&#202;", "Ê")
                .replace("&#205;", "Í")
                .replace("&#213;", "Õ")
                .replace("&#212;", "Ô")
                .replace("&#211;", "Ó")
                .replace("&#218;", "Ú")
                //MINÚSCULAS
                .replace("&#231;", "ç")
                .replace("&#227;", "ã")
                .replace("&#225;", "á")
                .replace("&#224;", "à")
                .replace("&#226;", "â")
                .replace("&#233;", "é")
                .replace("&#234;", "ê")
                .replace("&#237;", "í")
                .replace("&#245;", "õ")
                .replace("&#244;", "ô")
                .replace("&#243;", "ó")
                .replace("&#250;", "ú");*/
    }
}
