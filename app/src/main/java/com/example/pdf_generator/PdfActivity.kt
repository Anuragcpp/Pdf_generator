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
        val endMargin = 30f
        val startMargin = 30f
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
}