package com.gbancarel.adoptyourpet.ui.page

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.gbancarel.adoptyourpet.Activity.SearchActivity
import com.gbancarel.adoptyourpet.presenter.data.PetAnimalViewModel
import com.gbancarel.adoptyourpet.ui.elementUI.ButtonSearch
import com.gbancarel.adoptyourpet.ui.elementUI.CardHomePage

class HomePage {

    @Composable
    fun Page(
        applicationContext: Context,
        liveData: MutableLiveData<List<PetAnimalViewModel>>
    ) {
        val data = liveData.observeAsState(initial = emptyList())

        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ButtonSearch(
                    onClick = {
                        applicationContext.startActivity(
                            SearchActivity.newIntent(
                                applicationContext
                            )
                        )
                    }
                )
                Divider(
                    color = Color.Transparent,
                    modifier = Modifier.preferredHeight(32.dp)
                )
                LazyColumnFor(data.value) { item ->
                    CardHomePage(
                        name = item.name,
                        description = item.description!!,
                        image = item.photos,
                        shouldShowPhoto = item.shouldShowPhoto
                    )
                    Divider(
                        color = Color.Transparent,
                        modifier = Modifier.preferredHeight(16.dp)
                    )
                }
            }
        }
    }
}