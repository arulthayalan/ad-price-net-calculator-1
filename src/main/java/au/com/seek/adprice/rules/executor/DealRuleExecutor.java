package au.com.seek.adprice.rules.executor;

import au.com.seek.adprice.model.Product;
import au.com.seek.adprice.rules.DealRule;
import au.com.seek.adprice.rules.PricingRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DealRuleExecutor implements RuleExecutor {

    @Override
    public List<Product> execute(final List<Product> productList, final PricingRule pricingRule) {
        if (notValid(productList, pricingRule)) {
            return productList;
        }

        DealRule dealRule = (DealRule) pricingRule;

        List<Product> resultProductList = new ArrayList<>();

        int totalItems = (int) productList.stream()
                .filter(Objects::nonNull)
                .filter(p -> p.getId().equals(dealRule.getProduct().getId()))
                .count();

        int forDealGroups = groups(totalItems, dealRule);
        int totalItemsToBeKept = itemsToBeKept(forDealGroups, totalItems, dealRule);

        resultProductList.addAll(limitDealProducts(productList, dealRule, totalItemsToBeKept));
        resultProductList.addAll(filterNonDealProducts(productList, dealRule));

        return resultProductList;
    }

    private int groups(int totalItems, DealRule dealRule) {
        return (totalItems / dealRule.getForItems());
    }

    private int itemsToBeKept(int groups, int totalItems, DealRule dealRule) {
        return (totalItems - (groups * dealRule.getForItems())) + (groups * dealRule.getDealItems());
    }

    private List<Product> limitDealProducts(List<Product> allProducts, DealRule dealRule, int totalDealItemsToBeKept) {
        return allProducts.stream()
                .filter(Objects::nonNull)
                .filter(p -> p.getId().equals(dealRule.getProduct().getId()))
                .limit(totalDealItemsToBeKept)
                .collect(Collectors.toList());
    }

    private List<Product> filterNonDealProducts(List<Product> allProducts, DealRule dealRule) {
        return allProducts.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.getId().equals(dealRule.getProduct().getId()))
                .collect(Collectors.toList());
    }

    private boolean notValid(List<Product> productList, PricingRule pricingRule) {
        return productList == null || productList.isEmpty() || pricingRule == null || !(pricingRule instanceof DealRule);
    }
}
