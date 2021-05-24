/**
 * 
 */
package fr.aka.supermarket.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.aka.supermarket.models.Discount;
import fr.aka.supermarket.models.Item;
import fr.aka.supermarket.models.ItemToBuy;
import fr.aka.supermarket.service.IPricing;
import fr.aka.supermarket.utils.CheckoutUtils;

/**
 * @author fguichard
 *
 */
@Service	
public class Pricing implements IPricing {
	

	@Override
	/*Calculates the amount based on the item price, the selected quantity
	 *
	 * @param ItemToBuy
	 * @param float
	 * @return float
	 */
	public float calculateDefaultPricing(LinkedHashMap<Integer, Float> cart, ItemToBuy itemToBuy, List<Discount> discounts) {
		
		float quantity = cart.get(itemToBuy.getItemBought()) == null ?itemToBuy.getQuantity():cart.get(itemToBuy.getItemBought())+itemToBuy.getQuantity();

		//record the entry in the map
		cart.put(itemToBuy.getItemBought(), quantity);
		
		return (itemToBuy.getQuantity() * itemToBuy.getPriceUnit());
	}


	/*Create the list of discount and promotion 
	 * 
	 * @param List<Discount>
	 * @return LinkedHashMap<Discount,Integer>
	 */
	public LinkedHashMap<Discount,Integer> createListDiscount(List<Discount> discounts) {
		LinkedHashMap<Discount,Integer> lhpDiscount = new LinkedHashMap<Discount,Integer>();
		
		//Add all the items of the list in the cart
		for(Discount discount: discounts) {
			lhpDiscount.putIfAbsent(discount, 0);
		}
		return lhpDiscount;
	}
	
	/*
	 * 
	 * @param LinkedHashMap<Item, Float>
	 * @param ItemToBuy
	 * @param Item
	 * @param List<Discount>
	 * @return float
	 */
	float calculatePricing(LinkedHashMap<Integer, Float> cart, ItemToBuy itemToBuy, Item item,
			List<Discount> discounts) {

		float amount = (float) 0;
		
		if (item.getPriceKilo()) {
			amount += calculateDefaultPricing(cart, itemToBuy, discounts);
		}else {
			amount += calculatePackagePricing(cart, itemToBuy, discounts);
		}
		
		return amount;
	}

	@Override
	/*Calculate the amount based on the price of the item, the quantity selected and the various discounts
	 *
	 * @param LinkedHashMap<Integer, Float>
	 * @param ItemToBuy
	 * @param List<Discount> discounts
	 * @return float
	 */
	public float calculatePackagePricing(LinkedHashMap<Integer, Float> cart, ItemToBuy itemToBuy, List<Discount> discounts) {
		
		float quantity = cart.get(itemToBuy.getItemBought()) == null ?itemToBuy.getQuantity():cart.get(itemToBuy.getItemBought())+itemToBuy.getQuantity();

		float amount = itemToBuy.getQuantity() * itemToBuy.getPriceUnit();
		
		Discount discount =  checkDiscountApplicable(quantity, +itemToBuy.getItemBought(), discounts);
		
		if (discount != null){
			amount -= calculateDiscount(itemToBuy, discount, quantity); 
			cart.put(itemToBuy.getItemBought(), (quantity-discount.getQuantity()));
		}else {
			cart.put(itemToBuy.getItemBought(), quantity);
		}
		
		return amount;
	}

	/*Caculate the amount of the discount 
	 * 
	 * @param Item
	 * @param Discount
	 * @float quantity
	 * @return float
	 * 
	 */
	private float calculateDiscount(ItemToBuy itemToBuy, Discount discount, float quantity) {
		float discountAmount = 0;
		
		if("BuyFor".contains(discount.getName())){
			CheckoutUtils.displayPromotionApplicableOnTheReceipt(itemToBuy, discount);
			discountAmount = discount.getQuantity()*itemToBuy.getPriceUnit();
		}else {
			CheckoutUtils.displayDiscountApplicableOnTheReceipt(itemToBuy, quantity-discount.getQuantity(), discount);
			discountAmount = itemToBuy.getPriceUnit();
		}
		
		
		return discountAmount;
	}


	/* Return the discount applicable to the itemCode
	 * @param quantit
	 * @param List<Discount>
	 * @return Discount
	 */
	private Discount checkDiscountApplicable(float quantity, Integer itemCode, List<Discount> discounts) {
		for(Discount discount: discounts) {
			if (discount.getItemCode().equals(itemCode)) {
				if (discount.getQuantity()<=quantity) {
					return discount;
				}
			}
		}
		return null;
	}

}
