package com.filit.testweather.common.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.filit.testweather.viewmodel.ViewVisibilityState
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

fun ViewPager.addOnPageSelectedListener(listener: (Int) -> Unit) =
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            listener(position)
        }

        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }
    })

fun View.applyVisibilityState(state: ViewVisibilityState) {
    visibility = when (state) {
        ViewVisibilityState.Visible -> View.VISIBLE
        ViewVisibilityState.Invisible -> View.INVISIBLE
        ViewVisibilityState.Gone -> View.GONE
    }
}




fun Activity.finishSuccessful(result: Intent? = null) {
    setResult(Activity.RESULT_OK, result)
    finish()
}


fun ImageView.loadImage(
    url: String?
) = Picasso.get().load(url).fit().centerInside().into(this)


fun Editable?.trimString() = this?.trim()?.toString() ?: ""

fun EditText.doAfterTextChanged(
    listener: (String) -> Unit
) = addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable?) = listener(s.trimString())
})

fun EditText.onAction(callback: (value: String) -> Unit) =
    setOnEditorActionListener { _, id, _ ->
        when (id) {
            EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_SEARCH, EditorInfo.IME_ACTION_SEND -> {
                callback.invoke(text.trimString())
                true
            }
            else -> false
        }
    }


fun TextInputEditText.setOnClearListener(listener: () -> Unit) {
    doAfterTextChanged { if (it.isNullOrEmpty()) listener() }
}


fun Fragment.hideKeyboard() = view?.let { context?.hideKeyboard(it) }
fun Activity.hideKeyboard() = hideKeyboard(currentFocus ?: View(this))
fun Context.hideKeyboard(view: View) =
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
        hideSoftInputFromWindow(view.windowToken, 0)
    }

fun Context.showKeyboard(view: View, delay: Long = 0) =
    view.postDelayed({
        view.requestFocus()
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }, delay)

fun Context.showToastMessage(message: String): Toast =
    Toast
        .makeText(this, message, Toast.LENGTH_SHORT)
        .also {
            (it.view.findViewById(android.R.id.message) as? TextView)?.gravity = Gravity.CENTER_HORIZONTAL
            it.show()
        }




