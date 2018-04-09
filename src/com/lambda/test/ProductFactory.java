package com.lambda.test;

import com.lambda.bean.Bond;
import com.lambda.bean.Loan;
import com.lambda.bean.Product;
import com.lambda.bean.Stock;

public class ProductFactory {
    public static Product createProduct(String name){
        switch(name){
            case "loan": return new Loan();
            case "stock": return new Stock();
            case "bond": return new Bond();
            default: throw new RuntimeException("No such product " + name);
} }
}
