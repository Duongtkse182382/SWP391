package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Gem;
import com.example.demo.Entity.GemPriceList;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.GemPriceListRepository;
import com.example.demo.Repository.GemRepository;
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
    private final GemRepository gemRepository;

    private final CategoryRepository categoryRepository;


    private final GemPriceListRepository gemPriceListRepository;

    private final TypeRepository typeRepository;

   
    private final MaterialPriceListRepository materialPriceListRepository;


    public ProductServiceImpl(ProductRepository productRepository,TypeRepository typeRepository, CategoryRepository categoryRepository, GemPriceListRepository gemPriceListRepository,
    		MaterialPriceListRepository materialPriceListRepository,GemRepository gemRepository) {
        this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.gemPriceListRepository = gemPriceListRepository;
		this.typeRepository = typeRepository;
		this.materialPriceListRepository = materialPriceListRepository;
		this.gemRepository=gemRepository;
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
    

    @Override
    public Product updateProduct(Product product) {
    	return  productRepository.save(product);
     
    }

    @Transactional
    public void saveProduct(Product product) {
        if (productRepository.existsByProductCode(product.getProductCode())) {
            throw new IllegalArgumentException("Product code already exists");
        }

        if (product.getTypeID() == 2) {
            product.setGemPriceListID(49);
            product.setOrderType("Sell");
            product.setActive(true);
            productRepository.save(product);
        } 
        if (product.getTypeID() == 1) {
                product.setOrderType("Sell");
                product.setActive(true);
                productRepository.save(product);
            }
        }
    
    
    @Override
    public boolean existsByProductCodeAndNotProductId(String productCode, Integer productId) {
        return productRepository.countByProductCodeAndIdNot(productCode, productId) > 0;
    }



    }     

