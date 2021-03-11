package javabase;

import java.lang.ref.WeakReference;

/**
 * @author HaoHao
 * @date 2021/3/11 4:15 下午
 */
public class WeakReferenceTest {

    private Product product = new Product("1");

    WeakReference<Product> weakReference = new WeakReference<Product>(product);


    static class Product extends WeakReference<String> {


        public Product(String referent) {
            super(referent);
        }
    }
}
