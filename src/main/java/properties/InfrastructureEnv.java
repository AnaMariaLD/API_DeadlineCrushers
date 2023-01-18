package properties;

import java.util.HashMap;
import java.util.Map;

public class InfrastructureEnv {
    public static String getBaseUri() {
        String env = "local80";

        if(System.getenv().containsKey("CommonServiceEnv")){
            env = System.getenv("CommonServiceEnv");
        }
        if(System.getProperties().containsKey("env")){
            env = System.getProperty("env");
        }

        Map<String,String> urls = new HashMap<>();
        urls.put("local80","http://localhost:80/v2/");
        urls.put("web","http://petstore.swagger.io/v2/");

        return urls.get(env);
    }
}
