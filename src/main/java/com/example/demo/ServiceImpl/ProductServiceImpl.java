package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Product;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.GemPriceListRepository;
import com.example.demo.Repository.MaterialPriceListRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.TypeRepository;
import com.example.demo.Service.ProductService;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
	   @Autowired
	    private EntityManager entityManager;
    private final ProductRepository productRepository;
    

    private final CategoryRepository categoryRepository;


    private final GemPriceListRepository gemPriceListRepository;

    private final TypeRepository typeRepository;

   
    private final MaterialPriceListRepository materialPriceListRepository;


    public ProductServiceImpl(ProductRepository productRepository,TypeRepository typeRepository, CategoryRepository categoryRepository, GemPriceListRepository gemPriceListRepository, MaterialPriceListRepository materialPriceListRepository) {
        this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.gemPriceListRepository = gemPriceListRepository;
		this.typeRepository = typeRepository;
		this.materialPriceListRepository = materialPriceListRepository;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
   
    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }
   
    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }
    

    @Transactional
    public void updateProduct(Product product) {
        if (product.getCategoryID() == 0) {
            product.setCategoryID(4);
        }
        if (product.getGemPriceListID() == null) {
            product.setGemPriceListID(4);
        }
        if (product.getTypeID() == 0) {
            product.setTypeID(2);
        }
        if (product.getMaterialPriceListID() == 0) {
            product.setMaterialPriceListID(1);
        }
        boolean categoryExists = categoryRepository.existsById(product.getCategoryID());
        boolean gemPriceListExists = gemPriceListRepository.existsById(product.getGemPriceListID());
        boolean typeExists = typeRepository.existsById(product.getTypeID());
        boolean materialPriceListExists = materialPriceListRepository.existsById(product.getMaterialPriceListID());

        if (categoryExists && gemPriceListExists && typeExists && materialPriceListExists) {
            Product existingProduct = productRepository.findById(product.getProductID())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductCode(product.getProductCode());
            existingProduct.setWeight(product.getWeight());
            existingProduct.setPriceRate(product.getPriceRate());
            existingProduct.setImage(product.getImage());
            existingProduct.setOrderType(product.getOrderType());
            existingProduct.setCategoryID(product.getCategoryID());
            existingProduct.setGemPriceListID(product.getGemPriceListID());
            existingProduct.setTypeID(product.getTypeID());
            existingProduct.setMaterialPriceListID(product.getMaterialPriceListID());
            existingProduct.setOrderDetails(existingProduct.getOrderDetails());
            productRepository.save(existingProduct);
        } else {
            throw new DataIntegrityViolationException("Invalid foreign key value.");
        }
    }

     
    @Override
    public void saveProduct(Product product) {
        if (product.getTypeID() == 2) {
            product.setGemPriceListID(null);
            product.getGemPriceList().getGem().setCut(null);
            product.getGemPriceList().getGem().setClarity(null);
            product.getGemPriceList().getGem().setColor(null);
            product.setOrderType("Sell");
            product.setActive(true);
            productRepository.save(product);
        } 
        if( product.getTypeID() == 1 && product.getGemPriceList() != null){
            product.setGemPriceListID(2);
            product.getGemPriceList().getGem().setGemName("Diamond");
            product.getGemPriceList().getGem().setOrigin("Natural");
            product.getGemPriceList().getGem().setGemCode("GEM001");
            product.setOrderType("Sell");
            product.setActive(true);
            productRepository.save(product);
        }
    }
    	 
    		 

 
    	
    }     

