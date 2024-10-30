package br.com.lucas.demo.integrationTests.swagger;

import br.com.lucas.demo.configs.TestConfigs;
import br.com.lucas.demo.integrationTests.testContainer.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void shouldDisplaySwaggerPage(){

        var content =given()
                    .basePath("/swagger-ui/index.html")
                    .port(TestConfigs.SERVER_PORT)
                    .when()
                        .get()
                    .then()
                       .statusCode(200)
                    .extract()
                        .body().asString();

        assertTrue(content.contains("Swagger UI"));
    }

}
