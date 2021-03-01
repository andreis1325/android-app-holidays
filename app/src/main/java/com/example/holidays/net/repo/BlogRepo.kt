package com.example.authorization.net.repo

import com.example.authorization.net.responses.ArticleResponse
import com.example.authorization.net.services.BlogService
import io.reactivex.Observable

class BlogRepo(private val api: BlogService){

    fun getBlogs(): Observable<ArrayList<ArticleResponse>> = api.getBlogs()

    fun getBlogById(id: String): Observable<ArticleResponse> = api.getBlogById(id)

}