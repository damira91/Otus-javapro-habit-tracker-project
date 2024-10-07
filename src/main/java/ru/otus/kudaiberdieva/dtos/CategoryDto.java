package ru.otus.kudaiberdieva.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kudaiberdieva.entities.Category;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {
    private Long id;
    private String name;

    public static CategoryDto entityToDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category dtoToEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }
}
