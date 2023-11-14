package com.example.demo;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ItemController {
	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	CategoryRepository categoryRepo;

@GetMapping("/item")
private String viewItem(Model model) {
	List<Item> listItems=itemRepo.findAll();
	model.addAttribute("listItems", listItems);
	List<Category> listCategories=categoryRepo.findAll();
	model.addAttribute("listCategories", listCategories); 
	return "view_item";
}

@GetMapping("/addItem")
public String addItem(Model model) {
	Item item = new Item();
	model.addAttribute("item", item);
	List<Category> listCategories = categoryRepo.findAll();
	model.addAttribute("listCategories", listCategories);
	return "add_item";
}

@PostMapping("/saveItem")
public String saveItem(@Valid Item item, BindingResult bindingResult,@RequestParam("imageName") MultipartFile imgFile,Model model) {
	if(bindingResult.hasErrors()) {

		// get data from category
		List<Category> listCategories = categoryRepo.findAll();
		model.addAttribute("listCategories", listCategories);
		return "add_item";
	}
	String imgName = imgFile.getOriginalFilename();
	item.setImgName(imgName);
	item.setDescription("Best Ever Premium Quality");
	Item savedItem=itemRepo.save(item);
	
			try {
			String uploadDir="uploads/items/" +savedItem.getId();
			Path uploadPath=Paths.get(uploadDir);
			if(!Files.exists(uploadPath)) {
              Files.createDirectories(uploadPath);
			}
			Path fileToCreatePath=uploadPath.resolve(imgName);
			System.out.println("File path: " +fileToCreatePath);
			
			Files.copy(imgFile.getInputStream(), fileToCreatePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	return "redirect:/item";

}

//view single item
@GetMapping("/item{id}")
public String viewSingleItem(@PathVariable("id") Integer id, Model model) {
	Item item = itemRepo.getReferenceById(id);
	model.addAttribute(item);
	List<Category> listCategories=categoryRepo.findAll();
	model.addAttribute("listCategories", listCategories); 
	return "view_single_item";
}

@GetMapping("/edititem{id}")
public String editItem(@PathVariable("id") Integer id, Model model) {
	Item item = itemRepo.getReferenceById(id);
	model.addAttribute("item", item);
	List<Category> listCategories = categoryRepo.findAll();
	model.addAttribute("listCategories", listCategories);
	return "edit_item";
}

@PostMapping("/editsaveItem{id}/{img}")
public String saveUpdatedItem(Item item, @PathVariable("id") Integer id, @PathVariable("img") String imgName,
		@RequestParam("imageName") MultipartFile imgFile) throws IOException {
    System.out.println(imgName);
	item.setImgName(imgName);
	item.setDescription("Best Ever Premium Quality");
	if (!imgFile.isEmpty()) {
		imgName = imgFile.getOriginalFilename();

		// set the image name in item object
		item.setImgName(imgName);

		// prepare the directory path
		String uploadDir = "uploads/items/" + item.getId();
		Path uploadPath = Paths.get(uploadDir);

//		Path imagePathToDelete = uploadPath.resolve(imgName);

		File fileToDelete = new File(uploadDir, imgName);
		fileToDelete.delete();

		// Delete the image file
//		Files.delete(imagePathToDelete);
	}
	// save the item obj to the db
	Item savedItem = itemRepo.save(item);

	try {
		// prepare the directory path
		String uploadDir = "uploads/items/" + savedItem.getId();
		Path uploadPath = Paths.get(uploadDir);

		// check if the upload path exists, if not it will be created
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		// prepare path for file
		Path fileToCreatePath = uploadPath.resolve(imgName);
		System.out.println("File path: " + fileToCreatePath);

		// copy file to the upload location
		Files.copy(imgFile.getInputStream(), fileToCreatePath, StandardCopyOption.REPLACE_EXISTING);

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    itemRepo.save(item);
	
	return "redirect:/item";
}

@GetMapping("deleteitem{id}")
public String deleteCategory(@PathVariable("id") Integer id) {
	itemRepo.deleteById(id);
	return "redirect:/item";
}

@GetMapping("/viewuser")
public String viewUser(Model model) {
	List <Item> itemList=itemRepo.findAll();
	model.addAttribute("itemList", itemList);
	List<Category> listCategories=categoryRepo.findAll();
	model.addAttribute("listCategories", listCategories); 
	return "view_user";
}
  @GetMapping("/viewusercategory{id}") 
  public String viewUserByCategory(Model model,@PathVariable("id") Integer id) { 
  Category category=categoryRepo.getReferenceById(id); 
  List<Item> itemlist=itemRepo.findAllByCategory(category); 
  model.addAttribute("itemList",itemlist); 
  List<Category> listCategories=categoryRepo.findAll();
  model.addAttribute("listCategories", listCategories); 
  return "view_user_bycategory"; 
  }
 
//view single item
@GetMapping("/detailitem{id}")
public String viewDetail(@PathVariable("id") Integer id, Model model) {
	Item item = itemRepo.getReferenceById(id);
	
	List<Category> listCategories = categoryRepo.findAll();
	model.addAttribute("listCategories", listCategories);
	
	model.addAttribute(item);
	 	return "view_detail";
}
  }
 
