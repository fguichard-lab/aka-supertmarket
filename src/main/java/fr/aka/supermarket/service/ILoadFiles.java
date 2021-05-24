/**
 * 
 */
package fr.aka.supermarket.service;

import java.util.List;

import fr.aka.supermarket.models.Discount;
import fr.aka.supermarket.models.Item;
import fr.aka.supermarket.models.ItemToBuy;

/**
 * @author fguichard
 *
 */
public interface ILoadFiles {
	
	public List<ItemToBuy> loadCartFile(List<Item> items) throws RuntimeException;
	
	
	public List<Item> loadItemFile() throws RuntimeException;
	
	
	public List<Discount> loadDiscountFile() throws RuntimeException;

}
