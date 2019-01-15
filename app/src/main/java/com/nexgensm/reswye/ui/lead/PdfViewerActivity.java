package com.nexgensm.reswye.ui.lead;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.nexgensm.reswye.R;

import com.shockwave.pdfium.PdfDocument;


import java.io.File;
import java.util.List;

public class PdfViewerActivity extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener {
    private static final String TAG = PdfViewerActivity.class.getSimpleName();
    public static final String SAMPLE_FILE = "android_tutorial.pdf";
   PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        String newString,dbname;
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("doc_name");
        dbname=extras.getString("dbdoc_name");
        pdfView= (PDFView)findViewById(R.id.pdfView);

      //  displayFromAsset(SAMPLE_FILE);

 //       File downloadDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());

       // String path=downloadDir+"/"+newString;

     //  getFilesFromDir(downloadDir);
try{
    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator +dbname);
   //Uri path = Uri.fromFile(file);
    Uri path=FileProvider.getUriForFile(getApplicationContext(),PdfViewerActivity.this.getPackageName()+"com.nexgensm.reswye.ui.lead",file);
    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
    pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    pdfOpenintent.setDataAndType(path, "application/pdf");
    startActivity(pdfOpenintent);
}
catch(Exception e){
    e.printStackTrace();
}

//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator +dbname);
//       // Uri path = Uri.fromFile(file);
//       Uri path=FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getApplicationContext().getPackageName()+"com.nexgensm.reswye.ui.lead",file);
//        // Uri path= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".my.package.name.provider", createImageFile());
      //  Log.i("Fragment2", String.valueOf(path));





//        catch (ActivityNotFoundException e) {
//         e.printStackTrace();
//        }

        TextView property_Name=(TextView)findViewById(R.id.PropertyName_Text);
        property_Name.setText(newString);

        ImageButton close= (ImageButton)findViewById(R.id.documentView_closeBtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getFilesFromDir(File filesFromSD) {

        File listAllFiles[] = filesFromSD.listFiles();

        if (listAllFiles != null && listAllFiles.length > 0) {
            for (File newString : listAllFiles) {
                if (newString.isDirectory()) {
                    getFilesFromDir(newString);
                } else {
                    if (newString.getName().endsWith("")) {
                        // File absolute path
                        Log.e("File path", newString.getAbsolutePath());
                        // File Name
                        Log.e("File path", newString.getName());

                    }
                }
            }
        }
    }
    public void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;
try{
    pdfView.fromAsset(SAMPLE_FILE)
            .defaultPage(pageNumber)
            .enableSwipe(true)
            .swipeHorizontal(true)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(new DefaultScrollHandle(this))
            .load();
}

catch(Exception e){
    e.printStackTrace();
}
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }



    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }
}
