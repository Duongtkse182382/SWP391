package com.example.demo.Controller;


import com.example.demo.Entity.Counter;
import com.example.demo.Entity.Gem;
import com.example.demo.Entity.GemPriceList;
import com.example.demo.Entity.MaterialPriceList;
import com.example.demo.Entity.Material;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Promotion;
import com.example.demo.Entity.Staff;
import com.example.demo.Exception.DuplicateCounterNameException;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.CounterService;
import com.example.demo.Service.GemPriceListService;
import com.example.demo.Service.GemService;
import com.example.demo.Service.MaterialPriceListService;
import com.example.demo.Service.MaterialService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.PromotionService;
import com.example.demo.Service.TypeService;
import com.example.demo.saveLog.csvlog;
import com.example.demo.saveLog.csvlog2;
import com.example.demo.Entity.MaterialPriceList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    private ProductService productService;
    private  CategoryService categoryService;
    private GemPriceListService gemPriceListService;
    private  MaterialPriceListService materialPriceListService;
    private  TypeService typeService;
    private PromotionService promotionService;
    private CounterService counterService;
    private MaterialService materialService;
    private GemService gemService;
    @Autowired
    private StaffRepository staffRepository;
    private static final Logger logger = LogManager.getLogger(ProductController.class);

    public ProductController(ProductService productService, CategoryService categoryService,GemPriceListService gemPriceListService,
    		MaterialPriceListService materialPriceListService,TypeService typeService,PromotionService promotionService,
    		CounterService counterService,MaterialService materialService,GemService gemService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.gemPriceListService = gemPriceListService;
        this.materialPriceListService = materialPriceListService;
        this.typeService = typeService;
        this.promotionService = promotionService;
        this.counterService=counterService;
        this.materialService=materialService;
        this.gemService=gemService;
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
        model.addAttribute("material",materialService.getAllMaterials());
        model.addAttribute("category", categoryService.getAllCategorys());
        model.addAttribute("counter", counterService.findAll());
        model.addAttribute("product", product);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/detailProduct";
    }
    
     
    @PostMapping("manager/products/detail-product/{productID}/update")
    public String updateProduct(@PathVariable Integer productID, Product updatedProduct, Model model) {
        Product existingProduct = productService.findById(productID)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (productService.existsByProductCodeAndNotProductId(updatedProduct.getProductCode(), productID)) {
            model.addAttribute("product", existingProduct);
            model.addAttribute("updateSuccess", false);
            model.addAttribute("error", "Product code must be unique.");
            model.addAttribute("material", materialService.getAllMaterials());
            model.addAttribute("category", categoryService.getAllCategorys());
            model.addAttribute("counter", counterService.findAll());
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Staff staff = staffRepository.findByEmail(email);
            model.addAttribute("staff", staff);
            return "manager/detailProduct";
        }
        // Cập nhật các trường cần thiết từ updatedProduct
        existingProduct.setProductCode(updatedProduct.getProductCode());
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setCategoryID(updatedProduct.getCategoryID());
        existingProduct.setTypeID(updatedProduct.getTypeID());
        existingProduct.setCounterID(updatedProduct.getCounterID());

        // Lấy materialID từ updatedProduct
        Integer materialID = updatedProduct.getMaterialPriceList().getMaterialID();

        // Tìm kiếm MaterialPriceList dựa trên materialID
        List<MaterialPriceList> materialPriceLists = materialPriceListService.getMaterialPriceListById(materialID);
        if (!materialPriceLists.isEmpty()) {
            existingProduct.setMaterialPriceListID(materialPriceLists.get(0).getMaterialPriceListID()); // Chọn MaterialPriceList đầu tiên
        } else {
            // Xử lý trường hợp không tìm thấy MaterialPriceList
            model.addAttribute("product", existingProduct);
            model.addAttribute("updateSuccess", false);
            model.addAttribute("error", "Material price list not found for the given material ID.");
            model.addAttribute("material", materialService.getAllMaterials());
            model.addAttribute("category", categoryService.getAllCategorys());
            model.addAttribute("counter", counterService.findAll());
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Staff staff = staffRepository.findByEmail(email);
            model.addAttribute("staff", staff);
            return "manager/detailProduct";
        }
        // Cập nhật sản phẩm
        productService.updateProduct(existingProduct);
        
        model.addAttribute("product", existingProduct);
        model.addAttribute("updateSuccess", true);
        model.addAttribute("material", materialService.getAllMaterials());
        model.addAttribute("category", categoryService.getAllCategorys());
        model.addAttribute("counter", counterService.findAll());
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
         model.addAttribute("counters", counterService.findAll());
         model.addAttribute("gems",gemService.getAllGems());
         String email = SecurityContextHolder.getContext().getAuthentication().getName();
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/createNewProduct";
    }

    @PostMapping("manager/products/create-product/create")
    public String saveProduct(Product product, Model model, RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        model.addAttribute("staff", staff);
        try {
            productService.saveProduct(product);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Product code already exists")) {
                redirectAttributes.addFlashAttribute("codeErrorMessage", e.getMessage());
            }
            return "redirect:/manager/products/create-product";
        }
        return "redirect:/manager/products";
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
    public String savePromotionList(Model model, Promotion promotion, RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        model.addAttribute("staff", staff);
        
        try {
            promotionService.save(promotion);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Promotion code already exists")) {
                redirectAttributes.addFlashAttribute("codeErrorMessage", e.getMessage());
            } else if (e.getMessage().equals("Start date must be before end date")) {
                redirectAttributes.addFlashAttribute("dateErrorMessage", e.getMessage());
            } else if (e.getMessage().startsWith("Invalid promotion percent format") || e.getMessage().equals("Promotion percent must be greater than 0")) {
                redirectAttributes.addFlashAttribute("percentErrorMessage", e.getMessage());
            }
            return "redirect:/promotion";
        }
        
        return "redirect:/promotion";
    }
    
    @GetMapping("/promotion/update/{promotionID}") 
    public String showPromotionDetail(@PathVariable Integer promotionID, Model model) { 
    	 Optional<Promotion> PromotionOpt = promotionService.findByPromotionID(promotionID);
        model.addAttribute("promotion", PromotionOpt.get());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("material", materialService.getAllMaterials());
	    Staff staff = staffRepository.findByEmail(email);
	    model.addAttribute("staff", staff);
        return "manager/editPromotion";
    } 
    @PostMapping("/promotion/update/{promotionID}")
    public String updatePromotion(@PathVariable Integer promotionID, Model model, Promotion promotion,RedirectAttributes redirectAttributes) {
        promotion.setPromotionID(promotionID);
        try {
            promotionService.update(promotion);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Start date must be before end date")) {
                redirectAttributes.addFlashAttribute("dateErrorMessage", e.getMessage());
            } else if (e.getMessage().startsWith("Invalid promotion percent format") || e.getMessage().equals("Promotion percent must be greater than 0")) {
                redirectAttributes.addFlashAttribute("percentErrorMessage", e.getMessage());
            }
            return "manager/editPromotion";
        }   
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        model.addAttribute("staff", staff);   
        return "redirect:/promotion";
    }

    @GetMapping("/price-list")
    public String showPriceList(Model model, @RequestParam(defaultValue = "0") int page, 
                                @RequestParam(defaultValue = "10") int size) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        PageRequest pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "applyDate"));
        Page<MaterialPriceList> materialPriceListPage = materialPriceListService.findAllMaterial(pageable);
        model.addAttribute("material", materialService.getAllMaterials());
        model.addAttribute("currentPage", materialPriceListPage.getNumber());
        model.addAttribute("totalPages", materialPriceListPage.getTotalPages()); 
        model.addAttribute("materialPriceListPage", materialPriceListPage);
        model.addAttribute("materialPrice", new MaterialPriceList()); 
        model.addAttribute("staff", staff);
        return "manager/priceList";
    }

    
    
    @PostMapping("/price-list")
    public String savePriceList(Model model, MaterialPriceList materialPriceList) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        materialPriceList.setApplyDate(new Date());
        model.addAttribute("staff", staff);
        materialPriceListService.save(materialPriceList);
        String logEntry = String.format("%s, Saved material price list: ApplyDate: %s, BuyPrice: %.2f, SellPrice: %.2f",
                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                                        materialPriceList.getApplyDate(), 
                                        materialPriceList.getBuyPrice(), 
                                        materialPriceList.getSellPrice());
        csvlog.log(logEntry);

        return "redirect:/price-list";
    } 
    @GetMapping("price-list/update/{materialPriceListID}") 
    public String showPriceListDetail(@PathVariable Integer materialPriceListID, Model model) { 
    	 Optional<MaterialPriceList> materialPriceListOpt = materialPriceListService.findMaterialPriceListById(materialPriceListID);
        model.addAttribute("materialPriceList", materialPriceListOpt.get());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("material", materialService.getAllMaterials());
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/editMaterialPriceList";
    } 
    @PostMapping("/price-list/update/{materialPriceListID}")
    public String updateMaterialPriceList(@PathVariable Integer materialPriceListID, Model model, MaterialPriceList materialPriceList) {
        materialPriceList.setMaterialPriceListID(materialPriceListID);
        materialPriceList.setApplyDate(new Date());
        materialPriceListService.update(materialPriceList);
        model.addAttribute("materialPriceList", materialPriceList);
        model.addAttribute("updateSuccess", true);      
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        model.addAttribute("staff", staff);
        String logEntry = String.format("%s, Update material price list: , ApplyDate: %s, BuyPrice: %.2f, SellPrice: %.2f",
                materialPriceList.getMaterialPriceListID(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(materialPriceList.getApplyDate()),
                materialPriceList.getBuyPrice(),
                materialPriceList.getSellPrice());
        csvlog.log(logEntry);

        return "redirect:/price-list";
    }
  
    @GetMapping("/gem-price-list")
    public String showGemPriceList(Model model, @RequestParam(defaultValue = "0") int page, 
                                @RequestParam(defaultValue = "10") int size) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        PageRequest pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "applyDate"));
        Page<GemPriceList> gemPriceListPage = gemPriceListService.findAllGemPriceList(pageable);
        model.addAttribute("gem",gemService.getAllGems());
        model.addAttribute("currentPage", gemPriceListPage.getNumber());
        model.addAttribute("totalPages", gemPriceListPage.getTotalPages()); 
        model.addAttribute("gemPriceListPage", gemPriceListPage);
        model.addAttribute("gemPriceList", new GemPriceList()); 
        model.addAttribute("staff", staff);
        return "manager/gemPriceList";
    }

    @PostMapping("/gem-price-list")
    public String saveGemPriceList(Model model, GemPriceList gemPriceList) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        gemPriceList.setApplyDate(new Date());
        model.addAttribute("staff", staff);
        gemPriceListService.save(gemPriceList);
        
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gemPriceList.getApplyDate());
        String logEntry = String.format("%s, Saved game price list:ID, ApplyDate: %s, BuyPrice: %.2f, SellPrice: %.2f",
                                        gemPriceList.getGemPriceListID(),
                                        formattedDate,
                                        gemPriceList.getBuyPrice(), 
                                        gemPriceList.getSellPrice());
        csvlog2.log(logEntry);

        return "redirect:/gem-price-list";
    }
    @GetMapping("/gem-price-list/update/{gemPriceListID}") 
    public String showGemPriceListDetail(@PathVariable Integer gemPriceListID, Model model) { 
    	 Optional<GemPriceList> gemPriceListOpt = gemPriceListService.findGemPriceListById(gemPriceListID);
        model.addAttribute("gemPriceList", gemPriceListOpt.get());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("gem",gemService.getAllGems());
	       Staff staff = staffRepository.findByEmail(email);
	       model.addAttribute("staff", staff);
        return "manager/editGemPriceList";
    }
    @PostMapping("/gem-price-list/update/{gemPriceListID}")
    public String updateGemPriceList(@PathVariable Integer gemPriceListID, Model model, GemPriceList gemPriceList) {
        gemPriceList.setGemPriceListID(gemPriceListID);
        gemPriceList.setApplyDate(new Date());
        gemPriceListService.update(gemPriceList);
        model.addAttribute("gemPriceList", gemPriceList);
        model.addAttribute("updateSuccess", true);      
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        model.addAttribute("staff", staff);
        
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gemPriceList.getApplyDate());
        String logEntry = String.format("%s, Update game price list: , ApplyDate: %s, BuyPrice: %.2f, SellPrice: %.2f",
                                        gemPriceList.getGemPriceListID(),
                                        formattedDate,
                                        gemPriceList.getBuyPrice(),
                                        gemPriceList.getSellPrice());
        csvlog2.log(logEntry);

        return "redirect:/gem-price-list";
    }

	@GetMapping("/counter")
	public String counterList(Model model,@RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
         Staff staff = staffRepository.findByEmail(email);
         PageRequest pageable = PageRequest.of(page, size);
         Page<Counter> counterPage = counterService.findPaginated(pageable);
         model.addAttribute("currentPage", counterPage.getNumber());
         model.addAttribute("totalPages", counterPage.getTotalPages()); 
         model.addAttribute("counterPage", counterPage);
         model.addAttribute("counter", new Counter());
         model.addAttribute("staff", staff);
		return "manager/counterList";
	}
	@PostMapping("/saveCounter")
    public String saveCounter(@ModelAttribute Counter counter, RedirectAttributes redirectAttributes) {
        try {
            counter.setActive(true);
            counterService.saveCounter(counter);
        } catch (DuplicateCounterNameException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/counter";
        }
        return "redirect:/counter";
    }
    @GetMapping("counter/editCounter/{counterID}")
    public String editCounter(@PathVariable("counterID") Integer counterID, Model model) {
    	Optional<Counter> counter = counterService.getCounterById(counterID);
    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
    
        model.addAttribute("staff", staff);
        model.addAttribute("counter", counter.get());
 	   return "manager/editCounter";
    }
    @PostMapping("counter/editCounter/{counterID}/update")
    public String updateCounter(@ModelAttribute("counter") Counter counter, @PathVariable("counterID") Integer counterID, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Staff staff = staffRepository.findByEmail(email);
        model.addAttribute("staff", staff);

        try {
            counterService.updateCounter(counter);
        } catch (DuplicateCounterNameException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("counter", counter);
            return "manager/editCounter"; 
        }

        return "redirect:/counter";
    }

	@GetMapping("/counter/counterDetail/{counterID}")
	public String counterDetail(@PathVariable("counterID") Integer counterID, Model model) {
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
         Staff staff = staffRepository.findByEmail(email);
         Optional<Counter> counter = counterService.getCounterById(counterID);
         model.addAttribute("staff", staff);
         model.addAttribute("counter", counter.get());
         return "manager/counterDetail";
	}
	  @GetMapping("/manage-material")
	    public String showManageMaterial(Model model, @RequestParam(defaultValue = "0") int page, 
	                                @RequestParam(defaultValue = "10") int size) {
		      Page<Material> materialPage = materialService.findAllMaterialList(PageRequest.of(page, 10,Sort.by(Sort.Direction.DESC, "materialID")));
	        model.addAttribute("materials", materialPage.getContent());
	        model.addAttribute("currentPage", materialPage.getNumber());
	        model.addAttribute("totalPages", materialPage.getTotalPages());
	        model.addAttribute("material", new Material());
	           String email = SecurityContextHolder.getContext().getAuthentication().getName();
		       Staff staff = staffRepository.findByEmail(email);
		       model.addAttribute("staff", staff);
	        return "manager/manageMaterial";
	    }
	  @PostMapping("/manage-material")
	    public String saveMaterial(@ModelAttribute Material material, RedirectAttributes  redirectAttributes) {	      
	        try {
	        	   materialService.saveMaterial(material);
	        } catch ( IllegalArgumentException e) {
	        	 if (e.getMessage().equals("Material name already exists")) {
	                 redirectAttributes.addFlashAttribute("codeErrorMessage", e.getMessage());
	             }
	        	  return "redirect:/manage-material";
	        }
	        return "redirect:/manage-material";
	    }
	  @GetMapping("/manage-gem")
	    public String showManageGem(Model model, @RequestParam(defaultValue = "0") int page, 
	                                @RequestParam(defaultValue = "10") int size) {
		      Page<Gem> gemPage = gemService.findAllGemList(PageRequest.of(page, 10,Sort.by(Sort.Direction.DESC, "gemID")));
	        model.addAttribute("gems", gemPage.getContent());
	        model.addAttribute("currentPage", gemPage.getNumber());
	        model.addAttribute("totalPages", gemPage.getTotalPages());
	        model.addAttribute("gem", new Gem());
	           String email = SecurityContextHolder.getContext().getAuthentication().getName();
		       Staff staff = staffRepository.findByEmail(email);
		       model.addAttribute("staff", staff);
	        return "manager/manageGem";
	    }
	  @PostMapping("/manage-gem")
	    public String saveGem(@ModelAttribute Gem gem, Model model,  RedirectAttributes redirectAttributes) {
		  try {
	            gemService.saveGem(gem);
	        } catch ( IllegalArgumentException e) {
	        	 if (e.getMessage().equals("Gem code is already exist")) {
	                 redirectAttributes.addFlashAttribute("codeErrorMessage", e.getMessage());
	             }
	            return "redirect:/manage-gem";
	        }
	        return "redirect:/manage-gem";
	    }
	  @GetMapping("/manange-material/update/{materialID}")
	    public String editMaterial(@PathVariable("materialID") Integer materialID, Model model) {
	    	Optional<Material> material = materialService.getMaterialById(materialID);
	    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
	        Staff staff = staffRepository.findByEmail(email);
	    
	        model.addAttribute("staff", staff);
	        model.addAttribute("material", material.get());
	 	   return "manager/editMaterial";
	    }
	   @PostMapping("/manange-material/update/{materialID}")
	    public String updateMaterial(@ModelAttribute("material") Material material, @PathVariable("materialID") Integer materialID, Model model,
	    		RedirectAttributes redirectAttributes) {
	        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	        Staff staff = staffRepository.findByEmail(email);
	        material.setMaterialID(materialID);
	        model.addAttribute("staff", staff);
	        try {
		        materialService.updateMaterial(material);
	        } catch ( IllegalArgumentException e) {
	        	 if (e.getMessage().equals("Material name already exists.")) {
	                 redirectAttributes.addFlashAttribute("errorName", e.getMessage());
	             }
	        	 return "manager/editMaterial";
	        }
	        return "redirect:/manage-material";
	    }
	   @GetMapping("/manange-gem/update/{gemID}")
	    public String editGem(@PathVariable("gemID") Integer gemID, Model model) {
	    	Optional<Gem> gem = gemService.findGemById(gemID);
	    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
	        Staff staff = staffRepository.findByEmail(email);
	    
	        model.addAttribute("staff", staff);
	        model.addAttribute("gem", gem.get());
	 	   return "manager/editGem";
	    }
	   @PostMapping("/manange-gem/update/{gemID}")
	    public String updateGem(@ModelAttribute("gem") Gem gem, @PathVariable("gemID") Integer gemID, Model model) {
	        String email = SecurityContextHolder.getContext().getAuthentication().getName();
	        Staff staff = staffRepository.findByEmail(email);
	        model.addAttribute("staff", staff);
	        gem.setGemID(gemID);
	        gemService.updateGem(gem);
	        return "redirect:/manage-gem";
	    }
}
