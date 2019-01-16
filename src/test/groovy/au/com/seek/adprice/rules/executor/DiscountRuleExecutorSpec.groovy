package au.com.seek.adprice.rules.executor

import au.com.seek.adprice.model.Product
import au.com.seek.adprice.rules.DealRule
import au.com.seek.adprice.rules.PriceDiscountRule
import au.com.seek.adprice.rules.PricingRule
import au.com.seek.adprice.util.Fixtures
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class DiscountRuleExecutorSpec extends Specification implements Fixtures {

    @Subject
    DiscountRuleExecutor discountRuleExecutor = new DiscountRuleExecutor()

    @Unroll
    def "Should return #result when product list is #scenario"() {
        given:
        Product product = createProduct([id: 'abc', price: 1.0])
        PricingRule pricingRule = new PriceDiscountRule(id: 'discount_rule', product: product, discountedPrice: 0.5)

        expect:
        discountRuleExecutor.execute(input, pricingRule) == result

        where:
        scenario | input             || result
        'null'   | null              || null
        'empty'  | new ArrayList<>() || new ArrayList<>()
    }

    @Unroll
    def "Should return #result when price is #scenario"() {

        given:
        Product product = createProduct([id: 'abc', price: 1.0])

        when:
        def actual = discountRuleExecutor.execute([product], input).size()

        then:
        assert actual == result

        where:
        scenario       | input                                                                                                     || result
        'null'         | null                                                                                                      || 1
        'not instance' | new DealRule(id: 'deal_rule', product: createProduct([id: 'abc', price: 1.0]), forItems: 3, dealItems: 2) || 1
    }

    def "Should return new price in product"() {
        given:
        Product abc = createProduct([id: 'abc', price: 1.0])
        Product defp = createProduct([id: 'def', price: 2.0])
        PricingRule pricingRule = new PriceDiscountRule(id: 'discount_rule', product: abc, discountedPrice: 0.5)
        List<Product> productList = [abc, abc, abc, defp]

        when:
        List<Product> actual = discountRuleExecutor.execute(productList, pricingRule)

        then:
        assert actual.size() == 4
        assert actual.count {
            it.id == 'abc' && it.price.doubleValue() == 0.5
        } == 3

        assert actual.count {
            it.id == 'def' && it.price.doubleValue() == 2.0
        } == 1
    }

}
