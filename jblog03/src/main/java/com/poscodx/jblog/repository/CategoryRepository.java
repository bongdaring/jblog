package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {

	@Autowired
	private SqlSession sqlSession;

	public void insert(CategoryVo categoryVo) {
		// TODO Auto-generated method stub
		sqlSession.insert("category.insert", categoryVo);
		
	}

	public List<CategoryVo> findAll(String blogId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("category.findAll", blogId);
	}

	public void deleteByNo(Long categoryNo) {
		sqlSession.delete("category.deleteByNo", categoryNo);
		
	}

	public CategoryVo findRecentCategory(String blogId) {
		return sqlSession.selectOne("category.findRecentCategory", blogId);
	}
	
	
}
