/**
 * 
 */
package fr.aka.supermarket.service;

import java.util.LinkedHashMap;
import java.util.List;

import fr.aka.supermarket.models.Discount;
import fr.aka.supermarket.models.ItemToBuy;

/**
 * @author fguichard
 *
 */
public interface IPricing {

	public float calculateDefaultPricing(LinkedHashMap<Integer, Float> cart, ItemToBuy itemToBuy, List<Discount> discounts);
	
	public float calculatePackagePricing(LinkedHashMap<Integer, Float> cart, ItemToBuy itemToBuy, List<Discount> discounts);
}
