package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Order;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.GemPriceListRepository;
import com.example.demo.Repository.MaterialPriceListRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.TypeRepository;
import com.example.demo.Service.ProductService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    

    private final CategoryRepository categoryRepository;


    private final GemPriceListRepository gemPriceListRepository;

    private final TypeRepository typeRepository;

   
    private final MaterialPriceListRepository materialPriceListRepository;


    @Autowired
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
    

    @Override
    @Transactional
    public void updateProduct(Product product) {
        if (categoryRepository.existsById(product.getCategory().getCategoryID()) &&
            gemPriceListRepository.existsById(product.getGemPriceList().getGemPriceListID()) &&
            typeRepository.existsById(product.getType().getTypeID()) &&
            materialPriceListRepository.existsById(product.getMaterialPriceList().getMaterialPriceListID())) {

            productRepository.save(product);
        } else {
            throw new DataIntegrityViolationException("Invalid foreign key value.");
        }
    }
     
    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }     
    @Override
    public void deleteProductById(Integer id) {
        productRepository.deleteById(id);
    }
}
