package au.com.seek.adprice.rules;

import au.com.seek.adprice.model.Product;
import au.com.seek.adprice.rules.executor.DealRuleExecutor;

public class DealRule extends PricingRule {

    private int dealItems;

    private int forItems;

    public DealRule() {
        setRuleExecutor();
    }

    public DealRule(final String id, final Product product, final int forItems, final int dealItems) {
        super(id, product);
        this.forItems = forItems;
        this.dealItems = dealItems;
        setRuleExecutor();
    }

    public DealRule(final String id, final Product product, final int forItems, final int dealItems, int executionOrder) {
        this(id, product, forItems, dealItems);
        setExecutionOrder(executionOrder);
        setRuleExecutor();
    }

    public int getDealItems() {
        return dealItems;
    }

    public int getForItems() {
        return forItems;
    }

    @Override
    void setRuleExecutor() {
        super.ruleExecutor = new DealRuleExecutor();
    }

}
