package com.lambda.test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.lambda.bean.Bond;
import com.lambda.bean.Loan;
import com.lambda.bean.Product;
import com.lambda.bean.Stock;
import com.lambda.function.TriFunction;

@SuppressWarnings("all")
public class ProductTest {

	//无参构造函数
	static Map<String, Supplier<Product>> map = new HashMap<>();

	//假设构造函数有多个参数
	static Map<String, TriFunction<Integer, Integer, String, Product>> map1 = new HashMap<>();
	
	static {
		map1.put("loan", Loan::new);
		map.put("loan", Loan::new);
		map.put("stock", Stock::new);
		map.put("bond", Bond::new);

	}

	public static Product createProduct(String name) {
		Supplier<Product> p = map.get(name);
		if (p != null)
			return p.get();
		throw new IllegalArgumentException("No such product " + name);
	}

	public static void main(String[] args) {
		// 工厂模式

		createProduct("loan");
		
		Product p = ProductFactory.createProduct("loan");

		Product p2 = new Loan();
		
		TriFunction<Integer,Integer,String,Product>  triFunction=Loan::new;
		Loan loan= (Loan) triFunction.apply(123456, 200, "小栗子");
		
		loan.filling();
		
		Supplier<Product> loanSupplier = Loan::new;
		Loan p1 = (Loan) loanSupplier.get();
		
		p1.filling();
	}
}
