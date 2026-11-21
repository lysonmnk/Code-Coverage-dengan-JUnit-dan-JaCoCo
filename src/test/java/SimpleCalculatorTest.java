
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleCalculatorTest {
    @Test
    @DisplayName("Test konstruktor default")
    public void testConstructor() {
        SimpleCalculator calc = new SimpleCalculator();
        assertNotNull(calc);
    }

    @Test
    @DisplayName("Test add: 2 + 3 = 5")
    public void testAdd() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("Test subtract: 5 - 3 = 2")
    public void testSubtract() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(2, calculator.subtract(5, 3));
    }

    @Test
    @DisplayName("Test multiply: 2 * 3 = 6")
    public void testMultiply() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    @DisplayName("Test divide normal: 6 / 2 = 3")
    public void testDivideNormal() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(3, calculator.divide(6, 2));
    }

    @Test
    @DisplayName("Test divide by zero melempar IllegalArgumentException")
    public void testDivideByZero() {
        SimpleCalculator calculator = new SimpleCalculator();
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.divide(6, 0)
        );
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @Test
    @DisplayName("Test divide with negative divisor returns 0")
    public void testDivideByNegative() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(0, calculator.divide(6, -2));
    }
}
