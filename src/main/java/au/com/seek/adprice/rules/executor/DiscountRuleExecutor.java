package au.com.seek.adprice.rules.executor;

import au.com.seek.adprice.model.Product;
import au.com.seek.adprice.rules.PriceDiscountRule;
import au.com.seek.adprice.rules.PricingRule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DiscountRuleExecutor implements RuleExecutor {

    @Override
    public List<Product> execute(final List<Product> productList, final PricingRule pricingRule) {
        if (notValid(productList, pricingRule)) {
            return productList;
        }

        PriceDiscountRule priceDiscountRule = (PriceDiscountRule) pricingRule;

        return productList.stream()
                .filter(Objects::nonNull)
                .map(p -> {
                    if (p.getId().equals(priceDiscountRule.getProduct().getId())) {
                        p.setPrice(priceDiscountRule.getDiscountedPrice());
                    }
                    return p;
                })
                .collect(Collectors.toList());
    }

    private boolean notValid(List<Product> productList, PricingRule pricingRule) {
        return productList == null || productList.isEmpty() || pricingRule == null || !(pricingRule instanceof PriceDiscountRule);
    }
}
