package si.unisanta.tcc.unisantaapp.application.services;

import java.io.IOException;
import java.net.ConnectException;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.exceptions.LoginFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingDataException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingTagException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.NotConnectedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.PageRequestFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UnavailableDataException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UserChangedPasswordException;
import si.unisanta.tcc.unisantaapp.domain.services.courseware.CantCreateFolderPathException;

public class UserErrorMessageHelper {
    public static String getMessage(Exception e) {
        if (e instanceof NotConnectedException) {
            return UnisantaApplication.getInstance().getString(R.string.no_connection_message);
        }

        if (e instanceof LoginFailedException) {
            return UnisantaApplication.getInstance().getString(R.string.invalid_login_message);
        }

        if (e instanceof UnavailableDataException ) {
            UnisantaApplication.getInstance().getString(
                    R.string.unavailable_data_message,
                    ((UnavailableDataException)e).getSyncName(), e.getMessage());
        }

        if (e instanceof MissingTagException) {
            return UnisantaApplication.getInstance().getString(
                    R.string.missing_tag_message,
                    ((MissingTagException) e).getTag(), ((MissingTagException) e).getUrl());
        }

        if (e instanceof UserChangedPasswordException) {
            return UnisantaApplication.getInstance().getString(R.string.user_changed_password);
        }

        if (e instanceof CantCreateFolderPathException) {
            return UnisantaApplication.getInstance().getString(R.string.cant_create_folder,
                    ((CantCreateFolderPathException) e).getPath());
        }

        Throwable rootCause = getRootCause(e);

        if (rootCause instanceof ConnectException) {
            return UnisantaApplication.getInstance().getString(R.string.connection_down_message);
        }

        if (rootCause instanceof IOException) {
            return rootCause.getMessage();
            //return UnisantaApplication.getInstance().getString(R.string.bad_connection_message);
        }

        if (rootCause instanceof PageRequestFailedException) {
            PageRequestFailedException requestException = (PageRequestFailedException) rootCause;
            return UnisantaApplication.getInstance().getString(R.string.page_request_fail_message, requestException.getUrl());
        }

        if (rootCause instanceof MissingDataException) {
            MissingDataException missingDataException = (MissingDataException) rootCause;
            return UnisantaApplication.getInstance().getString(R.string.missing_data,
                    missingDataException.getSyncName(),
                    missingDataException.getWhatsMissing());
        }

        //Erro desconhecido
        return UnisantaApplication.getInstance().getString(R.string.general_sync_error, rootCause.getMessage());
    }

    private static Throwable getRootCause(Exception e)
    {
        Throwable rootCause = e;
        while (rootCause.getCause() != null && !(rootCause instanceof PageRequestFailedException)) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}
