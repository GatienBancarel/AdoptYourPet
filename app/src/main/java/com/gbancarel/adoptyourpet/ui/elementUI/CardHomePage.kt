package com.gbancarel.adoptyourpet.ui.elementUI

import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.R
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ContentGravity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.setValue
import androidx.compose.runtime.state
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.graphics.drawscope.drawCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.imageResource
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.gbancarel.adoptyourpet.presenter.data.PhotoViewModel


@Composable
fun CardHomePage(name: String, description: String, image: List<PhotoViewModel>, shouldShowPhoto: Boolean) {
    val typography = MaterialTheme.typography

    Box(
        modifier = Modifier.fillMaxWidth().clickable(onClick = {/* Go to DogPage */ })
    ) {
        Row(
            verticalGravity = Alignment.CenterVertically
        ) {
            if (shouldShowPhoto) {
                NetworkImageComponentGlide(url = image[0].small)
                //NetworkImageComponentPicasso(url = "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/48808161/1/?bust=1597742210&width=100")
            } else {
                Image(
                    asset = imageResource(id = R.drawable.chien),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.preferredHeight(64.dp).preferredWidth(64.dp).clip(shape = RoundedCornerShape(32.dp))
                )
            }
            Divider(
                color = Color.Transparent,
                modifier = Modifier.preferredHeight(10.dp).preferredWidth(10.dp)
            )
            Column() {
                Text(
                    text = name,
                    style = typography.h6,
                    maxLines = 1
                )
                Text(
                    text = description,
                    maxLines = 2,
                    style = typography.body2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun NetworkImageComponentGlide(
    url: String, modifier: Modifier = Modifier.preferredHeight(64.dp).preferredWidth(64.dp).clip(shape = RoundedCornerShape(32.dp))
) {
    var image by state<ImageAsset?> { null }
    var drawable by state<Drawable?> { null }
    val context = ContextAmbient.current
    onCommit(url) {
        val glide = Glide.with(context)
        val target = object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                image = null
                drawable = placeholder
            }

            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                image = bitmap.asImageAsset()
            }
        }
        glide
            .asBitmap()
            .load(url)
            .into(target)

        onDispose {
            image = null
            drawable = null
            glide.clear(target)
        }
    }

    val theImage = image
    val theDrawable = drawable
    if (theImage != null) {
        // Box is a predefined convenience composable that allows you to apply common draw & layout
        // logic. In addition we also pass a few modifiers to it.

        // You can think of Modifiers as implementations of the decorators pattern that are
        // used to modify the composable that its applied to. In this example, we configure the
        // Box composable to have a max height of 200dp and fill out the entire available
        // width.
            // Image is a pre-defined composable that lays out and draws a given [ImageAsset].
            Image(
                asset = theImage,
                contentScale = ContentScale.Crop,
                modifier = Modifier.preferredHeight(64.dp).preferredWidth(64.dp).clip(shape = RoundedCornerShape(32.dp))
            )
    } else if (theDrawable != null) {
        Canvas(modifier = modifier) {
            drawCanvas { canvas, _ ->
                theDrawable.draw(canvas.nativeCanvas)
            }
        }
    }
}