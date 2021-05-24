/**
 * 
 */
package fr.aka.supermarket.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.aka.supermarket.models.Discount;
import fr.aka.supermarket.models.Item;
import fr.aka.supermarket.models.ItemToBuy;
import fr.aka.supermarket.service.ICheckoutService;
import fr.aka.supermarket.utils.CheckoutUtils;

/**
 * @author fguichard
 *
 */
@Service
public class CheckoutService implements ICheckoutService {
	
	@Autowired
	Pricing pricing;

	@Autowired
	LoadFiles loadFiles;
	
	@Override
	public void perform() throws RuntimeException {

		/*Load the file which describe the different items */
		List<Item> items = loadFiles.loadItemFile();
		
		/*Load the file which describe the different items */
		List<ItemToBuy> itemsToBuy = loadFiles.loadCartFile(items);

		/*Load the file containing the discount to apply*/
		List<Discount> discounts = loadFiles.loadDiscountFile();
		
		//Definition of the cart 
		LinkedHashMap<Integer,Float> cart = new LinkedHashMap<>();
		
		String receipt ="";
		
		float bill = (float) 0;	
		

		CheckoutUtils.displayHeaderOnTheReceipt();
		
		
		//Analyse the cart
		for(ItemToBuy itemToBuy: itemsToBuy) {
			
			//Recover the item's data from code
			Item item = loadItem(itemToBuy); 
			CheckoutUtils.displayItemBoughtOnTheReceipt(itemToBuy);
			bill += pricing.calculatePricing(cart, itemToBuy, item, discounts);

			if (!cart.containsKey(itemToBuy.getItemBought())) {
				cart.put(itemToBuy.getItemBought(), itemToBuy.getQuantity());
			}
			
		}
		CheckoutUtils.displayAmountOfTheBillOnTheReceipt(bill);
		
		System.out.println(receipt);

	}


	private Item loadItem(ItemToBuy itemToBuy) {

		Item item = new Item();
		
		item.setCode(itemToBuy.getCode());
		item.setName(itemToBuy.getName());
		item.setPriceKilo(itemToBuy.getPriceKilo());
		item.setPriceUnit(itemToBuy.getPriceUnit());
		
		return item;
	}



}
