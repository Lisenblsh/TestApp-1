package com.lis.domain.tools

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageFun {
    fun setImage(image: Any,imageView: ImageView){
        Glide
            .with(imageView)
            .load(image)
            .into(imageView)
    }
}