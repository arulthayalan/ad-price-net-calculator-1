package au.com.seek.adprice;

import java.util.function.Consumer;

public class Application {


    public void run() {
        Consumer<CustomerCheckout> totalPrint = (checkout) -> {
            System.out.println(String.format("Total price $%.2f for customer %s", checkout.total(), checkout.getCustomer().getName()));
        };

        totalPrint.accept(InputBuilder.getDefaultCheckout());

        totalPrint.accept(InputBuilder.getSecondBiteCheckout());

        totalPrint.accept(InputBuilder.getAxilCoffeeCheckout());

        totalPrint.accept(InputBuilder.getMyerCheckout());

    }

    public static void main(String[] str) {
        Application application = new Application();
        application.run();

    }
}
