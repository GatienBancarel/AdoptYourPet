package com.gbancarel.adoptyourpet.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.AnimalSelected
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.page.SearchPage
import javax.inject.Singleton

class SearchActivity : AppCompatActivity() {

     var viewModel: SearchPageViewModel = SearchPageViewModel()

    companion object {
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SearchPage().Page(viewModel)
                }
            }
        }
    }
}

@Singleton
class SearchPageViewModel: ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<AnimalSelected> = MutableLiveData()
}
