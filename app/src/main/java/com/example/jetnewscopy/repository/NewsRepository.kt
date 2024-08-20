package com.example.jetnewscopy.repository

import com.example.jetnewscopy.api.Retrofitinstance
import com.example.jetnewscopy.db.ArticleDatabase
import com.example.jetnewscopy.models.Article

class NewsRepository(val db: ArticleDatabase) {

suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
Retrofitinstance.api.getHeadlines(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        Retrofitinstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article): Long {
        // Handle possible null values in the Article
        val safeArticle = article.copy(
            author = article.author ?: "Unknown Author",
            description = article.description ?: "No Description",
            urlToImage = article.urlToImage ?: "",
            content = article.content ?: "Content not available"
        )

        // Insert the safeArticle into the database using the DAO
        return db.getArticleDao().upsert(safeArticle)
    }

    fun getFavouriteNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}