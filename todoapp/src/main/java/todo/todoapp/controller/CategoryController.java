package todo.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.CategoryRequest;
import todo.todoapp.entity.Category;
import todo.todoapp.entity.Member;
import todo.todoapp.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") //
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest request,
                                                   @AuthenticationPrincipal Member member) {

        // 🔍 여기에 로그 출력 추가
        System.out.println("🔥 인증된 사용자 정보: " + member);

        // member.getId()로 memberId 대체
        request.setMemberId(member.getMemberId()); // setter 필요
        Category category = categoryService.createCategory(request);
        return ResponseEntity.ok(category);
    }


    @GetMapping
    public ResponseEntity<List<Category>> getCategories(@RequestParam Long memberId) {
        List<Category> categories = categoryService.getCategoriesByMemberId(memberId);
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
