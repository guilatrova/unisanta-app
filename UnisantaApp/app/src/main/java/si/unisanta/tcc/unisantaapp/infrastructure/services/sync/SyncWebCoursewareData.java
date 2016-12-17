package si.unisanta.tcc.unisantaapp.infrastructure.services.sync;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingTagException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.SyncFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UnavailableDataException;
import si.unisanta.tcc.unisantaapp.domain.factories.CoursewareFactory;
import si.unisanta.tcc.unisantaapp.domain.model.ICoursewareRepository;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncBaseData;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.CoursewareSubjectPage;

public class SyncWebCoursewareData extends SyncBaseData {
    private CoursewareSubjectPage page;
    private ICoursewareRepository coursewareRepository;

    private static final String URL_PREFIX = "http://portalaluno.unisanta.br/Academico/Download?";

    public SyncWebCoursewareData(CoursewareSubjectPage page, ICoursewareRepository coursewareRepository) {
        this.page = page;
        this.coursewareRepository = coursewareRepository;
    }

    public Subject getSubject() {
        return page.getSubject();
    }

    @Override
    public void sync() throws SyncFailedException {
        try {
            String html = page.getHtml();
            page.checkDataAvailable(html);

            int pos = SyncDataHelper.getIndexAndGoToEnd("id=\"listaDocumentos\">", html);
            int endPos = SyncDataHelper.getIndexOf("</table>", html);

            html = html.substring(pos, endPos);
            List<CoursewareDataHolder> dataHolderList = new ArrayList<>();
            endPos = 0;

            do {
                CoursewareDataHolder dataHolder = new CoursewareDataHolder();

                pos = SyncDataHelper.getNextPosition("<a href=\"/Academico/Download?", html, endPos, 2);
                endPos = SyncDataHelper.getIndexOf("\"", html, pos);

                dataHolder.link = URL_PREFIX + html.substring(pos, endPos).replace("&amp;","&");

                pos = SyncDataHelper.getIndexOf(">", html, endPos) + 1;
                endPos = SyncDataHelper.getIndexOf("</a>", html, pos);

                dataHolder.title = SyncDataHelper.fixSpecialCharacters(html.substring(pos, endPos));

                pos = SyncDataHelper.getIndexOf("/Academico/Download?", html, endPos);
                pos = SyncDataHelper.getIndexOf(">", html, pos) + 1;
                endPos = SyncDataHelper.getIndexOf("<", html, pos);

                dataHolder.description = SyncDataHelper.fixSpecialCharacters(html.substring(pos, endPos));

                pos = SyncDataHelper.getIndexAndGoToEnd("alinhamentoDireita\">", html, endPos);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);

                dataHolder.upload = html.substring(pos, endPos);

                pos = SyncDataHelper.getIndexAndGoToEnd("alinhamentoDireita\">", html, endPos);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);

                dataHolder.size = html.substring(pos, endPos);

                dataHolderList.add(dataHolder);
                UnisantaApplication.Log_i("Courseware " +
                        "\nTitle: " + dataHolder.title +
                        "\nSubtitle: " + dataHolder.description +
                        "\nLink: " + dataHolder.link +
                        "\nSize: " + dataHolder.size +
                        "\nUpload: " + dataHolder.upload);
            }
            while (html.indexOf("colunaDownload", endPos) > -1);

            saveCoursewares(dataHolderList);

        } catch (StringNotFoundException e) {
            throw new MissingTagException(e.getString(), e, page.getUrl(), page.getPageName(), e.getString());
        }
        catch (UnavailableDataException e) {
            throw e;
        } catch (Exception e) {
            throw new SyncFailedException(e, page.getUrl(), page.getPageName());
        }
    }

    @Override
    public String getMessage() {
        return UnisantaApplication.getInstance().getString(R.string.courseware_of, getSubject().getName());
    }

    private void saveCoursewares(List<CoursewareDataHolder> dataHolderList) {
        for (CoursewareDataHolder dataHolder : dataHolderList) {
            Courseware newCourseware = CoursewareFactory.createCourseware(
                    page.getSubject(),
                    dataHolder.title,
                    dataHolder.description,
                    dataHolder.upload,
                    dataHolder.size,
                    dataHolder.link
            );

            Courseware cmp = coursewareRepository.findByTitleAndUploadDate(
                    newCourseware.getTitle(),
                    newCourseware.getUploadDate(),
                    newCourseware.getUploadTime());

            if (cmp == null) {
                coursewareRepository.saveCourseware(newCourseware);
            }
            else {
                UnisantaApplication.Log_i("Material: " + newCourseware.getTitle() + " já existe");
            }
        }
    }

    private final class CoursewareDataHolder {
        public String title;
        public String description;
        public String link;
        public String upload;
        public String size;
    }
}
