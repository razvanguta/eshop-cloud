package com.onlineshop.productservice.service.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.model.RequestCategory;
import com.onlineshop.productservice.repository.CategoryRepository;
import com.onlineshop.productservice.service.CategoryService;
import com.onlineshop.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepo;

    @Autowired
    ProductService productService;

    public Category saveCategory(RequestCategory requestCategory) throws JsonProcessingException {
        if (productService.isAdmin(requestCategory.getUserId())) {
            Category category = new Category();
            category.setCategoryName(requestCategory.getCategoryName());
            category.setStatus("Approved");
            return categoryRepo.save(category);
        }
        return null;
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepo.getReferenceById(id);
    }

    public List<Category> findbyOrder(Long userId) throws JsonProcessingException {
        if (productService.isAdmin(userId)) {
            return categoryRepo.findAllByOrderByCategoryNameAsc();
        }
        return null;
    }

    public Category updatecategory(RequestCategory requestCategory, Long id) throws JsonProcessingException {
        if (productService.isAdmin(requestCategory.getUserId())) {
            Category category = findByCategory(id);
            category.setCategoryName(requestCategory.getCategoryName());
            return categoryRepo.save(category);
        }
        return null;
    }

    public void deleteById(Long id, Long userId) throws JsonProcessingException {
        if (productService.isAdmin(userId)) {
            Category category = findByCategory(id);
            List<Product> product = category.getProduct();
            product.stream().filter(e -> e.getStatus().equals("APPROVED")).findAny();

            categoryRepo.deleteById(category.getCategoryId());
        }

    }

    public Category findByCategory(Long id) {
        return categoryRepo.findById(id).get();
    }
}
