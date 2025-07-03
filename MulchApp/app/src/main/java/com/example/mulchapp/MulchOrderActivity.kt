package com.example.mulchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MulchOrderActivity : AppCompatActivity() {

    //Setting needed variables
    private lateinit var mulchType: String
    private var costPerCubicYard: Double = 0.0

    private lateinit var streetEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var stateEditText: EditText
    private lateinit var zipEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText

    private lateinit var cubicYardsEditText: EditText
    private lateinit var deliveryChargeTextView: TextView
    private lateinit var mulchCostTextView: TextView
    private lateinit var salesTaxTextView: TextView
    private lateinit var totalTextView: TextView

    private lateinit var nextButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mulch_order)

        //Get mulch type and cost from the intent
        mulchType = intent.getStringExtra("mulchType") ?: ""
        costPerCubicYard = intent.getDoubleExtra("costPerCubicYard", 0.0)

        //Bring in the views
        cubicYardsEditText = findViewById(R.id.cubicYardsEditText)
        zipEditText = findViewById(R.id.zipEditText)
        deliveryChargeTextView = findViewById(R.id.deliveryChargeTextView)
        mulchCostTextView = findViewById(R.id.mulchCostTextView)
        salesTaxTextView = findViewById(R.id.salesTaxTextView)
        totalTextView = findViewById(R.id.totalTextView)
        streetEditText = findViewById(R.id.streetEditText)
        cityEditText = findViewById(R.id.cityEditText)
        stateEditText = findViewById(R.id.stateEditText)
        emailEditText = findViewById(R.id.emailEditText)
        phoneEditText = findViewById(R.id.phoneEditText)


        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)

        //Set mulch type and cost in the view
        val mulchTypeSummaryTextView: TextView = findViewById(R.id.mulchTypeSummaryTextView)
        mulchTypeSummaryTextView.text = "$mulchType - $${String.format("%.2f", costPerCubicYard)}/cubic yard"

        //Set up text change listener for cubic yards
        cubicYardsEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateCost()
            }
        })

        //Set up click listener for the next button
        nextButton.setOnClickListener {
            val cubicYards = cubicYardsEditText.text.toString().toDoubleOrNull()
            val zipCode = zipEditText.text.toString()
            val street = streetEditText.text.toString()
            val city = cityEditText.text.toString()
            val state = stateEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()

            if (cubicYards != null && cubicYards > 0) {
                //Check if the ZIP code is valid
                val deliveryCharge = getDeliveryCharge(zipCode)
                if (deliveryCharge > 0) {
                    navigateToOrderSummary(cubicYards, deliveryCharge, street,
                        city,
                        state,
                        zipCode,
                        email,
                        phone)
                } else {
                    showSnackbar("Delivery is not available for the provided ZIP code.")
                    updateCost()
                }
            } else {
                showSnackbar("Please enter a valid number of cubic yards.")
            }
        }
        backButton.setOnClickListener {
            finish()
        }
    }
    private fun updateCost() {
        val cubicYards = cubicYardsEditText.text.toString().toDoubleOrNull() ?: 0.0
        val zipCode = zipEditText.text.toString()

        //Check if the ZIP code is valid
        val deliveryCharge = getDeliveryCharge(zipCode)
        //Calculating cost, sales tax, and total
        if (cubicYards > 0) {
            val mulchCost = cubicYards * costPerCubicYard
            val salesTax = 0.07 * mulchCost

            val totalCost = mulchCost + salesTax + deliveryCharge

            mulchCostTextView.text = "Mulch cost: $${String.format("%.2f", mulchCost)}"
            salesTaxTextView.text = "Sales tax: $${String.format("%.2f", salesTax)}"
            deliveryChargeTextView.text = "Delivery charge: $${String.format("%.2f", deliveryCharge)}"
            totalTextView.text = "Total: $${String.format("%.2f", totalCost)}"
        } else {
            mulchCostTextView.text = "Mulch cost: $0.00"
            salesTaxTextView.text = "Sales tax: $0.00"
            deliveryChargeTextView.text = "Delivery charge: $0.00"
            totalTextView.text = "Total: $0.00"
        }
    }
    private fun getDeliveryCharge(zipCode: String): Double {
        //Setting delivery charges to specific zip codes
        return when (zipCode) {
            "60540" -> 25.0
            "60563" -> 30.0
            "60564" -> 35.0
            "60565" -> 35.0
            "60187" -> 40.0
            "60188" -> 40.0
            "60189" -> 35.0
            "60190" -> 40.0
            else -> 0.0
        }
    }
    //function and intent to push user inputs to OrderSummary
    private fun navigateToOrderSummary(
        cubicYards: Double,
        deliveryCharge: Double,
        street: String,
        city: String,
        state: String,
        zipCode: String,
        email: String,
        phone: String
    ) {
        val intent = Intent(this, OrderSummaryActivity::class.java)
        intent.putExtra("mulchType", mulchType)
        intent.putExtra("costPerCubicYard", costPerCubicYard)
        intent.putExtra("cubicYards", cubicYards)
        intent.putExtra("deliveryCharge", deliveryCharge)
        intent.putExtra("street", street)
        intent.putExtra("city", city)
        intent.putExtra("state", state)
        intent.putExtra("zipCode", zipCode)
        intent.putExtra("email", email)
        intent.putExtra("phone", phone)
        startActivity(intent)
    }
    private fun showSnackbar(message: String) {
        val view: View = findViewById(android.R.id.content)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}


