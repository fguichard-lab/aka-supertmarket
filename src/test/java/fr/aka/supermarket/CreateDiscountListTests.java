/**
 * 
 */
package fr.aka.supermarket;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.ParseException;

import fr.aka.supermarket.models.Discount;
import fr.aka.supermarket.service.impl.LoadFiles;

/**
 * @author fguichard
 *
 */
@SpringBootTest
public class CreateDiscountListTests {

	@Value("${path.filenames.discount}") 
	String discountFileName;
	
	List<Discount> discounts;
	
	LinkedHashMap<Discount,Integer> lhpDiscount;
	
	@Autowired
	LoadFiles loadFiles;
	
	@BeforeEach
	public void initialiser()  {
		
		discounts = loadFiles.loadDiscountFile();

		createListDiscount();

		
	}
	
	public void createListDiscount() {
		lhpDiscount = new LinkedHashMap<Discount,Integer>();
		
		float quantity = 0;
		
		//Add all the items of the list in the cart
		for(Discount discount: discounts) {
			
			
			if (!lhpDiscount.containsKey(discount)) {
				lhpDiscount.put(discount, (int) quantity);
			}
			
		}
	}
	
	@Test
	public void when_ItemCode_234_found_and_quantity_equal_3_then_true() throws ParseException{
		
		 for (Entry<Discount, Integer> mapElement : lhpDiscount.entrySet()) {
       	  
	            Discount discountKey = mapElement.getKey();

	            // print the key : value pair
	            System.out.println(discountKey);
	            
	            if (discountKey.getItemCode() == 234) {
	            	if (discountKey.getQuantity() == 3)
		            	assertTrue("The quantity is 3.0", true);
		            else
		            	assertTrue("The quantity is not 3.0", false);
	            	break;
	            }
	      }
	}
}
