package com.example.cinee.component.text

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.cinee.ui.theme.Dimens

@Composable
fun EditableTextField(
    text: String,
    onTextChange: (String) -> Unit,
    isEditable: Boolean,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {

        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = modifier.padding(Dimens.paddingSmall),
            textStyle = textStyle.copy(textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface), // Match non-editable alignment
            singleLine = singleLine,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            enabled = isEditable,
            decorationBox = { innerTextField ->
                if(isEditable) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            shape = MaterialTheme.shapes.large
                        )
                            .padding(vertical = Dimens.paddingMedium, horizontal = Dimens.paddingExtraLarge * 2)
                    ) {
                        innerTextField()
                    }
                }
                else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(vertical = Dimens.paddingMedium)
                    ) {
                        innerTextField()
                    }
                }
            }
        )
}