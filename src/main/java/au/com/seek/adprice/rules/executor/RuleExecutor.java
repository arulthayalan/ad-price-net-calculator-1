package au.com.seek.adprice.rules.executor;


import au.com.seek.adprice.model.Product;
import au.com.seek.adprice.rules.PricingRule;

import java.util.List;

public interface RuleExecutor {
    List<Product> execute(List<Product> productList, PricingRule pricingRule);
}
