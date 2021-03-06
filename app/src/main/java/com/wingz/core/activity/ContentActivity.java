/*
 * Copyright (c) 2016. The Wingz Project
 * Developed by Wingz Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wingz.core.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wingz.core.test.R;

import java.io.IOException;
import java.io.InputStream;

public class ContentActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String contentText;
    private long contentLinkImage;
    private TextView mTextView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Floating button to save an item as a favorite
        // TODO: Implement favorite action
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_content);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Saved as favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // Extra contents to build the ContentActivity
        Bundle b = getIntent().getExtras();
        contentText = b.getString("content");
        contentLinkImage = b.getLong("id");
        mTextView = (TextView) findViewById(R.id.content_text);
        mTextView.setText(contentText);
        mImageView = (ImageView)findViewById(R.id.content_image);
        try
        {
            // Get input stream
            InputStream ims = this.getAssets().open("sites/photos/"+contentLinkImage+".jpg");
            // Load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // Set image to ImageView
            mImageView.setImageDrawable(d);
        }
        catch(IOException ex)
        {
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
