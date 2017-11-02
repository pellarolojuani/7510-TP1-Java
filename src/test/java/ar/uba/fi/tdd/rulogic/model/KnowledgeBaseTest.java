package ar.uba.fi.tdd.rulogic.model;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class KnowledgeBaseTest {

    @InjectMocks
    private KnowledgeBase knowledgeBase;
    private Database database;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        knowledgeBase.parseDatabase();
        this.database = new Database();
    }

    // Tests unitarios
    
    @Test
    public void getRuleNameFromQuery1Test() {
        Assert.assertEquals(this.database.getRuleNameFromQuery("esto no es un query"), "");
    }
    
    @Test
    public void getRuleNameFromQuery2Test() {
        Assert.assertEquals(this.database.getRuleNameFromQuery("animal(perro)"), "animal");
    }
    
    @Test
    public void countFactsFromRule1Test() {
        Assert.assertEquals(Integer.valueOf(this.database.countFactsFromRule("varon(roberto).")), Integer.valueOf(1));
    }

    @Test
    public void countFactsFromRule2Test() {
        Assert.assertEquals(Integer.valueOf(this.database.countFactsFromRule("varon: roberto.")), Integer.valueOf(0));
    }

    // Tests de aceptacion
    
    @Test
    public void test1() {
        Assert.assertTrue(this.knowledgeBase.answer("varon (juan)."));
    }

    @Test
    public void test2() {
        Assert.assertTrue(this.knowledgeBase.answer("tio(nicolas, alejandro, roberto)"));
    }

    @Test
    public void test3() {
        Assert.assertFalse(this.knowledgeBase.answer("tio(maria, ana, roberto)"));
    }
}
