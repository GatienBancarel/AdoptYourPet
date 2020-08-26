package com.gbancarel.adoptyourpet.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.gbancarel.adoptyourpet.controller.BreedsPageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.BreedsPageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModel
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.customView.CardBreeds
import com.gbancarel.adoptyourpet.ui.typography
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreedsActivity : AppCompatActivity() {

    @Inject lateinit var controller: BreedsPageControllerDecorator
    @Inject lateinit var viewModel: BreedsPageViewModel

    companion object {
        val RESULT_DATA_KEY = "RESULT_DATA_KEY"
        fun newIntent(context: Context) = Intent(context, BreedsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    controller.onCreate()
                    display(viewModel.liveData)
                }
            }
        }
    }

    @Composable
    fun display(liveData: MutableLiveData<List<BreedsViewModel>>) {
        val data = liveData.observeAsState(
            initial = emptyList()
        )
        Page(data)
    }

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