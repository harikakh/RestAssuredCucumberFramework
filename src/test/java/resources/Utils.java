package resources;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification requestSpecification;
    public RequestSpecification requestSpecification() throws IOException {

        if(requestSpecification==null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));

            requestSpecification = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return requestSpecification;

        }
        return requestSpecification;

    }

    public static String getGlobalValues(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\HKorada\\Documents\\RestAssuredWithCucumberFraemwork\\src\\test\\java\\resources\\global.properties");
        prop.load(fis);
       return prop.getProperty(key);

    }

    public String getJsonPath(Response response, String key)
    {
        String res=response.asString();
        JsonPath js = new JsonPath(res);
        return js.get(key).toString();

    }
}
