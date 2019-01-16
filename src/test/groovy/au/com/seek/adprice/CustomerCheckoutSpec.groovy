package au.com.seek.adprice

import au.com.seek.adprice.model.Customer
import au.com.seek.adprice.model.Product
import au.com.seek.adprice.rules.DealRule
import au.com.seek.adprice.rules.PriceDiscountRule
import au.com.seek.adprice.util.Fixtures
import spock.lang.Specification
import spock.lang.Subject


class CustomerCheckoutSpec extends Specification implements Fixtures {

    @Subject
    CustomerCheckout checkout;

    def "Should return total price for given default price for a customer"() {
        given:
        checkout = new CustomerCheckout(InputBuilder.getSecondBite())
        checkout.addProduct(InputBuilder.getClassicAdProduct())
        checkout.addProduct(InputBuilder.getStandOutAdProduct())
        checkout.addProduct(InputBuilder.getPremiumAdProduct())

        expect:
        checkout.total().doubleValue() == 987.97
    }

    def "Should return total price rule for customer with deal price rule"() {
        given:
        checkout = new CustomerCheckout(InputBuilder.getSecondBite())
        checkout.addProduct(InputBuilder.getClassicAdProduct())
        checkout.addProduct(InputBuilder.getClassicAdProduct())
        checkout.addProduct(InputBuilder.getClassicAdProduct())
        checkout.addProduct(InputBuilder.getPremiumAdProduct())

        DealRule classicDealRule = new DealRule('classic_deal_rule', InputBuilder.getClassicAdProduct(), 3, 2)
        checkout.addPricingRules(classicDealRule)

        expect:
        checkout.total().doubleValue() == 934.97
    }

    def "Should return total price rule for customer with discount price rule"() {
        given:
        checkout = new CustomerCheckout(InputBuilder.getAxilCoffee())
        checkout.addProduct(InputBuilder.getStandOutAdProduct())
        checkout.addProduct(InputBuilder.getStandOutAdProduct())
        checkout.addProduct(InputBuilder.getStandOutAdProduct())
        checkout.addProduct(InputBuilder.getPremiumAdProduct())

        PriceDiscountRule priceDiscountRule = new PriceDiscountRule('standout_discount_rule', InputBuilder.getStandOutAdProduct(), 299.99)
        checkout.addPricingRules(priceDiscountRule)

        expect:
        checkout.total().doubleValue() == 1294.96
    }

    def "Should return total price rule for customer with deal and discount price rule"() {
        given:
        checkout = new CustomerCheckout(InputBuilder.getMyer())
        checkout.addProduct(InputBuilder.getStandOutAdProduct())
        checkout.addProduct(InputBuilder.getStandOutAdProduct())
        checkout.addProduct(InputBuilder.getStandOutAdProduct())
        checkout.addProduct(InputBuilder.getStandOutAdProduct())
        checkout.addProduct(InputBuilder.getStandOutAdProduct())
        checkout.addProduct(InputBuilder.getPremiumAdProduct())

        PriceDiscountRule priceDiscountRule = new PriceDiscountRule('premium_discount_rule', InputBuilder.getPremiumAdProduct(), 389.99, 1)
        DealRule dealRule = new DealRule('standout_deal_rule', InputBuilder.getStandOutAdProduct(), 5, 4, 2)

        checkout.addPricingRules(priceDiscountRule)
        checkout.addPricingRules(dealRule)

        expect:
        checkout.total().doubleValue() == 1681.95
    }

}
