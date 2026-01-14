package praktikum;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;


public class  BaseTest {

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

    public void createMockAndClass() {
        MockitoAnnotations.openMocks(this);

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
}
