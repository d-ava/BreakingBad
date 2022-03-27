package com.example.breakingbad.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.breakingbad.R

fun ImageView.glideExtension(img: String?) {
    if (!img.isNullOrEmpty()) {
        Glide.with(context).load(img).error(R.drawable.ic_close)
            .into(this)

    } else setImageResource(R.drawable.ic_close)


}