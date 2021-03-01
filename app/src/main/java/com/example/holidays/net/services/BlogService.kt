package com.example.authorization.net.services

import com.example.authorization.net.responses.ArticleResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BlogService {

    @GET("/api/v2/blogs")
    fun getBlogs(@Query("_limit") size: Int = 50): Observable<ArrayList<ArticleResponse>>

    @GET("/api/v2/blogs/{id}")
    fun getBlogById(
        @Path("id") id: String
    ): Observable<ArticleResponse>
}
