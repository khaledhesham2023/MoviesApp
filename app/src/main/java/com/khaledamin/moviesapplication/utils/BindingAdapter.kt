package com.khaledamin.moviesapplication.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.presentation.model.Tab
import java.util.Locale

@BindingAdapter("imgUrl")
fun convertUrlToImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load("https://image.tmdb.org/t/p/w500/$url")
        .placeholder(R.drawable.ic_app).into(imageView)
}

@BindingAdapter("lng")
fun convertCodeIntoLanguage(textView: TextView, code: String) {
    val locale = Locale(code)
    textView.text = locale.displayLanguage
}


@SuppressLint("SetTextI18n")
@BindingAdapter("restricted")
fun convertBooleanToRestrictedContent(textView: TextView, adult: Boolean) {
    if (adult) {
        textView.text = "Yes"
    } else {
        textView.text = "No"
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("favorite")
fun setFavoriteButton(button: Button, isFavorite: Boolean) {
    if (isFavorite) {
        button.text = button.context.getString(R.string.added_favorite)
        button.setTextColor(button.context.getColor(R.color.midnight_blue))
        button.backgroundTintList = ColorStateList.valueOf(button.context.getColor(R.color.silver))
    } else {
        button.text = button.context.getString(R.string.add_to_favorite)
        button.setTextColor(button.context.getColor(R.color.silver))
        button.backgroundTintList =
            ColorStateList.valueOf(button.context.getColor(R.color.midnight_blue))
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("tab")
fun setTab(textView: TextView, tab: Tab) {
    textView.text = tab.title.keys.elementAt(0)
    if (tab.isSelected) {
        textView.setBackgroundColor(textView.context.getColor(R.color.silver))
        textView.setTextColor(textView.context.getColor(R.color.midnight_blue))
    } else {
        textView.setBackgroundColor(textView.context.getColor(R.color.midnight_blue))
        textView.setTextColor(textView.context.getColor(R.color.silver))
    }
}