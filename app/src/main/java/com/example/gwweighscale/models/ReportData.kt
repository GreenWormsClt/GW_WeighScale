package com.example.gwweighscale.models

data class ReportData(
    val reportId: Int,
    val staffName: String,
    val itemName: String,
    val totalWeight: Double,
    val date: String
)
