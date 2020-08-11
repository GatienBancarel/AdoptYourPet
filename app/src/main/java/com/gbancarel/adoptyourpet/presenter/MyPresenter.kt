package com.gbancarel.adoptyourpet.presenter

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.PetAnimal
import com.gbancarel.adoptyourpet.interactor.PetFinder
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
class MyPresenter @Inject constructor(
    val viewModel: MyViewModel,
    @ActivityContext private val context: Context
) {

    fun present(call: PetFinder) {
        Log.i("mylog","je suis dans le presenter")
        //Log.i("mylog", call?.animals?.get(0)?.name.toString())
        val petFinderViewModel = PetFinderViewModel(call.animals.map { PetAnimal ->
            PetAnimalViewModel(
                type = PetAnimal.type,
                age = PetAnimal.age,
                gender = PetAnimal.gender,
                size = PetAnimal.size,
                name = PetAnimal.name,
                description = PetAnimal.description
            )
        })
        viewModel.liveData.postValue(petFinderViewModel)
    }

    fun presentErrorOkHttp(){
        Log.i("mylog", "okhttp failed")
    }

    fun presentErrorMoshi(){
        Log.i("mylog", "moshi failed")
    }

}

@Singleton
class MyViewModel @Inject constructor(): ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<PetFinderViewModel> = MutableLiveData()
}