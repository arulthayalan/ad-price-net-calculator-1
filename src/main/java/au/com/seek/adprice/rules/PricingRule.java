package au.com.seek.adprice.rules;

import au.com.seek.adprice.model.Product;
import au.com.seek.adprice.rules.executor.RuleExecutor;

public abstract class PricingRule {

    protected String id;

    protected Product product;

    protected RuleExecutor ruleExecutor;

    private int executionOrder = 9999;

    public PricingRule() {
    }

    public PricingRule(final String id, final Product product) {
        this.id = id;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(int executionOrder) {
        this.executionOrder = executionOrder;
    }

    abstract void setRuleExecutor();

    public RuleExecutor getRuleExecutor() {
        return ruleExecutor;
    }
}
