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

        @Test
        public void countFactsFromRule1Test() {
            Assert.assertEquals(Integer.valueOf(this.database.countFactsFromRule("varon(roberto).")), Integer.valueOf(2));
        }
        
        @Test
        public void countFactsFromRule2Test() {
            Assert.assertEquals(Integer.valueOf(this.database.countFactsFromRule("varon: roberto.")), Integer.valueOf(1));
        }
        
//	@Test
//	public void test() {
//		Assert.assertTrue(this.knowledgeBase.answer("varon (juan)."));
//	}
}
