package hongrok.spring.project1.test;

import org.junit.jupiter.api.*;


public class TestLifeCycle {

    @BeforeAll          //전체 test가 시작되기 전에 한번 호출함
    static void beforeAll() {
        System.out.println("## beforeAll annotation 호출 ##");
        System.out.println();
    }

    @AfterAll           //전체 test가 끝난 후 한번 호출함
    static void afterAll() {
        System.out.println("## AfterAll annotation 호출 ##");
        System.out.println();
    }

    @BeforeEach         //각 test가 실행되기 전에 각각 호출함
    void beforeEach() {
        System.out.println("## BeforeEach annotation 호출 ##");
        System.out.println();
    }

    @AfterEach          //각 test가 실행된 후 각각 호출함
    void afterEach() {
        System.out.println("## AfterEach annotation 호출 ##");
        System.out.println();
    }

    @Test
    void test1() {
        System.out.println("## test1 시작 ##");
        System.out.println();
    }

    @Test
    @DisplayName("Test case 2")     //원래는 test2로 출력되어야 하자만 displayname에 지정된 test case 2 가 대신 출력됨
    void test2() {
        System.out.println("## test2 시작 ##");
        System.out.println();
    }

    @Test
    @Disabled       //해당 test를 disabled로 설정했기때문에 실행되지 않음
    void test3() {
        System.out.println("## test3 시작 ##");
        System.out.println();
    }
}
