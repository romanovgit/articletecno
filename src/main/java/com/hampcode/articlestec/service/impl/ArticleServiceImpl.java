package com.hampcode.articlestec.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlestec.model.Article;
import com.hampcode.articlestec.repository.ArticleRepository;
import com.hampcode.articlestec.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public List<Article> getAllArticles() {
		List<Article> articles=new ArrayList<Article>();		
		articleRepository.findAll()
		                 .iterator()
		                 .forEachRemaining(articles::add);
		return articles;
	}

	@Override
	public Article createArticle(Article article) {
		Article newArticle;
		newArticle=articleRepository.save(article);
		return newArticle;
	}

	@Override
	public Article updateArticle(Long id, Article articleDetails) {
		Article article=findById(id);
		
		article.setTitle(articleDetails.getTitle());
		article.setCategory(articleDetails.getCategory());
		article.setAuthor(articleDetails.getAuthor());
		article.setContent(articleDetails.getContent());
		article.setDescription(articleDetails.getDescription());
		
		articleRepository.save(article);
		return article;
	}

	@Override
	public void deleteArticle(Long articleId) {
		articleRepository.delete(findById(articleId));
	}

	@Override
	public Article findById(Long id) {
		
		Optional<Article> article=articleRepository.findById(id);
			
		//Exception
				
		return article.get();
	}

	@Override
	public boolean titleAndAuthorValid(Article article) {
		
		List<Article> articles=new ArrayList<>();
				
		articleRepository.findByTitleAndAuthor(
				 article.getTitle(), article.getAuthor())
		       .iterator()
		       .forEachRemaining(articles::add);
		
		if(!articles.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public Page<Article> findAll(Pageable pageable) {
		return articleRepository.findAll(pageable);
	}

	

	

}
