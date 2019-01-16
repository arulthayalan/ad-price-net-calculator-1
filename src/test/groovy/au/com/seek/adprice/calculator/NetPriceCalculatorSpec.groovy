package au.com.seek.adprice.calculator

import au.com.seek.adprice.model.Product
import au.com.seek.adprice.rules.DealRule
import au.com.seek.adprice.rules.PriceDiscountRule
import au.com.seek.adprice.rules.PricingRule
import au.com.seek.adprice.util.Fixtures
import spock.lang.Specification

class NetPriceCalculatorSpec extends Specification implements Fixtures {


    def "Should return net value for given discount rule"() {
        given:
        Product abc = createProduct([id: 'abc', price: 2.0])
        PricingRule pricingRule = new PriceDiscountRule(id: 'discount_rule', product: abc, discountedPrice: 0.25)
        when:
        double actual = NetPriceCalculator.calculate([abc, abc], [pricingRule])

        then:
        assert actual == 0.5.doubleValue()
    }

    def "Should return net value for given deal rule"() {
        given:
        Product abc = createProduct([id: 'abc', price: 2.0])

        PricingRule pricingRule = new DealRule(id: 'deal_rule', product: abc, forItems: 3, dealItems: 2)
        when:
        double actual = NetPriceCalculator.calculate([abc, abc, abc, abc, abc, abc, abc], [pricingRule])

        then:
        assert actual == 10.0.doubleValue()
    }

    def "Should return net value for given deals"() {
        given:
        Product abc = createProduct([id: 'abc', price: 2.0])
        Product xyz = createProduct([id: 'xyz', price: 1.0])

        PricingRule dealRule = new DealRule(id: 'deal_rule', product: abc, forItems: 3, dealItems: 2)
        dealRule.executionOrder = 2

        PricingRule discountRule = new PriceDiscountRule(id: 'discount_rule', product: abc, discountedPrice: 0.25)
        discountRule.executionOrder = 1

        when:
        double actual = NetPriceCalculator.calculate([abc, abc, abc, abc, abc, abc, abc, xyz], [dealRule, discountRule])

        then:
        assert actual == 2.25.doubleValue()
    }

}
