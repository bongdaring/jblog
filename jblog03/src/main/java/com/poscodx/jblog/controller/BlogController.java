package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;

	@RequestMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(@PathVariable(value = "id") String blogId,
			@PathVariable(value = "categoryNo") Optional<Long> categoryNo,
			@PathVariable(value = "postNo") Optional<Long> postNo, Model model) {
		
		BlogVo blogVo = blogService.findByBlogId(blogId);
		List<CategoryVo> list = categoryService.findAll(blogId);
		model.addAttribute("list", list);
		model.addAttribute("vo", blogVo);
		
		if(categoryNo.isPresent()) {
			List<PostVo> postList 
			= postService.findByCategoryNo(categoryNo.get());
			model.addAttribute("postList",postList);
			
			if(postNo.isPresent()) {
				PostVo postVo = postService.findByNo(postNo.get(), categoryNo.get());
				model.addAttribute("post", postVo);
			} else {
				PostVo postVo = postService.findRecentByCategoryNo(categoryNo.get());
				model.addAttribute("post", postVo);
			}
		} else {
			PostVo postVo = postService.findRecent(blogId);
			if(postVo != null) {
				List<PostVo> postList = postService.findByCategoryNo(postVo.getCategoryNo());
				model.addAttribute("postList",postList);
				model.addAttribute("post", postVo);
			}
		}
		
		return "blog/main";
	}

	@RequestMapping("/admin/basic")
	public String adminBasic(Model model, @PathVariable("id") String blogId) {
		BlogVo blogVo = blogService.findByBlogId(blogId);
		model.addAttribute("vo", blogVo);
		return "blog/admin-basic";
	}

	@RequestMapping(value = "/admin/basic/update", method = RequestMethod.POST)
	public String updateBlog(@PathVariable(value = "id") String blogId, BlogVo blogVo,
			@RequestParam("file") MultipartFile file, Model model) {

		String url = fileUploadService.restore(file);

		if (url != null) {
			blogVo.setImage(url);
		}

		blogVo.setBlogId(blogId);
		blogService.updateBlog(blogVo);

		return "redirect:/" + blogId+"/admin/basic";
	}

	@RequestMapping(value = "/admin/category", method = RequestMethod.GET)
	public String category(Model model, @PathVariable("id") String blogId) {
		BlogVo blogVo = blogService.findByBlogId(blogId);
		model.addAttribute("vo", blogVo);
		List<CategoryVo> list = categoryService.findAll(blogId);
		model.addAttribute("list", list);
		return "blog/admin-category";
	}

	@RequestMapping(value = "/admin/category", method = RequestMethod.POST)
	public String category(CategoryVo categoryVo, @PathVariable("id") String blogId) {
		categoryVo.setBlogId(blogId);
		categoryService.create(categoryVo);
		return "redirect:/" + blogId + "/admin/category";
	}

	@RequestMapping(value = "/admin/category/delete/{no}")
	public String categoryDelete(
			@PathVariable("id") String blogId,
			@PathVariable("no") Long categoryNo
	) {
		postService.deletePost(categoryNo);
		categoryService.deleteCategory(categoryNo);
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping(value = "/admin/write", method = RequestMethod.GET)
	public String writeForm(Model model, @PathVariable("id") String blogId) {
		BlogVo blogVo = blogService.findByBlogId(blogId);
		model.addAttribute("vo", blogVo);
		List<CategoryVo> list = categoryService.findAll(blogId);
		model.addAttribute("list", list);
		return "blog/admin-write";
	}
	
	@RequestMapping(value = "/admin/write", method = RequestMethod.POST)
	public String write(PostVo postVo, Model model, @PathVariable("id") String blogId) {
		postService.create(postVo);
		return "redirect:/" + blogId + "/admin/write";
	}
}
