package com.hampcode.articlestec.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hampcode.articlestec.model.Article;

public interface ArticleService {
	
	List<Article> getAllArticles();

	Article createArticle(Article article);

	Article updateArticle(Long id, Article articleDetails);

	void deleteArticle(Long articleId);

	Article findById(Long id);
	
    boolean titleAndAuthorValid(Article article);

    Page<Article> findAll( Pageable pageable);
	

}
