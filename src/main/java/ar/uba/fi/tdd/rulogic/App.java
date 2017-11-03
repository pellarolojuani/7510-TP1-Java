package ar.uba.fi.tdd.rulogic;

import ar.uba.fi.tdd.rulogic.model.KnowledgeBase;
import java.io.IOException;

/**
 * Console application.
 *
 */
public class App
{
	public static void main(String[] args) throws IOException {
            KnowledgeBase base = new KnowledgeBase();
            base.parseDatabase();
            int i = base.getDatabase().getFacts().size() + base.getDatabase().getRules().size();
    }
}
