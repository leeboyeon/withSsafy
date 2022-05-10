package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.report.Report
import com.ssafy.withssafy.src.dto.report.ReportRequest
import retrofit2.Response
import retrofit2.http.*

interface ReportApi {

    @GET("report")
    suspend fun selectReportList() : Response<Report>

    @POST("report")
    suspend fun insertReport(@Body reportDto: ReportRequest) : Response<Report>

    @DELETE("report")
    suspend fun deleteReport(@Query("id") id: Int) : Response<Report>
}