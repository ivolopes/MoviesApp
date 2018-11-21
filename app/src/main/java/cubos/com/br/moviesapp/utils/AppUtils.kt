package cubos.com.br.moviesapp.utils

import android.app.AlertDialog
import android.content.Context
import cubos.com.br.moviesapp.R

class AppUtils{

    companion object {
        fun showAlert(message: String, context: Context){
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle(R.string.error)
            alertDialog.setMessage(message)
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getText(R.string.ok),
                    { dialog, _ -> dialog.dismiss() })
            alertDialog.show()
        }
    }

}