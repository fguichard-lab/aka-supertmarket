/**
 * 
 */
package fr.aka.supermarket;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.aka.supermarket.models.Item;
import fr.aka.supermarket.models.ItemToBuy;
import fr.aka.supermarket.service.impl.LoadFiles;

/**
 * @author fguichard
 *
 */
@SpringBootTest
public class AddItemToCartTests {

	@Value("${path.filenames.test.cart}") 
	String cartFileName;

	@Value("${path.filenames.test.item}") 
	String itemFileName;
	
	String cartFileNameMissing = "cartFileNameMissing";
	
	
	List<ItemToBuy> itemsToBuy;
	
	List<Item> items;

	LinkedHashMap<Item,Float> cart;
	
	
	
	@Autowired
	LoadFiles loadFiles;
	
	@BeforeEach
	public void initialiser()  {

		//Load the file which contente the items to add into the cart
		itemsToBuy = loadFiles.loadCartFile(items);
		
		//Load the file which contente the items to add into the cart
		items = loadFiles.loadItemFile();
		
	}
	
	

		


}
