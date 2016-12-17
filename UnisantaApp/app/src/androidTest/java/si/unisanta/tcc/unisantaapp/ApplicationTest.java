package si.unisanta.tcc.unisantaapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Thread.sleep(3000);

        clearGrade();
    }

    public void clearGrade() {
        //RepositoryFactory.getGradeRepository().deleteAll();
        RepositoryFactory.getCoursewareRepository().deleteAll();
    }
}