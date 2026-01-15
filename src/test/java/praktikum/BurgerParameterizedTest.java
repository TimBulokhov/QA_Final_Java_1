package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import org.assertj.core.api.SoftAssertions;

@RunWith(Parameterized.class)
public class BurgerParameterizedTest extends BaseTest {

    @Parameterized.Parameters(name = "Тестовые данные: ingredientCount={0}, expectedPrice={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {0, 600f}, // Только булка
                {1, 800f}, // Булка + 1 ингредиент
                {3, 1200f}  // Булка + 3 ингредиента
        });
    }

    @Parameterized.Parameter
    public int ingredientCount;

    @Parameterized.Parameter(1)
    public float expectedPrice;

    @Before
    public void setUp() {
        super.createMockAndClass();
    }

    @Test
    public void testGetPriceWithDifferentIngredients() {
        burger.setBuns(bun);
        for (int i = 0; i < ingredientCount; i++) {
            burger.addIngredient(dinosaur);
        }
        assertEquals("Цена должна корректно рассчитываться", expectedPrice, burger.getPrice(), 0.01);
    }

    @Test
    public void testGetReceiptWithDifferentIngredients() {
        burger.setBuns(bun);
        for (int i = 0; i < ingredientCount; i++) {
            burger.addIngredient(dinosaur);
        }

        String receipt = burger.getReceipt();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(receipt).contains("Красная булка");
        softly.assertThat(receipt).contains(String.format("Price: %f", expectedPrice));

        if (ingredientCount > 0) {
            softly.assertThat(receipt).contains("= filling Динозавр =");
        }
        softly.assertAll();
    }
}
