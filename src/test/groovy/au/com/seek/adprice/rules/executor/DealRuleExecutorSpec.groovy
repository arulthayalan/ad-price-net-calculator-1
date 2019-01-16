package au.com.seek.adprice.rules.executor

import au.com.seek.adprice.model.Product
import au.com.seek.adprice.rules.DealRule
import au.com.seek.adprice.rules.PriceDiscountRule
import au.com.seek.adprice.rules.PricingRule
import au.com.seek.adprice.util.Fixtures
import org.hamcrest.CoreMatchers
import org.junit.Assert
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class DealRuleExecutorSpec extends Specification implements Fixtures {

    @Subject
    DealRuleExecutor dealRuleExecutor = new DealRuleExecutor()

    @Unroll
    def "Should return #result when product list is #scenario"() {
        given:
        Product product = createProduct([id: 'abc', price: 1.0])
        PricingRule pricingRules = new DealRule(id: 'deal_rule', product: product, forItems: 3, dealItems: 2)

        expect:
        dealRuleExecutor.execute(input, pricingRules) == result

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
        def actual = dealRuleExecutor.execute([product], input).size()

        then:
        assert actual == result

        where:
        scenario       | input                                                                                                         || result
        'null'         | null                                                                                                          || 1
        'not instance' | new PriceDiscountRule(id: 'deal_rule', product: createProduct([id: 'abc', price: 1.0]), discountedPrice: 0.0) || 1
    }

    def "Should return 6 items when 3 for 2 with other products"() {
        given:
        Product abc = createProduct([id: 'abc', price: 1.0])
        Product defp = createProduct([id: 'def', price: 1.0])
        PricingRule pricingRule = new DealRule(id: 'deal_rule', product: abc, forItems: 3, dealItems: 2)
        List<Product> productList = [abc, abc, abc, abc, abc, abc, abc, defp]

        when:
        List<Product> actual = dealRuleExecutor.execute(productList, pricingRule)

        then:
        assert actual.size() == 6
        Assert.assertThat(actual, CoreMatchers.hasItem(defp))
    }

}
