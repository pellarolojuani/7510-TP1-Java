package ar.uba.fi.tdd.rulogic.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Test
    public void getParamsFromFactTest() {
        Assert.assertEquals(Arrays.asList(database.getParamsFromFact("padre(juan, pepe)")).get(0), "juan");
        Assert.assertEquals(Arrays.asList(database.getParamsFromFact("padre(juan, pepe)")).get(1), "pepe");
    }

    @Test
    public void parseInvalidFactTest() {
        List<String> data = new ArrayList<String>();
        data.add("varon(juan).");       //Correct Fact
        data.add("noFact");             //Incorrect Fact
        data.add("mujer(cecilia).");    //Correct Fact
        database.loadDatabase(data);
        Assert.assertEquals(database.getFacts().size(), 2);
        List<String> moreData = new ArrayList<String>();
        moreData.add("mujer           (claudia).");  //Correct Fact
        database.loadDatabase(moreData);
        Assert.assertEquals(database.getFacts().size(), 3);
    }
    
    @Test
    public void parseDatabaseTest() throws IOException {
        knowledgeBase.getDatabase().getFacts().clear();
        knowledgeBase.getDatabase().getRules().clear();
        knowledgeBase.parseDatabase();
        Assert.assertEquals(knowledgeBase.getDatabase().getFacts().size() + knowledgeBase.getDatabase().getRules().size(), 19);
        Assert.assertEquals(knowledgeBase.getDatabase().getFacts().size(), 15);
        Assert.assertEquals(knowledgeBase.getDatabase().getRules().size(), 4);
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
