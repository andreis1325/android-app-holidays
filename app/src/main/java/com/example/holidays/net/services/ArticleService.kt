package com.example.authorization.net.services

import com.example.authorization.net.responses.ArticleResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {

    @GET("/api/v2/articles")
    fun getArticles(@Query("_limit") size:Int = 50): Observable<ArrayList<ArticleResponse>>

    @GET("/api/v2/articles/{id}")
    fun getArticleById(
        @Path("id") id: String
    ): Observable<ArticleResponse>

    @GET("/api/v2/articles/launch/{id}")
    fun getArticlesLinkedByLaunch(
        @Path("id") id: String
    ): Observable<ArticleResponse>
}