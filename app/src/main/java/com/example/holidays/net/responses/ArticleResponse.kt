package com.example.authorization.net.responses

class ArticleResponse(
    var id: String? = null,
    var featured: Boolean? = null,
    var title: String? = null,
    var url: String? = null,
    var imageUrl: String? = null,
    var newsSite: String? = null,
    var summary: String? = null,
    var publishedAt: String? = null,
    var launches: List<Provider>? = null,
    var events: List<Provider>?
)

class Provider(
    var id: String? = null,
    var provider: String? = null
)

class ArticleItemsWithBlogAndReport(
    var articleItems: ArrayList<ArticleResponse>,
    var blogItems: ArrayList<ArticleResponse>,
    var reportItems:ArrayList<ArticleResponse>
)
