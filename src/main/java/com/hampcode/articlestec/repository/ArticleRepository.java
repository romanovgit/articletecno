package com.hampcode.articlestec.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hampcode.articlestec.model.Article;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

	@Query(value="SELECT MAX(id) FROM articles", nativeQuery = true)
	Long findTopByOrderByIdDesc1();
	
	//Query ( SELECT a FROM Article a WHERE a.author=?1)
	List<Article> findByAuthor(String author);
	
	@Query("SELECT MAX(id) FROM Article")
	Long findTopByOrderByIdDesc();
	
	@Query("SELECT a FROM Article a WHERE a.title=:title and a.author=:author")
	List<Article> findByTitleAndAuthor(@Param("title") String title,
			  @Param("author") String author);
	
	Page<Article> findAll( Pageable pageable);
}
