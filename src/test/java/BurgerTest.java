
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest extends BurgerTestBase {

    @Before
    public void setUp() {
        super.init();
    }

    @Test
    public void testSetBuns() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100f);

        burger.setBuns(bun);

        assertEquals("Имя булочки должно совпадать",
                "black bun", burger.bun.getName());
        assertEquals("Цена булочки должна совпадать",
                100f, burger.bun.getPrice(), 0.0f);
    }

    @Test
    public void testAddIngredient() {
        Ingredient ingredient = mock(Ingredient.class);
        when(ingredient.getName()).thenReturn("hot sauce");
        when(ingredient.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient.getPrice()).thenReturn(100f);

        burger.addIngredient(ingredient);

        assertEquals("Должен быть добавлен один ингредиент",
                1, burger.ingredients.size());
        assertEquals("Имя ингредиента должно совпадать",
                "hot sauce", burger.ingredients.get(0).getName());
    }

    @Test
    public void testRemoveIngredient() {
        Ingredient sauce = mock(Ingredient.class);
        Ingredient filling = mock(Ingredient.class);

        when(filling.getType()).thenReturn(IngredientType.FILLING);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.removeIngredient(0);

        assertEquals("Должен остаться один ингредиент",
                1, burger.ingredients.size());
        assertEquals("Оставшийся ингредиент должен быть начинкой",
                IngredientType.FILLING, burger.ingredients.get(0).getType());
    }

    @Test
    public void testMoveIngredient() {
        Ingredient sauce1 = mock(Ingredient.class);
        Ingredient filling = mock(Ingredient.class);
        Ingredient sauce2 = mock(Ingredient.class);

        when(sauce1.getType()).thenReturn(IngredientType.SAUCE);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(sauce2.getType()).thenReturn(IngredientType.SAUCE);

        burger.addIngredient(sauce1);
        burger.addIngredient(filling);
        burger.addIngredient(sauce2);
        burger.moveIngredient(1, 0);

        List<Ingredient> ingredients = burger.ingredients;
        assertEquals("Первый ингредиент должен быть начинкой",
                IngredientType.FILLING, ingredients.get(0).getType());
        assertEquals("Второй ингредиент должен быть соусом",
                IngredientType.SAUCE, ingredients.get(1).getType());
    }

    @Test
    public void testGetPrice() {
        when(bun.getPrice()).thenReturn(100f);

        Ingredient sauce = mock(Ingredient.class);
        Ingredient filling = mock(Ingredient.class);

        when(sauce.getPrice()).thenReturn(50f);
        when(filling.getPrice()).thenReturn(80f);

        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        float actualPrice = burger.getPrice();
        assertEquals("Цена рассчитана неверно",
                100f * 2 + 50f + 80f, actualPrice, 0.0f);
    }

    @Test
    public void testGetReceipt() {
        when(bun.getName()).thenReturn("white bun");
        when(bun.getPrice()).thenReturn(200f);

        Ingredient sauce = mock(Ingredient.class);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("hot sauce");

        Ingredient filling = mock(Ingredient.class);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("cutlet");

        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();
        assertTrue("Чек должен содержать название булочки",
                receipt.contains("(==== white bun ====)"));
        assertTrue("Чек должен содержать соус",
                receipt.contains("= sauce hot sauce ="));
        assertTrue("Чек должен содержать начинку",
                receipt.contains("= filling cutlet ="));
    }

    @Test
    public void testGetReceiptWithNoIngredients() {
        when(bun.getName()).thenReturn("red bun");
        when(bun.getPrice()).thenReturn(150f);

        burger.setBuns(bun);
        String receipt = burger.getReceipt();

        assertTrue("Чек должен содержать название булочки",
                receipt.contains("(==== red bun ====)"));
        assertFalse("Чек не должен содержать ингредиенты",
                receipt.contains("= "));
    }
}