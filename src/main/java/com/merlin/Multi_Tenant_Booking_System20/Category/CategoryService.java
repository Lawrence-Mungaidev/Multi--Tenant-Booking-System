package com.merlin.Multi_Tenant_Booking_System20.Category;

import com.merlin.Multi_Tenant_Booking_System20.Exceptions.DuplicateExceptions;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    public CategoryResponseDto createCategory(CategoryDto categoryDto) {

        if(categoryRepository.existsByCategoryName(categoryDto.name())){
            throw  new DuplicateExceptions("Category already exists");
        }

        Category category = categoryMapper.toCategory(categoryDto);

        var savedCategory =  categoryRepository.save(category);

        return categoryMapper.toCategoryResponseDto(savedCategory);
    }

    public CategoryResponseDto updateCategory(Long categoryId,CategoryDto categoryDto) {
        Category category =  categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFound("Category not found"));

        if(categoryDto.name() != null ){
            category.setName(categoryDto.name());
        }
        if(categoryDto.description() != null ){
            category.setDescription(categoryDto.description());
        }

        categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(category);
    }


    public List<CategoryResponseDto> findAll(){
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper ::toCategoryResponseDto)
                .toList();
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFound("Category not found"));

        categoryRepository.delete(category);
    }






}
