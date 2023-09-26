package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	public void create(CategoryVo categoryVo) {
		categoryRepository.insert(categoryVo);
		
	}

	public List<CategoryVo> findAll(String blogId) {
		// TODO Auto-generated method stub
		return categoryRepository.findAll(blogId);
	}

	public void deleteCategory(Long categoryNo) {
		categoryRepository.deleteByNo(categoryNo);
		
	}

	public CategoryVo findRecentCategory(String blogId) {
		return categoryRepository.findRecentCategory(blogId);
	}

	
}
