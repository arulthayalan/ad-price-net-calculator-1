package au.com.seek.adprice;

import au.com.seek.adprice.model.Product;

import java.math.BigDecimal;

public interface CheckOut {

    void addProduct(Product product);

    BigDecimal total();
}
