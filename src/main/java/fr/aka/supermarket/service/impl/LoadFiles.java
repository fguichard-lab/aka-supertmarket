/**
 * 
 */
package fr.aka.supermarket.service.impl;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.aka.supermarket.models.Discount;
import fr.aka.supermarket.models.Item;
import fr.aka.supermarket.models.ItemToBuy;
import fr.aka.supermarket.service.ILoadFiles;
import fr.aka.supermarket.utils.CheckoutUtils;

/**
 * @author fguichard
 *
 */
@Service
public class LoadFiles implements ILoadFiles{
	
	@Value("${path.filenames.cart}")
	String cartFileName;
	
	@Value("${path.filenames.item}") 
	String itemFileName;

	@Value("${path.filenames.discount}") 
	String discountFileName;
	
	/*Load the file containing the items to buy
	 * 
	 */
	public List<ItemToBuy> loadCartFile(List<Item> items) throws RuntimeException {
		

		//load the item json list
		List<ItemToBuy> itemsToBuy = null;
		
		try {
		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get(cartFileName));
		    
		    // convert JSON array to list of items
		    itemsToBuy = new Gson().fromJson(reader, new TypeToken<List<ItemToBuy>>() {}.getType());

		    //load item 
	    	loadItem(itemsToBuy, items);	
		    
		    // close reader
		    reader.close();
		} catch (Exception ex) {
		    throw new RuntimeException("the file of the cart "+cartFileName+" is missing");
		} 
		return itemsToBuy;
	}

	/*Load the file which describe the different items
	 * 
	 */
	public List<Item> loadItemFile() throws RuntimeException{

		List<Item >items = null;

		try {
		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get(itemFileName));

		    // convert JSON array to list of items
		    items = new Gson().fromJson(reader, new TypeToken<List<Item>>() {}.getType());

		    // close reader
		    reader.close();
		} catch (Exception ex) {
			throw new RuntimeException("the file of the cart "+itemFileName+" is missing");
		} 
		return items;		
	}

	@Override
	/*Load the file containing the discount to apply
	 * 
	 */
	public List<Discount> loadDiscountFile() throws RuntimeException {
		
		List<Discount> discounts = null;

		try {
		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get(discountFileName));

		    // convert JSON array to list of discount
		    discounts = new Gson().fromJson(reader, new TypeToken<List<Discount>>() {}.getType());

		    // close reader
		    reader.close();

		} catch (Exception ex) {
			throw new RuntimeException("the file of the cart "+discountFileName+" is missing");
		}
		return discounts;
	}


	/*
	 *@param List<ItemToBuy>  
	 *@param List<Item> items
	 * 
	 */
	private void loadItem(List<ItemToBuy> itemsToBuy, List<Item> items)  {
		
		for(ItemToBuy itemToBuy: itemsToBuy) {
			for(Item item: items) {
				if (itemToBuy.getItemBought().equals(item.getCode())) {
					itemToBuy.setPriceKilo(item.getPriceKilo());	
					itemToBuy.setCode(item.getCode());
					itemToBuy.setName(item.getName());
					itemToBuy.setPriceUnit(item.getPriceUnit());
					break;
				}
			}	
		}

	}

	
}
