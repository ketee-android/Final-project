import Main.Main;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestHW6 {

    Main main;
    @Before
    public void prepare() {
        main = new Main ();
    }

// ТЕСТЫ К ЗАДАНИЮ №1

    @Test
    public void firstTaskTestOne() {
        System.out.println (" ");
        System.out.println ("Тест 1.1:");
        main.firstTask(new int[]{1, 52, 4, 11, 7, 23, 48, 100, 16, 0});
        System.out.println (" ");
    }

    @Test
    public void firstTaskTestTwo() {
        System.out.println ("Тест 1.2:");
        main.firstTask(new int[]{2, 4, 1, 11, 25, 4, 31, 59, 0, 4});
        System.out.println (" ");
    }

    @Test
    public void firstTaskTestThree() {
        System.out.println ("Тест 1.3:");
        int[] finalArr = new int[] {31, 59, 0, 13};
        Assert.assertArrayEquals (finalArr, main.firstTask(new int[]{2, 4, 1, 11, 25, 4, 31, 59, 0, 13}));
        System.out.println (" ");
    }

    @Test (expected = RuntimeException.class)
    public void firstTaskTestFour() {
        System.out.println ("Тест 1.4:");
        main.firstTask(new int[]{1, 1, 2, 0, 15, 16});
        System.out.println (" ");
    }

// ТЕСТЫ К ЗАДАНИЮ №2

    @Test
    public void secondTaskTestOne() {
        System.out.println (" ");
        System.out.println ("Тест 2.1:");
        Assert.assertTrue (main.secondTask(new int[]{1, 4, 4, 1, 4, 1, 1, 1, 4}));
        System.out.println (" ");
    }

    @Test
    public void secondTaskTestTwo() {
        System.out.println ("Тест 2.2:");
        Assert.assertFalse (main.secondTask(new int[]{1, 4, 7, 1, 4, 8, 1, 2, 4}));
        System.out.println (" ");
    }

    @Test
    public void secondTaskTestThree() {
        System.out.println ("Тест 2.3:");
        Assert.assertFalse (main.secondTask(new int[]{4, 4, 4, 4, 4}));
        System.out.println (" ");
    }

    @Test
    public void secondTaskTestFour() {
        System.out.println ("Тест 2.4:");
        Assert.assertFalse (main.secondTask(new int[]{1, 1, 1, 1, 1}));
        System.out.println (" ");
    }

}
