package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;

	public void insert(PostVo postVo) {
		sqlSession.insert("post.insert", postVo);
		
	}

	public List<PostVo> findByCategoryNo(Long categoryNo) {
		return sqlSession.selectList("post.findByCategoryNo", categoryNo);
	}

	public PostVo findByNo(PostVo postVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("post.findByNo", postVo);
	}

	public void deleteByCategoryNo(Long categoryNo) {
		sqlSession.delete("post.deleteByCategoryNo", categoryNo);
		
	}

	public PostVo findRecentByCategoryNo(Long categoryNo) {
		return sqlSession.selectOne("post.findRecentByCategoryNo", categoryNo);
	}
	
	public PostVo findRecent(String blogId) {
		return sqlSession.selectOne("post.findRecent", blogId);
	}

}
