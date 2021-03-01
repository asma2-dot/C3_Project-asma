import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        if (openingTime.isAfter(getCurrentTime()) || closingTime.isBefore(getCurrentTime()) || closingTime.equals(getCurrentTime())) {
            return false;
        }
        return true;

    }

    public LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    public List<Item> getMenu() {
        return this.menu;

    }

    private Item findItemByName(String itemName) {
        for (Item item : menu) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name, price);
        menu.add(newItem);
    }

    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }

    public void displayDetails() {
        System.out.println("Restaurant:" + name + "\n"
                + "Location:" + location + "\n"
                + "Opening time:" + openingTime + "\n"
                + "Closing time:" + closingTime + "\n"
                + "Menu:" + "\n" + getMenu());

    }

    public String getName() {
        return name;
    }

    /////////////////////////////////////TDD/////////////////////////////////////////////////////

    public Receipt totalCost(Item itemName,int price){
        Receipt receipt=new Receipt();

        receipt.itemName=addedItem("Sweet corn soup", 119);
        return new Receipt();
    }

    public Item addedItem(String name,int price){
        Item iName=new Item(name,price);
        return iName;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        this = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        addToMenu("Food", 22);
        addToMenu("Vegetable lasagne", 269);
        LocalTime time = LocalTime.parse("22:00:00");
        Restaurant spyRestaurant = Mockito.spy(this);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(time);
        Assertions.assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        this = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        addToMenu("Food", 22);
        addToMenu("Vegetable lasagne", 269);
        LocalTime time = LocalTime.parse("22:30:00");
        Restaurant spyRestaurant = Mockito.spy(this);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(time);
        Assertions.assertFalse(spyRestaurant.isRestaurantOpen());
    }
}


