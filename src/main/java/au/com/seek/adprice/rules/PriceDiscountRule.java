package au.com.seek.adprice.rules;

import au.com.seek.adprice.model.Product;
import au.com.seek.adprice.rules.executor.DiscountRuleExecutor;

import java.math.BigDecimal;

public class PriceDiscountRule extends PricingRule {

    private BigDecimal discountedPrice;

    public PriceDiscountRule() {
        this.discountedPrice = new BigDecimal(0.0);
        setRuleExecutor();
    }

    public PriceDiscountRule(String id, Product product) {
        super(id, product);
        setRuleExecutor();
    }

    public PriceDiscountRule(final String id, final Product product, final BigDecimal discountedPrice) {
        this(id, product);
        this.discountedPrice = discountedPrice;
        setRuleExecutor();
    }

    public PriceDiscountRule(final String id, final Product product, final BigDecimal discountedPrice, int executionOrder) {
        this(id, product, discountedPrice);
        setExecutionOrder(executionOrder);
        setRuleExecutor();
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    @Override
    void setRuleExecutor() {
        super.ruleExecutor = new DiscountRuleExecutor();
    }
}
