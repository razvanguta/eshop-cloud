package com.onlineshop.productservice.service.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.productservice.dto.User;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.model.RequestProduct;
import com.onlineshop.productservice.model.RequestUpdateProduct;
import com.onlineshop.productservice.repository.CategoryRepository;
import com.onlineshop.productservice.repository.ProductMappingRepository;
import com.onlineshop.productservice.response.ProductResponse;
import com.onlineshop.productservice.rest.OnlineShopClient;
import com.onlineshop.productservice.service.CategoryService;
import com.onlineshop.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMappingRepository productRepo;

    @Autowired
    CategoryRepository categoryRepo;

    @Lazy
    @Autowired
    CategoryService categoryService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    OnlineShopClient onlineShopClient;

    @Transactional
    public Product saveProduct(String requestProduct, MultipartFile file) throws IOException {
        RequestProduct requestCreateProduct = mapper.readValue(requestProduct, RequestProduct.class);
        User useraccount = onlineShopClient.getUserById(requestCreateProduct.getUserId());
        System.out.println(useraccount);
        if (useraccount.getRole().equalsIgnoreCase("admin")) {
            Category category = categoryService.findByCategory(requestCreateProduct.getCategoryId());
            Product product = new Product();
            product.setProductName(requestCreateProduct.getProductName());
            product.setDescription(requestCreateProduct.getDescription());
            product.setStocks(100L);
            product.setSeller(requestCreateProduct.getUserId());
            product.setUnit(requestCreateProduct.getUnit());
            product.setPrice(requestCreateProduct.getPrice());
            product.setAddedAt(LocalDateTime.now());
            product.setCategory(category);
            if (file != null) {
                product.setImageData(file.getBytes());
            }

            product.setStatus("APPROVED");
            return productRepo.save(product);
        }
        return null;
    }

    public void deletebyId(Long id, Long userId) throws JsonProcessingException {
        User useraccount = onlineShopClient.getUserById(userId);
        Product product = productRepo.findByProductId(id);
        if (useraccount.getRole().equalsIgnoreCase("admin")) {
            product.setStatus("DELETED");
            productRepo.save(product);
        }
    }

    public Product updateProduct(String updateReqProduct, MultipartFile file, Long id) throws Exception {
        RequestUpdateProduct requestUpdateProduct = mapper.readValue(updateReqProduct, RequestUpdateProduct.class);
        User useraccount = onlineShopClient.getUserById(requestUpdateProduct.getUserId());
        Product product = findByProduct(id);
        if (useraccount.getRole().equalsIgnoreCase("admin")
                && product.getSeller() == useraccount.getId()) {
            product.setProductName(requestUpdateProduct.getProductName());
            Category category = categoryService.findByCategory(requestUpdateProduct.getCategoryId());
            product.setCategory(category);
            product.setDescription(requestUpdateProduct.getDescription());
            product.setStocks(100l);
            product.setUnit(requestUpdateProduct.getUnit());
            product.setPrice(requestUpdateProduct.getPrice());
            if (file != null) {
                product.setImageData(file.getBytes());
            }
            return productRepo.save(product);
        }
        return null;
    }

    public Product getProductById(Long productId) {
        return findByProduct(productId);
    }

    public List<ProductResponse> filterbyId(String pName, Long userId) {
        if (pName.isEmpty()) {
            return mapFIlterProduct(productRepo.findAllByStatusOrderByAddedAtDesc("APPROVED"));
        } else {
            return mapFIlterProduct(
                    productRepo.findAllByStatusAndProductNameContainingOrStatusAndCategoryCategoryNameContaining(
                            "APPROVED", pName, "APPROVED", pName));
        }
    }

    public List<ProductResponse> mapFIlterProduct(List<Product> product) {
        return product.stream()
                .map(e -> new ProductResponse(e.getProductId(), e.getProductName(), e.getUnit(), e.getCategory().getCategoryId(), e.getCategory().getCategoryName(), e.getPrice(), e.getImageData(), e.getStatus()))
                .collect(Collectors.toList());
    }

    public boolean isAdmin(Long userId) throws JsonProcessingException {
        User useraccount = onlineShopClient.getUserById(userId);
        return useraccount.getRole().equalsIgnoreCase("admin");

    }

    public Product findByProduct(Long productId) {
        return productRepo.findByProductId(productId);
    }

    @Override
    public List<ProductResponse> getAllPendingPoduct() {
        return mapFIlterProduct(productRepo.findAllByStatus("pending"));
    }

    @Override
    public Product setStatusApproveProduct(Long productId, Long userId, String status) throws JsonProcessingException {
        Product product = findByProduct(productId);
        if (isAdmin(userId)) {
            if (status.equalsIgnoreCase("pending")) {
                product.setStatus("pending");
            } else if (status.equalsIgnoreCase("approved")) {
                product.setStatus("APPROVED");
            }
            return productRepo.save(product);
        }
        return null;
    }
}
