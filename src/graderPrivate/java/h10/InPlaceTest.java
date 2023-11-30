package h10;

import h10.utils.Links;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class InPlaceTest extends AbstractTest {

    @BeforeAll
    public void globalSetup() {
        type = Links.getTypeLink(MySetInPlace.class);
    }
}
