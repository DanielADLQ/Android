package Api

import Modelo.Pokemon
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
    val myResponse: MutableLiveData<Pokemon> = MutableLiveData()
    val myResponseList: MutableLiveData<List<Pokemon>> = MutableLiveData()

    fun getPost(id:Int) {
        viewModelScope.launch {
            myResponse.value = UserNetwork.retrofit.getUsuario(id)
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            myResponseList.value = UserNetwork.retrofit.getUsuarios()
        }
    }
}