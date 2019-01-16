package au.com.seek.adprice.calculator;

import au.com.seek.adprice.model.Product;
import au.com.seek.adprice.rules.PricingRule;

import java.util.ArrayList;
import java.util.List;

public class NetPriceCalculator {

    public static double calculate(List<Product> checkoutProducts, List<PricingRule> pricingRules) {

        if (checkoutProducts == null || checkoutProducts.isEmpty()) {
            return 0.0;
        }

        if (pricingRules == null) {
            pricingRules = new ArrayList<>();
        }

        List<Product> ruleAppliedProduct = new ArrayList<>();
        ruleAppliedProduct.addAll(checkoutProducts);

        for (PricingRule pricingRule : pricingRules) {
            ruleAppliedProduct = pricingRule.getRuleExecutor().execute(ruleAppliedProduct, pricingRule);
        }

        return ruleAppliedProduct.stream()
                .mapToDouble(p -> p.getPrice().doubleValue())
                .sum();
    }
}
