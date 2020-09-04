package com.gbancarel.adoptyourpet.presenter

import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.PetAnimal
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.*
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
class ResultPagePresenter @Inject constructor(
    val viewModel: ResultPageViewModel,
    @ActivityContext private val context: Context
) {

    fun present(listAnimal: List<PetAnimal>, requestedAnimal: PetAnimal? = null) {
        val petFinderViewModel = PetFinderViewModelData(
            state = PetFinderViewModelState.finished,
            animals = listAnimal.map { PetAnimal ->
                PetAnimalViewModel(
                    name = PetAnimal.name,
                    description = PetAnimal.description ?: "No description",
                    photos = PetAnimal.photos.map { Photo ->
                        PhotoViewModel(medium = Photo.medium)
                    },
                    shouldShowPhoto = PetAnimal.photos.isNotEmpty(),
                )
            },
            detailAnimal = requestedAnimal?.let {
                PetDetailViewModelData(
                    it.name,
                    when (it.age?.toLowerCase()) {
                        "young" -> R.drawable.young
                        "old" -> R.drawable.old
                        else -> null
                    },
                    when (it.type?.toLowerCase()) {
                        "cat" -> R.mipmap.cat
                        "dog" -> R.mipmap.dog
                        else -> null
                    },
                    "${it.breed}",
                    it.color ?: "",
                    when (it.size?.toLowerCase()) {
                        "small" -> R.drawable.small
                        "medium" -> R.drawable.medium
                        "large" -> R.drawable.large
                        else -> null
                    },
                    when (it.gender?.toLowerCase()) {
                        "male" -> R.drawable.male
                        "female" -> R.drawable.female
                        else -> null
                    },
                    it.description,
                    it.photos.firstOrNull()?.medium
                )
            }
        )
        viewModel.liveData.postValue(petFinderViewModel)
    }

    fun presentLoader() {
        val petFinderViewModel = PetFinderViewModelData(
            state = PetFinderViewModelState.loading,
            animals = emptyList(),
            detailAnimal = null
        )
        viewModel.liveData.postValue(petFinderViewModel)
    }

    fun presentError() {
        val petFinderViewModel = PetFinderViewModelData(
            state = PetFinderViewModelState.error,
            animals = emptyList(),
            detailAnimal = null
        )
        viewModel.liveData.postValue(petFinderViewModel)
    }

}

@Singleton
class ResultPageViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<PetFinderViewModelData> = MutableLiveData()
}