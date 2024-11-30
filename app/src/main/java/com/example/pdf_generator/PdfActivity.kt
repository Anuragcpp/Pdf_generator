package com.example.pdf_generator

import android.graphics.Canvas
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
    private val pageWidth : Int = 595
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

        val pageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(pageWidth,pageHeight,1).create()

        //setting start page for our pdf file
        val myPage : PdfDocument.Page = pdfDocument.startPage(pageInfo)

        // creating a variable for canvas from our page of PDF.
        val canvas : Canvas = myPage.canvas

        //adding typeface to our text which we will be adding in our PDF file
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))

        //setting text size which we will be displaying in our PDF file
        title.textSize = 10f

        // setting color of our text inside our PDF file
        title.color = ContextCompat.getColor(this,R.color.black)
        //title.color = getColor(R.color.black)

        canvas.drawText("FirstCare Pharmacy",30f,30f,title)


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
}