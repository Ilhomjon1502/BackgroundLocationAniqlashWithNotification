package uz.ilhomjon.backgroundlocationaniqlash.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.ilhomjon.backgroundlocationaniqlash.models.MyLocation

object MySharedPreference {
    private const val NAME = "catch_file_name"
    private const val MODE = Context.MODE_PRIVATE

    private lateinit var preferences: SharedPreferences

    fun init(context: Context){
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation:(SharedPreferences.Editor) -> Unit){
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var locationList:ArrayList<MyLocation>
        get() = gsonStringToList(preferences.getString("list", "[]")!!)
        set(value) = preferences.edit {
            it.putString("list", listToJson(value))
        }

    private fun listToJson(list:ArrayList<MyLocation>):String{
        return Gson().toJson(list)
    }

    private fun gsonStringToList(gsonString:String):ArrayList<MyLocation>{
        val type = object : TypeToken<ArrayList<MyLocation>>(){}.type
        return Gson().fromJson(gsonString, type)
    }

}