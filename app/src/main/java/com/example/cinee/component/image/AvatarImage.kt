package com.example.cinee.component.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cinee.ui.theme.CineeTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon

@Composable
fun AvatarImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Medium,
    contentDescription: String? = "Profile picture",
    fallbackInitials: String? = null
) {
    Surface(
        modifier = modifier.size(size.size),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 2.dp
    ) {
        Box(
            modifier = Modifier.clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            when {
                imageUrl != null -> {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = contentDescription,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                fallbackInitials != null -> {
                    Text(
                        text = fallbackInitials,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                }
                else -> {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = contentDescription,
                        modifier = Modifier
                            .size(size.size * 0.6f)
                            .padding(4.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

enum class AvatarSize(val size: Dp) {
    Small(48.dp),
    Medium(64.dp),
    Large(96.dp),
    ExtraLarge(128.dp)
}

@Preview(name = "Avatar Image", showBackground = true)
@PreviewLightDark
@Composable
fun AvatarImagePreview() {
    CineeTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            AvatarImage(
                imageUrl = null,
                fallbackInitials = "JD",
                size = AvatarSize.Large
            )

            AvatarImage(
                imageUrl = null,
                fallbackInitials = null,
                size = AvatarSize.Large
            )
        }
    }
}

