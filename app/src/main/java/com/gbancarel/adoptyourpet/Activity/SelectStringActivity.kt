package com.gbancarel.adoptyourpet.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.gbancarel.adoptyourpet.controller.SelectedStringPageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.BreedsPageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StringSelectedViewModelData
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.customView.CardBreeds
import com.gbancarel.adoptyourpet.ui.typography
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectStringActivity : AppCompatActivity() {

    @Inject lateinit var controller: SelectedStringPageControllerDecorator
    @Inject lateinit var viewModel: BreedsPageViewModel
    private var selectedSting = emptyList<String>()
    private var title: String = ""

    companion object {
        val RESULT_DATA_KEY = "RESULT_DATA_KEY"
        private val SELECTED_RESULT_KEY = "SELECTED_RESULT_KEY"
        private val SELECTED_TITLE_KEY = "SELECTED_TITLE_KEY"
        fun newIntent(context: Context, selectedString: List<String>, title: String) = Intent(context, SelectStringActivity::class.java).apply {
            putExtra(SELECTED_RESULT_KEY, ArrayList(selectedString))
            putExtra(SELECTED_TITLE_KEY, title)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedSting = intent.getStringArrayListExtra(SELECTED_RESULT_KEY)?.toList() ?: emptyList()
        title = intent.getStringExtra(SELECTED_TITLE_KEY) ?: ""
        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    controller.onCreate(selectedSting, title)
                    display(viewModel.liveData)
                }
            }
        }
    }

    @Composable
    fun display(liveData: MutableLiveData<List<StringSelectedViewModelData>>) {
        Log.i("mylog","selectedString: ${selectedSting.toString()}")
        val data = liveData.observeAsState(
            initial = emptyList()
        )
        Page(data)
    }

    @Composable
    fun Page(data: State<List<StringSelectedViewModelData>>) {
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
                        text = "Choose your $title:",
                        style = typography.h6,
                    )
                    Divider(
                        color = Color.Transparent,
                        modifier = Modifier.preferredHeight(16.dp)
                    )
                    Card(
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Box {
                            LazyColumnFor(data.value) { item ->
                                CardBreeds(breed = item.name, checked = selectedSting.contains(item.name), onCheckedChange = { name, selected ->
                                    controller.checkedChange(name, selected)
                                })
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
                        onClick = {
                            intent.putStringArrayListExtra(RESULT_DATA_KEY, ArrayList(data.value.filter { it.selected }.map { it.name }))
                            setResult(RESULT_OK, intent)
                            finish()
                        }
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