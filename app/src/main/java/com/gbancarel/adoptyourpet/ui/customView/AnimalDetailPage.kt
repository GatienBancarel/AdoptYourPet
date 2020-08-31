package com.gbancarel.adoptyourpet.ui.customView

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.transition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope.weight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.drawLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.PetDetailViewModelData
import com.gbancarel.adoptyourpet.ui.typography

private enum class TranslationState {
    Start, End
}

private val heightKey = FloatPropKey()

@SuppressLint("Range")
private val translation = transitionDefinition<TranslationState> {
    state(TranslationState.Start) {
        this[heightKey] = 1000f
    }
    state(TranslationState.End) {
        this[heightKey] = 0f
    }
    transition(fromState = TranslationState.Start, toState = TranslationState.End) {
        heightKey using keyframes {
            durationMillis = 300
        }
    }
}

@Composable
fun AnimalDetailPage(
    detailAnimal: PetDetailViewModelData,
    onBack: () -> Unit
) {
    Stack {
        val scroll = rememberScrollState(0f)
        val showSnackBar = remember { mutableStateOf(false) }
        BackgroundAnimalDetail()
        ScrollableCard(scroll, detailAnimal)
        AppBar(onBack, showSnackBar)
        if (showSnackBar.value) {
            Snackbar(
                text = { Text(stringResource(id = R.string.feature_unavailable)) }
            )
        }
    }
}

@Composable
fun AppBar(onBack: () -> Unit, showSnackBar: MutableState<Boolean>) {
    Row(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .weight(1f),
            gravity = ContentGravity.CenterStart
        ) {
            Image(
                asset = vectorResource(id = R.drawable.ic_arrow_back_24px),
                modifier = Modifier
                    .preferredSize(32.dp)
                    .clickable(onClick = onBack)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f),
            gravity = ContentGravity.CenterEnd
        ) {
            Image(
                asset = vectorResource(id = R.drawable.ic_favorite_border_24px),
                modifier = Modifier
                    .preferredSize(32.dp)
                    .clickable(onClick = {
                        showSnackBar.value = true
                        Handler(Looper.getMainLooper()).postDelayed({
                            showSnackBar.value = false
                        }, 1000)
                    })
            )
        }
    }
}

@Composable
fun ScrollableCard(scroll: ScrollState, detailAnimal: PetDetailViewModelData) {
    val transition = transition(
        definition = translation,
        toState = TranslationState.End,
        initState = TranslationState.Start
    )
    WithConstraints(modifier = Modifier.fillMaxSize()) {
        val spaceHeight = with(DensityAmbient.current) { constraints.maxHeight.toDp() }.times(0.6f)
        val cardHeight = with(DensityAmbient.current) { constraints.maxHeight.toDp() }

        ScrollableColumn(
            modifier = Modifier.drawLayer(translationY = transition[heightKey]),
            scrollState = scroll
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (space, image, card) = createRefs()
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(spaceHeight)
                        .constrainAs(space) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(card.top)
                            end.linkTo(parent.end)
                        })
                Card(
                    shape = RoundedCornerShape(topLeft = 32.dp, topRight = 32.dp),
                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(cardHeight)
                        .constrainAs(card) {
                            start.linkTo(parent.start)
                            top.linkTo(space.bottom)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    Column(
                        Modifier.padding(
                            top = 104.dp,
                            start = 32.dp,
                            bottom = 32.dp,
                            end = 32.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = detailAnimal.name,
                                style = typography.h1
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            detailAnimal.breed?.let {
                                Text(
                                    text = it,
                                    style = typography.body1
                                )
                            }
                            Text(text = detailAnimal.color, style = typography.body1)
                        }
                        Spacer(modifier = Modifier.preferredHeight(16.dp))
                        Row(modifier = Modifier.preferredHeight(24.dp)) {
                            detailAnimal.type?.let { Image(asset = imageResource(id = it)) }
                            detailAnimal.gender?.let { Image(asset = imageResource(id = it)) }
                            detailAnimal.size?.let { Image(asset = imageResource(id = it)) }
                            detailAnimal.age?.let { Image(asset = imageResource(id = it)) }
                        }
                        Spacer(modifier = Modifier.preferredHeight(16.dp))
                        detailAnimal.description?.let {
                            Text(
                                text = it,
                                style = typography.body2
                            )
                        }
                    }
                }

                val scale = 2 - (scroll.value / scroll.maxValue)
                Box(
                    modifier = Modifier
                        .preferredHeight(100.dp)
                        .preferredWidth(100.dp)
                        .drawLayer(scaleX = scale, scaleY = scale)
                        .drawShadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .zIndex(with(DensityAmbient.current) { 4.dp.toPx() })
                        .constrainAs(image) {
                            start.linkTo(card.start)
                            top.linkTo(card.top)
                            bottom.linkTo(card.top)
                            end.linkTo(card.end)
                        }
                ) {
                    if (detailAnimal.photo != null) {
                        NetworkImageComponentGlide(
                            url = detailAnimal.photo,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            asset = imageResource(id = R.mipmap.dog),
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun BackgroundAnimalDetail() {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.primary)
            .fillMaxSize()
    )
}