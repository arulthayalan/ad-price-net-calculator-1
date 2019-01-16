package au.com.seek.adprice;

import au.com.seek.adprice.model.Customer;
import au.com.seek.adprice.model.Product;
import au.com.seek.adprice.rules.DealRule;
import au.com.seek.adprice.rules.PriceDiscountRule;

import java.math.BigDecimal;

public class InputBuilder {

    public static CustomerCheckout getDefaultCheckout() {
        CustomerCheckout checkout = new CustomerCheckout(InputBuilder.getSecondBite());
        checkout.addProduct(InputBuilder.getClassicAdProduct());
        checkout.addProduct(InputBuilder.getStandOutAdProduct());
        checkout.addProduct(InputBuilder.getPremiumAdProduct());

        return checkout;
    }

    public static CustomerCheckout getSecondBiteCheckout() {
        CustomerCheckout checkout = new CustomerCheckout(InputBuilder.getSecondBite());
        checkout.addProduct(InputBuilder.getClassicAdProduct());
        checkout.addProduct(InputBuilder.getClassicAdProduct());
        checkout.addProduct(InputBuilder.getClassicAdProduct());
        checkout.addProduct(InputBuilder.getPremiumAdProduct());

        DealRule classicDealRule = new DealRule("classic_deal_rule", InputBuilder.getClassicAdProduct(), 3, 2);
        checkout.addPricingRules(classicDealRule);

        return checkout;
    }

    public static CustomerCheckout getAxilCoffeeCheckout() {
        CustomerCheckout checkout = new CustomerCheckout(InputBuilder.getAxilCoffee());
        checkout.addProduct(InputBuilder.getStandOutAdProduct());
        checkout.addProduct(InputBuilder.getStandOutAdProduct());
        checkout.addProduct(InputBuilder.getStandOutAdProduct());
        checkout.addProduct(InputBuilder.getPremiumAdProduct());

        PriceDiscountRule priceDiscountRule = new PriceDiscountRule("standout_discount_rule", InputBuilder.getStandOutAdProduct(), new BigDecimal(299.99));
        checkout.addPricingRules(priceDiscountRule);

        return checkout;
    }

    public static CustomerCheckout getMyerCheckout() {
        CustomerCheckout checkout = new CustomerCheckout(InputBuilder.getMyer());
        checkout.addProduct(InputBuilder.getStandOutAdProduct());
        checkout.addProduct(InputBuilder.getStandOutAdProduct());
        checkout.addProduct(InputBuilder.getStandOutAdProduct());
        checkout.addProduct(InputBuilder.getStandOutAdProduct());
        checkout.addProduct(InputBuilder.getStandOutAdProduct());
        checkout.addProduct(InputBuilder.getPremiumAdProduct());

        PriceDiscountRule priceDiscountRule = new PriceDiscountRule("premium_discount_rule", InputBuilder.getPremiumAdProduct(), new BigDecimal(389.99), 1);
        DealRule dealRule = new DealRule("standout_deal_rule", InputBuilder.getStandOutAdProduct(), 5, 4, 2);

        checkout.addPricingRules(priceDiscountRule);
        checkout.addPricingRules(dealRule);

        return checkout;
    }

    public static Product getClassicAdProduct() {
        return new Product("classic_ad", "classic add", "basic level advertisement", new BigDecimal(269.99));
    }

    public static Product getStandOutAdProduct() {
        return new Product("standout_ad", "standout add", "company logo and longer presentation text", new BigDecimal(322.99));
    }

    public static Product getPremiumAdProduct() {
        return new Product("premium_ad", "premium add", "higher visibility", new BigDecimal(394.99));
    }

    public static Customer getSecondBite() {
        return new Customer("second_bite", "Second Bite");
    }

    public static Customer getAxilCoffee() {
        return new Customer("axil_coffee", "Axil Coffee Roasters");
    }

    public static Customer getMyer() {
        return new Customer("myer", "Myer");
    }

    Customer createCustomer(String id, String name) {
        return new Customer(id, name);
    }
}
