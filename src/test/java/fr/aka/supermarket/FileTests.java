/**
 * 
 */
package fr.aka.supermarket;

import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.ParseException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.aka.supermarket.models.Discount;
import fr.aka.supermarket.models.Item;

/**
 * @author fguichard
 *
 */
@SpringBootTest
public class FileTests {
	
	
	@Value("${path.filenames.test.item}") 
	String itemFileName;

	@Value("${path.filenames.test.discount}") 
	String discountFileName;
		
	List<Item> items;
	List<Discount> discounts;
	
	
	@BeforeEach
	public void initialiser() {
		System.out.println("filename"+itemFileName);
	}
	


	@Test
	public void when_ItemCode_234_found_then_true() throws ParseException{
		
		items = new ArrayList<>();
		
		try {
		    // create Gson instance
		    Gson gson = new Gson();

		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get(itemFileName));

		    // convert JSON array to list of items
		    items = new Gson().fromJson(reader, new TypeToken<List<Item>>() {}.getType());

		    // print items
		    items.forEach(System.out::println);
		    
		    for(Item item: items) {
		    	if (item.getCode().hashCode()==234) {
		    		assertTrue("The item 234 has been found", true);
		    		break;
		    	}
		    }
		    

		    // close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	@Test
	public void when_DiscountName_xBuyYFree_For_Item_123_then_True() throws ParseException{
		

		discounts = new ArrayList<>();


		try {
		    // create Gson instance
		    Gson gson = new Gson();

		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get(discountFileName));

		    // convert JSON array to list of discount
		    discounts = new Gson().fromJson(reader, new TypeToken<List<Discount>>() {}.getType());

		    // print discounts
		    discounts.forEach(System.out::println);
		    
		    for(Discount discount : discounts) {
		    	if (discount.getItemCode().hashCode()==123 &&
		    		"xBuyYFree".equalsIgnoreCase(discount.getName())){
		    		assertTrue("The discount for the item 123 has been found", true);
		    		break;
		    	}
		    }

		    // close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
}
