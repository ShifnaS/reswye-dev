package com.nexgensm.reswye.ui.lead;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.nexgensm.reswye.R;

import com.shockwave.pdfium.PdfDocument;


import java.io.File;
import java.util.List;

public class PdfViewerActivity extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener, OnPageErrorListener {
    private static final String TAG = PdfViewerActivity.class.getSimpleName();
    public static final String SAMPLE_FILE = "android_tutorial.pdf";
    WebView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        String newString,dbname;
        Intent i = getIntent();
        newString= i.getStringExtra("doc_name");
        dbname=i.getStringExtra("dbdoc_name");
        pdfView= (WebView)findViewById(R.id.pdfView);
        Toast.makeText(getApplicationContext(), ""+dbname, Toast.LENGTH_SHORT).show();
        Log.e("5555555555",""+dbname);

try{

    String dbname1="http://www.africau.edu/images/default/sample.pdf";
    pdfView.getSettings().setJavaScriptEnabled(true);
    pdfView.getSettings().setPluginState(WebSettings.PluginState.ON);
   // pdfView.loadUrl(dbname);
    pdfView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + dbname1);

    pdfView.setWebViewClient(new WebViewClient() {
        public void onPageFinished(WebView view, String url) {
        }
    });
  //  Uri path=Uri.parse(dbname);
   // displayFromUri(path);
}
catch(Exception e){
    e.printStackTrace();
}



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



   /* private void displayFromUri(Uri uri) {
        pdfFileName = getFileName(uri);

        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }*/



    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
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
       // PdfDocument.Meta meta = pdfView.getDocumentMeta();
       // printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
    }
}
