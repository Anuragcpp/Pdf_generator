package com.example.pdf_generator

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pdf_generator.databinding.ActivityPdfBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfBinding
    private val width : Int = 595
    private val pageHeight : Int = 842

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.generatePdf.setOnClickListener { generatePdf() }

    }

    private fun generatePdf() {
        // creating an object variable for our PDF document.
        val pdfDocument : PdfDocument = PdfDocument()

        //two variables for paint "paint" is used for drawing shapes and we
        // will use title for adding text in our pdf file
        val paint : Paint = Paint()
        val title : Paint = Paint()

        val pageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(width,pageHeight,1).create()

        //setting start page for our pdf file
        val myPage : PdfDocument.Page = pdfDocument.startPage(pageInfo)

        // creating a variable for canvas from our page of PDF.
        val canvas : Canvas = myPage.canvas

        //adding typeface to our text which we will be adding in our PDF file
        //title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))

        //setting text size which we will be displaying in our PDF file
        //title.textSize = 10f

        // setting color of our text inside our PDF file
        //title.color = ContextCompat.getColor(this,R.color.black)
        //title.color = getColor(R.color.black)

        //canvas.drawText("FirstCare Pharmacy",30f,30f,title)

        //pharmacy name
        var startX = 30f
        var startY = 30f
        var lineSpacing = 7f
        var endMargin = 30f
        var startMargin = 30f
        writeText(
            title = title,
            canvas = canvas,
            textSize = 10f,
            xCor = startX,
            yCor = startY,
            textColor = R.color.black,
            message = "FirstCare Pharmacy",
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
            )

        //address
        writeText(
            title = title,
            canvas = canvas,
            textSize = 6f,
            xCor = startX,
            yCor = startY + lineSpacing,
            textColor = R.color.gray,
            message = "XYZ ,Rasidency Bharat TDM",
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL)
        )

        //tdm number
        writeText(
            title = title,
            canvas = canvas,
            textSize = 6f,
            xCor = startX,
            yCor = startY + (2 * lineSpacing),
            textColor = R.color.gray,
            message = "TDM No : 12355",
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )

        //bridge health care title
        var pageWidth = pageInfo.pageWidth
        var message = "Bridge Health Care"
        title.textSize = 10f
        var textWidth = title.measureText(message)
        startX = pageWidth - textWidth - endMargin // Subtracting margin from the right edge
        startY = 30f

        writeText(
            title,
            canvas,
            textSize = 10f,
            xCor = startX,
            yCor = startY,
            textColor = R.color.black,
            message =  message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )

        //websiet
        message = "www.bridgeHealthCar"
        writeText(
            title,
            canvas,
            textSize = 6f,
            startX,
            startY + lineSpacing,
            textColor = R.color.gray,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL)
        )

        //phone number
        message = "1234567890"
        writeText(
            title,
            canvas,
            textSize = 6f,
            startX,
            startY + 2 * lineSpacing,
            textColor = R.color.gray,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL)
        )

        //health report title
        message = "Health Report"
        title.textSize = 20f
        textWidth = title.measureText(message)
        startY = 70f
        startX = (pageWidth - textWidth) / 2
        writeText(
            title,
            canvas,
            textSize = 20f,
            startX,
            startY,
            textColor = R.color.black,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )

        //patient details light gray bg
        var bgPaint = Paint()
        bgPaint.color = getColor(R.color.light_gray)
        startY = 90f
        startX = 30f
        lineSpacing = 15f
        canvas.drawRect(0f,startY, pageInfo.pageWidth.toFloat(),160f,bgPaint)

        //patient
        startY = 110f
        message = "PATIENT"
        writeText(
            title,
            canvas,
            textSize = 10f,
            startX,
            startY,
            textColor = R.color.black,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL)
        )

        //patient name
        message = "Ranvihay Singh Balbir"
        writeText(
            title,
            canvas,
            textSize = 17f,
            startX,
            startY + lineSpacing,
            textColor = R.color.black,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL)
        )

        // gender and age
        message = "Male , 78 years old"
        writeText(
            title,
            canvas,
            textSize = 10f,
            startX,
            startY + (2 * lineSpacing),
            textColor = R.color.black,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL)
        )

        //weight
        message = "Weight : 85kg"
        title.textSize = 10f
        textWidth = title.measureText(message)
        startY = 120f
        writeText(
            title,
            canvas,
            textSize = 10f,
            (pageWidth - textWidth)/2,
            startY,
            textColor = R.color.gray,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )

        //height
        message = "Height : 175 cm"
        title.textSize = 10f
        writeText(
            title,
            canvas,
            textSize = 10f,
            (pageWidth - textWidth)/2,
            startY + lineSpacing,
            textColor = R.color.gray,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )

        //date
        message = "Date : 01/12/2024"
        title.textSize = 10f
        textWidth = title.measureText(message)
        startX = pageWidth - textWidth - 30f
        writeText(
            title,
            canvas,
            textSize = 10f,
            startX,
            startY ,
            textColor = R.color.gray,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )


        //time
        message = "Time : 06 : 00 : 00"
        title.textSize = 10f
        textWidth = title.measureText(message)
        startX = pageWidth - textWidth - 30f
        writeText(
            title,
            canvas,
            textSize = 10f,
            startX,
            startY + lineSpacing ,
            textColor = R.color.gray,
            message,
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )

        //symptoms
        message = "Symptoms :"
        startY = 170f
        startX = 30f
        lineSpacing = 10f
        title.textSize = 10f
        writeText(
            title,
            canvas,
            textSize = 10f,
            startX,
            startY,
            textColor = R.color.gray,
            message,
            Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )

        //symptoms list bg color box
        startY = 180f
        var boxBottom = startY + (5* lineSpacing)
        var boxWidth = (pageInfo.pageWidth.toFloat() / 2) - 30f - 15f // 30f form start 15 form end
        bgPaint = Paint()
        bgPaint.color = getColor(R.color.light_gray)
        canvas.drawRect(30f,startY, (pageInfo.pageWidth.toFloat()/2) - 15f,boxBottom,bgPaint)

        //symptoms list
        //startY = startY + lineSpacing
        message = "This is a sample paragraph that needs to fit inside the colored box. " +
                "The text should wrap automatically and not overflow beyond the boundaries of the box."
        paragraphInBox(
            message = message,
            title = title,
            canvas = canvas,
            boxWidth = boxWidth - (2*lineSpacing),
            yCor = 190f,
            lineSpacing = lineSpacing,
            boxBottom = boxBottom,
            startX = startX + lineSpacing
        )

//        val lines = mutableListOf<String>()
//        var remainingText = message
//        while (remainingText.isNotEmpty()) {
//            // Measure the text to fit within the box width
//            var breakIndex = title.breakText(remainingText, true, boxWidth - (2*lineSpacing), null)
//            lines.add(remainingText.substring(0, breakIndex))
//            remainingText = remainingText.substring(breakIndex)
//        }
//
//        var currentY = 190f
//        for (line in lines) {
//            if (currentY + lineSpacing > boxBottom) break // Stop if the text exceeds the box height
//            canvas.drawText(line,  startX + 10f, currentY, title) // Add small padding inside the box
//            currentY += lineSpacing
//        }

        // Define table data
        var headers = listOf("Basic Health Parameters", "Normal Value", "Text Value", "Unit", "Status")
        var tableData = listOf(
            listOf("BMI", "30", "128", "kg/m2", "Pre diabetic"),
            listOf("Blood Pressure", "120/80", "128/84", "mmHg", "Normal"),
            listOf("Pulse Rate", "60-100", "72", "BMP", "Normal"),
            listOf("SPO2", "92", "180", "%", "High"),
            listOf("Body Temperature", "98.6", "101", "℉", "High")
        )

        // Table layout configuration
        startX = 30f  // Left margin
        startY = 250f // Top margin
        val columnWidths = listOf(170f, 120f, 100f, 80f, 80f) //column width
        val rowHeight = 15f // Row height

        //basics health parameters table
        table(
            title = title,
            canvas = canvas,
            headers = headers,
            tableData = tableData,
            columnWidths = columnWidths,
            startX = startX,
            startY = startY,
            rowHeight = rowHeight
        )

        //basic blood test
        headers = listOf("Basic Blood Test", "Normal Value", "Text Value", "Unit", "Status")
        tableData = listOf(
            listOf("Blood Sugar", "310", "168", "mg/ml", "Pre diabetic"),
            listOf("Hemoglobin", "13.2 - 16.6", "120-80", "mg/ml", "Normal"),
            listOf("Body Cholesterol", "> 200", "100", "g/dl", "Normal"),
            listOf("Uric Acid", "3.5 - 7.2", "100", "mg/dl", "High")
        )

        startX = 30f  // Left margin
        startY = 350f // Top margin

        table(
            title,
            canvas,
            headers,
            tableData,
            columnWidths,
            startX,
            startY,
            rowHeight
        )

        //not text
        startY = 645f
        writeText(
            title,
            canvas,
            textSize = 10f,
            xCor = 30f,
            yCor = startY,
            textColor = R.color.black,
            message = "Note",
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )


        //note box
        startY = 650f
        boxBottom = startY + (5* lineSpacing)
        boxWidth = pageInfo.pageWidth.toFloat() // 30f form start 15 form end
        bgPaint = Paint()
        bgPaint.color = getColor(R.color.light_gray)
        canvas.drawRect(0f,startY, boxWidth,boxBottom,bgPaint)



        //signature
        startY = 740f
        writeText(
            title,
            canvas,
            textSize = 10f,
            xCor = 30f,
            yCor = startY,
            textColor = R.color.gray,
            message = "Signature ...............................................................",
            typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        )



        //finish our page
        pdfDocument.finishPage(myPage)


        // setting the name of our PDF file and its path
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"HealthReport.pdf")

        try {
            // writing our PDF file to that location.
            pdfDocument.writeTo(FileOutputStream(file))

            // printing toast message on completion of PDF generation.
            Toast.makeText(this,"PDF file generated Successfully", Toast.LENGTH_SHORT).show()

        }catch (e : IOException) {
            e.printStackTrace()
            Toast.makeText(this,"Failed to generate PDF file", Toast.LENGTH_SHORT).show()
        }

        // closing our PDF file
        pdfDocument.close()
    }

    private fun writeText(
        title : Paint,
        canvas: Canvas ,
        textSize : Float,
        xCor : Float,
        yCor : Float,
        textColor : Int,
        message : String,
        typeface : Typeface
    ) {
        title.setTypeface(typeface)
        title.textSize = textSize
        title.color = ContextCompat.getColor(this,textColor)
        canvas.drawText(message,xCor,yCor,title)
    }

    private fun paragraphInBox(
        message : String,
        title: Paint,
        canvas: Canvas,
        boxWidth : Float,
        yCor : Float,
        lineSpacing : Float,
        boxBottom : Float,
        startX : Float
    ) {
        var currentY = yCor
        val lines = mutableListOf<String>()
        var remainingText = message
        while (remainingText.isNotEmpty()) {
            // Measure the text to fit within the box width
            var breakIndex = title.breakText(remainingText, true, boxWidth , null)
            lines.add(remainingText.substring(0, breakIndex))
            remainingText = remainingText.substring(breakIndex)
        }

        for (line in lines) {
            if (currentY + lineSpacing > boxBottom) break // Stop if the text exceeds the box height
            canvas.drawText(line,  startX, currentY, title) // Add small padding inside the box
            currentY += lineSpacing
        }
    }

    private fun table(
        title: Paint,
        canvas: Canvas,
        headers : List<String>,
        tableData : List<List<String>>,
        columnWidths : List<Float>,
        startX : Float,
        startY : Float,
        rowHeight : Float
    ) {
        var currentY = startY

        // Draw headers
        title.color = getColor(R.color.black)
        title.typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
        for (i in headers.indices) {
            val textX = startX + columnWidths.take(i).sum() // Calculate the starting X for each column
            canvas.drawText(headers[i], textX, currentY, title)
        }

        // Draw rows
        title.typeface = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL)
        currentY += rowHeight // Move to the next row
        for (row in tableData) {
            for (i in row.indices) {
                val textX = startX + columnWidths.take(i).sum() // Calculate the starting X for each column
                canvas.drawText(row[i], textX, currentY, title)
            }
            currentY += rowHeight // Move to the next row
        }
    }
}