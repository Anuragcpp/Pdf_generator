package com.example.pdf_generator

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pdf_generator.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var bmp : Bitmap
    private lateinit var scaledBmp : Bitmap
    private val pageHeight : Int = 1120
    private val pageWidth : Int = 792

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (checkPermission()) {
            Toast.makeText(this,"Permission Granted!",Toast.LENGTH_SHORT).show()
        }else requestPermission()

        bmp = BitmapFactory.decodeResource(resources,R.drawable.gfglogo)
        scaledBmp = Bitmap.createScaledBitmap(bmp,140,140,false)

        binding.generatePdf.setOnClickListener { generatePdf() }


    }

    private fun generatePdf() {
        // creating an object variable for our PDF document.
        val pdfDocument : PdfDocument = PdfDocument()

        //two variables for paint "paint" is used for drawing shapes and we
        // will use title for adding text in our pdf file
        val paint : Paint = Paint()
        val title : Paint = Paint()

        //adding page info to our pdf file , height width and number of pages
        val pageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(pageWidth,pageHeight,1).create()

        //setting start page for our pdf file
        val myPage : PdfDocument.Page = pdfDocument.startPage(pageInfo)

        // creating a variable for canvas from our page of PDF.
        val canvas : Canvas = myPage.canvas

        if (scaledBmp != null ) {
            // drawing our image on our PDF file
            canvas.drawBitmap(scaledBmp,56f,40f,paint)
        }else Log.e("generatePDF", "Bitmap is null. Skipping bitmap drawing.")

        //adding typeface to our text which we will be adding in our PDF file
        title.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL))

        //setting text size which we will be displaying in our PDF file
        title.textSize = 15f

        // setting color of our text inside our PDF file
        title.color = ContextCompat.getColor(this,R.color.black)
        //title.color = getColor(R.color.black)

        // drawing text in our PDF file.
        canvas.drawText("A Portal for IT Professional",209f,100f,title)
        canvas.drawText("Geeks for Geeks",209f,80f,title)

        //creating another text and in this we are aligning this text to center of our pdf
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.color = getColor(R.color.black)
        title.textSize = 15f

        // setting our text to center of PDF.
        title.textAlign = Paint.Align.CENTER

        //drawing text at the center
        canvas.drawText("This is a simple document which we have created.",396f,560f,title)

        //finish our page
        pdfDocument.finishPage(myPage)

        // setting the name of our PDF file and its path
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"GFG.pdf")

        try {
            // writing our PDF file to that location.
            pdfDocument.writeTo(FileOutputStream(file))

            // printing toast message on completion of PDF generation.
            Toast.makeText(this,"PDF file generated Successfully",Toast.LENGTH_SHORT).show()

        }catch (e : IOException) {
            e.printStackTrace()
            Toast.makeText(this,"Failed to generate PDF file",Toast.LENGTH_SHORT).show()
        }

        // closing our PDF file
        pdfDocument.close()

    }

    private fun checkPermission() : Boolean {
        //chick permission
        val permission1 = ContextCompat.checkSelfPermission(applicationContext,WRITE_EXTERNAL_STORAGE)
        val permission2 = ContextCompat.checkSelfPermission(applicationContext,READ_EXTERNAL_STORAGE)

        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        //request permission if not provided
        ActivityCompat.requestPermissions(
            this,
            arrayOf(WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if(requestCode == PERMISSION_REQUEST_CODE) {
            if(grantResults.isNotEmpty()) {
                //after requesting permissions we are showing
                // users a toast message of permission granted.
                val writePermission : Boolean = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readPermission : Boolean = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if (writePermission && readPermission ) Toast.makeText(this,"Permission Granted!",Toast.LENGTH_SHORT).show()
                else Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        private var PERMISSION_REQUEST_CODE : Int = 200
    }
}