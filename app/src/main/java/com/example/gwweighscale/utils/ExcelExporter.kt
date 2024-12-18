package com.example.gwweighscale.utils

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.example.gwweighscale.models.PopupData
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object ExcelExporter {

    fun exportReportData(context: Context, reportData: List<PopupData>, fileName: String = "ItemReport.xlsx") {
        val workbook: Workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Item Reports")

        // Define header row
        val headerRow = sheet.createRow(0)
        val headers = listOf("Report ID", "Weight", "Staff Name", "Item Name", "Date", "Time")

        // Set style for headers
        val headerStyle = workbook.createCellStyle().apply {
            alignment = HorizontalAlignment.CENTER
        }

        headers.forEachIndexed { index, header ->
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            cell.cellStyle = headerStyle
            sheet.setColumnWidth(index, 20 * 256) // Fixed column width
        }

        // Populate rows with data
        reportData.forEachIndexed { rowIndex, item ->
            val row = sheet.createRow(rowIndex + 1)
            row.createCell(0).setCellValue(item.reportId.toDouble())
            row.createCell(1).setCellValue(item.weight)
            row.createCell(2).setCellValue(item.staffName)
            row.createCell(3).setCellValue(item.itemName)
            row.createCell(4).setCellValue(item.date)
            row.createCell(5).setCellValue(item.time)
        }

        // Write the file to storage
        // Write file to Downloads folder
        val fileNameWithExtension = fileName
        val outputStream: OutputStream?

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // For Android 10 and above
                val resolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileNameWithExtension)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }

                val fileUri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
                outputStream = fileUri?.let { resolver.openOutputStream(it) }
            } else {
                // For Android 9 and below
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, fileNameWithExtension)
                outputStream = FileOutputStream(file)
            }

            outputStream?.use { stream ->
                workbook.write(stream)
                workbook.close()
            }

            Toast.makeText(context, "Excel file saved to Downloads", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error saving Excel file: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}
