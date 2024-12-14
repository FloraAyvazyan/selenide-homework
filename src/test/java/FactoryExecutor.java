import ge.tbcitacademy.data.Constants;
import org.testng.annotations.Factory;

public class FactoryExecutor {
    @Factory
    public Object[] factoryExecutor(){
        return new Object[]{
                new ParametrizedSwoopTests2(Constants.DASVENEBA_URL),
                new ParametrizedSwoopTests2(Constants.KVEBA_URL),
                new ParametrizedSwoopTests2(Constants.SPORT_URL),
        };
    }
}
