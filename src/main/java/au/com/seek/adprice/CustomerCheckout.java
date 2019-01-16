package au.com.seek.adprice;

import au.com.seek.adprice.calculator.NetPriceCalculator;
import au.com.seek.adprice.model.Customer;
import au.com.seek.adprice.model.Product;
import au.com.seek.adprice.rules.PricingRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomerCheckout implements CheckOut {

    private final Customer customer;

    private List<PricingRule> pricingRules = new ArrayList<>();

    private List<Product> items = new ArrayList<>();

    public CustomerCheckout(final Customer customer) {
        this.customer = customer;
    }

    public void addProduct(final Product product) {
        if (product != null) {
            items.add(product);
        }
    }

    public CustomerCheckout addPricingRules(final PricingRule pricingRule) {
        if (pricingRule != null) {
            pricingRules.add(pricingRule);
        }
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public BigDecimal total() {
        BigDecimal total = new BigDecimal(0.00);
        if (items.isEmpty()) {
            return total;
        }
        return new BigDecimal(NetPriceCalculator.calculate(items, pricingRules));
    }
}
