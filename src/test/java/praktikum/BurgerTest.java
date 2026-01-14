package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BurgerTest {

    protected Burger burger;

    @Mock
    protected Bun bun;

    @Mock
    protected Ingredient dinosaur;

    @Mock
    protected Ingredient sausage;

    @Mock
    protected Ingredient chili;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Настраиваем моки
        when(bun.getName()).thenReturn("Красная булка");
        when(bun.getPrice()).thenReturn(300f);

        when(dinosaur.getName()).thenReturn("Динозавр");
        when(dinosaur.getType()).thenReturn(IngredientType.FILLING);
        when(dinosaur.getPrice()).thenReturn(200f);

        when(sausage.getName()).thenReturn("Колбаса");
        when(sausage.getType()).thenReturn(IngredientType.FILLING);
        when(sausage.getPrice()).thenReturn(300f);

        when(chili.getName()).thenReturn("Соус чили");
        when(chili.getType()).thenReturn(IngredientType.SAUCE);
        when(chili.getPrice()).thenReturn(100f);
        burger = new Burger();
    }

    @Test
    public void testReceiptContainsBunName() {
    burger.setBuns(bun);
    burger.addIngredient(dinosaur);
    String receipt = burger.getReceipt();
    assertTrue("В чеке содержится название булки", receipt.contains("Красная булка"));
}
    @Test
    public void testReceiptContainsIngredientName() {
    burger.setBuns(bun);
    burger.addIngredient(dinosaur);
    String receipt = burger.getReceipt();
    assertTrue("В чеке содержится название ингридиента", receipt.contains("Динозавр"));
}
    @Test
    public void testReceiptContainsPriceLabel() {
        burger.setBuns(bun);
        burger.addIngredient(dinosaur);
        String receipt = burger.getReceipt();
        assertTrue("В чеке содержится метка цены", receipt.contains("Price:"));
    }

    @Test
    public void testReceiptContainsCorrectPriceValueForOneIngredient() {
        burger.setBuns(bun);
        burger.addIngredient(dinosaur);
        String receipt = burger.getReceipt();

        // Проверяем, что цена 800 присутствует в чеке
        assertTrue("В чеке содержится цена 800", receipt.contains("800"));
    }

    // Проверка рецепта
    @Test
    public void testReceiptStructure() {
        burger.setBuns(bun);
        burger.addIngredient(dinosaur);
        String receipt = burger.getReceipt();
        String lineSep = System.lineSeparator();

        // Формируем ожидаемые части чека
        String expectedTopBun = "(==== Красная булка ====)" + lineSep;
        String expectedIngredient = "= filling Динозавр =" + lineSep;
        String expectedBottomBun = "(==== Красная булка ====)" + lineSep;
        String expectedSpacing = lineSep; // Пустая строка перед ценой
        String expectedPriceEndDot = "Price: 800.000000" + lineSep;
        String expectedPriceEndComma = "Price: 800,000000" + lineSep;

        // Собираем ожидаемый чек для обеих локалей
        String expectedReceiptWithDot = expectedTopBun + expectedIngredient + expectedBottomBun + expectedSpacing + expectedPriceEndDot;
        String expectedReceiptWithComma = expectedTopBun + expectedIngredient + expectedBottomBun + expectedSpacing + expectedPriceEndComma;

        // Проверяем, совпадает ли полученный чек с одним из ожидаемых
        boolean matchesDotLocale = receipt.equals(expectedReceiptWithDot);
        boolean matchesCommaLocale = receipt.equals(expectedReceiptWithComma);

        assertTrue("Структура чека должна соответствовать ожидаемой (с точкой или запятой как разделителем)",
                matchesDotLocale || matchesCommaLocale);
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(bun);
        assertNotNull("Булочка должна быть установлена", burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(dinosaur);
        assertEquals("Должен быть добавлен один ингредиент", 1, burger.ingredients.size());
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(dinosaur);
        burger.removeIngredient(0);
        assertEquals("Список ингредиентов должен быть пустым после удаления", 0, burger.ingredients.size());
    }

    @Test
    public void testMoveIngredientMovedElement() {
        burger.addIngredient(dinosaur);
        burger.addIngredient(sausage);
        burger.addIngredient(chili);
        burger.moveIngredient(0, 1);
        assertEquals("Ингредиент dinosaur должен быть перемещен на позицию 1", dinosaur, burger.ingredients.get(1));
    }

    @Test
    public void testMoveIngredientShiftedElement() {
        burger.addIngredient(dinosaur);
        burger.addIngredient(sausage);
        burger.addIngredient(chili);
        burger.moveIngredient(0, 1);
        assertEquals("Ингредиент sausage должен переместиться на позицию 0", sausage, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientUnchangedElement() {
        burger.addIngredient(dinosaur);
        burger.addIngredient(sausage);
        burger.addIngredient(chili);
        burger.moveIngredient(0, 1);
        assertEquals("Ингредиент chili_sauce должен остаться на позиции 2", chili, burger.ingredients.get(2));
    }

    @Test
    public void testBurgerWithBunOnlyPrice() {
        burger.setBuns(bun);
        assertEquals("Цена бургера только с булкой должна быть 2 * price булки", 600f, burger.getPrice(), 0.01);
    }

    @Test(expected = NullPointerException.class)
    public void testGetPriceWithoutBunThrowsException() {
        burger.getPrice();
    }

    @Test(expected = NullPointerException.class)
    public void testGetReceiptWithoutBunThrowsException() {
        burger.getReceipt();
    }

    @Test
    public void testEmptyBurgerReceiptContainsBunName() {
        burger.setBuns(bun);
        String receipt = burger.getReceipt();
        assertTrue("В чеке содержится название булки", receipt.contains("Красная булка"));
    }

    @Test
    public void testEmptyBurgerReceiptContainsPriceLabel() {
        burger.setBuns(bun);
        String receipt = burger.getReceipt();
        assertTrue("В чеке содержится цена", receipt.contains("Price:"));
    }

    @Test
    public void testEmptyBurgerReceiptContainsCorrectPriceValue() {
        burger.setBuns(bun);
        String receipt = burger.getReceipt();
        // Проверяем, что цена 600 присутствует в чеке
        assertTrue("В чеке содержится цена 600", receipt.contains("600"));
    }

    @Test
    public void testReceiptContainsSauceType() {
        burger.setBuns(bun);
        burger.addIngredient(chili);
        String receipt = burger.getReceipt();
        assertTrue("В чеке должен быть тип ингредиента sauce в нижнем регистре", receipt.contains("= sauce "));
    }

    @Test
    public void testReceiptContainsFillingType() {
        burger.setBuns(bun);
        burger.addIngredient(dinosaur);
        String receipt = burger.getReceipt();
        assertTrue("В чеке должен быть тип ингредиента filling в нижнем регистре", receipt.contains("= filling "));
    }

    @Test
    public void testGetPriceWithMultipleIngredients() {
        burger.setBuns(bun);
        burger.addIngredient(dinosaur);
        burger.addIngredient(sausage);
        burger.addIngredient(chili);
        float expectedPrice = 600f + 200f + 300f + 100f; // bun*2 + ingredients
        assertEquals("Цена должна быть рассчитана правильно для нескольких ингредиентов", 
                expectedPrice, burger.getPrice(), 0.01f);
    }

    @Test
    public void testGetReceiptWithMultipleIngredients() {
        burger.setBuns(bun);
        burger.addIngredient(dinosaur);
        burger.addIngredient(chili);
        String receipt = burger.getReceipt();
        assertTrue("В чеке должен быть первый ингредиент", receipt.contains("Динозавр"));
        assertTrue("В чеке должен быть второй ингредиент", receipt.contains("Соус чили"));
        assertTrue("В чеке должен быть тип filling", receipt.contains("filling"));
        assertTrue("В чеке должен быть тип sauce", receipt.contains("sauce"));
    }

    @Test
    public void testMoveIngredientToBeginning() {
        burger.addIngredient(dinosaur);
        burger.addIngredient(sausage);
        burger.addIngredient(chili);
        burger.moveIngredient(2, 0);
        assertEquals("Ингредиент должен быть перемещен в начало", chili, burger.ingredients.get(0));
        assertEquals("Первый ингредиент должен сдвинуться", dinosaur, burger.ingredients.get(1));
    }

    @Test
    public void testMoveIngredientToEnd() {
        burger.addIngredient(dinosaur);
        burger.addIngredient(sausage);
        burger.addIngredient(chili);
        burger.moveIngredient(0, 2);
        assertEquals("Ингредиент должен быть перемещен в конец", dinosaur, burger.ingredients.get(2));
        assertEquals("Второй ингредиент должен остаться на месте", sausage, burger.ingredients.get(0));
    }

    @Test
    public void testAddMultipleIngredients() {
        burger.addIngredient(dinosaur);
        burger.addIngredient(sausage);
        burger.addIngredient(chili);
        assertEquals("Должно быть добавлено 3 ингредиента", 3, burger.ingredients.size());
    }

    @Test
    public void testRemoveIngredientFromMiddle() {
        burger.addIngredient(dinosaur);
        burger.addIngredient(sausage);
        burger.addIngredient(chili);
        burger.removeIngredient(1);
        assertEquals("Должно остаться 2 ингредиента", 2, burger.ingredients.size());
        assertEquals("Первый ингредиент должен остаться", dinosaur, burger.ingredients.get(0));
        assertEquals("Третий ингредиент должен стать вторым", chili, burger.ingredients.get(1));
    }

    @Test
    public void testGetPriceWithMixedIngredientTypes() {
        burger.setBuns(bun);
        burger.addIngredient(dinosaur); // FILLING, 200f
        burger.addIngredient(chili);    // SAUCE, 100f
        float expectedPrice = 600f + 200f + 100f;
        assertEquals("Цена должна быть рассчитана правильно для разных типов ингредиентов", 
                expectedPrice, burger.getPrice(), 0.01f);
    }
}
