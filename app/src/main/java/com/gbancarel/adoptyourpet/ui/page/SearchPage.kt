package com.gbancarel.adoptyourpet.ui.page

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.gbancarel.adoptyourpet.Activity.SearchPageViewModel
import com.gbancarel.adoptyourpet.StateSearchActivity
import com.gbancarel.adoptyourpet.ui.elementUI.MyCat
import com.gbancarel.adoptyourpet.ui.elementUI.MyDog
import com.gbancarel.adoptyourpet.ui.typography


class SearchPage {

    @Composable
    fun Page(viewModel: SearchPageViewModel) {
        val data = viewModel.liveData.observeAsState(
            initial = StateSearchActivity(type = "unknown")
        )
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column (
                modifier = Modifier
                    .padding(16.dp),
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                Box(){
                    Text(
                        text = "Tap to choose your pet:",
                        style = typography.body2
                    )
                    Row {
                        MyDog(data.value.type,viewModel)
                        MyCat(data.value.type,viewModel)
                    }
                }
            }
        }
    }
}