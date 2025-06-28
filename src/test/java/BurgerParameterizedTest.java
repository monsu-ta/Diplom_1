
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerParameterizedTest extends BurgerTestBase {

    @Mock
    protected Ingredient testIngredient;

    private final IngredientType ingredientType;
    private final String ingredientName;
    private final float ingredientPrice;

    public BurgerParameterizedTest(IngredientType ingredientType, String ingredientName, float ingredientPrice) {
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
        this.ingredientPrice = ingredientPrice;
    }

    @Parameterized.Parameters(name = "Тип: {0}, Название: {1}, Цена: {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {IngredientType.SAUCE, "hot sauce", 100},
                {IngredientType.FILLING, "cutlet", 200}
        });
    }

    @Before
    public void setUp() {
        init();
        initIngredientMock(testIngredient, ingredientType, ingredientName, ingredientPrice);
    }

    @Test
    public void testAddDifferentIngredients() {
        addIngredient(testIngredient);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredientName, burger.ingredients.get(0).getName());
    }
}