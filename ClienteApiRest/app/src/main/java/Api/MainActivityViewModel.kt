package Api


import Modelo.User
import Modelo.Usuario
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val myResponse: MutableLiveData<Usuario> = MutableLiveData()
    val myResponseList: MutableLiveData<List<Usuario>> = MutableLiveData()

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