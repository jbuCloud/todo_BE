package todo.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todo.todoapp.dto.CategoryRequest;
import todo.todoapp.entity.Category;
import todo.todoapp.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest request) {
        Category category = Category.builder()
                .memberId(request.getMemberId())
                .type(request.getType())
                .name(request.getName())
                .color(request.getColor())
                .build();
        return categoryRepository.save(category);
    }

    public List<Category> getCategoriesByMemberId(Long memberId) {
        return categoryRepository.findAllByMemberId(memberId);
    }

    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다.");
        }
        categoryRepository.deleteById(categoryId);
    }

}
