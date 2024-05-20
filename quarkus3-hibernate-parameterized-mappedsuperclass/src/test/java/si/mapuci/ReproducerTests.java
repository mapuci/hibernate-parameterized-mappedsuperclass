package si.mapuci;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ReproducerTests {
    //TESTS for https://github.com/quarkusio/quarkus/issues/40719
    @Test
    void reproduceProblem() {
        given()
          .when().get("/test/this-will-fail")
          .then()
             .statusCode(200)
             .body(is("fourConcrete"));
    }
    @Test
    void showcaseLazyInitWithNonParameterizedGetterSolvesProblem() {
        given()
          .when().get("/test/this-will-fail-because-we-use-non-parameterized-abstract-getter-to-init-lazy-entity")
          .then()
             .statusCode(200)
             .body(is("fourConcrete"));
    }
    //TESTS for https://github.com/quarkusio/quarkus/issues/40719







    //Tests for GraphParser
    @Test
    void stringBasedFetchGraphErrorShowcase() {
        given()
          .when().get("/test/stringBasedFetchGraphErrorShowcase")
          .then()
             .statusCode(200)
             .body(is("fourConcrete"));
    }
    @Test
    void programmaticallyCreatedGraphWorks() {
        given()
          .when().get("/test/programmaticallyCreatedGraphWorks")
          .then()
             .statusCode(200)
             .body(is("fourConcrete"));
    }
    //Tests for GraphParser

}