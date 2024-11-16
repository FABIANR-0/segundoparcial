package com.segundoparcial.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.helpers.DataBaseHelper
import com.github.dhaval2404.imagepicker.ImagePicker
import com.segundoparcial.R
import com.segundoparcial.model.User

class RegisterFragment : Fragment() {

    private lateinit var etName : EditText
    private lateinit var etDescription : EditText
    private lateinit var checkPrimary : CheckBox
    private lateinit var checkSecundary : CheckBox
    private lateinit var checkExtra : CheckBox
    private lateinit var btnLoad : ImageButton
    private lateinit var btnSave : Button
    private lateinit var dataBaseHelper: DataBaseHelper
    private var imageUri : Uri? = null
    private var type : String = ""

    companion object {
        private const val PRIMARY = "primario"
        private const val SECUNDARY = "secundario"
        private const val EXTRA = "extra"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        initComponent(view)

        btnLoad.setOnClickListener {
            selecctionImg()
        }

        btnSave.setOnClickListener {
            saveUser(view)
        }
        return view
    }

    private fun initComponent(view: View) {
        etName = view.findViewById(R.id.etName)
        etDescription = view.findViewById(R.id.etDescrip)
        checkPrimary = view.findViewById(R.id.checkPrimary)
        checkSecundary = view.findViewById(R.id.checkSecundary)
        checkExtra = view.findViewById(R.id.checkExtra)
        btnLoad = view.findViewById(R.id.btnLoad)
        btnSave = view.findViewById(R.id.btnSave)
    }


    private fun selecctionImg(){
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                resultImg.launch(intent)
            }
    }

    private val resultImg = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            resultado ->
        if(resultado.resultCode == Activity.RESULT_OK) {
            val data = resultado.data
            imageUri = data!!.data
            btnLoad.setImageURI(imageUri)
        } else {
            Toast.makeText(context , "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUser (view: View) {
        dataBaseHelper = DataBaseHelper(view.context)
        val name = etName.text.toString()
        val description = etDescription.text.toString()
        val primary = checkPrimary.isChecked
        val secundary = checkSecundary.isChecked
        val extra = checkExtra.isChecked
        val image = imageUri
        if (validation(name, description, primary, secundary, extra, image)) {
            addType(primary, secundary, extra)
            val user =
                User(name = name, description = description, type = type, img = image.toString())

            dataBaseHelper.insertName(user)
            Toast.makeText(context, "Usuario Resgistrado con exito", Toast.LENGTH_SHORT).show()

            etName.text.clear()
            etDescription.text.clear()
            checkPrimary.isChecked = false
            checkSecundary.isChecked = false
            checkExtra.isChecked = false
            btnLoad.setImageResource(R.drawable.select_image)
        }
    }

    private fun addType(primary: Boolean, secundary: Boolean, extra: Boolean) {
        var type1 = ""
        var type2 = ""
        var type3 = ""
        if (primary) {
            type1 = PRIMARY
        }
        if (secundary) {
            type2 = if (type1.isEmpty()) SECUNDARY else "," + SECUNDARY
        }
        if (extra) {
            type3 = if (type1.isNotEmpty() || type2.isNotEmpty())"," + EXTRA else EXTRA
        }
        type = type1 + type2 + type3
    }


    private fun validation(name: String, description: String, primary: Boolean, secundary: Boolean, extra: Boolean, image: Uri?) : Boolean {
        when {
            name.isEmpty() -> {
                Toast.makeText(context , "Ingrese nombre", Toast.LENGTH_SHORT).show()
                return false
            }
            description.isEmpty() -> {
                Toast.makeText(context , "Ingrese una descripcion", Toast.LENGTH_SHORT).show()
                return false
            }
            !primary && !secundary && !extra -> {
                Toast.makeText(context , "Seleccione al menos un tipo", Toast.LENGTH_SHORT).show()
                return false
            }
            image == null -> {
                Toast.makeText(context , "Seleccione una imagen", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> {
                return true
            }
        }
    }
}