package dev.brevitz.nike.core.ui

import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun TextInputEditText.showFieldError(@StringRes errorMessageId: Int) {
    (this.parent.parent as TextInputLayout?)?.apply {
        isErrorEnabled = true
        error = context.getString(errorMessageId)
    }

    this.requestFocus()
}

fun TextInputEditText.observeTextChanges(): Observable<String> = textChanges()
    .doOnNext { (this.parent.parent as TextInputLayout?)?.isErrorEnabled = false }
    .debounce(250, TimeUnit.MILLISECONDS)
    .map { it.toString() }

fun TextInputLayout.disableErrorIfShown() {
    if (this.isErrorEnabled) this.isErrorEnabled = false
}
