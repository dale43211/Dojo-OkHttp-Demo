package daleread.net.dojookhttpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import android.widget.ImageView
import daleread.net.dojookhttpdemo.databinding.ActivityMainBinding
// import kotlinx.android.synthetic.main.activity_main.*

import okhttp3.*
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val imageUrl = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcRnPjw6RNYmW2vSwUNbwOHd7LMXsXu152mSYjlfO4gv0cJe22AYM6eoga0IwL_Tudmx2JnPf3Z9BmZ0kJGYlB2Z05P8rQqjru4bcnsRjmBbM_2_dVLaU5CpAtM&usqp=CAc"
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
                // lateinit var imageView: ImageView
                this@MainActivity.runOnUiThread{
                    binding.imageView.setImageBitmap(bitmap)
                }
            }
        })
    }   // end loadImage
}