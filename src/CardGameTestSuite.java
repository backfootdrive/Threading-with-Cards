
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CardTest.class,
		CardDeckTest.class,
        CardGameTest.class,
        PlayerTest.class})
public class CardGameTestSuite {
}