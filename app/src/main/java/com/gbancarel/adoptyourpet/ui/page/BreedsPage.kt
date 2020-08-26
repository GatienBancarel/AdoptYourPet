package com.gbancarel.adoptyourpet.ui.page

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModel
import com.gbancarel.adoptyourpet.ui.customView.CardBreeds
import com.gbancarel.adoptyourpet.ui.typography

class BreedsPage {

    @Composable
    fun Page(data: State<List<BreedsViewModel>>) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.preferredHeight(500.dp).fillMaxWidth()
                ) {
                    Text(
                        text = "Choose your breeds:",
                        style = typography.h6,
                    )
                    Divider(
                        color = Color.Transparent,
                        modifier = Modifier.preferredHeight(16.dp)
                    )
                    Card(
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Box() {
                            LazyColumnFor(data.value) { item ->
                                CardBreeds(breed = item.name)
                                Divider(
                                    color = Color.Transparent,
                                    modifier = Modifier.preferredHeight(2.dp)
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalGravity = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {}
                    ) {
                        Text(
                            text = "Validate",
                            style = typography.body2
                        )
                    }
                }
            }
        }
    }
}