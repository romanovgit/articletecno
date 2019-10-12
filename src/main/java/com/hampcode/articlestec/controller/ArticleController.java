package com.hampcode.articlestec.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hampcode.articlestec.common.PageInitPagination;
import com.hampcode.articlestec.model.Article;
import com.hampcode.articlestec.service.ArticleService;

@Controller
@RequestMapping("/articles")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private PageInitPagination pageInitPagination;
	
	protected static final String ARTICLE_VIEW_PAGE="articles/allArticles";
	protected static final String ARTICLE_ADD_FORM_PAGE="articles/newArticle";
	protected static final String ARTICLE_EDIT_FORM_PAGE="articles/editArticle";
	protected static final String ARTICLE_DETAIL_PAGE="articles/showArticle";

	@GetMapping
	public ModelAndView getAllArticles(@RequestParam("pageSize") 
			Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		ModelAndView mv=pageInitPagination.initPagination(pageSize, page,ARTICLE_VIEW_PAGE);
		return mv;
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/new")
	public String newArticle(Model model) {
		model.addAttribute("article", new Article());
		return ARTICLE_ADD_FORM_PAGE;
		
	}
	
	//@Secured({"ROLE_ADMIN"})
	@PreAuthorize("hasRole('ROLA_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/{id}")
	public String getArticleById(@PathVariable(value="id") Long articleId, Model model) {
		model.addAttribute("article", articleService.findById(articleId));
		return ARTICLE_DETAIL_PAGE;
		
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/create")
	public String createArticle(@Valid Article article, BindingResult result, Model model, RedirectAttributes attr) {
		
		if(result.hasErrors() || articleService.titleAndAuthorValid(article)==false) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.articles", result);
			attr.addFlashAttribute("article", article);
			attr.addFlashAttribute("error", "No se permiten articulos con el mismo nombre");
			return "redirect:/articles/new";
		}
		
		Article newArticle = articleService.createArticle(article);
		model.addAttribute("article",newArticle);
		return "redirect:/articles/"+newArticle.getArticleId();
	}
}
