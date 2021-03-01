package com.example.authorization.net.repo

import com.example.authorization.net.responses.ArticleResponse
import com.example.authorization.net.services.ReportService
import io.reactivex.Observable


class ReportRepo(private val api: ReportService) {

    fun getReports(): Observable<ArrayList<ArticleResponse>> = api.getReports()

    fun getReportById(id: String): Observable<ArticleResponse> = api.getReportById(id)
}