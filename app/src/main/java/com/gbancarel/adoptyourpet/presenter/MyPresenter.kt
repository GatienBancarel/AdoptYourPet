package com.gbancarel.adoptyourpet.presenter

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.donnes.*
import com.gbancarel.adoptyourpet.presenter.donnees.*
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
        val petFinderViewModel = PetFinderViewModel(call.animals.map { PetAnimal ->
            PetAnimalViewModel(
                    type = PetAnimal.type,
                    breeds = BreedViewModel(primary = PetAnimal.breeds?.primary),
                    colors = ColorViewModel(primary = PetAnimal.colors?.primary),
                    age = PetAnimal.age,
                    gender = PetAnimal.gender,
                    size = PetAnimal.size,
                    environment = EnvironmentViewModel(
                            children = PetAnimal.environment?.children,
                            dog = PetAnimal.environment?.dog,
                            cat = PetAnimal.environment?.cat
                    ),
                    name = PetAnimal.name,
                    description = PetAnimal.description,
                    photos = PetAnimal.photos?.map { Photo ->
                        PhotoViewModel(
                                small = Photo?.small,
                                medium = Photo?.medium,
                                large = Photo?.large,
                                full = Photo?.full
                        )
                    },
                    contact = ContactViewModel(
                            email = PetAnimal.contact?.email,
                            phone = PetAnimal.contact?.phone,
                            address = Adress(
                                    address1 = PetAnimal.contact?.address?.address1,
                                    address2 = PetAnimal.contact?.address?.address2,
                                    city = PetAnimal.contact?.address?.city,
                                    state = PetAnimal.contact?.address?.state,
                                    postCode = PetAnimal.contact?.address?.postCode,
                                    country = PetAnimal.contact?.address?.country
                            )
                    )
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