package com.example.mulchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mulchapp.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    //set binding to Main
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            //Value to check each radio button
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            //Value selectedMulchType to bind the buttons to Calculate Cost strings
            val selectedMulchType = when {
                binding.premiumbarkmulch.isChecked -> "Premium Bark Mulch"
                binding.specialblend.isChecked -> "Special Blend"
                binding.tripleground.isChecked -> "Triple Ground"
                binding.chocolatedyed.isChecked -> "Chocolate Dyed"
                binding.reddyed.isChecked -> "Red Dyed"
                binding.blackdyed.isChecked -> "Black Dyed"
                binding.playmat.isChecked -> "Play Mat"
                binding.cedar.isChecked -> "Cedar"
                else -> ""
            }
            if (selectedRadioButtonId != -1) {
                val intent = Intent(this, MulchOrderActivity::class.java)
                intent.putExtra("mulchType", selectedMulchType)
                intent.putExtra(
                    "costPerCubicYard",
                    calculateCost(selectedMulchType)
                )
                startActivity(intent)
            } else {
                //Show a message indicating that a mulch type must be selected
                Toast.makeText(this, "Please select a mulch type", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //calculateCost function is setting cubic yard values to strings
    private fun calculateCost(mulchType: String): Double {
        return when (mulchType) {
            "Premium Bark Mulch" -> 55.0
            "Special Blend" -> 35.0
            "Triple Ground" -> 40.0
            "Chocolate Dyed" -> 38.0
            "Red Dyed" -> 38.0
            "Black Dyed" -> 38.0
            "Play Mat" -> 38.0
            "Cedar" -> 38.0
            else -> 0.0
        }
    }
}