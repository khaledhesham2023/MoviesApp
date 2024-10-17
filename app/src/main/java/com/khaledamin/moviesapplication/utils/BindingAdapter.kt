package com.khaledamin.moviesapplication.utils

import android.content.res.ColorStateList
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.khaledamin.moviesapplication.BuildConfig
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.presentation.model.Tab
import java.util.Locale

@BindingAdapter("imgUrl")
fun convertUrlToImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load("${BuildConfig.IMAGE_URL}$url")
        .placeholder(R.drawable.ic_app).into(imageView)
}

@BindingAdapter("lng")
fun convertCodeIntoLanguage(textView: TextView, code: String) {
    val locale = Locale(code)
    textView.text = locale.displayLanguage
}


@BindingAdapter("restricted")
fun convertBooleanToRestrictedContent(textView: TextView, adult: Boolean) {
    if (adult) {
        textView.text = ContextCompat.getString(textView.context, R.string.yes)
    } else {
        textView.text = ContextCompat.getString(textView.context, R.string.no)
    }
}

@BindingAdapter("favorite")
fun setFavoriteButton(button: Button, isFavorite: Boolean) {
    if (isFavorite) {
        button.text = button.context.getString(R.string.added_favorite)
        button.setTextColor(ContextCompat.getColor(button.context,R.color.midnight_blue))
        button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(button.context,R.color.silver))
    } else {
        button.text = button.context.getString(R.string.add_to_favorite)
        button.setTextColor(ContextCompat.getColor(button.context,R.color.silver))
        button.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(button.context,R.color.midnight_blue))
    }
}

@BindingAdapter("tab")
fun setTab(textView: TextView, tab: Tab) {
    textView.text = tab.title.keys.elementAt(0)
    if (tab.isSelected) {
        textView.setBackgroundColor(ContextCompat.getColor(textView.context,R.color.silver))
        textView.setTextColor(ContextCompat.getColor(textView.context,R.color.midnight_blue))
    } else {
        textView.setBackgroundColor(ContextCompat.getColor(textView.context, R.color.midnight_blue))
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.silver))
    }
}