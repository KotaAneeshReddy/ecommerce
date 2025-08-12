package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    private List<Category> categories = new ArrayList<>();

    @GetMapping("/public/categories")
//    @RequestMapping(value = "/public/categories",method = RequestMethod.GET) another way for get mapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categoryList = categoryService.getAllCategories();
       return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
       categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully",HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try{
           String status = categoryService.deleteCategory(categoryId);
           return new ResponseEntity<>(status, HttpStatus.OK);
//           return ResponseEntity.ok(status);  another way of handling ResponseEntity
//            return ResponseEntity.status(HttpStatus.OK).body(status); another way of handling ResponseEntity
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }

    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category,@PathVariable Long categoryId){
        try{
            Category category1 = categoryService.updateCategory(category,categoryId);
            return new ResponseEntity<>(category1,HttpStatus.OK);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(null,e.getStatusCode());
        }
    }
}
