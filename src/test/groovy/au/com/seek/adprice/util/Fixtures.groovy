package au.com.seek.adprice.util

import au.com.seek.adprice.model.Product

trait Fixtures {

    Product createProduct(Map bindings = [:]) {
        def defaults = [
                description: 'ad product',
                price      : 0.0,
                name       : 'ad product'
        ]
        def options = [:] << defaults << bindings
        new Product(options)
    }

}
