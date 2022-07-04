package com.example.demo.Processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.Model.Product;

public class ProductProcessor implements ItemProcessor<Product, Product>{

	@Override
	public Product process(Product item) throws Exception {
		
		Double cost = item.getProdcost();
		item.setProddisc(cost*10/100.0);
		item.setProdgst(cost*14/100.0);
		return item;
		
	}

}
