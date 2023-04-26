package daleread.net.dojookhttpdemo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import android.util.Log
import daleread.net.dojookhttpdemo.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import okhttp3.*
import java.io.IOException
import kotlinx.coroutines.launch as kotlinxCoroutinesLaunch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // display the remote image right away or after a brief delay
    // depending on whether or not the URL is available in onCreate
    // just trying to get used to the Activity lifecycle

    //var imageUrl = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcRnPjw6RNYmW2vSwUNbwOHd7LMXsXu152mSYjlfO4gv0cJe22AYM6eoga0IwL_Tudmx2JnPf3Z9BmZ0kJGYlB2Z05P8rQqjru4bcnsRjmBbM_2_dVLaU5CpAtM&usqp=CAc"
    var imageUrl = ""
    private val okClient by lazy {
        OkHttpClient()
    }
    private val okRequest by lazy {
        Request.Builder()
            .url(imageUrl)
            .build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setImageResource(R.drawable.maria02)
        lifecycleScope.kotlinxCoroutinesLaunch {
            if (imageUrl == "") { changeImageUrl()}
        }
       Log.d("Main", "Hello")
    }


    private suspend fun  changeImageUrl(){
        delay(6000)
        Log.d("Coroutine", "World")
        imageUrl = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcRnPjw6RNYmW2vSwUNbwOHd7LMXsXu152mSYjlfO4gv0cJe22AYM6eoga0IwL_Tudmx2JnPf3Z9BmZ0kJGYlB2Z05P8rQqjru4bcnsRjmBbM_2_dVLaU5CpAtM&usqp=CAc"
        loadImage()
    }

    private fun loadImage(){

        okClient.newCall(okRequest).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException){
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response){
                val inputStream = response.body?.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                this@MainActivity.runOnUiThread{
                    binding.imageView.setImageBitmap(bitmap)
                }
            }
        })
    }   // end loadImage
}