package com.example.demo.Controller;


import com.example.demo.Entity.Product;
import com.example.demo.Entity.Promotion;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.GemPriceListService;
import com.example.demo.Service.MaterialPriceListService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.PromotionService;
import com.example.demo.Service.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final GemPriceListService gemPriceListService;
    private final MaterialPriceListService materialPriceListService;
    private final TypeService typeService;
    private final PromotionService promotionService;
    @Autowired
    private StaffRepository staffRepository;

    public ProductController(ProductService productService, CategoryService categoryService,GemPriceListService gemPriceListService,
    		MaterialPriceListService materialPriceListService,TypeService typeService,PromotionService promotionService ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.gemPriceListService = gemPriceListService;
        this.materialPriceListService = materialPriceListService;
        this.typeService = typeService;
        this.promotionService = promotionService;
    }
    @GetMapping("seller/products")
    public String showProductSeller(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Product> productPage = productService.findAll(PageRequest.of(page, 10));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "seller/productList";
    }
  

    @GetMapping("manager/products")
    public String showProductList(Model model, @RequestParam(defaultValue = "0") int page) {
    	Page<Product> productPage = productService.findAll(
    	        PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "productID"))
    	    );
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages()); 
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/productList";
    }
    @GetMapping("seller/products/detail-product/{productID}") 
    public String showProductDetailSeller(@PathVariable Integer productID, Model model) { 
        Product product = productService.findById(productID).orElseThrow(() -> new RuntimeException("Product not found")); 
        model.addAttribute("product", product);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "seller/detailProduct";
    }

    @GetMapping("manager/products/detail-product/{productID}") 
    public String showProductDetail(@PathVariable Integer productID, Model model) { 
        Product product = productService.findById(productID).orElseThrow(() -> new RuntimeException("Product not found")); 
        model.addAttribute("product", product);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/detailProduct";
    }
    
     
    @PostMapping("manager/products/detail-product/{productID}/update") 
    public String updateProduct(@PathVariable Integer productID, Product product, Model model) { 
        product.setProductID(productID);
        productService.updateProduct(product);
        model.addAttribute("product", product);
        model.addAttribute("updateSuccess", true); 
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/detailProduct"; 
    }

    @PostMapping("manager/products/{productID}/soldOut")
    public String deleteProduct(@PathVariable Integer productID, Model model) { 
        Product product = productService.findById(productID).orElseThrow(() -> new RuntimeException("Product not found")); 
        product.setActive(false);
        productService.updateProduct(product);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
	       return "redirect:/manager/products";
    }

    @GetMapping("manager/products/create-product")
    public String showCreateProductForm(Model model) {
    	 model.addAttribute("product", new Product());
         model.addAttribute("categories", categoryService.getAllCategorys());
         model.addAttribute("gemPriceLists", gemPriceListService.getAllGemPriceLists());
         model.addAttribute("materialPriceLists", materialPriceListService.getAllMaterialPriceLists());
         model.addAttribute("types", typeService.getAllTypes());
         model.addAttribute("products", productService.findAllProduct());
         String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/createNewProduct";
    }

    @PostMapping("manager/products/create-product/create")
    public String saveroduct(Product product,Model model) {
        productService.saveProduct(product);
        model.addAttribute("product",product);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/createNewProduct";
    }
  
    @GetMapping("/promotion")
    public String showPromotionList(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Promotion> promotionPage = promotionService.findAll(PageRequest.of(page, 10));
        model.addAttribute("promotions", promotionPage); 
        model.addAttribute("currentPage", promotionPage.getNumber());
        model.addAttribute("totalPages", promotionPage.getTotalPages()); 
        model.addAttribute("promotion", new Promotion()); 
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/promotion";
    }

    @PostMapping("/promotion")
 	public String savePromotionList(Model model,Promotion promotion) {
    	 String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
    	 promotionService.save(promotion); 
    	 return "redirect:/promotion";
     }
    @GetMapping("/price-list")
    public String showPriceList() {
        return "manager/priceList";
    }
    
}
