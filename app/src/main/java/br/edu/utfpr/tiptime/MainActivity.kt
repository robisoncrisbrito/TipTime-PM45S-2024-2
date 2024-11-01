package br.edu.utfpr.tiptime

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        if ( savedInstanceState != null ) {
            val tip = savedInstanceState.getString( "tip" )
            binding.tipResult.text = tip
        }

        binding.calculateButton.setOnClickListener {
            calculateButtonOnClick()
        }
    }

    private fun calculateButtonOnClick() {

        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull() ?: return

        val selectedId = binding.tipOption.checkedRadioButtonId

        val tipPercentage = when ( selectedId ) {
            R.id.option_twenty_percent -> 0.2
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = cost * tipPercentage

        val roundUp = binding.roundUpSwitch.isChecked

        if ( roundUp ) {
            tip = kotlin.math.ceil( tip )
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format ( tip )

        binding.tipResult.text = getString( R.string.tip_amount, formattedTip )

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString( "tip", binding.tipResult.text.toString() )
    }
}