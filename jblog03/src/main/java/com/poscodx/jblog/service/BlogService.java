package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public void create(String blogId) {
		BlogVo blogVo = new BlogVo();
		blogVo.setBlogId(blogId);
		blogVo.setTitle(blogId+"님의 블로그");
		blogVo.setImage("/assets/images/오둥이2.png");
		blogRepository.insert(blogVo);
	}

	public BlogVo findByBlogId(String blogId) {
		return blogRepository.findByBlogId(blogId);
	}

	public void updateBlog(BlogVo blogVo) {
		blogRepository.update(blogVo);
	}
}
