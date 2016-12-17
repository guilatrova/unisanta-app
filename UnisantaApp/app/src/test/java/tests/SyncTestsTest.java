package tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ITestRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebTestsData;
import tests.mocks.pages.si.TestsPageMock;
import tests.mocks.repositories.SubjectMockRepository;
import tests.mocks.repositories.TestMockRepository;

public class SyncTestsTest {
    private ISubjectRepository subjectRepository;
    private ITestRepository testRepository;

    @Before
    public void setUp() throws Exception {
        subjectRepository = new SubjectMockRepository();
        testRepository = new TestMockRepository();

        subjectRepository.saveSubject(new Subject(0, "ÉTICA, MEIO AMBIENTE E SUSTENTABILIDADE", "ÉTICA MEIO AMB. E SUSTENTAB.", false, "1052N7B", SchoolYear.getCurrent(), null));
        subjectRepository.saveSubject(new Subject(0, "GESTÃO DA INFORMÇÃO", "GESTÃO DA INFORMAÇÃO", false, "1052N7B", SchoolYear.getCurrent(), null));
        subjectRepository.saveSubject(new Subject(0, "PESQUISA OPERACIONAL I", "PESQUISA OPERAC. I", false, "1052N7B", SchoolYear.getCurrent(), null));
        subjectRepository.saveSubject(new Subject(0, "PRATICA E GERENCIAMENTO DE PROJETOS I", "PRAT. E GERENC. DE PROJETOS I", false, "1052N7B", SchoolYear.getCurrent(), null));
        subjectRepository.saveSubject(new Subject(0, "SISTEMAS DISTRIBUIDOS I", "SIST. DISTRIBUIDOS I", false, "1052N7B", SchoolYear.getCurrent(), null));
        subjectRepository.saveSubject(new Subject(0, "SISTEMAS COOPERATIVOS", "SISTEMAS COOPERATIVOS", false, "1052N7B", SchoolYear.getCurrent(), null));
        subjectRepository.saveSubject(new Subject(0, "INTERFACE HOMEM MÁQUINA", "INTERFACE HOMEM  MÁQUINA", false, "1052N7B", SchoolYear.getCurrent(), null));
        subjectRepository.saveSubject(new Subject(0, "AUDITORIA E SEGURANÇA DA INFORMAÇÃO I", "AUDITORIA E SEG. DA INFORM. I", false, "1052N7B", SchoolYear.getCurrent(), null));
    }

    @Test
    public void testSyncTests() throws Exception {
        TestsPageMock pageMock = new TestsPageMock();

        SyncWebTestsData syncTests = new SyncWebTestsData(pageMock, testRepository, subjectRepository);
        syncTests.sync();

        List<si.unisanta.tcc.unisantaapp.domain.entities.Test> lstTest = testRepository.findByWeight(si.unisanta.tcc.unisantaapp.domain.entities.Test.P1);
        Assert.assertEquals(8 , lstTest.size());
    }
}
