package me.scpark.springbootdeveloper;

import org.junit.jupiter.api.*;

public class JUnitTest {

    @DisplayName("1+2는 3이다")
    @Test
    public void junitTest(){
        int a =1;
        int b =2;
        int sum = 3;

        System.out.println("1+2는 3이다");

        //expected value, actual value
        Assertions.assertEquals(sum, a + b);
    }

    @DisplayName("1+3은 3이다.")
    @Test
    public void junitFailTest(){
        int a = 1;
        int b = 3;
        int sum = 3;
        System.out.println("1+3은 3이다");

        Assertions.assertNotEquals(sum, a+b);
    }


    @BeforeEach
    public void prepare(){
        System.out.println("테스트 준비");
    }

    @AfterEach
    public void clean(){
        System.out.println("테스트 후 설겆이");
    }

    @BeforeAll
    public static void prepareAll(){
        System.out.println("최초 준비");
    }

    @AfterAll
    public static void cleanAll(){
        System.out.println("최종 마무리");
    }
}
